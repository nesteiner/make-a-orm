package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.constract.IDefaultValueColumn;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;

public class BooleanColumn
        extends AbstractNumericColumn<Boolean>
        implements IDefaultValueColumn<Boolean, BooleanColumn> {

    public BooleanColumn(String name) {
        super(name);
    }

    @Override
    @NotNull
    public BooleanColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "tinyint(1)";
    }

    @Override
    public String valueToDB(@Nonnull Boolean value) {
        return value ? "1" : "0";
    }
}
