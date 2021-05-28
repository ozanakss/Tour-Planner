package Controllers;

import Dashboard.Dashboard;
import Models.LogsModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateTour implements Initializable {

//referring to FXML elements and log element
    public static LogsModel log;

    public static void setLog(LogsModel logTemp){
        log = logTemp;
    }

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
    String stepsText;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	//setting data to empty fields related to log
        desc.setText(log.getDescription());
        date.setText(log.getDate());
        total_time.setText(log.getTotal_time());
        maxspeed.setText(log.getMaxspeed());
        minspeed.setText(log.getMinspeed());
        distance.setText(log.getDistance());
        duration.setText(log.getDuration());
        rating.setText(log.getRating());
        steps.setText(log.getSteps());

        steps.setEditable(false);

       stepsText = steps.getText();
    }

    public void updateLogClicked() throws IOException, SQLException {
       
    	//getting updated data
        String description = desc.getText();
        String dateText = date.getText();
        String durationText = duration.getText();
        String distanceText = distance.getText();
        String name = Dashboard.TOUR_NAME;
        String totaltimeText = total_time.getText();
        String ratingText = rating.getText();
        String maxspeedText = maxspeed.getText();
        String minspeedText = minspeed.getText();



        
        
        String sql = "UPDATE public.logs\r\n"
        		+ "	SET description=?, date=?, duration=?, distance=?, name=?, total_time=?, rating=?, maxspeed=?, minspeed=?, steps=?\r\n"
        		+  "\tWHERE steps='"+stepsText+"';";
        
        Connection conn = DBController.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
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


       

        System.out.println("Log Updated");

        JOptionPane.showMessageDialog(null, "Updated Log Successfully");
        
        LoggerClass.writeLog("info","Tour Data updated: Name: "+name);

        //closing current window
        Stage stage = (Stage) submit_btn.getScene().getWindow();
        stage.close();
    }
}
