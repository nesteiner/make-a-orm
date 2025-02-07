package com.steiner.make_a_orm.column.string;

import jakarta.annotation.Nonnull;

public final class CharacterVaryingColumn extends AbstractStringColumn {
    private static final int DEFAULT_LENGTH = 256;

    private final int length;

    public CharacterVaryingColumn(String name, int length) {
        super(name);
        this.length = length;
    }

    public CharacterVaryingColumn(String name) {
        this(name, DEFAULT_LENGTH);
    }

    @Nonnull
    @Override
    public CharacterVaryingColumn self() {
        return this;
    }

    @Nonnull
    @Override
    public String sqlType() {
        return String.format("varchar(%s)", length);
    }
}
