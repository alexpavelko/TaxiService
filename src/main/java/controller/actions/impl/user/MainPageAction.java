package controller.actions.impl.user;

import controller.actions.Action;
import exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import static controller.actions.PageNameConstants.MAIN_PAGE;
import static controller.actions.RequestUtils.transferAttributeFromSessionToRequest;
import static database.dao.impl.FieldsConstants.ERROR_ATTRIBUTE;
import static database.dao.impl.FieldsConstants.MESSAGE_ATTRIBUTE;

/**
 * @author Oleksandr Pavelko
 */
public class MainPageAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return MAIN_PAGE;
    }
}
