package com.steiner.make_a_orm.operation.where.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereClause;

public class NullPredicate<T> extends WhereClause<T> {
    public boolean isnull;

    public NullPredicate(Column<T> column, boolean isnull) {
        super(column);
        this.isnull = isnull;
    }

    @Override
    public String buildWhere() {
        if (isnull) {
            return "(%s is null)".formatted(column.name);
        } else {
            return "(%s is not null)".formatted(column.name);
        }
    }
}
