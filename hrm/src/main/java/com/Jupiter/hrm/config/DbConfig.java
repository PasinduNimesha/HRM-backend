package com.Jupiter.hrm.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    public static Connection getConnection() throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jupiter", "root", "30104771");
            System.out.println("Connection success");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return con;
    }
}


