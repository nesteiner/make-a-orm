package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.numeric.IntegerType;
import com.steiner.make_a_orm.column.string.CharacterType;
import com.steiner.make_a_orm.column.string.CharacterVaryingType;
import com.steiner.make_a_orm.exception.SQLBuildException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Table {
    private static final Logger logger = LoggerFactory.getLogger(Table.class);

    public String name;
    public List<Column<?>> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public String build() {
        String columnStrings = columns.stream()
                .map(column -> "\t" + column.build())
                .collect(Collectors.joining(",\n"));

        // TODO add primary key
        List<Column<?>> primaryKeys = columns.stream()
                .filter(column -> column.isPrimaryKey)
                .toList();

        if (primaryKeys.size() > 1) {
            throw new SQLBuildException("too many primary key");
        }

        if (primaryKeys.size() == 1) {
            Column<?> primaryKey = primaryKeys.get(0);
            columnStrings += String.format(",\n\tprimary key(`%s`)", primaryKey.name);
        }

        return String.format("create table %s (\n%s\n);", name, columnStrings);
    }

    @NotNull
    public CharacterVaryingType characterVarying(@NotNull String name, int length) {
        CharacterVaryingType column = new CharacterVaryingType(name, length);
        columns.add(column);

        return column;
    }

    @NotNull
    public CharacterType character(@NotNull String name, int length) {
        CharacterType column = new CharacterType(name, length);
        columns.add(column);

        return column;
    }

    @NotNull
    public IntegerType integer(@NotNull String name) {
        IntegerType column = new IntegerType(name);
        columns.add(column);

        return column;
    }


}
