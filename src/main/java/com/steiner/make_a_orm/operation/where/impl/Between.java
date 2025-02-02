package com.steiner.make_a_orm.operation.where.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereClause;

public class Between<T extends Number> extends WhereClause<T> {
    public Number min;
    public Number max;

    public Between(Column<T> column, Number min, Number max) {
        super(column);
        this.min = min;
        this.max = max;
    }

    @Override
    public String buildWhere() {
        return "(%s between %s and %s)".formatted(column.name, min, max);
    }
}