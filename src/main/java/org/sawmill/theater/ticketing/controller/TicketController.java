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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author jeremy
 */
public class TicketController implements Initializable {

    @FXML
    private Label lblShowName;
    @FXML
    private Label lblGroup;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblSection;
    @FXML
    private Label lblRow;
    @FXML
    private Label lblSeat;
    @FXML
    private Label lblHolder;
    @FXML
    private HBox ticketStub;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setShowName(String showName) {
        lblShowName.setText(showName);
    }
    
    public void setGroupName(String group) {
        lblGroup.setText(group);
    }
    
    public void setDate(String showDate) {
        lblDate.setText(showDate);
    }
    
    public void setSection(String section) {
        lblSection.setText(section);
    }
    
    public void setRow(String row) {
        lblRow.setText(row);
    }
    
    public void setSeat(String seat) {
        lblSeat.setText(seat);
    }
    
    public void setHolder(String holder) {
        lblHolder.setText(holder);
    }
    
    public HBox getTicketStub() {
        return ticketStub;
    }
    
}
