package com.steiner.make_a_orm.column.string;

import com.steiner.make_a_orm.column.Column;

public class TextType extends Column<String> {
    public TextType(String name) {
        super(name);
    }

    @Override
    public String sqlType() {
        return "text";
    }
}
