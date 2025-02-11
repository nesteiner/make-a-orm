package com.steiner.make_a_orm.column.bool;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.trait.IDefaultValueColumn;
import com.steiner.make_a_orm.column.trait.IEqualColumn;
import com.steiner.make_a_orm.column.trait.INullOrNotColumn;
import com.steiner.make_a_orm.exception.SQLBuildException;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class BooleanColumn extends Column<Boolean>
        implements
        IEqualColumn<Boolean, BooleanColumn>,
        IDefaultValueColumn<Boolean, BooleanColumn>,
        INullOrNotColumn<Boolean, BooleanColumn> {
    public BooleanColumn(String name) {
        super(name);
    }

    @Override
    public void inject(@Nonnull PreparedStatement statement, int index, @Nonnull Boolean value) {
        try {
            statement.setBoolean(index, value);
        } catch (SQLException e) {
            throw new SQLBuildException(e);
        }
    }

    @Nonnull
    @Override
    public String sqlType() {
        return "tinyint(1)";
    }

    @Nonnull
    @Override
    public String format(@Nonnull Boolean value) {
        return String.valueOf(value);
    }

    @Nonnull
    @Override
    public BooleanColumn self() {
        return this;
    }
}
