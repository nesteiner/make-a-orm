package com.steiner.make_a_orm.column.constract;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLBuildException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface IDefaultValueColumn<T, E extends Column<T>>
        extends IDefaultColumn<T> {
    @Nonnull
    E self();

    @Nonnull
    default E defaultValue(@Nullable T value) {
        if (value == null && !isNullable()) {
            throw new SQLBuildException("field %s cannot be null".formatted(name()));
        }

        setDefault(true);
        setDefaultValue(value);
        return self();
    }
}
