package com.steiner.make_a_orm.table;

import com.steiner.make_a_orm.column.numeric.IntegerType;
import com.steiner.make_a_orm.column.string.CharacterVaryingType;

public class Users extends IntIdTable {
    public CharacterVaryingType name = characterVarying("name", 256);
    public CharacterVaryingType address = characterVarying("address", 256);
    public IntegerType age = integer("age");

    public Users(String name) {
        super(name);
    }
}
