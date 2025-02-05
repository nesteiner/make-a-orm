package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class DecimalColumn
        extends NumericColumn<BigDecimal>
        implements IDefaultValueColumn<BigDecimal, DecimalColumn> {
    public DecimalColumn(String name) {
        super(name);
    }

    @Override
    @NotNull
    public DecimalColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "decimal";
    }
}
