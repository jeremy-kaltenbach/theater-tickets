/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.sawmill.theater.ticketing.fx.TextFieldLimited;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeremy
 */
@Component
public class ShowtimeController implements Initializable { 
    
    @Autowired
    private TheatreService theatreService;
    
    // Validation error messages
    private static final String NAME_MISSING_ERROR = "* Please enter a name";
    private static final String GROUP_MISSING_ERROR= "* Please enter a group";
    private static final String DATE_MISSING_ERROR = "* Please select a date";
    private static final String TIME_MISSING_ERROR = "* Please enter a time";
    private static final String HOUR_MISSING_ERROR = "* Please enter the hour";
    private static final String MINUTE_MISSING_ERROR = "* Please enter the minute";
    private static final String INVALID_HOUR = "* Hour entered is invalid";
    private static final String INVALID_MINUTE = "* Minute entered is invalid";
    
    private static final String NAME_TOO_LONG = "Name is too long";
    private static final String GROUP_TOO_LONG = "Group is too long";
    
    @FXML private TextFieldLimited txtBxShowName;
    @FXML private TextFieldLimited txtBxGroup;
    @FXML private DatePicker showDatePicker;
    @FXML private TextFieldLimited txtBxTimeHour;
    @FXML private TextFieldLimited txtBxTimeMinute;
    @FXML private ComboBox cmboBxAmPm;
    
    @FXML private Label lblNameError;
    @FXML private Label lblGroupError;
    @FXML private Label lblDateError;
    @FXML private Label lblTimeError;
    
    public void setTheatreService(TheatreService service) {
        this.theatreService = service;
    }
    
    public void showMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent tableViewParent = loader.load();
        MainController controller = loader.getController();
        controller.setTheatreService(theatreService);
        
//        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    public void showChart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chart.fxml"));
        Parent tableViewParent = loader.load();
        ChartController controller = loader.getController();
        controller.setTheatreService(theatreService);
        Scene tableViewScene = new Scene(tableViewParent);
        controller.setUpSeatLabels(tableViewScene);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    public void validateFields(ActionEvent event) throws IOException, ParseException {
        
        boolean isValid = true;
        
        hideErrorLabels();
        
        String showName = txtBxShowName.getText();
        String showGroup = txtBxGroup.getText();
        LocalDate showDate = showDatePicker.getValue();
        String hour = txtBxTimeHour.getText();
        String minute = txtBxTimeMinute.getText();
        String amPm = cmboBxAmPm.getValue().toString();
        
        // Validate show name
        if (showName.isEmpty()) {
            // Check for empty field
            lblNameError.setText(NAME_MISSING_ERROR);
            lblNameError.setVisible(true);
            isValid = false;
        }
        else if (showName.length() > 255) {
            // Check if it's too long
            lblNameError.setText(NAME_TOO_LONG);
            lblNameError.setVisible(true);
            isValid = false;
        }
        
        // Validate show group
        if (showGroup.isEmpty()) {
            // Check for empty field
            lblGroupError.setText(GROUP_MISSING_ERROR);
            lblGroupError.setVisible(true);
            isValid = false;
        }
        else if (showGroup.length() > 255) {
            // Check if it's too long
            lblGroupError.setText(GROUP_TOO_LONG);
            lblGroupError.setVisible(true);
            isValid = false;
        }
        
        // Validate show date
        if (showDate == null) {
            lblDateError.setText(DATE_MISSING_ERROR);
            lblDateError.setVisible(true);
            isValid = false;
        }
        
        // Validate time
        if (hour.isEmpty() && minute.isEmpty()) {
            lblTimeError.setText(TIME_MISSING_ERROR);
            lblTimeError.setVisible(true);
            isValid = false;
        }
        else if (hour.isEmpty()) {
            lblTimeError.setText(HOUR_MISSING_ERROR);
            lblTimeError.setVisible(true);
            isValid = false;
        }
        else if (minute.isEmpty()) {
            lblTimeError.setText(MINUTE_MISSING_ERROR);
            lblTimeError.setVisible(true);
            isValid = false;
        }
        else if (!isValidHour(hour)) {
            lblTimeError.setText(INVALID_HOUR);
            lblTimeError.setVisible(true);
            isValid = false;
        }
        
        else if (!isValidMinute(minute)) {
            lblTimeError.setText(INVALID_MINUTE);
            lblTimeError.setVisible(true);
            isValid = false;
        }
        
        if (isValid) {
            System.out.println("Fields are valid");
            saveShowtime(showName, showGroup, showDate, hour, minute, amPm);
            showChart(event);
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Hide error labels
        hideErrorLabels();
        
        // Make sure the date picker can only be edited by selecting a date
        showDatePicker.setEditable(false);
        
        // Set the character limits in the text fields
        txtBxShowName.setMaxlength(255);
        txtBxGroup.setMaxlength(255);
        txtBxTimeHour.setMaxlength(2);
        txtBxTimeMinute.setMaxlength(2);
       
        // Default the show time to 7:00 PM
        txtBxTimeHour.setText("7");
        txtBxTimeMinute.setText("00");
        cmboBxAmPm.getItems().add("AM");
        cmboBxAmPm.getItems().add("PM");
        cmboBxAmPm.setValue("PM");
        
    }
    
    private boolean isValidHour(String hourString) {
        boolean isValid = true;
        if (!StringUtils.isNumeric(hourString)) {
            isValid = false;
        }
        else {
            int hour = Integer.parseInt(hourString);
            if (hour < 1 || hour > 12) {
                isValid = false;
            }
        }
        return isValid;
    }
    
    private boolean isValidMinute(String minuteString) {
        boolean isValid = true;
        if (!StringUtils.isNumeric(minuteString)) {
            isValid = false;
        }
        else {
            int minute = Integer.parseInt(minuteString);
            if (minute < 0 || minute > 59) {
                isValid = false;
            }
        }
        return isValid;
    }
    
    private void saveShowtime(String name, String group, LocalDate showDate, String hour, String minute, String amPm) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm aaa");
        
        // Parse the date fields into the full datetime
        String dateString = showDate.toString() + " " + hour + ":" + minute + " " + amPm;
        
        Showtime newShow = new Showtime();
        newShow.setShowName(name);
        newShow.setTheatreGroup(group);
        newShow.setShowDate(df.parse(dateString));
        newShow.setLastUpdated(new Date());
        
        theatreService.addShowtime(newShow);
        
    }
    
    private void hideErrorLabels() {
        lblNameError.setVisible(false);
        lblGroupError.setVisible(false);
        lblDateError.setVisible(false);
        lblTimeError.setVisible(false);
    }
    
}
