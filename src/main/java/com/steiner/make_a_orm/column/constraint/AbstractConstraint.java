package com.steiner.make_a_orm.column.constraint;

import jakarta.annotation.Nonnull;

public abstract class AbstractConstraint {
    @Nonnull
    public abstract String constraint();

    @Nonnull
    public abstract ConstraintType type();
}
