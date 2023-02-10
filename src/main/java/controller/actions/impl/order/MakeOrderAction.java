package controller.actions.impl.order;

import controller.AppContext;
import controller.actions.Action;
import database.connection.MyDataSource;
import database.entity.User;
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
import java.io.IOException;

import static controller.actions.ActionNameConstants.MAKE_ORDER_ACTION;
import static controller.actions.ActionNameConstants.ORDER_SUBMIT_ACTION;
import static controller.actions.PageNameConstants.ORDER_PAGE;
import static controller.actions.RequestUtils.*;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class MakeOrderAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(MakeOrderAction.class);
    private final OrderService orderService;


    public MakeOrderAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, ValidateException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) throws ServiceException, ValidateException {
        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, LOCATION_ATTRIBUTE, PASSENGERS_ATTRIBUTE);
        try {
            req.setAttribute(LOCATION_ATTRIBUTE, orderService.getAllLocations());
            return ORDER_PAGE;
        } catch (ServiceException e) {
            throw new ValidateException(e);
        }

    }

    private String executePost(HttpServletRequest req) throws ServiceException, ValidateException {
        OrderDTO orderDTO = getOrderForAttribute(req);
        try {
            orderService.findCar(req, orderDTO);
            req.getSession().setAttribute(MESSAGE_ATTRIBUTE, "SUCCESSFUL");
            logger.info("Car(-s) for order was founded.");
        } catch (ValidateException e) {
            logger.error("Can't find car: " + e);
            req.getSession().setAttribute(PASSENGERS_ATTRIBUTE, orderDTO.getPassengers());
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(MAKE_ORDER_ACTION);
        }

        return getGetAction(ORDER_SUBMIT_ACTION);
    }

    private OrderDTO getOrderForAttribute(HttpServletRequest req) {
        String loc_from = req.getParameter("loc_from");
        String loc_to = req.getParameter("loc_to");
        String passengers = req.getParameter("passengers");
        String carClass = req.getParameter("class");

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(0);
        orderDTO.setLocationFrom(loc_from);
        orderDTO.setLocationTo(loc_to);

        try {
            orderDTO.setPassengers(Integer.parseInt(passengers));
        } catch (NumberFormatException e) {
            orderDTO.setPassengers(-1);
        }

        orderDTO.setCarClass(carClass);

        return orderDTO;
    }

}
