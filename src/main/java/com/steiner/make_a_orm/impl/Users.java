package com.steiner.make_a_orm.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.table.IntIdTable;

public class Users extends IntIdTable {
    public Column<String> name = characterVarying("name", 256);
    public Column<String> address = characterVarying("address", 256);
    public Column<Integer> age = integer("age").autoIncrement();
    public Users() {
        super("users");
    }
}
