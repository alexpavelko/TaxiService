package filter;

import java.util.HashSet;
import java.util.Set;

import static controller.actions.ActionNameConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class ActionAccessConstants {
    private static final Set<String> NO_LOGGED_USER_ACTIONS = new HashSet<>();
    private static final Set<String> LOGGED_USER_ACTIONS = new HashSet<>();
    private static final Set<String> ADMIN_ACTIONS = new HashSet<>();

    static {
        NO_LOGGED_USER_ACTIONS.add(MAIN_PAGE_ACTION);
        NO_LOGGED_USER_ACTIONS.add(REGISTER_ACTION);
        NO_LOGGED_USER_ACTIONS.add(LOGIN_ACTION);
    }

    static {
        LOGGED_USER_ACTIONS.add(MAIN_PAGE_ACTION);
        LOGGED_USER_ACTIONS.add(MAKE_ORDER_ACTION);
        LOGGED_USER_ACTIONS.add(ORDER_SUBMIT_ACTION);
        LOGGED_USER_ACTIONS.add(LOGOUT_ACTION);
    }

    static {
        ADMIN_ACTIONS.add(MAKE_ORDER_ACTION);
        ADMIN_ACTIONS.add(LOGIN_ACTION);
        ADMIN_ACTIONS.add(LOGOUT_ACTION);
        ADMIN_ACTIONS.add(REGISTER_ACTION);
        ADMIN_ACTIONS.add(STATISTICS_ACTION);
    }

    public static Set<String> getNoLoggedUserActions() {
        return NO_LOGGED_USER_ACTIONS;
    }

    public static Set<String> getLoggedUserActions() {
        return LOGGED_USER_ACTIONS;
    }

    public static Set<String> getAdminActions() {
        return ADMIN_ACTIONS;
    }
}
