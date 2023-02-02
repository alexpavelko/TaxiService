package controller.actions;

import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

import static controller.actions.ActionNameConstants.CONTROLLER;

/**
 * @author Oleksandr Pavelko
 */
public class RequestUtils {
    public static String getGetAction(String action, String... parameters) {
        String base = CONTROLLER + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    public static void transferAttributeFromSessionToRequest(HttpServletRequest req, String... attributeNames) {
        for (String attributeName : attributeNames) {
            Object obj = req.getSession().getAttribute(attributeName);
            req.setAttribute(attributeName, obj);
            req.getSession().removeAttribute(attributeName);
        }
    }
}
