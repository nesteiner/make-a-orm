package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.IAutoIncrement;
import com.steiner.make_a_orm.column.ICompareColumn;
import org.jetbrains.annotations.NotNull;

public class IntegerType extends Column<Integer> implements IAutoIncrement<IntegerType>, ICompareColumn<Integer, Column<Integer >> {
    public boolean isAutoIncrement = false;

    public IntegerType(String name) {
        super(name);
    }

    @Override
    public String sqlType() {
        if (isAutoIncrement) {
            return "int auto_increment";
        } else {
            return "int";
        }
    }

    @Override
    public IntegerType autoIncrement() {
        isAutoIncrement = true;
        return this;
    }

    @Override
    public boolean isAutoIncrement() {
        return this.isAutoIncrement;
    }

    @NotNull
    @Override
    public Column<Integer> self() {
        return this;
    }
}
