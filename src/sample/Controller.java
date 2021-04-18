package sample;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Controller {
    public static Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/tour-planner",
                            "postgres", "postgres");
            System.out.println("Opened database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;

    }

    public static boolean insertData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();



            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

            return true;
    }

    public static ResultSet getData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( sql);

        return rs;

    }

}