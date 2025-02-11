package com.steiner.make_a_orm.column.trait;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.predicate.InList;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface IInListColumn<T, E extends Column<T>> {
    @Nonnull
    E self();

    default WhereClause inList(@Nonnull List<T> list) {
        return new InList<>(self(), list);
    }
}
