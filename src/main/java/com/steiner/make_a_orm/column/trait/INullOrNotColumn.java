package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.predicate.NullOrNot;
import jakarta.annotation.Nonnull;

public interface INullOrNotColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereClause isNull() {
        return new NullOrNot(self(), true);
    }

    default WhereClause notNull() {
        return new NullOrNot(self(), false);
    }
}
