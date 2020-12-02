package donjinkrawler.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSingleton {

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final ConfigSingleton instance = new ConfigSingleton();
    private Properties properties;
    private Properties previousProperties;

    private ConfigSingleton() {
        previousProperties = new Properties();
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

    public void setTestProperty(String propertyKey, String value) {
        if (properties.get(propertyKey) != null) {
            previousProperties.put(propertyKey, properties.get(propertyKey));
        }
        properties.put(propertyKey, value);
    }

    public void resetProperties() {
        for (Object prop : previousProperties.keySet()) {
            try {
                String propKey = (String) prop;
                properties.put(propKey, previousProperties.get(propKey));
            } catch (Exception e) {
                // ignored
            }
        }
    }
}
