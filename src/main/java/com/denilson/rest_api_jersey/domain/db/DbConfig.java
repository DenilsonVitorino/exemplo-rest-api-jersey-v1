package com.denilson.rest_api_jersey.domain.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.denilson.rest_api_jersey.domain.exception.DbException;

public class DbConfig {

    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/denilson";
    private static String user = "postgres";
    private static String pass = "1234";

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Db Connected!");
        } catch (Exception e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Db closed Connection.");
            } catch (Exception e) {
                throw new DbException(e.getMessage(), e.getCause());
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                System.out.println("PreparedStatement closed.");
            } catch (Exception e) {
                throw new DbException(e.getMessage(), e.getCause());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                System.out.println("ResultSet closed.");
            } catch (Exception e) {
                throw new DbException(e.getMessage(), e.getCause());
            }
        }
    }
}