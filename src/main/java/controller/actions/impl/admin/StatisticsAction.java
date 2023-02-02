package controller.actions.impl.admin;

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
public class StatisticsAction implements Action {
    private final OrderService orderService;

    public StatisticsAction(AppContext appContext) {
        this.orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return null;
    }
}
