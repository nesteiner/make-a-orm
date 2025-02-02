package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.IEqColumn;
import org.jetbrains.annotations.NotNull;

public final class CharacterType extends Column<String> implements IEqColumn<String, Column<String>> {
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

    @NotNull
    @Override
    public Column<String> self() {
        return this;
    }
}
