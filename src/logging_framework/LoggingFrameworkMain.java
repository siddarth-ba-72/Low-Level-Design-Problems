package logging_framework;

import logging_framework.appender.ConsoleAppender;
import logging_framework.manager.LogManager;
import logging_framework.models.LogLevel;

public class LoggingFrameworkMain {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getInstance();

        // --- 1. Configure a logger and log at its level ---
        Logger logger = logManager.getLogger("com.example.Main");
        logger.setLevel(LogLevel.INFO); // Minimum level for this logger
        logger.addAppender(new ConsoleAppender());
        logger.info("Application starting up.");
        logger.debug("This is a debug message, it should NOT appear."); // Below INFO
        logger.warn("This is a warning message.");

        // --- 2. A second, independently configured logger ---
        Logger serviceLogger = logManager.getLogger("com.example.UserService");
        serviceLogger.setLevel(LogLevel.DEBUG); // More verbose for this logger
        serviceLogger.addAppender(new ConsoleAppender());
        serviceLogger.info("User service starting.");
        serviceLogger.debug("Detailed debug output for the user service.");

        // --- 3. Raise the Main logger's verbosity at runtime ---
        logger.setLevel(LogLevel.DEBUG);
        logger.debug("This debug message should now be visible.");

        try {
            Thread.sleep(500);
            logManager.shutdown();
        } catch (Exception e) {
            System.out.println("Caught exception");
        }
    }
}
