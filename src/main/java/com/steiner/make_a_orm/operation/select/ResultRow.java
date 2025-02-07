package com.steiner.make_a_orm.operation.select;

import com.steiner.make_a_orm.column.Column;
import jakarta.annotation.Nullable;

import java.sql.ResultSet;

public class ResultRow {
    public ResultSet resultSet;

    public ResultRow(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Nullable
    public <T> T get(Column<T> column) {
        return column.valueFromDB(resultSet);
    }
}
