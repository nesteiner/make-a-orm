package com.steiner.make_a_orm;

import com.mysql.cj.jdbc.Driver;
import com.steiner.make_a_orm.database.Database;
import com.steiner.make_a_orm.table.Users;
import com.steiner.make_a_orm.transaction.Transaction;
import com.steiner.make_a_orm.utils.SchemaUtils;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DatabaseTest {
    @Test
    public void testCreateTable() throws SQLException {

        Database database = Database.builder()
                .driver(new Driver())
                .url("jdbc:mysql://localhost/playground")
                .username("steiner")
                .password("779151714")
                .build();

        Transaction.runWith(database, () -> {
            Users userTable = new Users("users");
            SchemaUtils.create(userTable);
        });
    }

    @Test
    public void testQuery() throws SQLException {
        Database database = Database.builder()
                .driver(new Driver())
                .url("jdbc:mysql://localhost/playground")
                .username("steiner")
                .password("779151714")
                .build();

        Transaction.runWith(database, () -> {
            Users userTable = new Users("users");
            userTable.selectAll()
                    .where(userTable.name.eq("name2"))
                    .stream()
                    .findFirst()
                    .ifPresent(resultRow -> {
                        int id = resultRow.get(userTable.id);
                        String name = resultRow.get(userTable.name);
                        String address = resultRow.get(userTable.address);
                        int age = resultRow.get(userTable.age);

                        System.out.printf("id=%s, name=%s, address=%s, age=%s\n", id, name, address, age);
                    });

        });
    }
}
