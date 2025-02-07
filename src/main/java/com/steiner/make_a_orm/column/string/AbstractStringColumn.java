package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;
import jakarta.annotation.Nonnull;

public abstract class AbstractStringColumn extends Column<String> {
    public AbstractStringColumn(String name) {
        super(name);
    }

    public abstract String sqlType();

    @Override
    public String valueToDB(@Nonnull String value) {
        return "'%s'".formatted(value);
    }
}
