package controller.actions;

import exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Oleksandr Pavelko
 */
public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, Exception;
}
