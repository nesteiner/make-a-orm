package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class Like extends WhereClause {
    @Nonnull
    public String pattern;

    public Like(@Nonnull Column<String> column, @Nonnull String pattern) {
        super(column);
        this.pattern = pattern;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "`%s` like ?".formatted(column.name);
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        int index = getInjectIndex();

        //noinspection unchecked
        Column<String> trueColumn = (Column<String>) column;
        trueColumn.inject(statement, index, pattern);
        return index + 1;
    }
}
