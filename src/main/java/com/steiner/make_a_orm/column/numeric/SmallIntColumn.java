package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;

public class SmallIntColumn extends NumericColumn<Short> implements IDefaultValueColumn<Short, SmallIntColumn> {
    public SmallIntColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public SmallIntColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "smallint";
    }
}
