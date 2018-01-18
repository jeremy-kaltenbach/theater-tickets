/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FXML Controller class
 *
 * @author jeremy
 */
public class ChartController implements Initializable {
    
    
    private static final String[] SECTION_ONE_SEAT_LABELS = 
      {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "b1", "b2", "b3", "b4", "b5", "b6", 
       "b7", "b8", "b9", "b10", "b11", "b12", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", 
       "c12", "c13", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "d11", "d12", "d13", "d14", 
       "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10", "e11", "e12", "e13", "e14", "e15"};
    
    private static final String[] SECTION_TWO_SEAT_LABELS = 
      {"a12", "a13", "a14", "a15", "b13", "b14", "b15", "b16", "b17", "c14", "c15", "c16", "c17", "c18", "c19",
       "d15", "d16", "d17", "d18", "d19", "d20", "d21", "e16", "e17", "e18"};
    
    private static final String[] SECTION_THREE_SEAT_LABELS = 
      {"a16", "a17", "a18", "a19", "b18", "b19", "b20", "b21", "b22", "c20", "c21", "c22", "c23", "c24", "c25",
       "d22", "d23", "d24", "d25", "d26", "d27", "d28", "e19", "e20", "e21"};
    
    private static final String[] SECTION_FOUR_SEAT_LABELS = 
      {"a20", "a21", "a22", "a23", "a24", "a25", "a26", "a27", "a28", "a29", "a30", "b23", "b24", "b25", "b26", 
       "b27", "b28", "b29", "b30", "b31", "b32", "b33", "b34", "c26", "c27", "c28", "c29", "c30", "c31", "c32", 
       "c33", "c34", "c35", "c36", "c37", "c38", "d29", "d30", "d31", "d32", "d33", "d34", "d35", "d36", "d37", 
       "d38", "d39", "d40", "d41", "d42", "e22", "e23", "e24", "e25", "e26", "e27", "e28", "e29", "e30", "e31", 
       "e32", "e33", "e34", "e35", "e36"};
    
    @Autowired
    private TheatreService theatreService;

