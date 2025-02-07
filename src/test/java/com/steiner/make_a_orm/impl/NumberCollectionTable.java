package com.steiner.make_a_orm.impl;

import com.steiner.make_a_orm.table.IntIdTable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.steiner.make_a_orm.column.numeric.*;

public class NumberCollectionTable extends IntIdTable {
    @Nullable
    private static NumberCollectionTable singleton = null;

    @Nonnull
    public static NumberCollectionTable getInstance() {
        if (singleton == null) {
            singleton = new NumberCollectionTable();
        }

        return singleton;
    }

    public final BooleanColumn column1 = bool("boolean").defaultValue(false);
    public final DecimalColumn column2 = decimal("decimal");
    public final DoubleColumn column3 = float64("double");
    public final FloatColumn column4 = float32("float");
    public final SmallIntColumn column5 = smallint("smallint");
    public final TinyIntColumn column6 = tinyint("tinyint");

    private NumberCollectionTable() {
        super("long-id-table");
    }
}
