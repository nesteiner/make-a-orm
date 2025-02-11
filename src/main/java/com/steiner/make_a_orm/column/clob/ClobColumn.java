package com.steiner.make_a_orm.column.clob;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLUnsupportException;
import jakarta.annotation.Nonnull;

import java.io.Reader;

public abstract class ClobColumn extends Column<Reader> {
    public ClobColumn(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public String format(@Nonnull Reader value) {
        throw new SQLUnsupportException("too large to return a value");
    }
}
