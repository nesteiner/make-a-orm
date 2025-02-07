package com.steiner.make_a_orm.column.string;

import jakarta.annotation.Nonnull;

public final class CharacterColumn extends AbstractStringColumn {
    private static final int DEFAULT_LENGTH = 256;

    private final int length;

    public CharacterColumn(String name, int length) {
        super(name);
        this.length = length;
    }

    public CharacterColumn(String name) {
        this(name, DEFAULT_LENGTH);
    }

    @Nonnull
    @Override
    public String sqlType() {
        return String.format("char(%s)", length);
    }

    @Nonnull
    @Override
    public CharacterColumn self() {
        return this;
    }
}
