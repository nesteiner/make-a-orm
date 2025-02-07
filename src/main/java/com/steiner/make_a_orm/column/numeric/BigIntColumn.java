package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IAutoIncrement;
import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;

public class BigIntColumn
        extends AbstractNumericColumn<Long>
        implements
        IAutoIncrement<BigIntColumn>,
        IDefaultValueColumn<Long, BigIntColumn> {

    private boolean isAutoIncrement;

    public BigIntColumn(String name) {
        super(name);
        this.isAutoIncrement = false;
    }

    @Override
    @Nonnull
    public BigIntColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        if (isAutoIncrement) {
            return "bigint auto_increment";
        } else {
            return "bigint";
        }
    }

    @Override
    public boolean isAutoIncrement() {
        return this.isAutoIncrement;
    }

    @Nonnull
    @Override
    public BigIntColumn autoIncrement() {
        this.isAutoIncrement = true;
        return this;
    }
}
