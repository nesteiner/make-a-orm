package com.steiner.make_a_orm.transaction;

import com.steiner.make_a_orm.database.Database;
import com.steiner.make_a_orm.exception.SQLRuntimeException;
import jakarta.annotation.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Optional;

public class Transaction {
    private static final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public static void runWith(Database database, Runnable block) {
        Optional<Connection> ifConnection = Optional.ofNullable(database.connection);
        Connection connection = ifConnection.orElseThrow(() -> new SQLRuntimeException("missing connection"));

        currentConnection.set(connection);
        @Nullable Savepoint savepoint = null;

        try {
            savepoint = connection.setSavepoint();
            connection.setAutoCommit(false);
            block.run();
            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException exp) {
                exp.printStackTrace(System.out);
                throw new SQLRuntimeException(exp);
            }
        } finally {
            currentConnection.remove();

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace(System.out);
                throw new SQLRuntimeException(e);
            }
        }
    }

    public static Connection currentConnection() {
        @Nullable Connection currentConn = currentConnection.get();
        if (currentConn == null) {
            throw new SQLRuntimeException("missing connection");
        }

        return currentConn;
    }
}
