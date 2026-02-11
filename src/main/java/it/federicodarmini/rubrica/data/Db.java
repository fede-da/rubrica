package it.federicodarmini.rubrica.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private final DbConfig cfg;

    public Db(DbConfig cfg) {
        this.cfg = cfg;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(cfg.jdbcUrl(), cfg.username(), cfg.password());
    }
}