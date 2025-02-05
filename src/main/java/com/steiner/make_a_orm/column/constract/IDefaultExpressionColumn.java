package com.steiner.make_a_orm.column.constract;

import com.steiner.make_a_orm.column.Column;
import jakarta.annotation.Nonnull;

public interface IDefaultExpressionColumn<T, E extends Column<T>> extends IDefaultColumn<T> {
    @Nonnull
    E self();

    @Nonnull
    default E defaultExpression(@Nonnull String expression) {
        setDefault(true);
        setDefaultExpression(expression);
        return self();
    }
}
