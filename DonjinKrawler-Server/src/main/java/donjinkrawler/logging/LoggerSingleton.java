package donjinkrawler.logging;

import donjinkrawler.config.ConfigSingleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Thread-safe singleton using eager initialization.
 */
public class LoggerSingleton {
    private static final LoggerSingleton instance = new LoggerSingleton();
    private final ConfigSingleton config;
    private final LoggingLevel logLevel;
    private PrintStream out;
    private boolean isTestingMode;

    // ONLY USED IN TESTS
    protected LoggerSingleton(ConfigSingleton config) {
        this.config = config;
        init();
        logLevel = getLoggingLevel();
        isTestingMode = true;
    }

    private LoggerSingleton() {
        config = ConfigSingleton.getInstance();
        init();
        logLevel = getLoggingLevel();
        isTestingMode = false;
        System.out.println("LoggerSingleton initialized");
    }

    public static LoggerSingleton getInstance() {
        return instance;
    }

    private void init() {
        String fileName = config.getPropertyValue("logging.fileName");
        if (fileName == null) {
            out = System.out;
        } else {
            initFileOutput(fileName);
        }
    }

    private void initFileOutput(String fileName) {
        try {
            fileName = fileName.replace("yyyyMMdd",
                    DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()));
            File file = new File(ClassLoader.getSystemResource(".").getFile() + "/logs/");
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new PrintStream(new FileOutputStream(file.getAbsolutePath() + "/" + fileName, true));
        } catch (FileNotFoundException e) {
            System.out.println("Failed initializing logging file. Printing to console");
            e.printStackTrace();
            out = System.out;
        }
    }

    public void info(String message) {
        if (isInfoEnabled()) {
            outputLogMessage(LoggingLevel.INFO, message);
        }
    }

    public void debug(String message) {
        if (isDebugEnabled()) {
            outputLogMessage(LoggingLevel.DEBUG, message);
        }
    }

    public void error(String message) {
        outputLogMessage(LoggingLevel.ERROR, message);
    }

    public void error(Exception e) {
        outputLogMessage(LoggingLevel.ERROR, e.getMessage());
    }

    private void outputLogMessage(LoggingLevel loggingLevel, String message) {
        StringBuilder builder = new StringBuilder();
        if (!isTestingMode) {
            builder.append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())).append(" ");
        }
        builder.append("[").append(loggingLevel).append("] ");
        if (!isTestingMode) {
            builder.append(getCallerClassMethod()).append(": ");
        }
        builder.append(message);
        out.println(builder.toString());
    }

    protected String getCallerClassMethod() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String callerClass = ste.getClassName();
        String callerMethod = ste.getMethodName();
        return callerClass + "#" + callerMethod;
    }

    private LoggingLevel getLoggingLevel() {
        String loggingLevel = config.getPropertyValue("logging.level", "INFO");
        if (loggingLevel == null) {
            return LoggingLevel.INFO;
        }
        switch (loggingLevel) {
            case "DEBUG":
                return LoggingLevel.DEBUG;
            case "ERROR":
                return LoggingLevel.ERROR;
            default:
                return LoggingLevel.INFO;
        }
    }

    private boolean isInfoEnabled() {
        return logLevel == LoggingLevel.INFO || logLevel == LoggingLevel.DEBUG;
    }

    private boolean isDebugEnabled() {
        return logLevel == LoggingLevel.DEBUG;
    }

    // used in testing
    public boolean isLogEnabled() {
        return out != null;
    }
}