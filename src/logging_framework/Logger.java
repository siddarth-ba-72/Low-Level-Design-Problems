package logging_framework;

import logging_framework.appender.LogAppender;
import logging_framework.manager.LogManager;
import logging_framework.models.LogLevel;
import logging_framework.models.LogMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger {
    private final String name;
    private LogLevel level;
    private final List<LogAppender> appenders;

    public Logger(String name) {
        this.name = name;
        this.level = LogLevel.INFO; // Default level
        this.appenders = new CopyOnWriteArrayList<>();
    }

    public void addAppender(LogAppender appender) {
        appenders.add(appender);
    }

    public List<LogAppender> getAppenders() {
        return appenders;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public void log(LogLevel messageLevel, String message) {
        if (messageLevel.isGreaterOrEqual(level)) {
            LogMessage logMessage = new LogMessage(messageLevel, this.name, message);
            callAppenders(logMessage);
        }
    }

    private void callAppenders(LogMessage logMessage) {
        if (!appenders.isEmpty()) {
            LogManager.getInstance().getProcessor().process(logMessage, this.appenders);
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
