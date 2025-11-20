package com.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    // Method to get logger for any class
    public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }
}
