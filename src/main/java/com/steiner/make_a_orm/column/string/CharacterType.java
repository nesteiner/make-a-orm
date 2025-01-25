package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;

public final class CharacterType extends Column<String> {
    public int length;

    public CharacterType(String name, int length) {
        super(name);
        this.length = length;
    }

    public CharacterType(String name) {
        this(name, 256);
    }

    @Override
    public String sqlType() {
        return String.format("char(%s)", length);
    }
}
