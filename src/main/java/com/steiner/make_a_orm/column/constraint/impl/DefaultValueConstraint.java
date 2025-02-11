package com.steiner.make_a_orm.column.constraint.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.InlineConstraint;
import com.steiner.make_a_orm.exception.SQLBuildException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class DefaultValueConstraint<T> extends InlineConstraint {
    @Nullable
    public T value;

    @Nonnull
    public Column<T> column;

    public DefaultValueConstraint(@Nonnull Column<T> column, @Nullable T value) {
        this.column = column;
        this.value = value;

        if (!this.column.isNullable && value == null) {
            throw new SQLBuildException("cannot set default value is null when the column is not null");
        }
    }

    @Nonnull
    @Override
    public String constraint() {
        if (value == null) {
            return "default null";
        } else {
            return "default %s".formatted(column.format(value));
        }
    }
}
