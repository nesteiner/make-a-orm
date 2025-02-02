package com.steiner.make_a_orm.operation.where;

public abstract class WhereStatement implements IBuildWhere {
    public enum Comparator {
        Less("<"),
        LessEq("<="),
        Greater(">"),
        GreaterEq(">="),
        Eq("="),
        NotEq("<>");

        public final String sign;

        Comparator(String sign) {
            this.sign = sign;
        }
    }

    public enum Combination {
        And("and"),
        Or("or"),
        Not("not");

        public final String sign;
        Combination(String sign) {
            this.sign = sign;
        }
    }

    public static class LogicOperation extends WhereStatement {
        public Combination combination;
        public WhereStatement statement;

        public LogicOperation(Combination combination, WhereStatement statement) {
            this.combination = combination;
            this.statement = statement;
        }

        @Override
        public String buildWhere() {
            return "(%s %s)".formatted(combination.sign, statement.buildWhere());
        }
    }

    @Override
    public abstract String buildWhere();

    public WhereStatement and(WhereStatement other) {
        return new LogicOperation(Combination.And, other);
    }

    public WhereStatement or(WhereStatement other) {
        return new LogicOperation(Combination.Or, other);
    }

    public WhereStatement not(WhereStatement other) {
        return new LogicOperation(Combination.Not, other);
    }

}
