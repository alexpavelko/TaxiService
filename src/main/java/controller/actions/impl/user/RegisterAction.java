package controller.actions.impl.user;

import controller.AppContext;
import controller.actions.Action;
import dto.UserDTO;
import exception.ServiceException;
import exception.ValidateException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static controller.actions.ActionNameConstants.REGISTER_ACTION;
import static controller.actions.ActionNameConstants.REGISTER_PAGE;
import static controller.actions.RequestUtils.*;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class RegisterAction implements Action {
    private final UserService userService;

    public RegisterAction(AppContext appContext) {
        this.userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_ATTRIBUTE);
        return REGISTER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        UserDTO userDTO = getUserForAttribute(req);
        try { ///
            checkConfirmPassword(password, repeatPassword);
            userService.addUser(userDTO);
            req.getSession().setAttribute("MESSAGE", "SUCCESSFUL");
            HttpSession session = req.getSession(true);
            session.setAttribute(USER_ATTRIBUTE, userDTO);
            int ONE_DAY = 86400;
            session.setMaxInactiveInterval(ONE_DAY);
        } catch (ValidateException e) {
            req.getSession().setAttribute(USER_ATTRIBUTE, userDTO);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
        }

        return getGetAction(REGISTER_ACTION);
    }

    private UserDTO getUserForAttribute(HttpServletRequest req) {
        String email = req.getParameter("email").toLowerCase().trim();
        String password = req.getParameter("password");
        String name = req.getParameter("username");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0);
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPassword(password);

        return userDTO;
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException("wrongPasswordRepeat");
        }
    }
}
