package logging_framework.manager;

import logging_framework.Logger;
import logging_framework.appender.LogAppender;
import logging_framework.processor.AsyncLogProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogManager {
    private static final LogManager INSTANCE = new LogManager();
    private final Map<String, Logger> loggers = new ConcurrentHashMap<>();
    private final AsyncLogProcessor processor;

    private LogManager() {
        this.processor = new AsyncLogProcessor();
    }

    public static LogManager getInstance() {
        return INSTANCE;
    }

    public Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, Logger::new);
    }

    public AsyncLogProcessor getProcessor() {
        return processor;
    }

    public void shutdown() {
        // Stop the processor first to ensure all logs are written.
        processor.stop();

        // Then, close all appenders.
        loggers.values().stream()
                .flatMap(logger -> logger.getAppenders().stream())
                .distinct()
                .forEach(LogAppender::close);
        System.out.println("Logging framework shut down gracefully.");
    }
}
