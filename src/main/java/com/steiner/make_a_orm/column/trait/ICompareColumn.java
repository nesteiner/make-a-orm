package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.operator.Comparator;
import com.steiner.make_a_orm.where.predicate.Compare;
import jakarta.annotation.Nonnull;

public interface ICompareColumn<T extends Comparable<T>, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereClause greater(@Nonnull T value) {
        return new Compare<>(self(), Comparator.Greater, value);
    }

    default WhereClause greaterEq(@Nonnull T value) {
        return new Compare<>(self(), Comparator.GreaterEq, value);
    }

    default WhereClause less(@Nonnull T value) {
        return new Compare<>(self(), Comparator.Less, value);
    }

    default WhereClause lessEq(@Nonnull T value) {
        return new Compare<>(self(), Comparator.LessEq, value);
    }
}
