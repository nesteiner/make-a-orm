package com.steiner.make_a_orm.column.constraint.impl;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.column.constraint.StandAloneConstraint;
import com.steiner.make_a_orm.table.Table;
import jakarta.annotation.Nonnull;

public class ForeignKeyConstraint extends StandAloneConstraint {
    @Nonnull
    public String columnName;

    @Nonnull
    public Table referenceTable;

    @Nonnull
    public Column<?> referenceOn;

    public ForeignKeyConstraint(@Nonnull String columnName,
                                @Nonnull String name,
                                @Nonnull Table referenceTable,
                                @Nonnull Column<?> referenceOn) {
        super(name);
        this.columnName = columnName;
        this.referenceTable = referenceTable;
        this.referenceOn = referenceOn;
    }

    @Nonnull
    @Override
    public String constraint() {
        return "constraint %s foreign key (`%s`) references `%s` (`%s`)".formatted(name, columnName, referenceTable.name, referenceOn.name);
    }
}
