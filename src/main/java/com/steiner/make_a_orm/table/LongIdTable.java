package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.numeric.BigIntColumn;
import jakarta.annotation.Nonnull;

public class LongIdTable extends IdTable {
    @Nonnull
    public BigIntColumn id;

    public LongIdTable(@Nonnull String name, @Nonnull String idName) {
        super(name, idName);
        id = bigint(idName).autoIncrement().primaryKey();
    }

    public LongIdTable(@Nonnull String name) {
        super(name);
        id = bigint(idName).autoIncrement().primaryKey();
    }
}