    @FXML
    private Rectangle e36;
    @FXML
    private Rectangle d42;
    @FXML
    private Rectangle c38;
    @FXML
    private Rectangle b34;
    @FXML
    private Rectangle a30;
    @FXML
    private Rectangle a1;
    @FXML
    private Rectangle b1;
    @FXML
    private Rectangle c1;
    @FXML
    private Rectangle d1;
    @FXML
    private Rectangle e1;
    @FXML
    private Rectangle e35;
    @FXML
    private Rectangle d41;
    @FXML
    private Rectangle c37;
    @FXML
    private Rectangle b33;
    @FXML
    private Rectangle a29;
    @FXML
    private Rectangle a2;
    @FXML
    private Rectangle b2;
    @FXML
    private Rectangle c2;
    @FXML
    private Rectangle d2;
    @FXML
    private Rectangle e2;
    @FXML
    private Rectangle e34;
    @FXML
    private Rectangle d40;
    @FXML
    private Rectangle c36;
    @FXML
    private Rectangle b32;
    @FXML
    private Rectangle a28;
    @FXML
    private Rectangle a3;
    @FXML
    private Rectangle b3;
    @FXML
    private Rectangle c3;
    @FXML
    private Rectangle d3;
    @FXML
    private Rectangle e3;
    @FXML
    private Rectangle e33;
    @FXML
    private Rectangle d39;
    @FXML
    private Rectangle c35;
    @FXML
    private Rectangle b31;
    @FXML
    private Rectangle a27;
    @FXML
    private Rectangle a4;
    @FXML
    private Rectangle b4;
    @FXML
    private Rectangle c4;
    @FXML
    private Rectangle d4;
    @FXML
    private Rectangle e4;
    @FXML
    private Rectangle e32;
    @FXML
    private Rectangle d38;
    @FXML
    private Rectangle c34;
    @FXML
    private Rectangle b30;
    @FXML
    private Rectangle a26;
    @FXML
    private Rectangle a5;
    @FXML
    private Rectangle b5;
    @FXML
    private Rectangle c5;
    @FXML
    private Rectangle d5;
    @FXML
    private Rectangle e5;
    @FXML
    private Rectangle e31;
    @FXML
    private Rectangle d37;
    @FXML
    private Rectangle c33;
    @FXML
    private Rectangle b29;
    @FXML
    private Rectangle a25;
    @FXML
    private Rectangle a6;
    @FXML
    private Rectangle b6;
    @FXML
    private Rectangle c6;
    @FXML
    private Rectangle d6;
    @FXML
    private Rectangle e6;
    @FXML
    private Rectangle e30;
    @FXML
    private Rectangle d36;
    @FXML
    private Rectangle c32;
    @FXML
    private Rectangle b28;
    @FXML
    private Rectangle a24;
    @FXML
    private Rectangle a7;
    @FXML
    private Rectangle b7;
    @FXML
    private Rectangle c7;
    @FXML
    private Rectangle d7;
    @FXML
    private Rectangle e7;
    @FXML
    private Rectangle e29;
    @FXML
    private Rectangle d35;
    @FXML
    private Rectangle c31;
    @FXML
    private Rectangle b27;
    @FXML
    private Rectangle a23;
    @FXML
    private Rectangle a8;
    @FXML
    private Rectangle b8;
    @FXML
    private Rectangle c8;
    @FXML
    private Rectangle d8;
    @FXML
    private Rectangle e8;
    @FXML
    private Rectangle a12;
    @FXML
    private Rectangle b13;
    @FXML
    private Rectangle c14;
    @FXML
    private Rectangle d15;
    @FXML
    private Rectangle e16;
    @FXML
    private Rectangle a15;
    @FXML
    private Rectangle b17;
    @FXML
    private Rectangle c19;
    @FXML
    private Rectangle d21;
    @FXML
    private Rectangle a14;
    @FXML
    private Rectangle a13;
    @FXML
    private Rectangle b16;
    @FXML
    private Rectangle b15;
    @FXML
    private Rectangle b14;
    @FXML
    private Rectangle c18;
    @FXML
    private Rectangle c17;
    @FXML
    private Rectangle c16;
    @FXML
    private Rectangle c15;
    @FXML
    private Rectangle d20;
    @FXML
    private Rectangle d19;
    @FXML
    private Rectangle d18;
    @FXML
    private Rectangle d17;
    @FXML
    private Rectangle d16;
    @FXML
    private Rectangle e17;
    @FXML
    private Rectangle e18;
    @FXML
    private Rectangle a16;
    @FXML
    private Rectangle b18;
    @FXML
    private Rectangle c20;
    @FXML
    private Rectangle d22;
    @FXML
    private Rectangle a17;
    @FXML
    private Rectangle b19;
    @FXML
    private Rectangle c21;
    @FXML
    private Rectangle d23;
    @FXML
    private Rectangle a18;
    @FXML
    private Rectangle b20;
    @FXML
    private Rectangle c22;
    @FXML
    private Rectangle d24;
    @FXML
    private Rectangle a19;
    @FXML
    private Rectangle b22;
    @FXML
    private Rectangle c25;
    @FXML
    private Rectangle d28;
    @FXML
    private Rectangle e21;
    @FXML
    private Rectangle b21;
    @FXML
    private Rectangle c23;
    @FXML
    private Rectangle d25;
    @FXML
    private Rectangle c24;
    @FXML
    private Rectangle d26;
    @FXML
    private Rectangle d27;
    @FXML
    private Rectangle e20;
    @FXML
    private Rectangle e19;
    @FXML
    private Rectangle a22;
    @FXML
    private Rectangle a21;
    @FXML
    private Rectangle a20;
    @FXML
    private Rectangle b23;
    @FXML
    private Rectangle c26;
    @FXML
    private Rectangle d29;
    @FXML
    private Rectangle e22;
    @FXML
    private Rectangle b26;
    @FXML
    private Rectangle b25;
    @FXML
    private Rectangle b24;
    @FXML
    private Rectangle c30;
    @FXML
    private Rectangle c29;
    @FXML
    private Rectangle c28;
    @FXML
    private Rectangle c27;
    @FXML
    private Rectangle d30;
    @FXML
    private Rectangle d31;
    @FXML
    private Rectangle d32;
    @FXML
    private Rectangle d33;
    @FXML
    private Rectangle d34;
    @FXML
    private Rectangle e28;
    @FXML
    private Rectangle e27;
    @FXML
    private Rectangle e26;
    @FXML
    private Rectangle e25;
    @FXML
    private Rectangle e24;
    @FXML
    private Rectangle e23;
    @FXML
    private Rectangle e9;
    @FXML
    private Rectangle d9;
    @FXML
    private Rectangle c9;
    @FXML
    private Rectangle b9;
    @FXML
    private Rectangle a9;
    @FXML
    private Rectangle a10;
    @FXML
    private Rectangle a11;
    @FXML
    private Rectangle b12;
    @FXML
    private Rectangle c13;
    @FXML
    private Rectangle d14;
    @FXML
    private Rectangle e15;
    @FXML
    private Rectangle b10;
    @FXML
    private Rectangle b11;
    @FXML
    private Rectangle c10;
    @FXML
    private Rectangle c11;
    @FXML
    private Rectangle c12;
    @FXML
    private Rectangle d13;
    @FXML
    private Rectangle d12;
    @FXML
    private Rectangle d11;
    @FXML
    private Rectangle d10;
    @FXML
    private Rectangle e10;
    @FXML
    private Rectangle e11;
    @FXML
    private Rectangle e12;
    @FXML
    private Rectangle e13;
    @FXML
    private Rectangle e14;
    
    
    public void setTheatreService(TheatreService service) {
        theatreService = service;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUpSeatLabels(Scene scene) {
        addLabelsForSeatSection(scene, SECTION_ONE_SEAT_LABELS);
        addLabelsForSeatSection(scene, SECTION_TWO_SEAT_LABELS);
        addLabelsForSeatSection(scene, SECTION_THREE_SEAT_LABELS);
        addLabelsForSeatSection(scene, SECTION_FOUR_SEAT_LABELS);
    }
    
    private void addLabelsForSeatSection(Scene scene, String[] sectionSeats) {
        for (int i = 0; i < sectionSeats.length; i++) {
            Rectangle seat = (Rectangle)scene.lookup("#" + sectionSeats[i]);
            StackPane parent = (StackPane)seat.getParent();
            Label seatLabel = new Label(sectionSeats[i].toUpperCase());
            seatLabel.getStyleClass().clear();
            seatLabel.getStyleClass().add("seat-label");
            parent.getChildren().add(seatLabel);
        }
    }
    
    public void seatSelected(MouseEvent event) throws IOException {
        StackPane seatPane = ((StackPane)event.getSource());
        Rectangle selectedSeat = (Rectangle)seatPane.getChildren().get(0);
        System.out.println("Selected seat: " + selectedSeat.getId());
    }
     
}
