package Tests;

import Controllers.DBController;
//import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class DBControllerTest {

    @Test
   public void insertData() {

        try{
       
        String sql = "INSERT into logs values('Tour 2','1.1.2020','10:10','109km')";


           assertEquals(true, DBController.insertData(sql));

        }
        catch (SQLException e){

        }

    }
    
    @Test
   public void getData() {

        try{
       
        String sql = "SELECT * from logs";


           assertNotEquals(null, DBController.getData(sql));

        }
        catch (SQLException e){

        }

    }
}