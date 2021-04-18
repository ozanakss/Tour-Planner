package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;

public class View extends Application {
    //variable for counting number of tours in the database
    private int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tour-Planner");

        // reference to VBOX tours
        VBox tours = (VBox) root.lookup("#tours");
        // reference to button addTour
        Button addTour = (Button) root.lookup("#addTour");
        //getting reference to TableView logs
        TableView logs = (TableView) root.lookup("#logs");









        // reference   deleteTour
        Button deleteTourBtn = (Button) root.lookup("#deleteTour");


        // database connection
        Connection c = Controller.getConnection();
        //creating statement
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata;" );
        TextArea desc = (TextArea) root.lookup("#desc");

        // mouse event handler for every tour
        EventHandler<MouseEvent> tourClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                // reference  source button that was clicked
                Button calling = (Button) e.getSource();



                // refeence  Label having id title
                Label title = (Label) root.lookup("#title");

                // database connection
                Connection c = Controller.getConnection();

                try {
                    Statement stmt = c.createStatement();
                    // data from database  tour clicked
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata where name = '"+calling.getText()+"';" );
                    if(rs.next()){
                        String  name = rs.getString("name");
                        String description  = rs.getString("description");
                        String  info = rs.getString("info");
                        float distance = rs.getFloat("distance");

                        desc.setText(description);

                        // title update
                        title.setText("Title: "+name);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                // liste log table
                ObservableList<Model> data = FXCollections.observableArrayList();


                // references  auf alle Tabellenspalten
                TableColumn date_col = (TableColumn)logs.getColumns().get(0);
                TableColumn duration_col = (TableColumn)logs.getColumns().get(1);
                TableColumn distance_col = (TableColumn)logs.getColumns().get(2);
                date_col.setCellValueFactory(new PropertyValueFactory<Model,String>("date"));
                duration_col.setCellValueFactory(new PropertyValueFactory<Model,String>("duration"));
                distance_col.setCellValueFactory(new PropertyValueFactory<Model,String>("distance"));

                try {
                    // logs data from db
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
                    //update list in table
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

            // mouse event on alle neue buttons
            temp.setOnMouseClicked(tourClicked);
            //add button
            tours.getChildren().add(temp);


        }

        //Abstand zwischen  Tasten
        tours.setSpacing(5);

        tours.setPadding(new Insets(10, 10, 10, 10));





        // Hinzufügen neuer Touren
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

                tours.getChildren().add(temp);

            }
        };

        //zum Löschen einer Tour
        EventHandler<MouseEvent> deleteTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(count>0){
                    Button deleteTour = (Button) root.lookup("#Tour" + count--);
                    System.out.println(deleteTour.getId());
                    System.out.println(deleteTour.getText());

                    Connection c = Controller.getConnection();
                    //// Eintrag  aus Datenbank löschen
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


        EventHandler<MouseEvent> routeHandler = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {

                try {

                    URL url = new URL("https://www.mapquestapi.com/directions/v2/route?key=OXbb6HQzyC4dqRfdDJyLAbBjtTDiFHK4&" +
                            "from=wien&to=graz" +
                            "&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&" +
                            "enhancedNarrative=false&avoidTimedConditions=false");

                    // HTTP request
                    HttpURLConnection con = null;
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    //antwort
                    int status = con.getResponseCode();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    //st in
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

                    // response  JSON Objekt
                    JSONObject myResponse = new JSONObject(content.toString());

                    JSONObject routeObject = myResponse.getJSONObject("route");

                    System.out.println(routeObject);
                    //boundingBox
                    JSONObject boundingBox = routeObject.getJSONObject("boundingBox");
                    // kriegen lr und ul parameters
                    JSONObject lr = boundingBox.getJSONObject("lr");

                    JSONObject ul = boundingBox.getJSONObject("ul");

                    System.out.println(boundingBox);
                    // lr ul
                    String boundingBoxString = ul.getDouble("lat")+","+ul.getDouble("lng")+","+lr.getDouble("lat")+","+lr.getDouble("lng");


                    // MapQuest API
                    String session = routeObject.getString("sessionId");
                    //API key
                    String key = "OXbb6HQzyC4dqRfdDJyLAbBjtTDiFHK4";
                    //URL
                    url = new URL("https://www.mapquestapi.com/staticmap/v5/map?key=OXbb6HQzyC4dqRfdDJyLAbBjtTDiFHK4&size=640,480&defaultMarker=none" +
                            "&zoom=11&rand=737758036&session="+session +
                            "&boundingBox="+boundingBoxString);
                    System.out.println(url);

                    System.out.println("boundingBox: "+boundingBoxString);
                    System.out.println("sessionId: "+session);

                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    status = con.getResponseCode();

                    System.out.println(status);

                    InputStream image = con.getInputStream();
//
                    BufferedImage img = ImageIO.read(image);
                    //bild for map
                    Image mapImage = SwingFXUtils.toFXImage(img, null);


                    ImageView imageView = new ImageView(mapImage);
                    imageView.setFitHeight(640);
                    imageView.setFitWidth(480);

                    imageView.setPreserveRatio(true);



                    //gruppe und szene objekt
                    Group root = new Group(imageView);

                    Scene scene = new Scene(root, 640, 480);

                    Stage stage = new Stage();

                    imageView.fitWidthProperty().bind(stage.widthProperty());
                    stage.setTitle("Route");

                    //hinzu
                    stage.setScene(scene);


                    stage.show();


                    con.disconnect();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Button route = (Button) root.lookup("#route");
        route.setOnMouseClicked(routeHandler);





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
