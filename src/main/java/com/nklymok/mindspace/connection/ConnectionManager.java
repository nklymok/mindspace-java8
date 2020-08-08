package com.nklymok.mindspace.connection;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager {
    private static Logger logger = Logger.getLogger(ConnectionManager.class);
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

    private ConnectionManager() {

    }

    public static Connection openConnection() {
        logger.info("Connection is being opened");
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
                createTable();
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                logger.error("Failed to connect");
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
        logger.info("Connection is being closed");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
