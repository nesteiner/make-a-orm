package com.steiner.make_a_orm.operation.select;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.operation.where.WhereStatement;
import com.steiner.make_a_orm.table.Table;
import com.steiner.make_a_orm.transaction.Transaction;
import com.steiner.make_a_orm.utils.GlobalLogger;
import com.steiner.make_a_orm.utils.result.Result;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Query implements Spliterator<ResultRow> {
    private static final String COUNT_NAME = "total";

    public Table fromTable;
    public List<Column<?>> fields;

    @Nullable
    public ResultSet resultSet;

    @Nullable
    public ResultRow resultRow;

    @Nullable
    public WhereStatement whereStatement;

    @Nullable
    public Column<?> orderBy;
    public boolean reverse;

    @Nullable
    public Long limit;

    @Nullable
    public Long offset;

    public boolean isCount;

    public Connection connection;

    public Query(Table fromTable, Column<?>... columns) {
        // TODO check if columns are all in table


        this.fromTable = fromTable;
        this.fields = Arrays.asList(columns);
        this.orderBy = null;
        this.reverse = false;
        this.resultSet = null;
        this.resultRow = null;
        this.whereStatement = null;
        this.limit = null;
        this.offset = null;

        this.isCount = false;

        this.connection = Transaction.currentConnection();
    }

    public Query where(@Nonnull WhereStatement whereStatement) {
        this.whereStatement = whereStatement;
        return this;
    }

    public Query where(@Nonnull Supplier<WhereStatement> block) {
        this.whereStatement = block.get();
        return this;
    }

    public Query orderBy(@Nonnull Column<?> column) {
        this.orderBy = column;
        return this;
    }

    public Query orderBy(@Nonnull Column<?> column, @Nonnull SortBy sortBy) {
        this.orderBy = column;
        this.reverse = sortBy.reverse;
        return this;
    }

    public Query limit(long value) {
        this.limit = value;
        return this;
    }

    public Query offset(long value) {
        this.offset = value;
        return this;
    }

    public long count() {
        this.isCount = true;
        String sql = buildQuery();

        return Result.from(() -> {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (!result.next()) {
                 return 0L;
            } else {
                return result.getLong(COUNT_NAME);
            }
        }).orElse(0L);
    }

    public String buildQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        String fieldNames = fields.stream().map(field -> "`%s`".formatted(field.name)).collect(Collectors.joining(", "));
        String mainPart = null;

        if (isCount) {
            mainPart = "select count(*) as %s from `%s`".formatted(COUNT_NAME, fromTable.name);
        } else {
            mainPart = "select %s from `%s`".formatted(fieldNames, fromTable.name);
        }

        stringBuilder.append(mainPart);
        if (whereStatement != null) {
            stringBuilder.append(" ")
                    .append("where")
                    .append(" ")
                    .append(whereStatement.buildWhere());
        }

        if (orderBy != null) {
            stringBuilder.append(" ")
                    .append("order by %s".formatted(orderBy.name));

            if (reverse) {
                stringBuilder.append(" desc");
            }
        }

        if (limit != null) {
            stringBuilder.append(" ")
                    .append("limit %s".formatted(limit));
        }

        if (offset != null) {
            stringBuilder.append(" ")
                    .append("offset %s".formatted(offset));
        }

        return stringBuilder.toString();
    }

    public Stream<ResultRow> stream() {
        try {
            String sql = buildQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            GlobalLogger.logger().info("exec query: {}", sql);

            this.resultSet = statement.executeQuery();
            if (this.resultSet == null) {
                throw new SQLException("cannot access result set");
            }

            this.resultRow = new ResultRow(this.resultSet);
            return StreamSupport.stream(this, false);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return Stream.empty();
        }
    }

    @Override
    public boolean tryAdvance(@Nonnull Consumer<? super ResultRow> action) {
        boolean hasNext = Optional.ofNullable(this.resultSet).map(value -> {
            try {
                return value.next();
            } catch (SQLException e) {
                return false;
            }
        }).orElse(false);

        if (hasNext) {
            action.accept(this.resultRow);
        }

        return hasNext;
    }

    @Override
    @Nullable
    public Spliterator<ResultRow> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED | Spliterator.IMMUTABLE;
    }
}
