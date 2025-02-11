package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.numeric.IntColumn;
import jakarta.annotation.Nonnull;

public abstract class IntIdTable extends IdTable {
    @Nonnull
    public IntColumn id;

    public IntIdTable(@Nonnull String name, @Nonnull String idName) {
        super(name, idName);
        id = integer(idName).autoIncrement().primaryKey();
    }

    public IntIdTable(String name) {
        super(name);
        id = integer(idName).autoIncrement().primaryKey();
    }
}
