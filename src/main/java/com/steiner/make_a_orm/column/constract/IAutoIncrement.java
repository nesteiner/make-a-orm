package com.steiner.make_a_orm.column.constract;

import jakarta.annotation.Nonnull;

public interface IAutoIncrement<Self> {
    boolean isAutoIncrement();
    @Nonnull
    Self autoIncrement();
}
