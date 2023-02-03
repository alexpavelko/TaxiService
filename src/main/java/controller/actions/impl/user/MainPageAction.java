package controller.actions.impl.user;

import controller.AppContext;
import controller.actions.Action;
import exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.actions.ActionNameConstants.*;
import static controller.actions.PageNameConstants.MAIN_PAGE;

/**
 * @author Oleksandr Pavelko
 */
public class MainPageAction implements Action {
    public MainPageAction(AppContext appContext) {

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return MAIN_PAGE;
    }
}
