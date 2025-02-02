package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.IAutoIncrement;
import com.steiner.make_a_orm.column.numeric.IntegerType;
import com.steiner.make_a_orm.column.string.CharacterType;
import com.steiner.make_a_orm.column.string.CharacterVaryingType;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.operation.select.Query;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Table {
    public String name;
    public List<Column<?>> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public String build() {
        String columnStrings = columns.stream()
                .map(column -> "\t" + column.buildColumn())
                .collect(Collectors.joining(",\n"));

        // TODO add primary key
        List<Column<?>> primaryKeys = columns.stream()
                .filter(column -> column.isPrimaryKey)
                .toList();

        int autoIncrementCount = columns.stream()
                .filter(column -> column instanceof IAutoIncrement<?>)
                .map(column -> (IAutoIncrement<?>) column)
                .map(column -> column.isAutoIncrement() ? 1 : 0)
                .reduce(0, Integer::sum);


        if (primaryKeys.size() > 1) {
            throw new SQLBuildException("too many primary key");
        }

        if (autoIncrementCount > 1) {
            throw new SQLBuildException("too many auto increment field");
        }

        if (primaryKeys.size() == 1) {
            Column<?> primaryKey = primaryKeys.get(0);
            columnStrings += String.format(",\n\tprimary key(`%s`)", primaryKey.name);
        }

        return String.format("create table if not exists `%s` (\n%s\n);", name, columnStrings);
    }

    @Nonnull
    public CharacterVaryingType characterVarying(@Nonnull String name, int length) {
        CharacterVaryingType column = new CharacterVaryingType(name, length);
        columns.add(column);

        return column;
    }

    @Nonnull
    public CharacterType character(@Nonnull String name, int length) {
        CharacterType column = new CharacterType(name, length);
        columns.add(column);

        return column;
    }

    @Nonnull
    public IntegerType integer(@Nonnull String name) {
        IntegerType column = new IntegerType(name);
        columns.add(column);

        return column;
    }

    /**
     * example
     * Users.selectAll
     */

    @Nonnull
    public Query select(Column<?> column, Column<?>... otherColumns) {
        List<Column<?>> columns = new LinkedList<>();
        columns.add(column);
        columns.addAll(List.of(otherColumns));

        return new Query(this, columns.toArray(Column<?>[]::new));
    }

    @Nonnull
    public Query selectAll() {
        return new Query(this, columns.toArray(Column<?>[]::new));
    }
}
