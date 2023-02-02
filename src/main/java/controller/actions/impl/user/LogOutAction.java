package controller.actions.impl.user;

import controller.AppContext;
import controller.actions.Action;
import exception.ServiceException;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Oleksandr Pavelko
 */
public class LogOutAction implements Action {
    private OrderService orderService;

    public LogOutAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return null;
    }
}
