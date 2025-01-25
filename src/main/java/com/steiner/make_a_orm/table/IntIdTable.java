package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;

public abstract class IntIdTable extends Table {
    public Column<Integer> id = integer("id").primaryKey();

    public IntIdTable(String name) {
        super(name);
    }
}
