package com.steiner.make_a_orm.column;

import com.steiner.make_a_orm.column.constract.IBuildColumn;
import com.steiner.make_a_orm.column.constract.IDefaultColumn;
import com.steiner.make_a_orm.column.constract.IEqColumn;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.exception.SQLParseException;
import com.steiner.make_a_orm.operation.where.WhereStatement;
import com.steiner.make_a_orm.operation.where.impl.InList;
import com.steiner.make_a_orm.operation.where.impl.NullPredicate;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Column<T>
        implements IBuildColumn, IEqColumn<T, Column<T>>, IDefaultColumn<T> {
    public final String name;
    private boolean isNullable = false;
    private boolean isPrimaryKey = false;
    private boolean isUnique = false;
    private boolean hasDefault = false;

    @Nullable
    private T defaultValue = null;

    @Nullable
    private String defaultExpression = null;

    public Column(String name) {
        this.name = name;
    }

    @Nonnull
    public String name() {
        return this.name;
    }

    @Override
    public void setDefault(boolean hasDefault) {
        this.hasDefault = hasDefault;
    }

    @Override
    public boolean isNullable() {
        return this.isNullable;
    }

    @Override
    public void setDefaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void setDefaultExpression(@Nonnull String defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    // TODO this should be in interface
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

    public boolean isPrimary() {
        return isPrimaryKey;
    }

    public abstract String sqlType();
    public abstract String valueToDB(@Nonnull T value);

    @Override
    public String buildColumn() {
        if (hasDefault) {
            if (defaultValue != null && defaultExpression != null) {
                throw new SQLBuildException("cannot set both default value and default expression");
            }
        }

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

        if (hasDefault) {
            if (defaultValue != null) {
                builder.append(" ")
                        .append(valueToDB(defaultValue));
            } else if (defaultExpression != null) {
                builder.append(" ")
                        .append(defaultExpression);
            }
        }

        return builder.toString();
    }

    @Nullable
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

    public WhereStatement inlist(List<T> list) {
        return new InList<>(this, list);
    }
}
