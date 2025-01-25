package com.steiner.make_a_orm;

import com.mysql.cj.jdbc.Driver;
import com.steiner.make_a_orm.impl.Users;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {
    static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String USER = "steiner";
    static final String PASSWORD = "779151714";
    static final String URL = "jdbc:mysql://localhost/playground";
    @Test
    void testCreateTable() {
        Users users = new Users();

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            Statement statement = connection.createStatement();
            String sql = users.build();
            statement.execute(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
