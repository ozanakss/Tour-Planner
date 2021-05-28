package Controllers;

import Dashboard.Dashboard;
import Models.LogsModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddLog

{
	
	//refering to FXML buttons creating in FXML file
    @FXML
    TextField desc;

    @FXML
    TextField total_time;

    @FXML
    TextField steps;

    @FXML
    TextField date;

    @FXML
    TextField duration;

    @FXML
    TextField distance;

    @FXML
    TextField maxspeed;

    @FXML
    TextField minspeed;

    @FXML
    TextField rating;

    @FXML
    Button submit_btn;

    public void buttonClicked() throws IOException, SQLException {
       
    	//getting user inputs in following variables
        String description = desc.getText();
        String dateText = date.getText();
        String durationText = duration.getText();
        String distanceText = distance.getText();
        String name = Dashboard.TOUR_NAME;
        String totaltimeText = total_time.getText();
        String ratingText = rating.getText();
        String maxspeedText = maxspeed.getText();
        String minspeedText = minspeed.getText();
        String stepsText = steps.getText();


       
        
        
        Connection conn = DBController.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO public.logs(\r\n"
        		+ "	description, date, duration, distance, name, total_time, rating, maxspeed, minspeed, steps)\r\n"
        		+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        
        stmt.setString(1, description);
        stmt.setString(2, dateText);
        stmt.setString(3, dateText);
        stmt.setString(4, durationText);
        stmt.setString(5, name);
        stmt.setString(6, totaltimeText);
        stmt.setString(7, ratingText);
        stmt.setString(8, maxspeedText);
        stmt.setString(9, minspeedText);
        stmt.setString(10, stepsText);
        
        stmt.executeUpdate();
        

        LoggerClass.writeLog("info","Log Added Successfully for tour: "+name);

        Stage stage = (Stage) submit_btn.getScene().getWindow();
      
        stage.close();
    }



}
