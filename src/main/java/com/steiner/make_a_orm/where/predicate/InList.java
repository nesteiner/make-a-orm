package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.where.WhereClause;
import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

public class InList<T> extends WhereClause {
    @Nonnull
    public List<T> list;

    public InList(@Nonnull Column<T> column, @Nonnull List<T> list) {
        super(column);
        this.list = list;
    }

    @Nonnull
    @Override
    public String toSQL() {
        String slot = list.stream()
                .map(value -> "?")
                .collect(Collectors.joining(", "));
        return "`%s` in (%s)".formatted(column.name, slot);
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        int currentIndex = getInjectIndex();

        //noinspection unchecked
        Column<T> trueColumn = (Column<T>) column;

        for (int count = 1; count <= list.size(); count += 1) {
            trueColumn.inject(statement, currentIndex, list.get(count - 1));
            currentIndex += 1;
        }

        return currentIndex + 1;
    }
}
