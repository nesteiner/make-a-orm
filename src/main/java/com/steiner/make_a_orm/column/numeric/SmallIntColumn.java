package com.steiner.make_a_orm.column.numeric;

import com.steiner.make_a_orm.column.trait.*;
import com.steiner.make_a_orm.exception.SQLBuildException;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SmallIntColumn extends NumericColumn<Short>
        implements
        IEqualColumn<Short, SmallIntColumn>,
        IDefaultValueColumn<Short, SmallIntColumn>,
        IAutoIncrementColumn<Short, SmallIntColumn>,
        ICompareColumn<Short, SmallIntColumn>,
        IBetweenColumn<Short, SmallIntColumn>,
        IInListColumn<Short, SmallIntColumn>,
        INullOrNotColumn<Short, SmallIntColumn> {

    public SmallIntColumn(@Nonnull String name) {
        super(name);
    }

    @Override
    public void inject(@Nonnull PreparedStatement statement, int index, @Nonnull Short value) {
        try {
            statement.setShort(index, value);
        } catch (SQLException e) {
            throw new SQLBuildException(e);
        }
    }

    @Nonnull
    @Override
    public String sqlType() {
        return "smallint";
    }

    @Nonnull
    @Override
    public SmallIntColumn self() {
        return this;
    }
}
