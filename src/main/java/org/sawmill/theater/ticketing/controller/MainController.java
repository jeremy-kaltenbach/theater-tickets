package org.sawmill.theater.ticketing.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController implements Initializable {
    
    @Autowired
    private TheatreService theatreService;
    
    @FXML
    private Button btnAddShowtime;
    
    
    public void setTheatreService(TheatreService service) {
        this.theatreService = service;
    }
    
    /**
     * When this method is called, it will change the Scene to a New Show form
     */
    public void showNewShowtime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Showtime.fxml"));
        Parent tableViewParent = loader.load();
        ShowtimeController controller = loader.getController();
        controller.setTheatreService(theatreService);
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
        controller.setTheatreService(theatreService);
        controller.setEditMode(true);
        Scene tableViewScene = new Scene(tableViewParent);
        
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
