package cc.carm.study.practicum.student.database;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;
import cc.carm.lib.easysql.api.enums.ForeignKeyRule;
import cc.carm.lib.easysql.api.enums.IndexType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

public enum DataTables implements SQLTable {

    USERS("users", table -> {
        table.addColumn("username", "VARCHAR(36) NOT NULL PRIMARY KEY");
        table.addColumn("hashed_password", "TEXT NOT NULL");
        table.addColumn("cid", "CHAR(18) NOT NULL");
        table.addColumn("phone", "CHAR(11) NOT NULL");
        table.addColumn("create_time", "DATETIME NOT NULL");
    }),

    STUDENTS("students", table -> {
        table.addColumn("id", "VARCHAR(36) NOT NULL PRIMARY KEY");
        table.addColumn("name", "VARCHAR(32) NOT NULL");
        table.addColumn("age", "TINYINT UNSIGNED NOT NULL DEFAULT 0");
        table.addColumn("address", "TEXT NOT NULL");
        table.addColumn("create_time", "DATETIME NOT NULL");
    });;

    private final @Nullable String tableName;
    private final @Nullable Consumer<TableCreateBuilder> builder;
    private SQLManager manager;

    DataTables(@Nullable String tableName,
               @Nullable Consumer<TableCreateBuilder> builder) {
        this.tableName = tableName;
        this.builder = builder;
    }

    public boolean create(@NotNull SQLManager sqlManager) throws SQLException {
        if (this.manager == null) this.manager = sqlManager;

        TableCreateBuilder tableBuilder = sqlManager.createTable(getTableName());
        if (builder != null) builder.accept(tableBuilder);
        return tableBuilder.build().executeFunction(l -> l > 0, false);
    }

    @Override
    public @Nullable SQLManager getSQLManager() {
        return this.manager;
    }

    public @NotNull String getTableName() {
        return Optional.ofNullable(this.tableName).orElse(name().toLowerCase(Locale.ROOT));
    }

    public static void initializeTables(@NotNull SQLManager sqlManager) throws SQLException {
        for (DataTables value : values()) {
            value.create(sqlManager);
        }
    }
}