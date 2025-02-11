package com.steiner.make_a_orm.column.constraint.impl;

import com.steiner.make_a_orm.column.constraint.InlineConstraint;
import jakarta.annotation.Nonnull;

public class DefaultAutoIncrementConstraint extends InlineConstraint {
    @Nonnull
    @Override
    public String constraint() {
        return "auto_increment";
    }
}
