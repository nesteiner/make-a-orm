package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.Column;

public class LongIdTable extends Table {
    public Column<Long> id = bigint( "id").autoIncrement().primaryKey();

    public LongIdTable(String name) {
        super(name);
    }
}
