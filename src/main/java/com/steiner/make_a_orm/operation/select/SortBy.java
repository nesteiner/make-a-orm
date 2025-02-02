package com.steiner.make_a_orm.operation.select;

public enum SortBy {
    DESC(true),
    ASC(false);

    public final boolean reverse;
    SortBy(boolean reverse) {
        this.reverse = reverse;
    }
}
