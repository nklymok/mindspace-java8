package com.nklymok.mindspace.connection;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;
    private static String DB_DRIVER;
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_CREATE;
    private static String DB_ADDCOLUMN;

    private static boolean closed = false;

    private ConnectionManager() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(this.getClass().getResource("/sql.properties").toURI())));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        DB_DRIVER = properties.getProperty("sql.driver");
        DB_URL = properties.getProperty("sql.url");
        DB_USERNAME = properties.getProperty("sql.username");
        DB_PASSWORD = properties.getProperty("sql.password");
        DB_CREATE = properties.getProperty("sql.createdb");
        DB_ADDCOLUMN = properties.getProperty("sql.modifycolumn");
    }

    private static final class Helper {
        private static final ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return Helper.instance;
    }

    public Connection openConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
                closed = false;
                createTable();
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Failed to connect");
        }
    }

    private void createTable() {
        try {
            PreparedStatement ps1 = connection.prepareStatement(DB_CREATE);
            ps1.execute();
            PreparedStatement ps2 = connection.prepareStatement(DB_ADDCOLUMN);
            ps2.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                closed = true;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static boolean isClosed() {
        return closed;
    }
}
