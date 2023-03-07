package controller.actions.impl.user;

import controller.actions.Action;
import database.connection.MyDataSource;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.actions.RequestUtils.getGetAction;
import static database.dao.impl.FieldsConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class LogOutAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        if (req.getSession().getAttribute(USER_ATTRIBUTE) != null) {
            req.getSession().invalidate();
            logger.info("User was invalidated.");
        }
        return getGetAction("");
    }
}
