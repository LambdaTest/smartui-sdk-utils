package com.github.lambdatest;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerUtil {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

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
                    message = ANSI_BLUE + message + ANSI_RESET;
                } else if (record.getLevel().equals(Level.WARNING)) {
                    message = ANSI_YELLOW + message + ANSI_RESET;
                } else if (record.getLevel().equals(Level.SEVERE)) {
                    message = ANSI_RED + message + ANSI_RESET;
                }
                return String.format("[%s] %s%n", logContext, message);
            }
        });
        logger.addHandler(handler);

        return logger;
    }

    private static Level getLogLevel() {
        String debug = System.getenv("LT_SDK_DEBUG");
        String logLevelEnv = System.getenv("LT_SDK_LOG_LEVEL");
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
