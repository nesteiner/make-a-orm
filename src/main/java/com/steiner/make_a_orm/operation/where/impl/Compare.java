package com.steiner.make_a_orm.operation.where.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereClause;

public class Compare<T> extends WhereClause<T> {
    public Comparator comparator;
    public T literal;

    public Compare(Column<T> column, Comparator comparator, T literal) {
        super(column);

        this.comparator = comparator;
        this.literal = literal;
    }

    @Override
    public String buildWhere() {
        String literalString = "";

        if (literal instanceof Number) {
            literalString = "%s".formatted(literal.toString());
        } else {
            literalString = "'%s'".formatted(literal.toString());
        }

        return "(%s %s %s)".formatted(column.name, comparator.sign, literalString);
    }
}