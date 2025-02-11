package com.steiner.make_a_orm.column.date;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.trait.*;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.exception.SQLRuntimeException;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class TimestampColumn extends Column<java.sql.Timestamp>
        implements
        IEqualColumn<java.sql.Timestamp, TimestampColumn>,
        ICompareDateColumn<java.sql.Timestamp, TimestampColumn>,
        INullOrNotColumn<Timestamp, TimestampColumn>,
        IBetweenColumn<Timestamp, TimestampColumn>,
        IInListColumn<Timestamp, TimestampColumn> {

    public TimestampColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public String sqlType() {
        return "timestamp";
    }

    @Nonnull
    @Override
    public String format(@Nonnull Timestamp value) {
        return "'%s'".formatted(value.toString());
    }

    @Nonnull
    @Override
    public TimestampColumn self() {
        return this;
    }

    @Nullable
    @Override
    public java.sql.Timestamp valueFromDB(@Nonnull ResultSet result) {
        try {
            return result.getTimestamp(name);
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    @Override
    public void inject(@Nonnull PreparedStatement statement, int index, @Nonnull Timestamp value) {
        try {
            statement.setTimestamp(index, value);
        } catch (SQLException e) {
            throw new SQLBuildException(e);
        }
    }
}
