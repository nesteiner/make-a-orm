package com.steiner.make_a_orm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalLogger {
    private static final Logger logger = LoggerFactory.getLogger("GlobalLogger");

    public static Logger logger() {
        return logger;
    }
}
