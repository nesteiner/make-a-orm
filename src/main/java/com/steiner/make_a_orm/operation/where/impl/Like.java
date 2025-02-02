package com.steiner.make_a_orm.operation.where.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereClause;

public class Like extends WhereClause<String> {
    public String pattern;

    public Like(Column<String> column, String pattern) {
        super(column);
        this.pattern = pattern;
    }

    @Override
    public String buildWhere() {
        return "(%s like '%s')".formatted(column.name, pattern);
    }
}
