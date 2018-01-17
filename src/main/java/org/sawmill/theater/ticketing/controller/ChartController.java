/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    
    private static final String[] SEAT_LABELS = {"a1", "b1", "c1", "d1", "e1"};
    
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
        for (int i = 0; i < SEAT_LABELS.length; i++) {
            Rectangle seat = (Rectangle)scene.lookup("#" + SEAT_LABELS[i]);
            StackPane parent = (StackPane)seat.getParent();
            Label seatLabel = new Label(SEAT_LABELS[i].toUpperCase());
            seatLabel.getStyleClass().clear();
            seatLabel.getStyleClass().add("seat-label");
            parent.getChildren().add(seatLabel);
        }
    }
    
}
