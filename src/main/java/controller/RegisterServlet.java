package controller;

import database.dao.impl.MySqlUserDao;
import database.entity.User;
import utils.RequestUtils;
import utils.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    private static final String USER_ATTRIBUTE = "user";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String PATH = "/WEB-INF/pages/register.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if already logged in send to main page
        if (RequestUtils.getSessionAttribute(req, USER_ATTRIBUTE, User.class) != null) {
            resp.sendRedirect("/makeOrder");
        } else {
            req.getRequestDispatcher(PATH).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if already logged in send to main page
        if (RequestUtils.getSessionAttribute(req, USER_ATTRIBUTE, User.class) != null) {

            resp.sendRedirect("/");
            return;
        }

        MySqlUserDao userDao = MySqlUserDao.getInstance();

        //get parameters for validation
        String email = req.getParameter("email").toLowerCase().trim();
        String password = req.getParameter("password");
        String name = req.getParameter("username");

        Map<String, String> viewAttributes = new HashMap<>();
        viewAttributes.put("email", email);
        viewAttributes.put("username", name);

        //validating parameters
        if (!Security.isPasswordValid(password)) {
            viewAttributes.put(ERROR_ATTRIBUTE, "passwordNotValid");
            passErrorToView(req, resp, viewAttributes);
            return;
        }
        if (!Security.isEmailValid(email)) {
            viewAttributes.put(ERROR_ATTRIBUTE, "emailNotValid");
            passErrorToView(req, resp, viewAttributes);
            return;
        }
        if (name == null || name.isBlank()) {
            viewAttributes.put(ERROR_ATTRIBUTE, "usernameNotValid");
            passErrorToView(req, resp, viewAttributes);
            return;
        }

        //does this user already exist
        if (userDao.getByEmail(email) != null) {
            viewAttributes.put(ERROR_ATTRIBUTE, "emailExists");
            passErrorToView(req, resp, viewAttributes);
            return;
        }

        //trying to add user
        try {
            User userToAdd = new User(name, email, User.Role.USER, Security.hashPassword(password));
            userDao.add(userToAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userDao.getByEmail(email);

        HttpSession session = req.getSession(true);
        session.setAttribute(USER_ATTRIBUTE, user);
        int ONE_DAY = 86400;
        session.setMaxInactiveInterval(ONE_DAY);
        resp.sendRedirect("/");

    }

    private void passErrorToView(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.setAttribute(entry.getKey(), entry.getValue());
        request.getRequestDispatcher(PATH).forward(request, response);
    }
}
