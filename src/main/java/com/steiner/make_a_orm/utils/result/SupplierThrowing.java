package com.steiner.make_a_orm.utils.result;

@FunctionalInterface
public interface SupplierThrowing<T, E extends Throwable> {
    T get() throws E;
}
