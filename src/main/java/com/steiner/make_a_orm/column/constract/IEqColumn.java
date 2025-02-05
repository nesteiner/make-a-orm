package com.steiner.make_a_orm.column.constract;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereStatement;
import com.steiner.make_a_orm.operation.where.impl.Compare;
import jakarta.annotation.Nonnull;

public interface IEqColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereStatement eq(@Nonnull T value) {
        return new Compare<T>(self(), WhereStatement.Comparator.Eq, value);
    }

    default WhereStatement noteq(@Nonnull T value) {
        return new Compare<T>(self(), WhereStatement.Comparator.NotEq, value);
    }
}
