package com.steiner.make_a_orm;

import com.steiner.make_a_orm.impl.Users;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    @Test
    void testCreateTable() {
        Users users = new Users();

        System.out.println(users.build());
    }
}
