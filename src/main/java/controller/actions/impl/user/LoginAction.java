package controller.actions.impl.user;

import config.AppContext;
import controller.actions.Action;
import dto.Converter;
import dto.UserDTO;
import exception.ServiceException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

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
            req.getSession().invalidate();
            req.getSession().setAttribute(USER_ATTRIBUTE, Converter.convertDTOtoUser(userDTO));
            logger.info("Successful authorization.");
        } catch (ValidateException e) {
            logger.error("Authorization is failed: " + e.getMessage());
            req.getSession().setAttribute(USER_EMAIl, email);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(LOGIN_ACTION);
        }

        if (userDTO.getRole().isUser()) {
            path = getGetAction(MAKE_ORDER_ACTION);
        } else {
            path = getGetAction(STATISTICS_ACTION);
        }

        return path;
    }
}
