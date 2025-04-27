// filepath: e:\1 work\2nd yr\thiyu_CSA\BookStore_Application\src\main\java\com\BookStore\Application\Util\LoggerUtil.java
package com.BookStore.Application.Util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for logging messages across the application.
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    /**
     * Logs a warning message, typically for known client errors.
     *
     * @param message The message to log
     */
    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Logs a severe message, typically for unexpected or server errors.
     *
     * @param message The message to log
     */
    public static void logSevere(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Logs a severe message with an exception stack trace.
     *
     * @param message The message to log
     * @param throwable The exception that occurred
     */
    public static void logSevere(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Logs an info message.
     *
     * @param message The message to log
     */
    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }
}
