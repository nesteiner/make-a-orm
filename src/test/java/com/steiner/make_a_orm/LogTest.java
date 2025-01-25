package com.steiner.make_a_orm;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    @Test
    void testLog() {
        final Logger logger = LoggerFactory.getLogger("hello world");
        logger.info("hello world");
    }
}
