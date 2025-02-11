package com.steiner.make_a_orm.column.constraint.impl;

import com.steiner.make_a_orm.column.constraint.SuffixConstraint;
import jakarta.annotation.Nonnull;

public class CustomAutoIncrementConstraint extends SuffixConstraint {
    public int start;

    public CustomAutoIncrementConstraint(int start) {
        this.start = start;
    }

    @Nonnull
    @Override
    public String constraint() {
        return "auto_increment = %s".formatted(start);
    }
}
