package sample;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTests {

    @Test
    void insertData() {

        try{
            Connection c = Controller.getConnection();
            Statement statement = c.createStatement();
            String sql = "INSERT into logs values('Tour 2','1.1.2020','10:10','109km')";


            assertEquals(true,Controller.insertData(sql));

        }
        catch (SQLException e){

        }

    }
}