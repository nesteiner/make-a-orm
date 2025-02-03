package com.steiner.make_a_orm.utils.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/** for example
 * Result.from(() -> {
 *   Integer value = functionDoNotThrowsException();
 * }).ifOk(System.out::println);

 * Result.from(() -> {
 *   Integer value = functionThrowsException();
 * }).ifErr(error -> System.out.println(error.getMessage()));;
 */
public final class Result<T, E extends Throwable> {
    public static <T, E extends Throwable> Result<T, E> from(SupplierThrowing<T, E> supplier) {
        try {
            T data = supplier.get();
            return Result.Ok(data);
        } catch (Throwable e) {
            @SuppressWarnings("unchecked")
            E error = (E) e;
            return Result.Err(error);
        }
    }

    private static <T, E extends Throwable> Result<T, E> Ok(@Nonnull T data) {
        return new Result<>(data, null);
    }

    private static <T, E extends Throwable> Result<T, E> Err(@Nonnull E error) {
        return new Result<>(null, error);
    }

    private static <T, E extends Throwable, U> Result<U, E> Err(@Nonnull Result<T, E> result) {
        if (result.isOk()) {
            throw new UnsupportedException("cannot transform ok to error");
        }

        return new Result<>(null, result.getError());
    }

    @Nullable
    public T data;

    @Nullable
    public E error;

    private Result(@Nullable T data, @Nullable E error) {
        this.data = data;
        this.error = error;
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
    public <U> Result<U, E> map(@Nonnull Supplier<U> supplier) {
        if (isOk()) {
            return Result.Ok(supplier.get());
        } else {
            return Result.Err(this.getError());
        }
    }

    @Nonnull
    public T get() {
        if (isOk()) {
            return data;
        } else {
            throw new UnsupportedException("cannot unwrap result ok");
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
            throw new UnsupportedException("cannot unwrap result error");
        }
    }
}
