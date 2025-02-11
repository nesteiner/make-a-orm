package com.steiner.make_a_orm.column.constraint;

import jakarta.annotation.Nonnull;

public abstract class InlineConstraint extends AbstractConstraint {
    @Nonnull
    @Override
    public ConstraintType type() {
        return ConstraintType.Inline;
    }
}
