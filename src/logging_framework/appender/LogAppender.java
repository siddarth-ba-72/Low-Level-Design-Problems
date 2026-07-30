package logging_framework.appender;

import logging_framework.formatter.LogFormatter;
import logging_framework.models.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);

    void close();

    LogFormatter getFormatter();

    void setFormatter(LogFormatter formatter);
}
