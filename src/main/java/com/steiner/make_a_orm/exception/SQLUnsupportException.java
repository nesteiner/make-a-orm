package com.steiner.make_a_orm.exception;

public class SQLUnsupportException extends RuntimeException {
    public SQLUnsupportException(String message) {
        super(message);
    }

    public SQLUnsupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLUnsupportException(Throwable cause) {
        super(cause);
    }
}
