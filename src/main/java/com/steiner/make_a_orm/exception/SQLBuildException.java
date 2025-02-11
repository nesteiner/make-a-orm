package com.steiner.make_a_orm.exception;

public class SQLBuildException extends RuntimeException {
    public SQLBuildException(String message) {
        super(message);
    }

    public SQLBuildException(String message, Throwable cause) {
      super(message, cause);
    }

    public SQLBuildException(Throwable cause) {
        super(cause);
    }
}
