package com.steiner.make_a_orm.exception;

public class SQLInsertException extends RuntimeException {
    public SQLInsertException(String message) {
        super(message);
    }

    public SQLInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLInsertException(Throwable cause) {
        super(cause);
    }
}
