package com.steiner.make_a_orm.select;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLRuntimeException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.ResultSet;

public class ResultRow {
    public ResultSet resultSet;

    public ResultRow(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    // edit here
    @Nonnull
    public <T> T get(Column<T> column) {
        T result = column.valueFromDB(resultSet);
        if (result == null) {
            throw new SQLRuntimeException("the result is null, but using the nonnull constraint");
        }

        return result;
    }

    @Nullable
    public <T> T getOrNull(Column<T> column) {
        if (!column.isNullable) {
            throw new SQLRuntimeException("the value of this column `%s` cannot be null".formatted(column.name));
        }

        return column.valueFromDB(resultSet);
    }
}
