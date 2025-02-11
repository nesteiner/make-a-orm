package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import com.steiner.make_a_orm.where.operator.Equality;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class Equal<T> extends WhereClause {
    @Nonnull
    public Equality equality;

    @Nonnull
    public T literal;

    public Equal(@Nonnull Column<T> column, @Nonnull Equality equality, @Nonnull T literal) {
        super(column);
        this.equality = equality;
        this.literal = literal;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "`%s` %s ?".formatted(column.name, equality.sign);
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        int index = getInjectIndex();

        //noinspection unchecked
        Column<T> trueColumn = (Column<T> ) column;
        trueColumn.inject(statement, index, literal);
        return index + 1;
    }
}
