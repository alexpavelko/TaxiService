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
import static controller.actions.RequestUtils.getGetAction;
import static controller.actions.RequestUtils.isPostMethod;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class MakeOrderAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    private final OrderService orderService;
    private final CarService carService;

    public MakeOrderAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
        this.carService = appContext.getCarService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, ValidateException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
//        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_DTO_ATTRIBUTE);
        req.setAttribute(LOCATION_ATTRIBUTE, orderService.getAllLocations());
        return ORDER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException, ValidateException {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        OrderDTO orderDTO = getOrderForAttribute(req);
        try {
            orderService.findCar(req, orderDTO);

            req.getSession().setAttribute("MESSAGE", "SUCCESSFUL");
        } catch (ServiceException e) {
            logger.error("Can't find car: " + e);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(MAKE_ORDER_ACTION);
        }  catch (ValidateException e) {
            logger.error("Can't find car: " + e);
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
        orderDTO.setPassengers(Integer.parseInt(passengers));
        orderDTO.setCarClass(carClass);

        return orderDTO;
    }

}
