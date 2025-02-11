package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.operator.Comparator;
import com.steiner.make_a_orm.where.WhereClause;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class Compare<T> extends WhereClause {
    @Nonnull
    public Comparator comparator;

    @Nonnull
    public T literal;

    public Compare(@Nonnull Column<T> column, @Nonnull Comparator comparator, @Nonnull T literal) {
        super(column);
        this.comparator = comparator;
        this.literal = literal;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "`%s` %s ?".formatted(column.name, comparator.sign);
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        int index = getInjectIndex();

        //noinspection unchecked
        Column<T> trueColumn = (Column<T>) this.column;
        trueColumn.inject(statement, index, literal);
        return index + 1;
    }
}
