package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;

public class FloatColumn extends NumericColumn<Float> implements IDefaultValueColumn<Float, FloatColumn> {
    public FloatColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public FloatColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "float";
    }
}
