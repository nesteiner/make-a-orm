package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;

public final class CharacterVaryingType extends Column<String> {
    public int length;

    public CharacterVaryingType(String name, int length) {
        super(name);
        this.name = name;
        this.length = length;
    }

    public CharacterVaryingType(String name) {
        this(name, 256);
    }

    @Override
    public String sqlType() {
        return String.format("varchar(%s)", length);
    }
}
