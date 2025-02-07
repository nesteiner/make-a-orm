package com.steiner.make_a_orm.column.string;

import jakarta.annotation.Nonnull;

public class TextColumn extends AbstractStringColumn {
    public TextColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public TextColumn self() {
        return this;
    }

    @Override
    public String sqlType() {
        return "text";
    }
}
