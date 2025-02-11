package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.operator.Equality;
import com.steiner.make_a_orm.where.predicate.Equal;
import jakarta.annotation.Nonnull;

public interface IEqualColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereClause equal(@Nonnull T value) {
        return new Equal<>(self(), Equality.Eq, value);
    }

    default WhereClause notEqual(@Nonnull T value) {
        return new Equal<>(self(), Equality.NotEq, value);
    }
}
