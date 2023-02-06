package database.dao.impl;

import java.math.BigDecimal;

public class FieldsConstants {
    // Car fields
    public static final String CAR_ID = "car_id";
    public static final String CAR_NAME = "car_name";
    public static final String CAR_COST = "cost";
    public static final String CAR_STATUS = "status";
    public static final String CAR_TYPE = "type_name";
    public static final String CAR_PASSENGERS = "passengers";

    // Order fields

    public static final String ORDER_ID = "id";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_CAR_ID = "car_id";
    public static final String ORDER_LOCATION_FROM = "location_from";
    public static final String ORDER_LOCATION_TO = "location_to";
    public static final String ORDER_ORDER_DATE = "order_date";
    public static final String ORDER_PASSENGERS = "passengers";
    public static final String ORDER_COST = "cost";

    public static final String ORDER_CAR_NAME = "car_name";
    public static final String ORDER_USER_NAME = "user_name";

    public final static String LOCATION_ATTRIBUTE = "locationList";
    public final static String PASSENGERS_ATTRIBUTE = "passengers";

    public final static String ERROR_ATTRIBUTE = "error";

    public final static String CHOICE_ATTRIBUTE = "orderChoice";

    public static final String USER_ATTRIBUTE = "user";
    public static final String USER_DTO_ATTRIBUTE = "userDTO";
    public static final String ROLE_ATTRIBUTE = "role";
    public static final String MESSAGE_ATTRIBUTE = "message";

    public static final String ABSENT_CHOICE_ATTRIBUTE = "absentUserChoice";

    public static final String DOUBLE_ORDER_ATTRIBUTE = "doubleOrder";
    public static final String ORDER_ATTRIBUTE = "order";

    public static final BigDecimal PRICE_PER_KILOMETER = new BigDecimal("10.0");

    // User fields
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIl = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE_NAME = "role_name";
}
