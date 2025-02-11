package com.steiner.make_a_orm.where;

import com.steiner.make_a_orm.column.Column;
import jakarta.annotation.Nonnull;

/**
 * column `condition` value
 */
public abstract class WhereClause extends WhereStatement {
    @Nonnull
    public Column<?> column;

    public WhereClause(@Nonnull Column<?> column) {
        this(true, column);
    }

    public WhereClause(boolean shouldInject, @Nonnull Column<?> column) {
        super(shouldInject, -1);
        this.column = column;
    }
}
