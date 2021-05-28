package Dashboard;

import Controllers.AddLog;
import Controllers.DBController;
import Controllers.ReportMaker;
import Controllers.UpdateTour;
import Models.LogsModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Dashboard extends Application {
  
    //static variable to refer TOUR NAME
    public static String TOUR_NAME;   
    //this static variable will hold reference to current tour clicked button
    public static Button currentButton;
    //this static variable will hold reference to FROM location of tour
    public static String FROM;
  //this static variable will hold reference to TO location of tour
    public static String TO;
  //this static variable will hold reference to IMAGE of map of tour
    public static Image mapImage;

    //variable for holding key for map
    public static String KEY;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //loading FXML resources in root object
        Parent root = FXMLLoader.load(getClass().getResource("../Views/dashboard.fxml"));
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
        Connection c = DBController.getConnection();
        //creating statement from db object in order to perform DB operations
        Statement stmt = c.createStatement();
        //running query and getting data from statement
        ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata;" );
        //getting reference to text area of description
        TextArea desc = (TextArea) root.lookup("#desc");
        //Creating the mouse event handler for every tour created
        EventHandler<MouseEvent> tourClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                //getting reference to source button that was clicked
                Button calling = (Button) e.getSource();
                //setting tour name to tour clicked for using it
                TOUR_NAME = calling.getText();
                //setting current button clicked to this variable
                currentButton = (Button) e.getSource();



                //getting refeence to Label having id title
                Label title = (Label) root.lookup("#title");

                //getting database connection
                Connection c = DBController.getConnection();

                try {
                    Statement stmt = c.createStatement();
                    //getting data from database for tour clicked
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata where name = '"+calling.getText()+"';" );
                    if(rs.next()){
                    	//getting all data related to tour 
                        String  name = rs.getString("name");
                        String description  = rs.getString("description");
                        String  info = rs.getString("info");
                        String distance = rs.getString("distance");
                        FROM =  rs.getString("from");
                        TO =  rs.getString("to");

                        //after getting data updating the description text area
                        desc.setText(description);
                        //also updating the title
                        title.setText("Title: "+name);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                //a list for updating logs table
                ObservableList<LogsModel> data = FXCollections.observableArrayList();


                //getting references to all columns of table
                TableColumn date_col = (TableColumn)logs.getColumns().get(0);
                TableColumn duration_col = (TableColumn)logs.getColumns().get(1);
                TableColumn distance_col = (TableColumn)logs.getColumns().get(2);
                TableColumn name_col = (TableColumn)logs.getColumns().get(3);
                TableColumn desc_col = (TableColumn)logs.getColumns().get(4);
                TableColumn totalTime_col = (TableColumn)logs.getColumns().get(5);
                TableColumn rating_col = (TableColumn)logs.getColumns().get(6);
                TableColumn max_speed_col = (TableColumn)logs.getColumns().get(7);
                TableColumn min_speed_col = (TableColumn)logs.getColumns().get(8);
                TableColumn steps_col = (TableColumn)logs.getColumns().get(9);



                date_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("date"));
                duration_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("duration"));
                distance_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("distance"));

                name_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("name"));
                desc_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("description"));
                totalTime_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("total_time"));

                rating_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("rating"));
                max_speed_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("maxspeed"));
                min_speed_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("minspeed"));

                steps_col.setCellValueFactory(new PropertyValueFactory<LogsModel,String>("steps"));

                try {
                    //getting logs data from database
                    ResultSet rs = DBController.getData("SELECT * from logs where name = '"+calling.getText()+"';" );

                    while (rs.next()){
                        String  name = rs.getString("name");
                        String date  = rs.getString("date");
                        String  duration = rs.getString("duration");
                        String distance = rs.getString("distance");
                        String  description = rs.getString("description");
                        String total_time  = rs.getString("total_time");
                        String  rating = rs.getString("rating");
                        String maxspeed = rs.getString("maxspeed");
                        String  minspeed = rs.getString("minspeed");
                        String steps  = rs.getString("steps");


                        System.out.println("Name: "+name+" date: "+date+" duration: "+duration+" distance: "+distance+" rating "+rating);
                        //adding into list
                        data.add(new LogsModel(date, duration, distance,name,description,total_time,  rating,  maxspeed, minspeed, steps ));




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
                String distance = rs.getString("distance");


             

                Button temp = new Button(name);

              

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


                try {

                    primaryStage.close();
                    
                    //opening next window on button clicked and closing current window


                    URL fxmlLocation = getClass().getResource("../Views/add_tour.fxml");

                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    //   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_tour.fxml"));
                    Parent root1 = (Parent) loader.load();
                    Stage stage = new Stage();
                    ///stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("Add New Tour");
                    stage.setScene(new Scene(root1));
                    stage.show();



                } catch (Exception ed){
                    System.out.println(ed);
                }

            }
        };

        //Creating the mouse event handler, for deleting a tour
        EventHandler<MouseEvent> deleteTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //if there is tour then delete it otherwise print appropriate message using JOptionPane
           {



                Connection c = DBController.getConnection();
                //delete entry from daabase too
                try {
                    Statement stmt = c.createStatement();
                    String sql = "DELETE from tourdata where name = '" + TOUR_NAME + "';";
                    stmt.executeUpdate(sql);

                     sql = "DELETE from LOGS where name = '" + TOUR_NAME + "';";

                    stmt.executeUpdate(sql);
                    stmt.close();
                    c.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                currentButton.setVisible(false);



            }
              

        }
        };



        EventHandler<MouseEvent> routeHandler = new EventHandler<MouseEvent>(){

          

            @Override
            public void handle(MouseEvent event) {

              


                try {
                    //this is URL for sending RESTful request to MapQuest API
                    URL url = new URL("https://www.mapquestapi.com/directions/v2/route?key="+KEY+"&" +
                            "from="+FROM+"&to="+TO+"" +
                            "&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&" +
                            "enhancedNarrative=false&avoidTimedConditions=false");
                   //For sending HTTP request
                    HttpURLConnection con = null;
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    //getting response from MapQuest API
                    int status = con.getResponseCode();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    //getting input in string object
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

               //     System.out.println(content.toString());
                    //getting response as JSON Object
                    JSONObject myResponse = new JSONObject(content.toString());
                    //getting route JSON from main JSON
                    JSONObject routeObject = myResponse.getJSONObject("route");

                    System.out.println(routeObject);
                    //getting another JSON from route JSON
                    JSONObject boundingBox = routeObject.getJSONObject("boundingBox");
                    //getting lr and ul parameters
                    JSONObject lr = boundingBox.getJSONObject("lr");

                    JSONObject ul = boundingBox.getJSONObject("ul");

                    System.out.println(boundingBox);
                    //geting lattitude and langitude from ul and lr objects
                    String boundingBoxString = ul.getDouble("lat")+","+ul.getDouble("lng")+","+lr.getDouble("lat")+","+lr.getDouble("lng");


                    //creating a session for MapQuest API
                    String session = routeObject.getString("sessionId");
                    //API key
                    String key = "OXbb6HQzyC4dqRfdDJyLAbBjtTDiFHK4";
                    //URL for MapQuest Api request
                     url = new URL("https://www.mapquestapi.com/staticmap/v5/map?key=OXbb6HQzyC4dqRfdDJyLAbBjtTDiFHK4&size=640,480&defaultMarker=none" +
                             "&zoom=11&rand=737758036&session="+session +
                             "&boundingBox="+boundingBoxString);
                    System.out.println(url);

                    System.out.println("boundingBox: "+boundingBoxString);
                    System.out.println("sessionId: "+session);

                    con = (HttpURLConnection) url.openConnection();
                    //sending request
                    con.setRequestMethod("GET");
                    //getting response
                    status = con.getResponseCode();

                    System.out.println(status);

                    InputStream image = con.getInputStream();
//
                   BufferedImage img = ImageIO.read(image);
                   //getting image for map
                    mapImage = SwingFXUtils.toFXImage(img, null);

                    //Setting the image view
                    ImageView imageView = new ImageView(mapImage);



                    //setting the fit height and width of the image view
                    imageView.setFitHeight(640);
                    imageView.setFitWidth(480);

                    //Setting the preserve ratio of the image view
                    imageView.setPreserveRatio(true);

                    Menu fileMenu = new Menu("File");
                    MenuItem item1 = new MenuItem("Download");

                    fileMenu.getItems().addAll(item1);
                    MenuBar menuBar = new MenuBar(fileMenu);
                    menuBar.setTranslateX(0);
                    menuBar.setTranslateY(0);

                    imageView.setTranslateX(0);
                    imageView.setTranslateY(30);



                    //writing image to Photos/ folder on download click
                    item1.setOnAction(
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        ImageIO.write(img,"jpg", new File("Photos/"+TOUR_NAME+".jpg"));
                                        JOptionPane.showMessageDialog(null,"Image Saved Successfully");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );


                    //Creating a Group object
                    Group root = new Group(menuBar,imageView);

                    //Creating a scene object
                    Scene scene = new Scene(root, 640, 480);

                    //Setting title to the Stage
                    Stage stage = new Stage();

                    imageView.fitWidthProperty().bind(stage.widthProperty());
                    stage.setTitle("Route");

                    //Adding scene to the stage
                    stage.setScene(scene);

                    //Displaying the contents of the stage
                    stage.show();


                    con.disconnect();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Button route = (Button) root.lookup("#route");
        route.setOnMouseClicked(routeHandler);


        //handler for description of tour
        EventHandler<MouseEvent> descButton = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {

                desc.setText("See Map for your guidance");


            }
        };
        //setting event for description button, add tour button and delete tour button
        Button descBtn = (Button) root.lookup("#discBtn");
        descBtn.setOnMouseClicked(descButton);
        addTour.setOnMouseClicked(addTourHandler);
        deleteTourBtn.setOnMouseClicked(deleteTourHandler);

        rs.close();
            stmt.close();
            c.close();

        Button addtour_btn = (Button) root.lookup("#addtour_btn");

        
        EventHandler<MouseEvent> addtour_btn_event = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {

                try {

                	//on clicking add tour opening a new window

                    URL fxmlLocation = getClass().getResource("../Views/add_log.fxml");

                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    Parent root1 = (Parent) loader.load();
                    Stage stage = new Stage();
                   
                    stage.setTitle("Add tour log");
                    stage.setScene(new Scene(root1));
                    stage.show();

                } catch (Exception ed){
                    System.out.println(ed);
                }


            }
        };

        addtour_btn.setOnMouseClicked(addtour_btn_event);
        
        
        //generating report

        Button reportBtn = (Button) root.lookup("#report");

        EventHandler<MouseEvent> singleReportHandler = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {

           ReportMaker.generateTourReport();


            }
        };

        reportBtn.setOnMouseClicked(singleReportHandler);


        //generating summary report
        Button summaryBtn = (Button) root.lookup("#summary");

        EventHandler<MouseEvent> summaryReportHandler = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {

                ReportMaker.summaryReportMaker();


            }
        };

        summaryBtn.setOnMouseClicked(summaryReportHandler);


        Button editTableBtn = (Button) root.lookup("#editTable");

        EventHandler<MouseEvent> editTableHandler = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {


                LogsModel selected = (LogsModel) logs.getSelectionModel().getSelectedItem();
                UpdateTour.setLog(selected);

                try {


                	//editing tour in a new window

                    URL fxmlLocation = getClass().getResource("../Views/update_tour.fxml");

                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    Parent root1 = (Parent) loader.load();
                    Stage stage = new Stage();
                 
                    stage.setTitle("Edit Log");
                    stage.setScene(new Scene(root1));
                    stage.show();

                } catch (Exception ed){
                    System.out.println(ed);
                }






            }
        };

        editTableBtn.setOnMouseClicked(editTableHandler);
        
        Button deleteLogButton = (Button) root.lookup("#deleteLog");

        EventHandler<MouseEvent> deleteLogHandler = new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent e) {


                LogsModel selected = (LogsModel) logs.getSelectionModel().getSelectedItem();
                
                logs.getItems().remove(selected);
                
                String sql = "DELETE FROM logs where name='"+selected.getName()+"' AND maxspeed='"+selected.getMaxspeed()+"'";
                
                try {
					DBController.deleteData(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}





            }
        };

        deleteLogButton.setOnMouseClicked(deleteLogHandler);










        primaryStage.setScene(new Scene(root, 462, 325));
        primaryStage.show();
    }




    //main method to run the application
    public static void main(String[] args) {
        launch(args);
    }
}
