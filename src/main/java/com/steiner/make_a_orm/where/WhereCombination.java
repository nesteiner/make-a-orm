package com.steiner.make_a_orm.where;

import com.steiner.make_a_orm.where.operator.Combination;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;

public class WhereCombination extends WhereStatement {
    @Nonnull
    public Combination combination;

    @Nonnull
    public WhereStatement otherStatement;

    public WhereCombination(@Nonnull Combination combination, @Nonnull WhereStatement otherStatement) {
        super(otherStatement.shouldInject, -1);
        this.combination = combination;
        this.otherStatement = otherStatement;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "%s %s".formatted(combination.sign, otherStatement.toSQL());
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        if (shouldInject) {
            this.otherStatement.setInjectIndex(getInjectIndex());
            return otherStatement.inject(statement);
        } else {
            return -1;
        }
    }
}