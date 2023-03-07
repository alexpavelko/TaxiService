package database.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class MyDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    private static HikariDataSource ds;

    private MyDataSource() {
    }

    public static DataSource getDataSource(Properties properties) {
        if (ds == null) {
            HikariConfig config = getHikariConfig(properties);
            ds = new HikariDataSource(config);
        }
        return ds;
    }

    public static HikariConfig getHikariConfig(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty(ConnectionConstants.URL_PROPERTY));
        config.setUsername(properties.getProperty(ConnectionConstants.USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(ConnectionConstants.PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(ConnectionConstants.DRIVER_PROPERTY));

        return config;
    }

    public static HikariDataSource getConnectionsPool() {
        logger.info("Get connection pool");
        return ds;
    }

}
