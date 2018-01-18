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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    
    private static final String SHOW_NOT_SELECTED = "* Please select a show";
    
    @FXML private Label lblSelectShow;
    @FXML private ComboBox<Showtime> cmboBxSelectShow;
    @FXML private TextFieldLimited txtBxShowName;
    @FXML private TextFieldLimited txtBxGroup;
    @FXML private DatePicker showDatePicker;
    @FXML private TextFieldLimited txtBxTimeHour;
    @FXML private TextFieldLimited txtBxTimeMinute;
    @FXML private ComboBox cmboBxAmPm;
    
    @FXML private Label lblShowSelectError;
    @FXML private Label lblNameError;
    @FXML private Label lblGroupError;
    @FXML private Label lblDateError;
    @FXML private Label lblTimeError;
    
    @FXML private Button btnSubmit;
    
    private boolean editMode = false;
    
    public void setTheatreService(TheatreService service) {
        this.theatreService = service;
    }
    
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        toggleShowSelect();
        if (editMode) {
            getShowtimes();
            btnSubmit.setText("Update");
        }
    }
    
    public void showMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent tableViewParent = loader.load();
        MainController controller = loader.getController();
        controller.setTheatreService(theatreService);
        
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
    
    public void getShowtimes() {
        
        DateFormat df = new SimpleDateFormat("M/dd/yyyy h:mm aaa");
        List<Showtime> showtimeList = theatreService.getShowtimes();
        
        // Set the combo box to the list of showtimes and give it a custom display
        Callback<ListView<Showtime>, ListCell<Showtime>> factory = lv -> new ListCell<Showtime>() {
            @Override
            protected void updateItem(Showtime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getShowName() + "  |  " + df.format(item.getShowDate()));
            }
        };
        cmboBxSelectShow.setCellFactory(factory);
        cmboBxSelectShow.setButtonCell(factory.call(null));
        cmboBxSelectShow.getItems().addAll(showtimeList);
    }
    
    public void showSelected(ActionEvent event) throws IOException, ParseException {
        
        hideErrorLabels();
        
        // Parse out the date of the show in date and time pieces to fill the form
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("h");
        DateFormat minuteFormat = new SimpleDateFormat("mm");
        DateFormat amPmFormat = new SimpleDateFormat("aaa");
        
        Showtime selectedShow = cmboBxSelectShow.getValue();
        txtBxShowName.setText(selectedShow.getShowName());
        txtBxGroup.setText(selectedShow.getTheatreGroup());
        
        LocalDate localDate = LocalDate.parse(dateFormat.format(selectedShow.getShowDate()));
        showDatePicker.setValue(localDate);
        
        txtBxTimeHour.setText(hourFormat.format(selectedShow.getShowDate()));
        txtBxTimeMinute.setText(minuteFormat.format(selectedShow.getShowDate()));
        cmboBxAmPm.setValue(amPmFormat.format(selectedShow.getShowDate()));
        
    }
    
    public void validateFields(ActionEvent event) throws IOException, ParseException {
        
        boolean isValid = true;
        
        hideErrorLabels();
        
        if (editMode && cmboBxSelectShow.getValue() == null) {
            lblShowSelectError.setText(SHOW_NOT_SELECTED);
            lblShowSelectError.setVisible(true);
            return;
        }
        
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
            if (editMode) {
                updateShowtime(showName, showGroup, showDate, hour, minute, amPm);
            }
            else {
                saveShowtime(showName, showGroup, showDate, hour, minute, amPm);
                showChart(event);                
            }
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Hide error labels and show selector
        hideErrorLabels();
        toggleShowSelect();
             
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        
        // Parse the date fields into the full datetime
        String dateString = showDate.toString() + " " + hour + ":" + minute + " " + amPm;
        
        Showtime newShow = new Showtime();
        newShow.setShowName(name);
        newShow.setTheatreGroup(group);
        newShow.setShowDate(df.parse(dateString));
        newShow.setLastUpdated(new Date());
        
        theatreService.addShowtime(newShow);
        
    }
    
    private void updateShowtime(String name, String group, LocalDate showDate, String hour, String minute, String amPm) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        
        // Parse the date fields into the full datetime
        String dateString = showDate.toString() + " " + hour + ":" + minute + " " + amPm;
        
        Showtime updatedShow = cmboBxSelectShow.getValue();
        updatedShow.setShowName(name);
        updatedShow.setTheatreGroup(group);
        updatedShow.setShowDate(df.parse(dateString));
        updatedShow.setLastUpdated(new Date());
        
        theatreService.updateShowtime(updatedShow);
        
    }
    
    private void toggleShowSelect() {
        // Show/Hide show selector combo box
        lblSelectShow.setVisible(editMode);
        cmboBxSelectShow.setVisible(editMode);
    }
    
    private void hideErrorLabels() {
        lblShowSelectError.setVisible(false);
        lblNameError.setVisible(false);
        lblGroupError.setVisible(false);
        lblDateError.setVisible(false);
        lblTimeError.setVisible(false);
    }
    
}
