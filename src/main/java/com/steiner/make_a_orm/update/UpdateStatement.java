package com.steiner.make_a_orm.update;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.exception.SQLRuntimeException;
import com.steiner.make_a_orm.table.Table;
import com.steiner.make_a_orm.transaction.Transaction;
import com.steiner.make_a_orm.where.WhereStatement;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class UpdateStatement {
    @Nonnull
    Table table;

    @Nonnull
    List<Column<?>> columns;

    @Nullable
    PreparedStatement statement;

    @Nullable
    WhereStatement whereStatement;

    @Nonnull
    List<SettingPairs<?>> settingColumns;

    public UpdateStatement(@Nonnull Table table) {
        this.table = table;
        this.columns = table.columns.stream()
                .filter(column -> !(column.isPrimaryKey && column.isAutoIncrement))
                .toList();

        this.settingColumns = new ArrayList<>();
        this.whereStatement = null;
        this.statement = null;
    }

    public <T> void set(Column<T> column, @Nullable T value) {
        int index = table.columns.indexOf(column);
        if (index == -1) {
            throw new SQLBuildException("column `%s` not exist in table `%s`".formatted(column.name, table.name));
        }

        if (!column.isNullable && value == null) {
            throw new SQLBuildException("cannot set the not null column with null");
        }

        String pattern = "`%s` = ?".formatted(column.name);
        this.settingColumns.add(new SettingPairs<>(column, pattern, value));
    }

    public void executeUpdate() {
        StringBuilder stringBuilder = new StringBuilder();
        String settingPattern = settingColumns.stream()
                .map(setting -> setting.pattern)
                .collect(Collectors.joining(", "));

        stringBuilder.append("update `%s` \nset %s".formatted(table.name, settingPattern));

        if (whereStatement != null) {
            stringBuilder.append("\nwhere ")
                    .append(WhereStatement.buildWhere(whereStatement))
                    .append(";\n");
        }

        String sql = stringBuilder.toString();
        Connection connection = Transaction.currentConnection();

        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new SQLBuildException(e);
        }

        // replace slot
        Objects.requireNonNull(statement);
        int startIndex = 1;
        for (SettingPairs<?> setting: settingColumns) {
            setting.inject(statement, startIndex);
            startIndex += 1;
        }

        if (whereStatement != null) {
            List<WhereStatement> whereStatements = WhereStatement.flatten(whereStatement);

            for (WhereStatement whereStat: whereStatements) {
                whereStat.setInjectIndex(startIndex);
                startIndex = whereStat.inject(statement);
            }
        }

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    public void where(@Nonnull WhereStatement where) {
        this.whereStatement = where;
    }

    public void where(@Nonnull Supplier<WhereStatement> supplier) {
        this.whereStatement = supplier.get();
    }
}
