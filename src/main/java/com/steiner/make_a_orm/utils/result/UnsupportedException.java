package com.steiner.make_a_orm.utils.result;

public class UnsupportedException extends RuntimeException {
    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
