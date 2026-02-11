package it.federicodarmini.rubrica.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConfig {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private DbConfig(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public static DbConfig loadFromFile(String path) throws IOException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            p.load(fis);
        }

        String host = p.getProperty("mysql.host", "127.0.0.1").trim();
        int port = Integer.parseInt(p.getProperty("mysql.port", "3306").trim());
        String db = p.getProperty("mysql.database", "rubrica").trim();
        String user = p.getProperty("mysql.username").trim();
        String pass = p.getProperty("mysql.password", "");

        return new DbConfig(host, port, db, user, pass);
    }

    public String jdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database +
                "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }

    public String username() { return username; }
    public String password() { return password; }
}
