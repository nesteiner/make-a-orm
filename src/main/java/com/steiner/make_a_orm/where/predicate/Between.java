package com.steiner.make_a_orm.where.predicate;

import com.steiner.make_a_orm.column.Column;
import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.where.ProcessStrategy;
import com.steiner.make_a_orm.where.WhereClause;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// T should extend Comparable<T>, but java.sql.Date does not
public class Between<T> extends WhereClause {
    public static Map<Class<?>, ProcessStrategy<?>> strategies;

    static {
        strategies = new HashMap<>();
        strategies.put(Byte.class, (ProcessStrategy<Byte>) PreparedStatement::setByte);
        strategies.put(Short.class, (ProcessStrategy<Short>) PreparedStatement::setShort);
        strategies.put(Integer.class, (ProcessStrategy<Integer>) PreparedStatement::setInt);
        strategies.put(Long.class, (ProcessStrategy<Long>) PreparedStatement::setLong);
        strategies.put(Double.class, (ProcessStrategy<Integer>) PreparedStatement::setDouble);
        strategies.put(Float.class, (ProcessStrategy<Integer>) PreparedStatement::setFloat);
        strategies.put(BigDecimal.class, (ProcessStrategy<BigDecimal>) PreparedStatement::setBigDecimal);
        strategies.put(java.sql.Date.class, (ProcessStrategy<java.sql.Date>) PreparedStatement::setDate);
        strategies.put(java.sql.Time.class, (ProcessStrategy<java.sql.Time>) PreparedStatement::setTime);
        strategies.put(java.sql.Timestamp.class, (ProcessStrategy<java.sql.Timestamp>) PreparedStatement::setTimestamp);
    }

    @Nonnull
    public final T min;

    @Nonnull
    public final T max;

    public Between(@Nonnull Column<T> column, @Nonnull T min, @Nonnull T max) {
        super(column);
        this.min = min;
        this.max = max;
    }

    @Nonnull
    @Override
    public String toSQL() {
        return "`%s` between ? and ?".formatted(column.name);
    }

    @Override
    public int inject(@Nonnull PreparedStatement statement) {
        try {
            int index = getInjectIndex();
            @SuppressWarnings("unchecked")
            ProcessStrategy<T> strategy = (ProcessStrategy<T>) strategies.get(this.min.getClass());
            strategy.process(statement, index, min);
            strategy.process(statement, index + 1, max);

            return index + 2;
        } catch (SQLException e) {
            throw new SQLBuildException("error occurs when inject", e);
        }
    }

}
