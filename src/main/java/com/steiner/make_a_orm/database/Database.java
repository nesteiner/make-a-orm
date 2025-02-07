package com.steiner.make_a_orm.database;

import com.steiner.make_a_orm.exception.SQLBuildException;
import com.steiner.make_a_orm.utils.result.Result;
import jakarta.annotation.Nullable;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static class Builder {
        @Nullable
        private Driver driver;
        @Nullable
        private String url;
        @Nullable
        private String username;
        @Nullable
        private String password;

        private Builder() {
            this.driver = null;
            this.url = null;
            this.username = null;
            this.password = null;
        }

        public Builder driver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Result<Database, SQLException> build() {
            return Result.from(() -> {
                Field[] fields = getClass().getDeclaredFields();
                for (Field field: fields) {
                    try {
                        Object value = field.get(this);
                        if (value == null) {
                            throw new IllegalAccessException("there is null field in the builder");
                        }
                    } catch (IllegalAccessException e) {
                        throw new SQLException(e.getMessage());
                    }
                }

                return new Database(driver, url, username, password);
            });
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Driver driver;
    public String url;
    public String username;
    public String password;

    @Nullable
    public Connection connection;

    private Database(Driver driver, String url, String username, String password) throws SQLException {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        DriverManager.registerDriver(driver);
        this.connection = DriverManager.getConnection(url, username, password);
    }


}
