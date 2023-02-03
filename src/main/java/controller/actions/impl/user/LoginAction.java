package controller.actions.impl.user;

import controller.AppContext;
import controller.actions.Action;
import dto.UserDTO;
import exception.ServiceException;
import exception.ValidateException;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.actions.ActionNameConstants.*;
import static controller.actions.PageNameConstants.LOGIN_PAGE;
import static controller.actions.RequestUtils.*;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class LoginAction implements Action {
    private final UserService userService;
    public LoginAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_ATTRIBUTE);
        return LOGIN_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String path;
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDTO userDTO;
        try {
            userDTO = userService.authorize(email, password);
            req.getSession().setAttribute(USER_ATTRIBUTE, userDTO);
            req.getSession().setAttribute(ROLE_ATTRIBUTE, userDTO.getRole());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (ValidateException e) {
            req.getSession().setAttribute(USER_EMAIl, email);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(LOGIN_ACTION);
        }

        if (userDTO.getRole().isUser()) {
            path = "/controller?action=makeOrder";
        } else {
            path = "/controller?action=statistics";
        }

        return path;
    }
}
