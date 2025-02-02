package com.steiner.make_a_orm.operation.where.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereClause;

import java.util.List;
import java.util.stream.Collectors;

public class InList<T> extends WhereClause<T> {
    public List<T> list;

    public InList(Column<T> column, List<T> list) {
        super(column);
        this.list = list;
    }

    @Override
    public String buildWhere() {
        String listString = list.stream().map(value -> {
            if (value instanceof Number) {
                return value.toString();
            } else {
                return "'%s'".formatted(value.toString());
            }
        }).collect(Collectors.joining(", "));

        return "(%s in %s)".formatted(column.name, listString);
    }
}
