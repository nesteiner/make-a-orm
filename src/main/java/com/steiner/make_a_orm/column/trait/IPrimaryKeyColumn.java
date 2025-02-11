package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.impl.PrimaryKeyConstraint;
import jakarta.annotation.Nonnull;

public interface IPrimaryKeyColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default E primaryKey() {
        E selfColumn = self();
        selfColumn.constraints.add(new PrimaryKeyConstraint(selfColumn));
        selfColumn.isPrimaryKey = true;
        return selfColumn;
    }
}
