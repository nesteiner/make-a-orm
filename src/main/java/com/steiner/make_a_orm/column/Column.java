package com.steiner.make_a_orm.column;

public abstract class Column<T> {
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

    public final String build() {
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
}
