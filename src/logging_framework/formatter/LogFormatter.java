package logging_framework.formatter;

import logging_framework.models.LogMessage;

public interface LogFormatter {
    String format(LogMessage logMessage);
}
