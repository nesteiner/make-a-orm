package com.steiner.make_a_orm.impl;

import com.steiner.make_a_orm.column.bool.BooleanColumn;
import com.steiner.make_a_orm.table.IntIdTable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.steiner.make_a_orm.column.numeric.*;

import java.math.BigDecimal;

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
    public final DecimalColumn column2 = decimal("decimal", 3, 2).defaultValue(BigDecimal.valueOf(1.23));
    public final DoubleColumn column3 = float64("double").defaultValue(1.23);
    public final FloatColumn column4 = float32("float").defaultValue(1.23f);
    public final SmallIntColumn column5 = smallint("smallint").defaultValue((short) 12);
    public final TinyIntColumn column6 = tinyint("tinyint").defaultValue((byte) 8);
    public final BigIntColumn column7 = bigint("bigint").defaultValue(1L);

    private NumberCollectionTable() {
        super("long-id-table");
    }
}
