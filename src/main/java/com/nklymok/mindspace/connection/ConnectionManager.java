package com.nklymok.mindspace.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/mindspace";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";
    private static final String DB_CREATE = "CREATE TABLE IF NOT EXISTS tasks(\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    header VARCHAR(80) NOT NULL,\n" +
            "    description VARCHAR(200),\n" +
            "    duedate TIMESTAMP,\n" +
            "    priority INT NOT NULL\n" +
            ");";

    private static boolean closed = false;

    private ConnectionManager() {

    }

    public static Connection openConnection() {
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

    private static void createTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DB_CREATE);
            preparedStatement.execute();
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
