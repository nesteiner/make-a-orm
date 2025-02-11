package com.steiner.make_a_orm.where;

import jakarta.annotation.Nonnull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ProcessStrategy<E> {
    void process(@Nonnull PreparedStatement statement, int index, @Nonnull E value) throws SQLException;
}

