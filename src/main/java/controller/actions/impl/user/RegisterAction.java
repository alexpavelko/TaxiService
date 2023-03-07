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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static controller.actions.ActionNameConstants.LOGIN_ACTION;
import static controller.actions.ActionNameConstants.REGISTER_ACTION;
import static controller.actions.PageNameConstants.REGISTER_PAGE;
import static controller.actions.RequestUtils.*;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class RegisterAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(RegisterAction.class);
    private final UserService userService;

    public RegisterAction(AppContext appContext) {
        this.userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, Exception {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR_ATTRIBUTE, MESSAGE_ATTRIBUTE, USER_ATTRIBUTE);
        return REGISTER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException, Exception {
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        UserDTO userDTO = getUserForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            userService.addUser(userDTO);
            req.getSession().setAttribute("MESSAGE", "SUCCESSFUL");
            HttpSession session = req.getSession(true);
            session.setAttribute(USER_ATTRIBUTE, Converter.convertDTOtoUser(userDTO));
            int ONE_DAY = 86400;
            session.setMaxInactiveInterval(ONE_DAY);
            logger.info("User have been successfully registered.");
        } catch (ServiceException e) {
            logger.error("Can't add user: " + e);
            req.getSession().setAttribute(USER_DTO_ATTRIBUTE, userDTO);
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(REGISTER_ACTION);
        } catch (ValidateException e) {
            logger.error("Can't add user: " + e);
            req.getSession().setAttribute(USER_EMAIl, userDTO.getEmail());
            req.getSession().setAttribute(USER_NAME, userDTO.getName());
            req.getSession().setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return getGetAction(REGISTER_ACTION);
        }

        return getGetAction(LOGIN_ACTION);
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
