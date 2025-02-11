package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class NullOrNot extends WhereClause {
    public boolean isnull;
    public NullOrNot(@Nonnull Column<?> column, boolean isnull) {
        super(false, column);
        this.isnull = isnull;
    }

    @Nonnull
    @Override
    public String toSQL() {
        if (isnull) {
            return "`%s` is null".formatted(column.name);
        } else {
            return "`%s` is not null".formatted(column.name);
        }
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        return -1;
    }
}
