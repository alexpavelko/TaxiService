package service.impl;

import database.dao.CarDAO;
import database.dao.OrderDAO;
import database.entity.Car;
import database.entity.Order;
import database.entity.User;
import dto.Converter;
import dto.DoubleOrderDTO;
import dto.OrderDTO;
import exception.DAOException;
import exception.ServiceException;
import exception.ValidateException;
import service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static controller.actions.RequestUtils.getGetAction;
import static database.dao.impl.FieldsConstants.*;
import static utils.validator.Validator.validateOrderLocations;
import static utils.validator.Validator.validatePassengers;

public class OrderServiceImpl implements OrderService {

    private final static double DISCOUNT = 10;
    private final OrderDAO orderDAO;
    private final CarDAO carDAO;

    public OrderServiceImpl(OrderDAO orderDAO, CarDAO carDAO) {
        this.orderDAO = orderDAO;
        this.carDAO = carDAO;
    }

    public String findCar(HttpServletRequest req, OrderDTO orderDTO) throws ServiceException, ValidateException {
        validatePassengers(orderDTO.getPassengers());
        validateOrderLocations(orderDTO.getLocationTo(), orderDTO.getLocationFrom());
        try {
            HttpSession session = req.getSession(true);
            Car car = carDAO.findAppropriateCar(orderDTO.getCarClass(), orderDTO.getPassengers());
            Integer orderDistance = getDistance(orderDTO.getLocationFrom(), orderDTO.getLocationTo());
            session.setAttribute("orderDistance", orderDistance);
            if (car == null) { //finding appropriate cars
                return findTwoCars(session, orderDTO);
            } else { //if we have appropriate car
                return findOneCar(req, orderDTO, car);
            }
        } catch (DAOException e) {
            throw new ValidateException(e);
        }
    }

    @Override
    public void addOrder(OrderDTO orderDTO) throws ServiceException, ValidateException {
        try {
            orderDAO.add(Converter.convertDTOtoOrder(orderDTO));
            carDAO.updateStatus(orderDTO.getCarId(), 2);
        } catch (DAOException e) {
            throw new ValidateException(e);
        }
    }

    public String findTwoCars(HttpSession session, OrderDTO orderDTO) throws ServiceException, ValidateException, DAOException {
        List<Car> carsByType = carDAO.findTwoCarsByType(orderDTO.getCarClass(), orderDTO.getPassengers());

        //finding two cars of the needed type
        if (carsByType.size() == 2) {

            BigDecimal idealCost = carDAO.findAppropriateCarCost(orderDTO.getCarClass(), orderDTO.getPassengers());

            User user = (User) session.getAttribute("user");
            Date date = new Date();

            BigDecimal cost = costForTwoCars(carsByType, orderDTO.getLocationFrom(), orderDTO.getLocationTo());
            BigDecimal costWithDiscount = costWithDiscountForTwoCars(idealCost, carsByType, orderDTO.getLocationFrom(), orderDTO.getLocationTo());

            session.setAttribute(ABSENT_CHOICE_ATTRIBUTE, "noNeeded");

            DoubleOrderDTO doubleOrder = new DoubleOrderDTO();

            Car car1 = carsByType.get(0);
            Car car2 = carsByType.get(1);

            doubleOrder.setOrder1(new OrderDTO(0,
                    car1.getName(),
                    user.getId(),
                    car1.getId(),
                    date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    orderDTO.getLocationTo(),
                    orderDTO.getLocationFrom(),
                    orderDTO.getPassengers(),
                    cost,
                    car1.getCategory().toString()));

            doubleOrder.setOrder2(new OrderDTO(0,
                    car2.getName(),
                    user.getId(),
                    car2.getId(),
                    date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    orderDTO.getLocationTo(),
                    orderDTO.getLocationFrom(),
                    orderDTO.getPassengers(),
                    cost,
                    car2.getCategory().toString()));


            doubleOrder.setFullCost(cost);
            doubleOrder.setCostWithDiscount(costWithDiscount);

            session.setAttribute(DOUBLE_ORDER_ATTRIBUTE, doubleOrder);

        } else {
            Car carByPass = carDAO.findCarByPassengers(orderDTO.getPassengers());

            //finding car by passengers amount
            if (carByPass != null) {
                BigDecimal idealCost = carDAO.findAppropriateCarCost(orderDTO.getCarClass(), orderDTO.getPassengers());

                User user = (User) session.getAttribute(USER_ATTRIBUTE);
                Date date = new Date();

                BigDecimal cost = cost(carByPass.getCost(), orderDTO.getLocationFrom(), orderDTO.getLocationTo());
                BigDecimal costWithDiscount = costWithDiscount(idealCost, carByPass.getCost(), orderDTO.getLocationFrom(), orderDTO.getLocationTo());

                session.setAttribute(ABSENT_CHOICE_ATTRIBUTE, "noNeeded");

                OrderDTO order = new OrderDTO(0,
                        carByPass.getName(),
                        user.getId(),
                        carByPass.getId(),
                        date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        orderDTO.getLocationTo(),
                        orderDTO.getLocationFrom(),
                        orderDTO.getPassengers(),
                        cost,
                        carByPass.getCategory().toString());

                order.setCostWithDiscount(costWithDiscount);

                session.setAttribute(CHOICE_ATTRIBUTE, order);
            }
        }

        return getGetAction("/orderSubmit");
    }

