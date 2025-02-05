package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constract.ICompareColumn;
import jakarta.annotation.Nonnull;


public abstract class NumericColumn<T extends Comparable<T>>
        extends Column<T> implements ICompareColumn<T, Column<T>> {
    public NumericColumn(String name) {
        super(name);
    }

    public String valueToDB(@Nonnull T value) {
        return String.valueOf(value);
    }

    public abstract String sqlType();
}
