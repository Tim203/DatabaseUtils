package org.geysermc.databaseutils.sql;

import java.lang.Integer;
import java.lang.String;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.geysermc.databaseutils.IRepository;
import test.basic.BasicRepositorySqlImpl;

class SqlDatabaseGenerated {
    private static final boolean HAS_ASYNC = true;

    private static final List<Function<SqlDatabase, IRepository<?>>> REPOSITORIES;

    static {
        REPOSITORIES = new ArrayList<>();
        REPOSITORIES.add(BasicRepositorySqlImpl::new);
    }

    static void createEntities(SqlDatabase database) throws SQLException {
        SqlDialect dialect = database.dialect();
        try (Connection connection = database.dataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS hello (" +
                        "a " + SqlTypeMappingRegistry.sqlTypeFor(Integer.class, dialect) + ',' +
                        "b " + SqlTypeMappingRegistry.sqlTypeFor(String.class, dialect) + ',' +
                        "c " + SqlTypeMappingRegistry.sqlTypeFor(String.class, dialect) +
                        ")");
            }
        }
    }
}