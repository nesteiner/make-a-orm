package com.steiner.make_a_orm.column.constract;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface IDefaultColumn<T> {
    @Nonnull
    String name();
    void setDefault(boolean hasDefault);
    boolean isNullable();

    void setDefaultValue(@Nullable T value);
    void setDefaultExpression(@Nonnull String expression);
}
