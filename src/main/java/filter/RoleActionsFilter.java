package filter;

import database.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.actions.ActionNameConstants.LOGIN_ACTION;
import static controller.actions.RequestUtils.getGetAction;

@WebFilter(filterName = "/controller")
public class RoleActionsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RoleActionsFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");
        String servletPath = req.getServletPath();

        if (user == null) {
            if (ActionAccessConstants.getNoLoggedUserActions().contains(action)
                    || PageAccessConstants.getNoLoggedUserPages().contains(servletPath.substring(1))) {
                filterChain.doFilter(req, resp);
            } else {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + getGetAction(LOGIN_ACTION));
            }
            logger.info("Not authorized user uses " + action + " action.");
        } else {
            switch (user.getRole()) {
                case ADMIN: {
                    if (ActionAccessConstants.getAdminActions().contains(action)
                            || PageAccessConstants.getAdminPages().contains(servletPath.substring(1))) {
                        filterChain.doFilter(req, resp);
                    } else {
                        req.getSession().invalidate();
                        resp.sendRedirect(req.getContextPath() + getGetAction(LOGIN_ACTION));
                    }
                    logger.info("Admin uses " + action + " action.");
                    break;
                }
                case USER: {
                    if (ActionAccessConstants.getLoggedUserActions().contains(action)
                            || PageAccessConstants.getLoggedUserPages().contains(servletPath.substring(1))) {
                        filterChain.doFilter(req, resp);
                    } else {
                        req.getSession().invalidate();
                        resp.sendRedirect(req.getContextPath() + getGetAction(LOGIN_ACTION));
                    }
                    logger.info("Authorized user uses " + action + " action.");
                    break;
                }
            }
        }
    }
}
