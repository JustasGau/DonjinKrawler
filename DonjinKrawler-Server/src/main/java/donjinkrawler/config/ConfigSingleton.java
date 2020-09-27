package donjinkrawler.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSingleton {

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private Properties properties;

    private static final ConfigSingleton instance = new ConfigSingleton();

    private ConfigSingleton() {
        initProperties();
        System.out.println("ConfigSingleton initialized");
    }

    public static ConfigSingleton getInstance() {
        return instance;
    }

    private void initProperties() {
        try {
            properties = new Properties();
            try (InputStream is = ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE_NAME)) {
                if (is != null) {
                    properties.load(is);
                } else {
                    throw new FileNotFoundException("Property file " + PROPERTIES_FILE_NAME + " not found");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed loading properties values ");
            e.printStackTrace();
        }
    }

    public String getPropertyValue(String propertyKey) {
        checkIfPropertiesLoaded();
        return properties.getProperty(propertyKey);
    }

    public String getPropertyValue(String propertyKey, String defaultValue) {
        checkIfPropertiesLoaded();
        return properties.getProperty(propertyKey, defaultValue);
    }

    private void checkIfPropertiesLoaded() {
        if (properties == null) {
            throw new PropertyFileNotLoadedException(this.getClass(), "Properties file not loaded");
        }
    }
}
