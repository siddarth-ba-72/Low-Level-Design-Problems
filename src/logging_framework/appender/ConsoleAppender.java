package logging_framework.appender;

import logging_framework.formatter.LogFormatter;
import logging_framework.formatter.SimpleTextFormatter;
import logging_framework.models.LogMessage;

public class ConsoleAppender implements LogAppender {
    private LogFormatter formatter;

    public ConsoleAppender() {
        this.formatter = new SimpleTextFormatter();
    }

    @Override
    public void append(LogMessage logMessage) {
        System.out.print(formatter.format(logMessage));
    }

    @Override
    public void close() {
    }

    @Override
    public void setFormatter(LogFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LogFormatter getFormatter() {
        return formatter;
    }
}
