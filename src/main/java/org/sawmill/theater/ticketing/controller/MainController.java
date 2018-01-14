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
import javafx.scene.shape.Polygon;
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
    
    public void setTheatreService(TheatreService service) {
        this.theatreService = service;
    }
    
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
    
    /**
     * When this method is called, it will change the Scene to a New showtime form
     */
    public void showNewShowtime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewShowtime.fxml"));
        Parent tableViewParent = loader.load();
        ShowtimeController controller = loader.getController();
        controller.setTheatreService(theatreService);
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
