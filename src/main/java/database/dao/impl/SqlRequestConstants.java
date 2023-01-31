package database.dao.impl;

/**
 * Holder for SQL requests.
 *
 * @author Oleksandr Pavelko
 */
class SqlRequestConstants {

    /**
     * // SQL requests for Car DAO
     */

    static final String SQL_GET_CAR_BY_ID = "SELECT * FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id WHERE car_id=?";
    static final String SQL_GET_CAR_BY_TYPE = "SELECT * FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id WHERE type_name=? AND cars.status_id =?";
    static final String SQL_GET_ALL_CARS = "SELECT * FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id";
    static final String SQL_GET_CAR_BY_PASSENGERS = "SELECT * FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id WHERE passengers=? AND cars.status_id =?";
    static final String SQL_UPDATE_STATUS = "UPDATE cars SET cars.status_id=? WHERE cars.car_id=?";
    static final String SQL_GET_APPROPRIATE_CARS = "SELECT * FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id WHERE passengers=? AND cars.status_id =? AND type_name=?";
    static final String SQL_GET_APPROPRIATE_CAR_COST = "SELECT cost FROM cars LEFT JOIN status ON cars.status_id=status.status_id LEFT JOIN car_type ON cars.type_id = car_type.type_id WHERE passengers=? AND type_name=?";

    static final String SQL_GET_TWO_CARS_BY_TYPE = "SELECT x.car_id as x_id , y.car_id as y_id FROM cars x JOIN cars y ON y.car_id > x.car_id WHERE x.passengers + y.passengers =? AND x.type_id = ? AND y.type_id = ? AND x.status_id = 1 AND y.status_id = 1 LIMIT 1;";
    static final String SQL_INSERT_CAR = "INSERT INTO cars VALUES (DEFAULT,?,?,?,?,?)";
    static final String SQL_UPDATE_CAR = "UPDATE cars SET car_name=?, cost=?, status_id=?, type_id=?, passengers=? WHERE car_id=?";
    static final String SQL_DELETE_CAR = "DELETE FROM cars WHERE car_id=?";

    /**
     * // SQL order requests
     */

    static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id=?";
    static final String SQL_CREATE_ORDER = "INSERT INTO orders(user_id, car_id, location_from, location_to, order_date, passengers, cost) VALUES(?, ?, ?, ?, ?, ?, ?)";
    static final String SQL_UPDATE_ORDER = "UPDATE orders SET user_id=?, car_id=?, location_from=?, location_to=?, order_date=?, passengers=?, cost=? WHERE id=?";
    static final String SQL_DELETE_ORDER = "DELETE * FROM orders WHERE id=?";
    static final String SQL_GET_ORDERS = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id";
    static final String SQL_ROWS_NUM = "SELECT COUNT(id) FROM orders";

    /**
     * // SQL location requests
     */
    static final String SQL_GET_LOCATIONS = "SELECT * FROM locations";
    static final String SQL_GET_LOCATION_ID = "SELECT location_id FROM locations WHERE location_name=?";

    /**
     * // SQL distance requests
     */
    static final String SQL_GET_DISTANCE = "SELECT distance FROM distances WHERE location_1=? AND location_2=?";

    /**
     * // SQL user requests
     */
    static final String SQL_GET_USER_BY_ID = "SELECT * FROM users LEFT JOIN roles ON users.role_id=roles.role_id WHERE user_id=?";
    static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users LEFT JOIN roles ON users.role_id = roles.role_id WHERE email=?";
    static final String SQL_GET_ALL_USERS = "SELECT * FROM users LEFT JOIN roles ON users.role_id=roles.role_id";
    static final String SQL_ADD_USER = "INSERT INTO users(email,password,user_name,role_id)VALUES(?, ?, ?, (SELECT role_id FROM roles WHERE role_name=? LIMIT 1))";
    static final String SQL_GET_ROLE_AMOUNT = "SELECT COUNT(DISTINCT users.role_id) FROM users";
    static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?)";
    static final String SQL_UPDATE_USER = "UPDATE users SET user_name=?, email=?, password=?, role_id=? WHERE user_id=?";
    static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id=?";

    /**
     * // SQL pagination
     */

    static final String SQL_PAGINATION_ORDERS = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE user_name=? LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? AND user_name=? LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_ROWS = "SELECT COUNT(id) FROM orders LEFT JOIN users u on u.user_id = orders.user_id WHERE user_name=?";
    static final String SQL_PAGINATION_ORDERS_WITH_DATE_ROWS = "SELECT COUNT(id) FROM orders WHERE order_date=?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ROWS = "SELECT COUNT(id) FROM orders LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? AND user_name=?";

    static final String SQL_PAGINATION_ORDERS_ORDERED_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id ORDER BY order_date LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_ORDERED_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE user_name=? ORDER BY order_date LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_DATE_ORDERED_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? ORDER BY order_date LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ORDERED_DATE = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? AND user_name=? ORDER BY order_date LIMIT ?, ?";

    static final String SQL_PAGINATION_ORDERS_ORDERED_COST = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id ORDER BY orders.cost LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_ORDERED_COST = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE user_name=? ORDER BY orders.cost LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_DATE_ORDERED_COST = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? ORDER BY orders.cost LIMIT ?, ?";
    static final String SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ORDERED_COST = "SELECT * FROM orders LEFT JOIN cars c on c.car_id = orders.car_id LEFT JOIN users u on u.user_id = orders.user_id WHERE order_date=? AND user_name=? ORDER BY orders.cost LIMIT ?, ?";

}
