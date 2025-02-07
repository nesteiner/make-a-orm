package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constract.IAutoIncrement;
import com.steiner.make_a_orm.column.numeric.*;
import com.steiner.make_a_orm.column.string.CharacterColumn;
import com.steiner.make_a_orm.column.string.CharacterVaryingColumn;
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

    public final String build() {
        String columnStrings = columns.stream()
                .map(column -> "\t" + column.buildColumn())
                .collect(Collectors.joining(",\n"));

        // TODO add primary key
        List<Column<?>> primaryKeys = columns.stream()
                .filter(Column::isPrimary)
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
    public final CharacterVaryingColumn characterVarying(@Nonnull String name, int length) {
        CharacterVaryingColumn column = new CharacterVaryingColumn(name, length);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final CharacterColumn character(@Nonnull String name, int length) {
        CharacterColumn column = new CharacterColumn(name, length);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final TinyIntColumn tinyint(@Nonnull String name) {
        TinyIntColumn column = new TinyIntColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final SmallIntColumn smallint(@Nonnull String name) {
        SmallIntColumn column = new SmallIntColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final IntColumn integer(@Nonnull String name) {
        IntColumn column = new IntColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final BigIntColumn bigint(@Nonnull String name) {
        BigIntColumn column = new BigIntColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final BooleanColumn bool(@Nonnull String name) {
        BooleanColumn column = new BooleanColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final DecimalColumn decimal(@Nonnull String name) {
        DecimalColumn column = new DecimalColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final DoubleColumn float64(@Nonnull String name) {
        DoubleColumn column = new DoubleColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final FloatColumn float32(@Nonnull String name) {
        FloatColumn column = new FloatColumn(name);
        columns.add(column);

        return column;
    }




    /**
     * example
     * Users.selectAll
     */

    @Nonnull
    public final Query select(Column<?> column, Column<?>... otherColumns) {
        List<Column<?>> columns = new LinkedList<>();
        columns.add(column);
        columns.addAll(List.of(otherColumns));

        return new Query(this, columns.toArray(Column<?>[]::new));
    }

    @Nonnull
    public final Query selectAll() {
        return new Query(this, columns.toArray(Column<?>[]::new));
    }
}
