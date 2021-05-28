package Tests;

import Models.LogsModel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class LogsModelTest {

    @Test
    public void getDate(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, null, null);

        assertEquals("1.1.1",m.getDate());

    }

    @Test
    public  void getDuration(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, null, null);

        assertEquals("12:12:12",m.getDuration());

    }

    @Test
    public  void getDistance(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, null, null);

        assertEquals("120",m.getDistance());

    }
    
    @Test
    public  void getName(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", "Tour 1", null, null, null, null, null, null);

        assertEquals("Tour 1",m.getName());

    }
    
    
    @Test
    public  void getDescription(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, "Test", null, null, null, null, null);

        assertEquals("Test",m.getDescription());

    }
    
    
    @Test
    public  void getRating(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, "5", "5", null, null, null);

        assertEquals("5",m.getRating());

    }
    
    
    @Test
    public  void getMaxSpeed(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, "120", "120", null, null);

        assertEquals("120",m.getMaxspeed());

    }
    
    
    @Test
    public  void getMinSpeed(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, "10", null);

        assertEquals("10",m.getMinspeed());

    }
    
    
    @Test
    public  void getSteps(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, null, "1000");

        assertEquals("1000",m.getSteps());

    }
    
    @Test
    public void getDate2(){
        LogsModel m = new LogsModel("10/12/2020","12:12:12","120", null, null, null, null, null, null, null);

        assertEquals("10/12/2020",m.getDate());

    }

    @Test
    public  void getDuration2(){
        LogsModel m = new LogsModel("1.1.1","10hrs","120", null, null, null, null, null, null, null);

        assertEquals("10hrs",m.getDuration());

    }

    @Test
    public  void getDistance2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120km", null, null, null, null, null, null, null);

        assertEquals("120km",m.getDistance());

    }
    
    @Test
    public  void getName2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", "Tour 6", null, null, null, null, null, null);

        assertEquals("Tour 6",m.getName());

    }
    
    
    @Test
    public  void getDescription2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, "Test 2", null, null, null, null, null);

        assertEquals("Test 2",m.getDescription());

    }
    
    
    @Test
    public  void getRating2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, "5", "4", null, null, null);

        assertEquals("4",m.getRating());

    }
    
    
    @Test
    public  void getMaxSpeed2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, "120", "250", null, null);

        assertEquals("250",m.getMaxspeed());

    }
    
    
    @Test
    public  void getMinSpeed2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, "6", null);

        assertEquals("6",m.getMinspeed());

    }
    
    
    @Test
    public  void getSteps2(){
        LogsModel m = new LogsModel("1.1.1","12:12:12","120", null, null, null, null, null, null, "362");

        assertEquals("362",m.getSteps());

    }
    
    
   

}