package controller.actions.impl.admin;

import controller.AppContext;
import controller.actions.Action;
import database.entity.Order;
import dto.Converter;
import dto.OrderDTO;
import exception.ServiceException;
import exception.ValidateException;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.actions.PageNameConstants.STATISTICS_PAGE;
import static controller.actions.RequestUtils.isPostMethod;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class StatisticsAction implements Action {
    private final OrderService orderService;


    public StatisticsAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) throws ServiceException {
        String orderBy;
        String userName = req.getParameter(USER_NAME);
        String date = req.getParameter(DATE_ATTRIBUTE);
        int nOfPages, currentPage, recordsPerPage;

        if (userName != null && date != null) {
            if (!userName.equals("") && date.equals("")) {
                req.setAttribute(CURRENT_FILTER, USER_NAME);
                req.setAttribute(USER_NAME, userName);
            } else if (userName.equals("") && !date.equals("")) {
                req.setAttribute(CURRENT_FILTER, DATE_ATTRIBUTE);
                req.setAttribute(DATE_ATTRIBUTE, date);
            } else if (!userName.equals("")) {
                req.setAttribute(CURRENT_FILTER, ALL);
                req.setAttribute(USER_NAME, userName);
                req.setAttribute(DATE_ATTRIBUTE, date);
            }
        }

        if (req.getParameter(CURRENT_PAGE) == null || req.getParameter(RECORDS_PER_PAGE) == null) {
            currentPage = 1;
            recordsPerPage = 5;
        } else {
            currentPage = Integer.parseInt(req.getParameter(CURRENT_PAGE));
            recordsPerPage = Integer.parseInt(req.getParameter(RECORDS_PER_PAGE));
        }

        orderBy = req.getParameter(ORDER_BY);

        if (orderBy == null) {
            orderBy = NO_ORDERING;
            req.setAttribute(CURRENT_ORDER, orderBy);
        } else if (orderBy.equals(BY_COST) || orderBy.equals(BY_DATE)) {
            req.setAttribute(CURRENT_ORDER, orderBy);
        }
        int start = currentPage * recordsPerPage - recordsPerPage;

        try {
            List<OrderDTO> orders;
            int rows;
            if (req.getAttribute(CURRENT_FILTER) == null) {
                orders = prepareOrders(orderService.getOrdersNoFilter(start, recordsPerPage));
                rows = orderService.getNumberOfRows();
            } else if (req.getAttribute(CURRENT_FILTER) == USER_NAME) {
                userName = req.getAttribute(USER_NAME).toString();
                switch (orderBy) {
                    case (BY_COST): {
                        orders = prepareOrders(orderService.getOrdersUserFilterOrderedCost(start, recordsPerPage, userName));
                        break;
                    }
                    case (BY_DATE): {
                        orders = prepareOrders(orderService.getOrdersUserFilterOrderedDate(start, recordsPerPage, userName));
                        break;
                    }
                    default: {
                        orders = prepareOrders(orderService.getOrdersUserFilter(start, recordsPerPage, userName));
                        break;
                    }
                }

                rows = orderService.getNumberOfRowsFilterUser(userName);
            } else if (req.getAttribute(CURRENT_FILTER) == DATE_ATTRIBUTE) {
                date = req.getAttribute(DATE_ATTRIBUTE).toString();
                switch (orderBy) {
                    case (BY_COST): {
                        orders = prepareOrders(orderService.getOrdersDateFilterOrderedCost(start, recordsPerPage, date));
                        break;
                    }
                    case (BY_DATE): {
                        orders = prepareOrders(orderService.getOrdersDateFilterOrderedDate(start, recordsPerPage, date));
                        break;
                    }
                    default: {
                        orders = prepareOrders(orderService.getOrdersDateFilter(start, recordsPerPage, date));
                        break;
                    }
                }

                rows = orderService.getNumberOfRowsFilterDate(date);
            } else {
                date = req.getAttribute(DATE_ATTRIBUTE).toString();
                userName = req.getAttribute(USER_NAME).toString();
                switch (orderBy) {
                    case (BY_COST): {
                        orders = prepareOrders(orderService.getOrdersUserAndDateFilterOrderedCost(start, recordsPerPage, userName, date));
                        break;
                    }
                    case (BY_DATE): {
                        orders = prepareOrders(orderService.getOrdersUserAndDateFilterOrderedDate(start, recordsPerPage, userName, date));
                        break;
                    }
                    default: {
                        orders = prepareOrders(orderService.getOrdersUserAndDateFilter(start, recordsPerPage, userName, date));
                        break;
                    }
                }

                rows = orderService.getNumberOfRowsFilterDateUser(date, userName);
            }

            req.setAttribute(ORDERS_ATTRIBUTE, orders);
            nOfPages = rows / recordsPerPage;
            if (nOfPages % recordsPerPage > 0) {
                nOfPages++;
            }

            req.setAttribute(NUMBER_OF_PAGES, nOfPages);
            req.setAttribute(CURRENT_PAGE, currentPage);
            req.setAttribute(RECORDS_PER_PAGE, recordsPerPage);

            req.setAttribute(NUMBER_OF_PAGES, nOfPages);
            req.setAttribute(CURRENT_PAGE, currentPage);
            req.setAttribute(RECORDS_PER_PAGE, recordsPerPage);

            return STATISTICS_PAGE;
        } catch (ValidateException e) {
            throw new ServiceException(e);
        }
    }

    private List<OrderDTO> prepareOrders(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(Converter.convertOrderToDTO(order));
        }
        return orderDTOS;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        return executeGet(req);
    }
}
