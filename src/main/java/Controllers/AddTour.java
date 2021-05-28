package Controllers;

import Dashboard.Dashboard;
import javafx.fxml.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import  javafx.scene.control.*;

public class AddTour {
	
	//refering to FXML controls created in FXML file

    @FXML
    TextField to;

    @FXML
    TextField from;

    @FXML
    TextField tourname;

    @FXML
    TextField desc;

    @FXML
    TextField info;

    @FXML
    TextField distance;

    @FXML
    Button submit_btn;

    public void addTourtoDB() throws Exception {
    	
    	//geting all user inputs in following variables

        String description = desc.getText();
        String distanceText = distance.getText();
        String name = tourname.getText();
        String toText = to.getText();
        String fromText = from.getText();
        String infoText = info.getText();

       
        Connection c = DBController.getConnection();
        PreparedStatement stmt = c.prepareStatement("INSERT INTO public.tourdata(\r\n"
        		+ "	name, description, info, distance, \"from\", \"to\")\r\n"
        		+ "	VALUES (?, ?, ?, ?, ?, ?);");
        
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.setString(3, infoText);
        stmt.setString(4, distanceText);
        stmt.setString(5, fromText);
        stmt.setString(6, toText);
        
        stmt.executeUpdate();
     


        LoggerClass.writeLog("info","Tour Added Successfully with name: "+name);



        
        //closing current window

        Stage stage;
        stage = (Stage) submit_btn.getScene().getWindow();
        new Dashboard().start(stage);
        stage.show();

    }





    }
