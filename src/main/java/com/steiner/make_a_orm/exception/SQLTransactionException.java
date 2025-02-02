package com.steiner.make_a_orm.exception;

public class SQLTransactionException extends IllegalArgumentException {
    public SQLTransactionException(String message) {
        super(message);
    }
}
