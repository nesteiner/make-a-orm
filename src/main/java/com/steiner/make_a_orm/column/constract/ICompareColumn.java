package com.steiner.make_a_orm.column.constract;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereStatement;
import com.steiner.make_a_orm.operation.where.impl.Compare;
import jakarta.annotation.Nonnull;

public interface ICompareColumn <T extends Comparable<T>, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereStatement less(@Nonnull T value) {
        return new Compare<>(self(), WhereStatement.Comparator.Less, value);
    }

    default WhereStatement lessEq(@Nonnull T value) {
        return new Compare<>(self(), WhereStatement.Comparator.LessEq, value);
    }

    default WhereStatement greater(@Nonnull T value) {
        return new Compare<>(self(), WhereStatement.Comparator.Greater, value);
    }

    default WhereStatement greaterEq(@Nonnull T value) {
        return new Compare<>(self(), WhereStatement.Comparator.GreaterEq, value);
    }
}
