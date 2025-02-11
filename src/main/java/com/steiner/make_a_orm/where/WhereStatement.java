package com.steiner.make_a_orm.where;

import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.where.operator.Combination;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class WhereStatement {
    @Nonnull
    public static String buildWhere(@Nonnull WhereStatement statement) {
        Objects.requireNonNull(statement);
        StringBuilder stringBuilder = new StringBuilder();
        String first = statement.toSQL();
        stringBuilder.append(first).append(" ");

        if (statement.otherStatements != null) {
            String others = statement.otherStatements
                    .stream()
                    .map(WhereStatement::toSQL)
                    .collect(Collectors.joining(" "));

            stringBuilder.append(others);
        }

        return stringBuilder.toString();
    }

    @Nonnull
    public static List<WhereStatement> flatten(@Nonnull WhereStatement statement) {
        List<WhereStatement> whereStatements = new ArrayList<>();

        if (statement.shouldInject) {
            whereStatements.add(statement);
        }

        if (statement.otherStatements != null) {
            whereStatements.addAll(
                    statement.otherStatements
                            .stream()
                            .filter(stat -> stat.shouldInject)
                            .toList()
            );
        }

        return whereStatements;
    }

    public boolean shouldInject;
    private int injectIndex;

    @Nullable
    public List<WhereStatement> otherStatements;

    public WhereStatement(boolean shouldInject, int injectIndex) {
        this.shouldInject = shouldInject;
        this.injectIndex = injectIndex;
        this.otherStatements = null;
    }

    @Nonnull
    public WhereStatement and(@Nonnull WhereStatement other) {
        if (this.otherStatements == null) {
            this.otherStatements = new LinkedList<>();
        }

        this.otherStatements.add(new WhereCombination(Combination.And, other));
        return this;
    }

    @Nonnull
    public WhereStatement or(@Nonnull WhereStatement other) {
        if (this.otherStatements == null) {
            this.otherStatements = new LinkedList<>();
        }

        this.otherStatements.add(new WhereCombination(Combination.Or, other));
        return this;
    }

    @Nonnull
    public WhereStatement not() {
        return new WhereNot(this);
    }

    @Nonnull
    public abstract String toSQL();

    // 返回下一 ? 占位符的位置，从 1 开始
    public abstract int inject(@Nonnull PreparedStatement statement);

    // ATTENTION this should be very important
    public void setInjectIndex(int index) {
        if (!shouldInject) {
            throw new SQLBuildException("cannot inject index when should not inject");
        }

        if (index == -1) {
            throw new SQLBuildException("cannot set index with -1");
        }

        this.injectIndex = index;
    }

    public int getInjectIndex() {
        if (!shouldInject) {
            throw new SQLBuildException("cannot get inject value when should not inject");
        }

        return injectIndex;
    }
}
