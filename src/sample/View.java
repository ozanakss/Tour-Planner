package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.*;

public class View extends Application {
    //variable for counting number of tours in the database
    private int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //loading FXML resources in root object
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //setting title of window
        primaryStage.setTitle("Tour-Planner");

        //getting reference to VBOX tours
        VBox tours = (VBox) root.lookup("#tours");
        //getting reference to button addTour
        Button addTour = (Button) root.lookup("#addTour");
        //getting reference to TableView logs
        TableView logs = (TableView) root.lookup("#logs");









        //getting reference to Button deleteTour
        Button deleteTourBtn = (Button) root.lookup("#deleteTour");


        //getting database connection in c object
        Connection c = Controller.getConnection();
        //creating statement from db object in order to perform DB operations
        Statement stmt = c.createStatement();
        //running query and getting data from statement
        ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata;" );

        //Creating the mouse event handler for every tour created
        EventHandler<MouseEvent> tourClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                //getting reference to source button that was clicked
                Button calling = (Button) e.getSource();

                //getting reference to text area of description
                TextArea desc = (TextArea) root.lookup("#desc");

                //getting refeence to Label having id title
                Label title = (Label) root.lookup("#title");

                //getting database connection
                Connection c = Controller.getConnection();

                try {
                    Statement stmt = c.createStatement();
                    //getting data from database for tour clicked
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata where name = '"+calling.getText()+"';" );
                    if(rs.next()){
                        String  name = rs.getString("name");
                        String description  = rs.getString("description");
                        String  info = rs.getString("info");
                        float distance = rs.getFloat("distance");

                        //after getting data updating the description text area
                        desc.setText(description);
                        //also updating the title
                        title.setText("Title: "+name);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                //a list for updating logs table
                ObservableList<Model> data = FXCollections.observableArrayList();


                //getting references to all columns of table
                TableColumn date_col = (TableColumn)logs.getColumns().get(0);
                TableColumn duration_col = (TableColumn)logs.getColumns().get(1);
                TableColumn distance_col = (TableColumn)logs.getColumns().get(2);
                date_col.setCellValueFactory(new PropertyValueFactory<Model,String>("date"));
                duration_col.setCellValueFactory(new PropertyValueFactory<Model,String>("duration"));
                distance_col.setCellValueFactory(new PropertyValueFactory<Model,String>("distance"));

                try {
                    //getting logs data from database
                    ResultSet rs = Controller.getData("SELECT * from logs where name = '"+calling.getText()+"';" );

                    while (rs.next()){
                        String  name = rs.getString("name");
                        String date  = rs.getString("date");
                        String  duration = rs.getString("duration");
                        String distance = rs.getString("distance");

                        System.out.println("Name: "+name+" date: "+date+" duration: "+duration+" distance: "+distance);
                        //adding into list
                        data.add(new Model(date,duration,distance));




                    }
                    //updating the list in table
                    logs.setItems(data);




                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }
        };


        while ( rs.next() ) {

            String  name = rs.getString("name");
            String description  = rs.getString("description");
            String  info = rs.getString("info");
            float distance = rs.getFloat("distance");


            this.count++;

            Button temp = new Button(name);

            temp.setId("Tour"+count);

            //setting mouse event on every new button
            temp.setOnMouseClicked(tourClicked);
            //adding button in vbox
            tours.getChildren().add(temp);


        }

        //setting spacing between buttons
        tours.setSpacing(5);

        //setting padding between buttons
        tours.setPadding(new Insets(10, 10, 10, 10));





        //Creating the mouse event handler, for adding new tours
        EventHandler<MouseEvent> addTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button temp = new Button("Tour "+(++count));
                temp.setOnMouseClicked(tourClicked);
                temp.setId("Tour"+count);


                //updating new tour in database

                Connection c = Controller.getConnection();
                try {
                    Statement stmt = c.createStatement();
                    String sql = "INSERT INTO tourdata (NAME,DESCRIPTION,INFO,DISTANCE) "
                            + "VALUES ( 'Tour "+(count)+"', 'This is description of Tour "+count+"', '', 150.6 );";
                    stmt.executeUpdate(sql);


                    stmt.close();
                    c.close();



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //adding new tour button in tours VBOX
                tours.getChildren().add(temp);

            }
        };

        //Creating the mouse event handler, for deleting a tour
        EventHandler<MouseEvent> deleteTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //if there is tour then delete it otherwise print appropriate message using JOptionPane
                if(count>0){
                    Button deleteTour = (Button) root.lookup("#Tour" + count--);
                    System.out.println(deleteTour.getId());
                    System.out.println(deleteTour.getText());

                    Connection c = Controller.getConnection();
                    //delete entry from daabase too
                    try {
                        Statement stmt = c.createStatement();
                        String sql = "DELETE from tourdata where name = '" + deleteTour.getText() + "';";
                        stmt.executeUpdate(sql);


                        stmt.close();
                        c.close();


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    deleteTour.setVisible(false);



                }
                else{

                    JOptionPane.showMessageDialog(null,"No more tours");

                }

            }
        };





        addTour.setOnMouseClicked(addTourHandler);
        deleteTourBtn.setOnMouseClicked(deleteTourHandler);

        rs.close();
        stmt.close();
        c.close();








        primaryStage.setScene(new Scene(root, 462, 325));
        primaryStage.show();
    }


    //main method to run the application
    public static void main(String[] args) {
        launch(args);
    }
}
