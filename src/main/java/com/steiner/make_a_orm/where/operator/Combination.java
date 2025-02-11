package com.steiner.make_a_orm.where.operator;

public enum Combination {
    And("and"),
    Or("or");

    public final String sign;
    Combination(String sign) {
        this.sign = sign;
    }
}
