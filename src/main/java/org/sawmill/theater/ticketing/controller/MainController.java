package org.sawmill.theater.ticketing.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    /**
     * When this method is called, it will change the Scene to a New Show form
     */
    public void showNewShowtime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Showtime.fxml"));
        Parent tableViewParent = loader.load();
        ShowtimeController controller = loader.getController();
        controller.setEditMode(false);
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * When this method is called, it will change the Scene to a Edit Show form
     */
    public void updateShowtime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Showtime.fxml"));
        Parent tableViewParent = loader.load();
        ShowtimeController controller = loader.getController();
        controller.setEditMode(true);
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * When this method is called, it will change the Scene to a Delete Show form
     */
    public void deleteShowtime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Showtime.fxml"));
        Parent tableViewParent = loader.load();
        ShowtimeController controller = loader.getController();
        controller.setDeleteMode(true);
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    
    /**
     * When this method is called, it will change the Scene to a Delete Show form
     */
    public void updateSeats(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chart.fxml"));
        Parent tableViewParent = loader.load();
        ChartController controller = loader.getController();
        Scene tableViewScene = new Scene(tableViewParent);
        controller.setScene(tableViewScene);
        controller.setUpSeatLabels();
        controller.getShowtimes();
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    public void closeApplication() {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
