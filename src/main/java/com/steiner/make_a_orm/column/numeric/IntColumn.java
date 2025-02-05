package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IAutoIncrement;
import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;

public final class IntColumn extends NumericColumn<Integer>
        implements
        IAutoIncrement<IntColumn>,
        IDefaultValueColumn<Integer, IntColumn> {
    public boolean isAutoIncrement = false;

    public IntColumn(String name) {
        super(name);
    }

    @Override
    public String valueToDB(@Nonnull Integer value) {
        return String.valueOf(value);
    }

    @Override
    public String sqlType() {
        if (isAutoIncrement) {
            return "int auto_increment";
        } else {
            return "int";
        }
    }

    @Nonnull
    @Override
    public IntColumn autoIncrement() {
        isAutoIncrement = true;
        return this;
    }

    @Override
    public boolean isAutoIncrement() {
        return this.isAutoIncrement;
    }

    @Nonnull
    @Override
    public IntColumn self() {
        return this;
    }
}
