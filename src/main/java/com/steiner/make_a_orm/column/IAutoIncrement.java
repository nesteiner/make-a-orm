package com.steiner.make_a_orm.column;

public interface IAutoIncrement<Self> {
    boolean isAutoIncrement();
    Self autoIncrement();
}
