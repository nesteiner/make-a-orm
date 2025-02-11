package com.steiner.make_a_orm.column.constraint.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.StandAloneConstraint;
import jakarta.annotation.Nonnull;

public class PrimaryKeyConstraint extends StandAloneConstraint {
    @Nonnull
    public Column<?> column;

    public PrimaryKeyConstraint(@Nonnull Column<?> column) {
        super(null);
        this.column = column;
    }

    @Nonnull
    @Override
    public String constraint() {
        return "primary key(`%s`)".formatted(column.name);
    }
}
