package controller.actions.impl.order;

import config.AppContext;
import controller.actions.Action;
import dto.DoubleOrderDTO;
import dto.OrderDTO;
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

import static controller.actions.ActionNameConstants.MAIN_PAGE_ACTION;
import static controller.actions.ActionNameConstants.ORDER_SUBMIT_ACTION;
import static controller.actions.PageNameConstants.MAIN_PAGE;
import static controller.actions.PageNameConstants.ORDER_POSSIBILITY_PAGE;
import static controller.actions.RequestUtils.getGetAction;
import static controller.actions.RequestUtils.isPostMethod;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class OrderConfirmingAction implements Action {
    private final OrderService orderService;
    private final CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmingAction.class);

    public OrderConfirmingAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
        this.carService = appContext.getCarService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, ValidateException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        return ORDER_POSSIBILITY_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException, ValidateException {
        //depending on button continue
        if (req.getParameter("submit") != null) {
            OrderDTO order = (OrderDTO) req.getSession().getAttribute(CHOICE_ATTRIBUTE);
            if (order != null) {
                String userChoice = (String) req.getSession().getAttribute(ABSENT_CHOICE_ATTRIBUTE);
                addOderByType(userChoice, order);
                req.getSession().removeAttribute(DOUBLE_ORDER_ATTRIBUTE);
            } else {
                addDoubleOrder(req);
            }
            req.getSession().setAttribute("wait", "waitForCar");

            return getGetAction(ORDER_SUBMIT_ACTION);
        } else if (req.getParameter("cancel") != null) {
            clearOrderDetails(req.getSession());
            return getGetAction(MAIN_PAGE_ACTION);
        } else if (req.getParameter("ok") != null) {
            clearOrderDetails(req.getSession());
            return getGetAction(MAIN_PAGE_ACTION);
        }
        return MAIN_PAGE;
    }

    private void addDoubleOrder(HttpServletRequest req) throws ServiceException, ValidateException {
        DoubleOrderDTO dOrder = (DoubleOrderDTO) req.getSession().getAttribute(DOUBLE_ORDER_ATTRIBUTE);
        OrderDTO orderDTO1 = dOrder.getOrder1();
        OrderDTO orderDTO2 = dOrder.getOrder2();

        BigDecimal costOneCar = dOrder.getCostWithDiscount().divide(BigDecimal.valueOf(2));
        orderDTO1.setCost(costOneCar);

        orderDTO2.setPassengers(orderDTO1.getPassengers());
        orderDTO2.setCost(costOneCar);

        try {
            orderService.addOrder(orderDTO1);
            orderService.addOrder(orderDTO2);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ValidateException(e);
        }

        req.getSession().removeAttribute(CHOICE_ATTRIBUTE);
    }

    private void addOderByType(String userChoice, OrderDTO order) throws ServiceException, ValidateException {
        if (userChoice == null) {
            orderService.addOrder(order);
        } else {
            order.setCost(order.getCostWithDiscount());
            orderService.addOrder(order);
        }
    }

    public static void clearOrderDetails(HttpSession session) {
        session.removeAttribute("orderDistance");
        session.removeAttribute(ABSENT_CHOICE_ATTRIBUTE);
        session.removeAttribute(CHOICE_ATTRIBUTE);
        session.removeAttribute(DOUBLE_ORDER_ATTRIBUTE);
        session.removeAttribute("wait");
    }
}
