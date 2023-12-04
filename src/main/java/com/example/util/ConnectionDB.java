
package com.example.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {

    // khai báo thuộc tích driver_ lấy từ mysql
    private static final String DRIVER_JDBC = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASSWORD = "300701";

    //  phương thức lấy về kết nối
    public static Connection openConnection() {
        Connection connection = null;

        try {
            // đăng ký class cho driver
            Class.forName(DRIVER_JDBC);
            //thực thi mở kết nối
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ec) {
            throw new RuntimeException(ec);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    // phương thức đóng kết nối
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(openConnection());
    }

}
