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
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.sawmill.theater.ticketing.service.TheatreServiceImpl;

public class MainController implements Initializable {
    
    private TheatreService theatreService = new TheatreServiceImpl();
    
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
        
        // Check if there are any shows added first. If not, then alert the user
        if (theatreService.getShowtimes().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Shows Found");
            alert.setHeaderText("There are no shows to update. Please create one first.");
            alert.show();
        } else {
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
    }
    
    /**
     * When this method is called, it will change the Scene to a Delete Show form
     */
    public void deleteShowtime(ActionEvent event) throws IOException {
        
        // Check if there are any shows added first. If not, then alert the user
        if (theatreService.getShowtimes().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Shows Found");
            alert.setHeaderText("There are no shows to delete. Please create one first.");
            alert.show();
        } else {
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
    }
    
    
    /**
     * When this method is called, it will change the Scene to the seat chart for updating seats
     */
    public void updateSeats(ActionEvent event) throws IOException {
        
        // Check if there are any shows added first. If not, then alert the user
        if (theatreService.getShowtimes().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Shows Found");
            alert.setHeaderText("No shows found for updating seats. Please create one first.");
            alert.show();
        } else {
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
    }
    
    public void printTickets(ActionEvent event) throws IOException {
        
                // Check if there are any shows added first. If not, then alert the user
        if (theatreService.getShowtimes().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Shows Found");
            alert.setHeaderText("No shows found for printing tickets. Please create one first.");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chart.fxml"));
            Parent tableViewParent = loader.load();
            ChartController controller = loader.getController();
            Scene tableViewScene = new Scene(tableViewParent);
            controller.setScene(tableViewScene);
            controller.setUpSeatLabels();
            controller.getShowtimes();
            controller.setPrintMode(true);

            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }
    
    public void closeApplication() {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
