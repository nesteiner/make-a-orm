package com.steiner.make_a_orm;

import com.mysql.cj.jdbc.Driver;
import com.steiner.make_a_orm.database.Database;
import com.steiner.make_a_orm.utils.result.Result;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class StatementTest {
    Driver driver = Result.from(Driver::new).get();
    Database database = Database.builder()
            .driver(driver)
            .username("steiner")
            .password("779151714")
            .url("jdbc:mysql://localhost/playground")
            .build()
            .get();

    @Test
    public void testPrepareStatement() throws SQLException {
        Connection connection = Optional.ofNullable(database.connection).orElseThrow();
        PreparedStatement statement = connection.prepareStatement("age = ?");
        statement.setInt(1, 10);

    }
}
