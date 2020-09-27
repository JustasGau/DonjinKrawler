package donjinkrawler.config;

public class PropertyFileNotLoadedException extends RuntimeException {

    public PropertyFileNotLoadedException(Class<?> caller, String message) {
        super(caller.getName() + ":" + message);
    }

    public PropertyFileNotLoadedException(String message) {
        super(message);
    }
}
