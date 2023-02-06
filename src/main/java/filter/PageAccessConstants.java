package filter;

import java.util.HashSet;
import java.util.Set;

import static controller.actions.PageNameConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class PageAccessConstants {
    private static final Set<String> NO_LOGGED_USER_PAGES = new HashSet<>();
    private static final Set<String> LOGGED_USER_PAGES = new HashSet<>();
    private static final Set<String> ADMIN_PAGES = new HashSet<>();

    static {
        NO_LOGGED_USER_PAGES.add(MAIN_PAGE);
        NO_LOGGED_USER_PAGES.add(REGISTER_PAGE);
        NO_LOGGED_USER_PAGES.add(LOGIN_PAGE);
    }

    static {
        LOGGED_USER_PAGES.add(MAIN_PAGE);
        LOGGED_USER_PAGES.add(ORDER_PAGE);
        LOGGED_USER_PAGES.add(ORDER_POSSIBILITY_PAGE);
    }

    static {
        ADMIN_PAGES.add(MAIN_PAGE);
        ADMIN_PAGES.add(LOGIN_PAGE);
        ADMIN_PAGES.add(REGISTER_PAGE);
        ADMIN_PAGES.add(STATISTICS_PAGE);
    }

    public static Set<String> getNoLoggedUserPages() {
        return NO_LOGGED_USER_PAGES;
    }

    public static Set<String> getLoggedUserPages() {
        return LOGGED_USER_PAGES;
    }

    public static Set<String> getAdminPages() {
        return ADMIN_PAGES;
    }
}
