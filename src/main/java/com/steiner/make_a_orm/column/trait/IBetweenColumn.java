package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.predicate.Between;
import jakarta.annotation.Nonnull;

public interface IBetweenColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereClause between(@Nonnull T min, @Nonnull T max) {
        return new Between<>(self(), min, max);
    }
}
