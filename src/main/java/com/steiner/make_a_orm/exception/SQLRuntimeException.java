package com.steiner.make_a_orm.exception;

public class SQLRuntimeException extends RuntimeException {
  public SQLRuntimeException(String message) {
    super(message);
  }

  public SQLRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public SQLRuntimeException(Throwable cause) {
    super(cause);
  }
}
