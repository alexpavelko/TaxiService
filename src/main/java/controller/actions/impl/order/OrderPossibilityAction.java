package controller.actions.impl.order;

import controller.AppContext;
import controller.actions.Action;
import database.connection.MyDataSource;
import database.entity.User;
import dto.DoubleOrderDTO;
import dto.OrderDTO;
import exception.DAOException;
import exception.ServiceException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CarService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;

import static controller.actions.ActionNameConstants.MAIN_PAGE_ACTION;
import static controller.actions.PageNameConstants.MAIN_PAGE;
import static controller.actions.PageNameConstants.ORDER_POSSIBILITY_PAGE;
import static controller.actions.RequestUtils.*;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class OrderPossibilityAction implements Action {
    private final OrderService orderService;
    private final CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);

    public OrderPossibilityAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
        this.carService = appContext.getCarService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, ValidateException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
//        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_ATTRIBUTE);
        return ORDER_POSSIBILITY_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException, ValidateException {
        HttpSession session = req.getSession();
        //depending on button continue
        if (req.getParameter("submit") != null) {
            OrderDTO order = (OrderDTO) session.getAttribute(CHOICE_ATTRIBUTE);

            if (order != null) {
                //add order depending on its type
                if (session.getAttribute(ABSENT_CHOICE_ATTRIBUTE) == null) {
                    orderService.addOrder(order);
                } else {
                    order.setCost(order.getCostWithDiscount());
                    orderService.addOrder(order);
                }
                try {
                    carService.updateStatus(order.getCarId(), 2);
                } catch (DAOException e) {
                    logger.error(e.getMessage());
                    throw new ValidateException(e);
                }

            } else {
                //add two orders if it`s a double order
                DoubleOrderDTO dOrder = (DoubleOrderDTO) session.getAttribute(DOUBLE_ORDER_ATTRIBUTE);

                OrderDTO orderDTO1 = dOrder.getOrder1();
                OrderDTO orderDTO2 = dOrder.getOrder2();

                BigDecimal costOneCar = dOrder.getCostWithDiscount().divide(BigDecimal.valueOf(2));

                orderDTO1.setCost(costOneCar);
                try {
                    orderService.addOrder(orderDTO1);
                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                    throw new ValidateException(e);
                }

                orderDTO2.setPassengers(orderDTO1.getPassengers());
                orderDTO2.setCost(costOneCar);

                try {
                    orderService.addOrder(orderDTO2);
                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                    throw new ValidateException(e);
                }

                try {
                    carService.updateStatus(orderDTO1.getCarId(), 2);
                    carService.updateStatus(orderDTO2.getCarId(), 2);
                } catch (DAOException e) {
                    throw new ValidateException(e);
                }

            }

            req.setAttribute("wait", "waitForCar");
            session.removeAttribute("orderDistance");
            return executeGet(req);

        } else if (req.getParameter("cancel") != null) {
            session.removeAttribute(CHOICE_ATTRIBUTE);
            session.removeAttribute("orderDistance");
//           User user = (User) session.getAttribute(USER_ATTRIBUTE);
//           String role = (String) session.getAttribute(ROLE_ATTRIBUTE);
//            session.invalidate();
//            session.setAttribute(USER_ATTRIBUTE, user);
//            session.setAttribute(ROLE_ATTRIBUTE, role);

            return getGetAction(MAIN_PAGE_ACTION);
        } else if (req.getParameter("ok") != null) {
            //send to main page if messages is shown
            return getGetAction(MAIN_PAGE_ACTION);
        }
        return MAIN_PAGE;
    }
}
