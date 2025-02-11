package com.steiner.make_a_orm.where;

import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class WhereNot extends WhereStatement {
    @Nonnull
    public WhereStatement statement;

    public WhereNot(@Nonnull WhereStatement statement) {
        super(statement.shouldInject, -1);
        this.statement = statement;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "not (%s)".formatted(statement.toSQL());
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        if (shouldInject) {
            this.statement.setInjectIndex(getInjectIndex());
            return this.statement.inject(statement);
        } else {
            return -1;
        }
    }
}