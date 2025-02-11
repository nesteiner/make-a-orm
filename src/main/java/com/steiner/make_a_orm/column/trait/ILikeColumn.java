package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.predicate.Like;
import jakarta.annotation.Nonnull;

public interface ILikeColumn<E extends Column<String>> {
    @Nonnull
    E self();

    default WhereClause like(@Nonnull String pattern) {
        return new Like(self(), pattern);
    }
}
