package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void getDate(){
        Model m = new Model("1.1.1","12:12:12","120");

        assertEquals("1.1.1",m.getDate());

    }

    @Test
    void getDuration(){
        Model m = new Model("1.1.1","12:12:12","120");

        assertEquals("12:12:12",m.getDuration());

    }

    @Test
    void getDistance(){
        Model m = new Model("1.1.1","12:12:12","120");

        assertEquals("120",m.getDistance());

    }

}