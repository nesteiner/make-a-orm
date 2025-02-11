package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.blob.BlobColumn;
import com.steiner.make_a_orm.column.blob.LongBlobColumn;
import com.steiner.make_a_orm.column.blob.MediumBlobColumn;
import com.steiner.make_a_orm.column.bool.BooleanColumn;
import com.steiner.make_a_orm.column.clob.LongTextColumn;
import com.steiner.make_a_orm.column.clob.MediumTextColumn;
import com.steiner.make_a_orm.column.date.DateColumn;
import com.steiner.make_a_orm.column.date.TimeColumn;
import com.steiner.make_a_orm.column.date.TimestampColumn;
import com.steiner.make_a_orm.column.numeric.*;
import com.steiner.make_a_orm.column.string.CharacterColumn;
import com.steiner.make_a_orm.column.string.CharacterVaryingColumn;
import com.steiner.make_a_orm.column.string.TextColumn;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.insert.InsertStatement;
import com.steiner.make_a_orm.select.Query;
import com.steiner.make_a_orm.update.UpdateStatement;
import com.steiner.make_a_orm.where.WhereStatement;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Table {
    @Nonnull
    public String name;
    @Nonnull
    public List<Column<?>> columns;

    public Table(@Nonnull String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    @Nonnull
    public final String toSQL() {
        // check first
        long count = columns.stream().filter(column -> column.isPrimaryKey).count();
        if (count > 1) {
            throw new SQLBuildException("cannot set multi primary key in a table");
        }

        count = columns.stream().filter(column -> column.isAutoIncrement).count();
        if (count > 1) {
            throw new SQLBuildException("cannot set multi auto increment column in a table");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists `%s` (\n\t".formatted(name));

        // append fields and constraints
        stringBuilder.append(
                columns.stream()
                        .map(column -> "`%s` %s %s".formatted(column.name, column.sqlType(), column.inlineConstraints()))
                        .collect(Collectors.joining(",\n\t"))
        );

        if (!columns.isEmpty()) {
            stringBuilder.append(",\n\t");
        }

        // append standalone constraints
        for (Column<?> column: columns) {
            String constraints = String.join(",\n\t", column.standAloneConstraints());
            stringBuilder.append(constraints);
        }


        stringBuilder.append("\n) ");

        // append suffix constraint, only one
        columns.stream()
                .filter(column -> column.isAutoIncrement)
                .findFirst()
                .flatMap(Column::suffixConstraints)
                .ifPresent(stringBuilder::append);

        stringBuilder.append(";");
        return stringBuilder.toString();
    }

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

    public final void insert(@Nonnull Consumer<InsertStatement> consumer) {
        InsertStatement insertStatement = new InsertStatement(this);
        consumer.accept(insertStatement);
        insertStatement.executeInsert();
    }

    public final void update(@Nonnull WhereStatement where, @Nonnull Consumer<UpdateStatement> consumer) {
        UpdateStatement updateStatement = new UpdateStatement(this);
        consumer.accept(updateStatement);
        updateStatement.where(where);
        updateStatement.executeUpdate();
    }

    public final void update(@Nonnull Supplier<WhereStatement> supplier, @Nonnull Consumer<UpdateStatement> consumer) {
        UpdateStatement updateStatement = new UpdateStatement(this);
        consumer.accept(updateStatement);
        updateStatement.where(supplier);
        updateStatement.executeUpdate();
    }

    public final void update(@Nonnull Consumer<UpdateStatement> consumer) {
        UpdateStatement updateStatement = new UpdateStatement(this);
        consumer.accept(updateStatement);
        updateStatement.executeUpdate();
    }

    @Nonnull
    public final BlobColumn blob(@Nonnull String name) {
        BlobColumn column = new BlobColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final MediumBlobColumn mediumBlob(@Nonnull String name) {
        MediumBlobColumn column = new MediumBlobColumn(name);
        columns.add(column);

        return column;
    }

    @Nonnull
    public final LongBlobColumn longBlob(@Nonnull String name) {
        LongBlobColumn column = new LongBlobColumn(name);
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
    public final LongTextColumn longText(@Nonnull String name) {
        LongTextColumn column = new LongTextColumn(name);
        columns.add(column);
        return column;
    }

    @Nonnull
    public final MediumTextColumn mediumText(@Nonnull String name) {
        MediumTextColumn column = new MediumTextColumn(name);
        columns.add(column);
        return column;
    }

    @Nonnull
    public final DateColumn date(@Nonnull String name) {
        DateColumn column = new DateColumn(name);
        columns.add(column);
        return column;
    }

    @Nonnull
    public final TimeColumn time(@Nonnull String name) {
        TimeColumn column = new TimeColumn(name);
        columns.add(column);
        return column;
    }

    @Nonnull
    public final TimestampColumn timestamp(@Nonnull String name) {
        TimestampColumn column = new TimestampColumn(name);
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
    public final DecimalColumn decimal(@Nonnull String name, int precision, int scale) {
        DecimalColumn column = new DecimalColumn(name, precision, scale);
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

    @Nonnull
    public final IntColumn integer(@Nonnull String name) {
        IntColumn column = new IntColumn(name);
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
    public final TinyIntColumn tinyint(@Nonnull String name) {
        TinyIntColumn column = new TinyIntColumn(name);
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
    public final CharacterVaryingColumn characterVarying(@Nonnull String name, int length) {
        CharacterVaryingColumn column = new CharacterVaryingColumn(name, length);
        columns.add(column);
        return column;
    }

    @Nonnull
    public final TextColumn text(@Nonnull String name) {
        TextColumn column = new TextColumn(name);
        columns.add(column);
        return column;
    }

}
