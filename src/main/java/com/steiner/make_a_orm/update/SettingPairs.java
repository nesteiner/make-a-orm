package com.steiner.make_a_orm.update;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLRuntimeException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SettingPairs<T> {
    @Nonnull
    public Column<T> column;

    @Nonnull
    public String pattern;

    @Nullable
    public T value;

    public SettingPairs(@Nonnull Column<T> column, @Nonnull String pattern, @Nullable T value) {
        this.column = column;
        this.pattern = pattern;
        this.value = value;
    }

    public void inject(@Nonnull PreparedStatement statement, int index) {
        if (value == null) {
            try {
                statement.setObject(index, null);
            } catch (SQLException e) {
                throw new SQLRuntimeException(e);
            }
        } else {
            column.inject(statement, index, value);
        }
    }

}
