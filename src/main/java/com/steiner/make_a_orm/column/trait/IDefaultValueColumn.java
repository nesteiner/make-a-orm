package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.impl.DefaultValueConstraint;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface IDefaultValueColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default E defaultValue(@Nullable T value) {
        E selfColumn = self();
        selfColumn.constraints.add(new DefaultValueConstraint<>(selfColumn, value));
        selfColumn.setDefault = true;
        return selfColumn;
    }
}
