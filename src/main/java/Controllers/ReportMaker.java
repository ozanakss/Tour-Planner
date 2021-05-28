package Controllers;

import Dashboard.Dashboard;
import Models.LogsModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMaker {

    public static void generateTourReport(){
        String tourname = Dashboard.TOUR_NAME;
        try{

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 10);


            String file_name = "Reports\\"+tourname+".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();

        String pargraph1 = " Following are details for "+tourname+"\n\n";

            ResultSet rs = DBController.getData( "SELECT * FROM tourdata where name = '"+tourname+"';" );
            if(rs.next()){
                String  name = rs.getString("name");
                String description  = rs.getString("description");
                String  info = rs.getString("info");
                String distance = rs.getString("distance");
                String to =  rs.getString("to");
                String from =  rs.getString("from");

                pargraph1+= "From: "+from+"\n";
                pargraph1+= "To: "+to+"\n";
                pargraph1+= "name: "+name+"\n";
                pargraph1+= "distance: "+distance+"\n";
                pargraph1+= "description: "+description+"\n";
                pargraph1+= "info: "+info+"\n";


            }

            pargraph1+=" \nLogs\n\n";


            Paragraph paragraph = new Paragraph(pargraph1);


            //specify column widths
            float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Description", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Date", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Duration", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Distance", Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(table, "Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Total Time", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Rating", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Max Speed", Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(table, "Min Speed", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Steps", Element.ALIGN_RIGHT, 1, bfBold12);

            table.setHeaderRows(1);

            try {
                //getting logs data from database
                 rs = DBController.getData("SELECT * from logs where name = '"+Dashboard.TOUR_NAME+"';" );

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


                    
                    //adding into list

                    //insert column headings
                    insertCell(table, description, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, date, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, duration, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, distance, Element.ALIGN_RIGHT, 1, bfBold12);

                    insertCell(table, name, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, total_time, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, rating, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, maxspeed, Element.ALIGN_RIGHT, 1, bfBold12);

                    insertCell(table, minspeed, Element.ALIGN_RIGHT, 1, bfBold12);
                    insertCell(table, steps, Element.ALIGN_RIGHT, 1, bfBold12);




                }





            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            paragraph.add(table);
            paragraph.add("\n\n");
            document.add(paragraph);

            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            ImageIO.write( SwingFXUtils.fromFXImage( Dashboard.mapImage, null ), "png", byteOutput );

            com.itextpdf.text.Image  graph;
            graph = com.itextpdf.text.Image.getInstance( byteOutput.toByteArray() );

            document.add(graph);

            JOptionPane.showMessageDialog(null, "Report Saved Successfully!");
            LoggerClass.writeLog("info","Report Saved Successfully!");


        document.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    public static void summaryReportMaker(){


        String file_name = "Reports\\Summary.pdf";
        try{

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 10);



            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();

            ResultSet resultset = DBController.getData("Select * from tourdata");

            while(resultset.next()) {
                String tourname = resultset.getString("name");


                String pargraph1 = " Following are details for " + tourname + "\n\n";

                ResultSet rs = DBController.getData("SELECT * FROM tourdata where name = '" + tourname + "';");
                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String info = rs.getString("info");
                    String distance = rs.getString("distance");
                    String to = rs.getString("to");
                    String from = rs.getString("from");

                    pargraph1 += "From: " + from + "\n";
                    pargraph1 += "To: " + to + "\n";
                    pargraph1 += "name: " + name + "\n";
                    pargraph1 += "distance: " + distance + "\n";
                    pargraph1 += "description: " + description + "\n";
                    pargraph1 += "info: " + info + "\n";


                }

                pargraph1 += " \nLogs\n\n";


                Paragraph paragraph = new Paragraph(pargraph1);


                //specify column widths
                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                //create PDF table with the given widths
                PdfPTable table = new PdfPTable(columnWidths);
                // set table width a percentage of the page width
                table.setWidthPercentage(90f);

                //insert column headings
                insertCell(table, "Description", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Date", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Duration", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Distance", Element.ALIGN_RIGHT, 1, bfBold12);

                insertCell(table, "Name", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Total Time", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Rating", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Max Speed", Element.ALIGN_RIGHT, 1, bfBold12);

                insertCell(table, "Min Speed", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Steps", Element.ALIGN_RIGHT, 1, bfBold12);

                table.setHeaderRows(1);

                try {
                    //getting logs data from database
                    rs = DBController.getData("SELECT * from logs where name = '" + tourname + "';");

                    while (rs.next()) {
                        String name = rs.getString("name");
                        String date = rs.getString("date");
                        String duration = rs.getString("duration");
                        String distance = rs.getString("distance");
                        String description = rs.getString("description");
                        String total_time = rs.getString("total_time");
                        String rating = rs.getString("rating");
                        String maxspeed = rs.getString("maxspeed");
                        String minspeed = rs.getString("minspeed");
                        String steps = rs.getString("steps");


                        System.out.println("Name: " + name + " date: " + date + " duration: " + duration + " distance: " + distance + " rating " + rating);
                        //adding into list

                        //insert column headings
                        insertCell(table, description, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, date, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, duration, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, distance, Element.ALIGN_RIGHT, 1, bfBold12);

                        insertCell(table, name, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, total_time, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, rating, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, maxspeed, Element.ALIGN_RIGHT, 1, bfBold12);

                        insertCell(table, minspeed, Element.ALIGN_RIGHT, 1, bfBold12);
                        insertCell(table, steps, Element.ALIGN_RIGHT, 1, bfBold12);


                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                paragraph.add(table);
                paragraph.add("\n\n");
                document.add(paragraph);

            }



            JOptionPane.showMessageDialog(null, "Report Saved Successfully!");

            LoggerClass.writeLog("info","Report Saved Successfully - Summary!");

            document.close();

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
