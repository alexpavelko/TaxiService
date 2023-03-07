package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class PropertiesConfig {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

    public Properties getProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configuration.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            logger.error("Error getting properties from resources PropertiesConfig.class file", e);
            throw new UncheckedIOException(e);
        }
    }
}
