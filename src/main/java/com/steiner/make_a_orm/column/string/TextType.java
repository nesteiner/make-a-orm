package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.IEqColumn;
import org.jetbrains.annotations.NotNull;

public class TextType extends Column<String> implements IEqColumn<String, Column<String>> {
    public TextType(String name) {
        super(name);
    }

    @Override
    public String sqlType() {
        return "text";
    }

    @NotNull
    @Override
    public Column<String> self() {
        return this;
    }
}
