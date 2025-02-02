package com.steiner.make_a_orm.column;

import com.steiner.make_a_orm.exception.SQLParseException;
import com.steiner.make_a_orm.operation.where.WhereStatement;
import com.steiner.make_a_orm.operation.where.impl.InList;
import com.steiner.make_a_orm.operation.where.impl.NullPredicate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Column<T> implements IBuildColumn {
    public String name;
    public boolean isNullable = false;
    public boolean isPrimaryKey = false;
    public boolean isUnique = false;

    public Column(String name) {
        this.name = name;
    }

    public Column<T> nullable() {
        this.isNullable = true;
        return this;
    }

    public Column<T> primaryKey() {
        this.isPrimaryKey = true;
        return this;
    }

    public Column<T> uniqueIndex() {
        this.isUnique = true;
        return this;
    }

    public abstract String sqlType();

    @Override
    public String buildColumn() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("`%s`", name))
                .append(" ")
                .append(sqlType());

        if (!isNullable) {
            builder.append(" ")
                    .append("not null");
        }

        if (isUnique) {
            builder.append(" ")
                    .append("unique");
        }

        return builder.toString();
    }

    public T valueFromDB(ResultSet result) {
        try {
            return (T) result.getObject(name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLParseException(e.getMessage());
        }
    }

    public WhereStatement isnull() {
        return new NullPredicate<>(this, true);
    }

    public WhereStatement notnull() {
        return new NullPredicate<>(this, false);
    }

    public WhereStatement inList(List<T> list) {
        return new InList<>(this, list);
    }
}
