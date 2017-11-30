package org.sawmill.theater.ticketing.controller;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheatreController {
    
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
    
    public void getShowtimes() {
        List<Showtime> showtimeList = theatreService.getShowtimes();
        
        System.out.println("Found: " + showtimeList.size() + " showtimes");
        for (Showtime st : showtimeList) {
            System.out.println("----------Showtime--------------");
            System.out.println("Show: " + st.getShowName());
            System.out.println("Group: " + st.getTheatreGroup());
        }
    }  
}
