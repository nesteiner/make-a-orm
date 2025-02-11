package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.impl.CustomAutoIncrementConstraint;
import com.steiner.make_a_orm.column.constraint.impl.DefaultAutoIncrementConstraint;
import jakarta.annotation.Nonnull;

public interface IAutoIncrementColumn<T extends Number, E extends Column<T>> {
    @Nonnull
    E self();

    default E autoIncrement() {
        E selfColumn = self();
        selfColumn.isAutoIncrement = true;
        selfColumn.constraints.add(new DefaultAutoIncrementConstraint());
        return selfColumn;
    }

    default E autoIncrement(int start) {
        E selfColumn = self();
        selfColumn.isAutoIncrement = true;
        selfColumn.constraints.add(new CustomAutoIncrementConstraint(start));
        return selfColumn;
    }
}
