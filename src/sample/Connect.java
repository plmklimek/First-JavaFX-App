package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
                con = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "username", "password");
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println(ex);
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println(ex);
        }
        return con;
    }

}