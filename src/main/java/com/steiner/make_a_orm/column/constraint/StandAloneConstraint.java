package com.steiner.make_a_orm.column.constraint;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public abstract class StandAloneConstraint extends AbstractConstraint {
    @Nullable
    public String name;

    public StandAloneConstraint(@Nullable String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public ConstraintType type() {
        return ConstraintType.StandAlone;
    }
}
