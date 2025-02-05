package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;

public class DoubleColumn extends NumericColumn<Double>
        implements IDefaultValueColumn<Double, DoubleColumn> {
    public DoubleColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public DoubleColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "double";
    }
}
