package controller.actions.impl.admin;

import controller.AppContext;
import controller.actions.Action;
import database.connection.MyDataSource;
import database.entity.Order;
import database.entity.User;
import dto.UserDTO;
import exception.ServiceException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static controller.actions.PageNameConstants.LOGIN_PAGE;
import static controller.actions.PageNameConstants.STATISTICS_PAGE;
import static controller.actions.RequestUtils.getGetAction;
import static controller.actions.RequestUtils.isPostMethod;
import static database.dao.impl.FieldsConstants.ROLE_ATTRIBUTE;

/**
 * @author Oleksandr Pavelko
 */
public class StatisticsAction implements Action {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);

    public StatisticsAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String role = (String) req.getSession().getAttribute(ROLE_ATTRIBUTE);
        if(Objects.equals(role, "USER")) {
            return isPostMethod(req) ? executePost(req) : executeGet(req);
        } else {
            return LOGIN_PAGE;
        }

    }

    private String executeGet(HttpServletRequest req) {
        int currentPage;
        int recordsPerPage;

        String userName = req.getParameter("userName");
        String date = req.getParameter("date");

        String order = req.getParameter("orderBy");

        //set fields and parameters if exist
        if (order != null) {
            if (order.equals("noOrder")) {
                req.setAttribute("currOrder", order);
            } else if (order.equals("byDate")) {
                req.setAttribute("currOrder", order);
            } else if (order.equals("byCost")) {
                req.setAttribute("currOrder", order);
            }
        }

        if (userName != null && date != null) {

            if (!userName.equals("") && date.equals("")) {
                req.setAttribute("currFilter", "user");
                req.setAttribute("userField", userName);
            } else if (userName.equals("") && !date.equals("")) {
                req.setAttribute("currFilter", "date");
                req.setAttribute("dateField", date);
            } else if (!userName.equals("") && !date.equals("")) {
                req.setAttribute("currFilter", "all");
                req.setAttribute("userField", userName);
                req.setAttribute("dateField", date);
            }
        }

        //check and set current page
        if (req.getParameter("currentPage") == null || req.getParameter("recordsPerPage") == null) {
            currentPage = 1;
            recordsPerPage = 3;
        } else {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
            recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
        }

        List<Order> orders;
        int rows;

        //get list according to filters and sorting
        if (req.getAttribute("currFilter") == null) {

            if(order != null) {
                switch (order) {
                    case ("byCost"): {
                        orders = orderService.getOrdersNoFilterOrderedCost(currentPage * recordsPerPage - recordsPerPage,
                                recordsPerPage);
                        break;
                    }
                    case ("byDate"): {
                        orders = orderService.getOrdersNoFilterOrderedDate(currentPage * recordsPerPage - recordsPerPage,
                                recordsPerPage);
                        break;
                    }
                    default: {
                        orders = orderService.getOrdersNoFilter(currentPage * recordsPerPage - recordsPerPage,
                                recordsPerPage);
                        break;
                    }
                }
            }else{
                orders = orderService.getOrdersNoFilter(currentPage * recordsPerPage - recordsPerPage,
                        recordsPerPage);
            }

            rows = orderService.getNumberOfRows();
        } else if (req.getAttribute("currFilter") == "user") {

            switch (order) {
                case ("byCost"): {
                    orders = orderService.getOrdersUserFilterOrderedCost(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString());
                    break;
                }
                case ("byDate"): {
                    orders = orderService.getOrdersUserFilterOrderedDate(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString());
                    break;
                }
                default: {
                    orders = orderService.getOrdersUserFilter(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString());
                    break;
                }
            }

            rows = orderService.getNumberOfRowsFilterUser(req.getAttribute("userField").toString());
        } else if (req.getAttribute("currFilter") == "date") {

            switch (order) {
                case ("byCost"): {
                    orders = orderService.getOrdersDateFilterOrderedCost(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("dateField").toString());
                    break;
                }
                case ("byDate"): {
                    orders = orderService.getOrdersDateFilterOrderedDate(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("dateField").toString());
                    break;
                }
                default: {
                    orders = orderService.getOrdersDateFilter(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("dateField").toString());
                    break;
                }
            }

            rows = orderService.getNumberOfRowsFilterDate(req.getAttribute("dateField").toString());
        } else {

            switch (order) {
                case ("byCost"): {
                    orders = orderService.getOrdersUserAndDateFilterOrderedCost(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString(), req.getAttribute("dateField").toString());
                    break;
                }
                case ("byDate"): {
                    orders = orderService.getOrdersUserAndDateFilterOrderedDate(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString(), req.getAttribute("dateField").toString());
                    break;
                }
                default: {
                    orders = orderService.getOrdersUserAndDateFilter(currentPage * recordsPerPage - recordsPerPage,
                            recordsPerPage, req.getAttribute("userField").toString(), req.getAttribute("dateField").toString());
                    break;
                }
            }

            rows = orderService.getNumberOfRowsFilterDateUser(req.getAttribute("dateField").toString(), req.getAttribute("userField").toString());
        }

        req.setAttribute("statsList", orders);

        //number of pages
        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {

            nOfPages++;
        }

        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);

        return STATISTICS_PAGE;
    }

    private String executePost(HttpServletRequest req) {
        return executeGet(req);
    }
}
