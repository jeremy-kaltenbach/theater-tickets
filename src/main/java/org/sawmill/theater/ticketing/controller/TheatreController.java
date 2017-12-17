package org.sawmill.theater.ticketing.controller;

import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheatreController {
    
    @Autowired
    private TheatreService theatreService;
    
    private Stage primaryStage;
    
    @FXML
    private Button btnAddShowtime;
    
    @FXML
    private Polygon e36;
    @FXML
    private Polygon d42;
    @FXML
    private Polygon c38;
    @FXML
    private Polygon b34;
    @FXML
    private Polygon a30;
    
    public void getShowtimes() {
        List<Showtime> showtimeList = theatreService.getShowtimes();
        
        System.out.println("Found: " + showtimeList.size() + " showtimes");
        for (Showtime st : showtimeList) {
            System.out.println("----------Showtime--------------");
            System.out.println("Show: " + st.getShowName());
            System.out.println("Group: " + st.getTheatreGroup());
            System.out.println("Showtime: " + st.getShowDate());
            System.out.println("Last Updated: " + st.getLastUpdated());
        }
    }
    
    @FXML
    public void showNewShowtime() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NewShowtime.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showMain() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            primaryStage.setScene(new Scene(root));  
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closeApplication() {
        Platform.exit();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
            
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
}
