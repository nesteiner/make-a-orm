package com.steiner.make_a_orm.operation.where;

import com.steiner.make_a_orm.column.Column;
import jakarta.annotation.Nullable;
import java.util.List;

public abstract class WhereClause<T> extends WhereStatement {
    // field
    public Column<T> column;

    @Nullable
    public List<WhereClause<?>> otherExpressions;

    public WhereClause(Column<T> column) {
        this.column = column;
        this.otherExpressions = null;
    }
}
