package controller;

import controller.actions.Action;
import controller.actions.impl.admin.StatisticsAction;
import controller.actions.impl.order.MakeOrderAction;
import controller.actions.impl.order.OrderPossibilityAction;
import controller.actions.impl.user.LogOutAction;
import controller.actions.impl.user.LoginAction;
import controller.actions.impl.user.MainPageAction;
import controller.actions.impl.user.RegisterAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleksandr Pavelko
 */
public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {
        ACTION_MAP.put("", new MainPageAction(APP_CONTEXT));
        ACTION_MAP.put("register", new RegisterAction(APP_CONTEXT));
        ACTION_MAP.put("login", new LoginAction(APP_CONTEXT));
        ACTION_MAP.put("logout", new LogOutAction(APP_CONTEXT));

        ACTION_MAP.put("orderSubmit", new OrderPossibilityAction(APP_CONTEXT));
        ACTION_MAP.put("makeOrder", new MakeOrderAction(APP_CONTEXT));

        ACTION_MAP.put("statistics", new StatisticsAction(APP_CONTEXT));
    }

    private ActionFactory() {
    }

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action getAction(String actionName) {
        return ACTION_MAP.get(actionName);
    }

}
