package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;


public class TinyIntColumn extends AbstractNumericColumn<Byte> implements IDefaultValueColumn<Byte, TinyIntColumn> {
    public TinyIntColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public TinyIntColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "tinyint";
    }
}
