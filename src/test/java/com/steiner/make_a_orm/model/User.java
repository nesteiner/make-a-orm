package com.steiner.make_a_orm.model;

public class User {
    public int id;
    public String name;
    public String address;
    public int age;

    public User(int id, String name, String address, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }
}
