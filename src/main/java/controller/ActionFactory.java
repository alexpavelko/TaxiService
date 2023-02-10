package controller;

import controller.actions.Action;
import controller.actions.impl.admin.StatisticsAction;
import controller.actions.impl.order.MakeOrderAction;
import controller.actions.impl.order.OrderConfirmingAction;
import controller.actions.impl.user.LogOutAction;
import controller.actions.impl.user.LoginAction;
import controller.actions.impl.user.MainPageAction;
import controller.actions.impl.user.RegisterAction;

import java.util.HashMap;
import java.util.Map;

import static controller.actions.ActionNameConstants.*;

/**
 * @author Oleksandr Pavelko
 */
public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {
        ACTION_MAP.put(MAIN_PAGE_ACTION, new MainPageAction());
        ACTION_MAP.put(REGISTER_ACTION, new RegisterAction(APP_CONTEXT));
        ACTION_MAP.put(LOGIN_ACTION, new LoginAction(APP_CONTEXT));
        ACTION_MAP.put(LOGOUT_ACTION, new LogOutAction());

        ACTION_MAP.put(ORDER_SUBMIT_ACTION, new OrderConfirmingAction(APP_CONTEXT));
        ACTION_MAP.put(MAKE_ORDER_ACTION, new MakeOrderAction(APP_CONTEXT));

        ACTION_MAP.put(STATISTICS_ACTION, new StatisticsAction(APP_CONTEXT));
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
