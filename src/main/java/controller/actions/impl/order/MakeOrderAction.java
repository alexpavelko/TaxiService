package controller.actions.impl.order;

import controller.AppContext;
import controller.actions.Action;
import database.connection.MyDataSource;
import dto.OrderDTO;
import dto.UserDTO;
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
import java.util.Enumeration;

import static controller.actions.ActionNameConstants.*;
import static controller.actions.PageNameConstants.ORDER_PAGE;
import static controller.actions.RequestUtils.*;
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
        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_ATTRIBUTE);
        req.setAttribute(LOCATION_ATTRIBUTE, orderService.getAllLocations());
        return ORDER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException, ValidateException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("userDTO");

        OrderDTO orderDTO = getOrderForAttribute(req);
        try {
            orderService.findCar(req, orderDTO);
//            orderService.addOrder(orderDTO);
            req.getSession().setAttribute("MESSAGE", "SUCCESSFUL");

        } catch (ServiceException e) {
            logger.error("Can't add order: " + e);
            req.getSession().setAttribute("userDTO", userDTO);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(MAKE_ORDER_ACTION);
        }  catch (ValidateException e) {
            logger.error("Can't add order: " + e);
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
