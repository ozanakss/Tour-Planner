package Controllers;
import javax.swing.plaf.nimbus.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import Dashboard.Dashboard;

public class DBController {
	
	public static String DB_URL, USER, PASSWORD;
    //method for connecting to our PostgreSQL Database
    public static Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
        	
        	
        	  File configFile = new File("Files/Config.txt");
              try {
              	//getting map key from config text file
                  Scanner fileScanner = new Scanner(configFile);
                  DB_URL= ""+fileScanner.nextLine();
                  USER = ""+fileScanner.nextLine();
                  PASSWORD = ""+fileScanner.nextLine();
                 Dashboard.KEY = ""+fileScanner.nextLine();
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              }
            //refering PostgreSQL database driver
            Class.forName("org.postgresql.Driver");
            //getting jdbc connection
            c = DriverManager
                    .getConnection(DB_URL,
                    		USER,PASSWORD);
            //when database is fully connected then printing this
            LoggerClass.writeLog("info","Logined into Database!");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;

    }
    //this method is for inserting anything in database using SQL Query
    public static boolean insertData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();



            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

            return true;
    }
    
    //this method is for inserting anything in database using SQL Query
    public static boolean deleteData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();



            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

            return true;
    }

    //this method is for executing select statement on database to return data using ResultSet
    public static ResultSet getData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( sql);

        return rs;

    }

}