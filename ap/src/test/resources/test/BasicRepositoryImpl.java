package org.geysermc.databaseutils.generated;

import com.zaxxer.hikari.HikariDataSource;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.geysermc.databaseutils.sql.SqlDatabase;
import test.BasicRepository;
import test.TestEntity;

final class BasicRepositoryImpl implements BasicRepository {
    private final SqlDatabase database;

    private final HikariDataSource dataSource;

    BasicRepositoryImpl(SqlDatabase database) {
        this.database = database;
        this.dataSource = database.dataSource();
    }

    @Override
    public CompletableFuture<TestEntity> findByAAndB(int a, String b) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("select * from hello where (a = ? and b = ?)")) {
                    statement.setObject(0, a);
                    statement.setObject(1, b);
                    try (ResultSet result = statement.executeQuery()) {
                        if (!result.next()) {
                            return null;
                        }
                        Integer _a = (Integer) result.getObject("a");
                        String _b = (String) result.getObject("b");
                        String _c = (String) result.getObject("c");
                        return new TestEntity(_a, _b, _c);
                    }
                }
            } catch (SQLException exception) {
                throw new CompletionException("Unexpected error occurred", exception);
            }
        } , this.database.executorService());
    }

    @Override
    public CompletableFuture<Boolean> existsByAOrB(int a, String b) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("select 1 from hello where (a = ? or b = ?)")) {
                    statement.setObject(0, a);
                    statement.setObject(1, b);
                    try (ResultSet result = statement.executeQuery()) {
                        return result.next();
                    }
                }
            } catch (SQLException exception) {
                throw new CompletionException("Unexpected error occurred", exception);
            }
        } , this.database.executorService());
    }
}