package com.github.lambdatest.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import com.github.lambdatest.constants.Constants;

public class LoggerUtil {
    public static Logger createLogger(String logContext) {
        Logger logger = Logger.getLogger(logContext);
        logger.setLevel(getLogLevel());
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String message = formatMessage(record);
                if (record.getLevel().equals(Level.FINE)) {
                    message = Constants.LoggerColors.ANSI_BLUE + message + Constants.LoggerColors.ANSI_RESET;
                } else if (record.getLevel().equals(Level.WARNING)) {
                    message = Constants.LoggerColors.ANSI_YELLOW + message + Constants.LoggerColors.ANSI_RESET;
                } else if (record.getLevel().equals(Level.SEVERE)) {
                    message = Constants.LoggerColors.ANSI_RED + message + Constants.LoggerColors.ANSI_RESET;
                }
                return String.format("[%s] %s%n", logContext, message);
            }
        });
        logger.addHandler(handler);
        
        return logger;
    }

    private static Level getLogLevel() {
        String debug = System.getenv(Constants.LogEnvVars.LT_SDK_DEBUG);
        String logLevelEnv = System.getenv(Constants.LogEnvVars.LT_SDK_LOG_LEVEL);
        if ("true".equals(debug)) {
            return Level.FINE;
        } else if (logLevelEnv != null) {
            switch (logLevelEnv.toLowerCase()) {
                case "info":
                    return Level.INFO;
                case "warning":
                    return Level.WARNING;
                case "severe":
                    return Level.SEVERE;
                default:
                    return Level.INFO;
            }
        }
        return Level.INFO;
    }
}