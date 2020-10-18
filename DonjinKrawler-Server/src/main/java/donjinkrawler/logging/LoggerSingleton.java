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


    private LoggerSingleton() {
        config = ConfigSingleton.getInstance();
        init();
        logLevel = getLoggingLevel();
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

    private void outputLogMessage(LoggingLevel info, String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())).append(" ");
        builder.append("[").append(info).append("] ");
        builder.append(getCallerClassMethod()).append(": ");
        builder.append(message);
        out.println(builder.toString());
    }

    private String getCallerClassMethod() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String callerClass = ste.getClassName();
        String callerMethod = ste.getMethodName();
        return callerClass + "#" + callerMethod;
    }



    private LoggingLevel getLoggingLevel() {
        String loggingLevel = config.getPropertyValue("logging.level", "INFO");
        LoggingLevel level = switch (loggingLevel) {
            case "DEBUG" -> LoggingLevel.DEBUG;
            case "ERROR" -> LoggingLevel.ERROR;
            default -> LoggingLevel.INFO;
        };
        return level;
    }

    private boolean isInfoEnabled() {
        return logLevel == LoggingLevel.INFO || logLevel == LoggingLevel.DEBUG;
    }

    private boolean isDebugEnabled() {
        return logLevel == LoggingLevel.DEBUG;
    }
}