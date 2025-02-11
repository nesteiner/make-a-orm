package com.steiner.make_a_orm.utils.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/** for example
 * Result.from(() -> {
 *   Integer value = functionDoNotThrowsException();
 * }).ifOk(System.out::println);

 * Result.from(() -> {
 *   Integer value = functionThrowsException();
 * }).ifErr(error -> System.out.println(error.getMessage()));;
 *
 * 什么？你这个闭包里调用的函数会抛出多种错误
 * 那需要你手动 try-catch 并抛出统一类型的 Exception 了
 */
public final class Result<T, E extends Throwable> {
    public static <T, E extends Throwable> Result<T, E> from(SupplierThrowing<T, E> supplier) {
        try {
            T data = supplier.get();
            return Result.Ok(data);
        } catch (Throwable e) {
            @SuppressWarnings("unchecked")
            E error = (E) e;

            return Result.Err(error, e);
        }
    }

    public static <T, E extends Throwable> Result<T, E> Ok(@Nonnull T data) {
        return new Result<>(data, null, null);
    }

    public static <T, E extends Throwable> Result<T, E> Err(@Nonnull E error, @Nonnull Throwable cause) {
        return new Result<>(null, error, cause);
    }

    private static <T, E extends Throwable, U> Result<U, E> Err(@Nonnull Result<T, E> result, @Nonnull Throwable cause) {
        if (result.isOk()) {
            throw new UnsupportedException("cannot transform ok to error");
        }

        return new Result<>(null, result.getError(), cause);
    }

    @Nullable
    public T data;

    @Nullable
    public E error;

    @Nullable
    public Throwable cause;

    private Result(@Nullable T data, @Nullable E error, @Nullable Throwable cause) {
        this.data = data;
        this.error = error;
        this.cause = cause;
    }

    public boolean isOk() {
        return data != null;
    };

    public boolean isErr() {
        return error != null;
    };

    public void ifOk(@Nonnull Consumer<T> consumer) {
        if (isOk()) {
            consumer.accept(get());
        }
    }

    public void ifErr(@Nonnull Consumer<E> consumer) {
        if (isErr()) {
            consumer.accept(getError());
        }
    }

    @Nonnull
    public <U> Result<U, E> map(@Nonnull Function<T, U> function) {
        if (isOk()) {
            return Result.Ok(function.apply(get()));
        } else {
            return Result.Err(this.getError(), this.getCause());
        }
    }

    @Nonnull
    public T get() {
        if (isOk()) {
            return data;
        } else {
            throw new UnsupportedException("cannot unwrap result err", this.getCause());
        }
    };

    @Nonnull
    public T orElse(@Nonnull T value) {
        if (isOk()) {
            return get();
        } else {
            return value;
        }
    }

    @Nonnull
    public T orElseGet(@Nonnull Supplier<? extends T> supplier) {
        if (isOk()) {
            return get();
        } else {
            return supplier.get();
        }
    }

    @Nonnull
    public E getError() {
        if (isErr()) {
            return error;
        } else {
            throw new UnsupportedException("cannot unwrap result ok");
        }
    }

    @Nonnull
    public Throwable getCause() {
        if (isErr()) {
            return cause;
        } else {
            throw new UnsupportedException("cannot unwrap result ok");
        }
    }
}