    public String findOneCar(HttpServletRequest req, OrderDTO orderDTO, Car car) throws ServiceException, ValidateException {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);

        Date date = new Date();

        BigDecimal cost = cost(car.getCost(), orderDTO.getLocationFrom(), orderDTO.getLocationTo());

        req.getSession().setAttribute(CHOICE_ATTRIBUTE, new OrderDTO(0,
                car.getName(),
                user.getId(),
                car.getId(),
                date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),
                orderDTO.getLocationTo(),
                orderDTO.getLocationFrom(),
                orderDTO.getPassengers(),
                cost,
                car.getCategory().toString()));

        return getGetAction("/orderSubmit");
    }

    /**
     * Counting the cost depending on car cost and distance price
     */
    public BigDecimal cost(BigDecimal costPerK, String loc_from, String loc_to) throws ServiceException {
        try {
            BigDecimal dist = BigDecimal.valueOf(orderDAO.getDistance(loc_from, loc_to));
            return costPerK.add(dist.multiply(PRICE_PER_KILOMETER));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Counting the cost depending on car cost and locations with discount
     */
    public BigDecimal costWithDiscount(BigDecimal idealCost, BigDecimal costPerK, String loc_from, String loc_to) throws ServiceException {
        double cost = cost(costPerK, loc_from, loc_to).doubleValue();
        double discountVal = cost / 100 * DISCOUNT;

        return BigDecimal.valueOf(cost - discountVal);
    }

    /**
     * Counting the cost for double order depending on cars cost and locations
     */
    public BigDecimal costForTwoCars(List<Car> cars, String loc_from, String loc_to) throws ServiceException {
        BigDecimal costPerK = cars.get(0).getCost();
        costPerK = costPerK.add(cars.get(1).getCost());

        return cost(costPerK, loc_from, loc_to);
    }

    /**
     * Counting the cost for double order depending on cars cost and locations with discount
     */
    public BigDecimal costWithDiscountForTwoCars(BigDecimal idealCost, List<Car> cars, String loc_from, String loc_to) throws ServiceException {
        BigDecimal costPerK = cars.get(0).getCost();
        costPerK = costPerK.add(cars.get(1).getCost());

        return costWithDiscount(idealCost, costPerK, loc_from, loc_to);
    }

    @Override
    public List<String> getAllLocations() throws ServiceException {
        try {
            return orderDAO.getAllLocations();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer getLocIdByName(String location) throws ServiceException {
        try {
            return orderDAO.getLocIdByName(location);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getDistance(String loc_from, String loc_to) throws ServiceException {
        try {
            return orderDAO.getDistance(loc_from, loc_to);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRows() throws ServiceException {
        try {
            return orderDAO.getNumberOfRows();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRowsFilterUser(String userName) throws ServiceException {
        try {
            return orderDAO.getNumberOfRowsFilterUser(userName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRowsFilterDate(String date) throws ServiceException {
        try {
            return orderDAO.getNumberOfRowsFilterDate(date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRowsFilterDateUser(String date, String userName) throws ServiceException {
        try {
            return orderDAO.getNumberOfRowsFilterDateUser(date, userName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersNoFilter(int start, int recordsPerPage) throws ServiceException {
        try {
            return orderDAO.getOrdersNoFilter(start, recordsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage) throws ServiceException {
        try {
            return orderDAO.getOrdersNoFilterOrderedDate(start, recordsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage) throws ServiceException {
        try {
            return orderDAO.getOrdersNoFilterOrderedCost(start, recordsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName) throws ServiceException {
        try {
            return orderDAO.getOrdersUserFilter(start, recordsPerPage, userName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName) throws ServiceException {
        try {
            return orderDAO.getOrdersUserFilterOrderedDate(start, recordsPerPage, userName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName) throws ServiceException {
        try {
            return orderDAO.getOrdersUserFilterOrderedCost(start, recordsPerPage, userName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersDateFilter(start, recordsPerPage, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersDateFilterOrderedDate(start, recordsPerPage, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersDateFilterOrderedCost(start, recordsPerPage, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersUserAndDateFilter(start, recordsPerPage, userName, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersUserAndDateFilterOrderedDate(start, recordsPerPage, userName, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date) throws ServiceException {
        try {
            return orderDAO.getOrdersUserAndDateFilterOrderedCost(start, recordsPerPage, userName, date);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
