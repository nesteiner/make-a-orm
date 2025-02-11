package com.steiner.make_a_orm.column.blob;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.trait.INullOrNotColumn;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.exception.SQLUnsupportException;
import jakarta.annotation.Nonnull;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class BlobColumn extends Column<InputStream> implements INullOrNotColumn<InputStream, BlobColumn> {
    public BlobColumn(String name) {
        super(name);
    }

    @Override
    public void inject(@Nonnull PreparedStatement statement, int index, @Nonnull InputStream value) {
        try {
            statement.setBinaryStream(index, value);
        } catch (SQLException e) {
            throw new SQLBuildException(e);
        }
    }

    @Nonnull
    @Override
    public String sqlType() {
        return "blob";
    }

    @Nonnull
    @Override
    public String format(@Nonnull InputStream value) {
        throw new SQLUnsupportException("cannot format a byte array");
    }

    @Nonnull
    @Override
    public BlobColumn self() {
        return this;
    }
}
