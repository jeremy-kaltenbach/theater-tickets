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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.sawmill.theater.ticketing.fx.TextFieldLimited;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.sawmill.theater.ticketing.service.TheatreServiceImpl;

/**
 *
 * @author jeremy
 */
public class ShowtimeController implements Initializable {

    private TheatreService theatreService = new TheatreServiceImpl();

    // Validation error messages
    private static final String NAME_MISSING_ERROR = "* Please enter a name";
    private static final String GROUP_MISSING_ERROR = "* Please enter a group";
    private static final String DATE_MISSING_ERROR = "* Please select a date";
    private static final String TIME_MISSING_ERROR = "* Please enter a time";
    private static final String HOUR_MISSING_ERROR = "* Please enter the hour";
    private static final String MINUTE_MISSING_ERROR = "* Please enter the minute";
    private static final String INVALID_HOUR = "* Hour entered is invalid";
    private static final String INVALID_MINUTE = "* Minute entered is invalid";

    private static final String NAME_TOO_LONG = "* Name is too long";
    private static final String GROUP_TOO_LONG = "* Group is too long";

    private static final String SHOW_NOT_SELECTED = "* Please select a show";

    @FXML
    private Label lblSelectShow;
    @FXML
    private ComboBox<Showtime> cmboBxSelectShow;
    @FXML
    private TextFieldLimited txtBxShowName;
    @FXML
    private TextFieldLimited txtBxGroup;
    @FXML
    private DatePicker showDatePicker;
    @FXML
    private TextFieldLimited txtBxTimeHour;
    @FXML
    private TextFieldLimited txtBxTimeMinute;
    @FXML
    private ComboBox cmboBxAmPm;

    @FXML
    private Label lblShowSelectError;
    @FXML
    private Label lblNameError;
    @FXML
    private Label lblGroupError;
    @FXML
    private Label lblDateError;
    @FXML
    private Label lblTimeError;

    @FXML
    private Button btnSubmit;

    private boolean editMode = false;
    private boolean deleteMode = false;
    private boolean deletingShow = false;

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        toggleShowSelect(editMode);
        if (editMode) {
            getShowtimes();
            btnSubmit.setText("Update");
        } else {
            setDefaultTime();
        }
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        toggleShowSelect(true);
        btnSubmit.setText("Delete");
        setFieldsReadOnly();
        getShowtimes();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Hide error labels and show selector
        hideErrorLabels();
        toggleShowSelect(false);

        // Make sure the date picker can only be edited by selecting a date
        showDatePicker.setEditable(false);

        // Set the character limits in the text fields
        txtBxShowName.setMaxlength(255);
        txtBxGroup.setMaxlength(255);
        txtBxTimeHour.setMaxlength(2);
        txtBxTimeMinute.setMaxlength(2);
    }

    public void showMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent tableViewParent = loader.load();
        MainController controller = loader.getController();
        controller.checkDatabaseStatus();

        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private void showChart(ActionEvent event, Showtime newShow) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chart.fxml"));
        Parent tableViewParent = loader.load();
        ChartController controller = loader.getController();
        Scene tableViewScene = new Scene(tableViewParent);
        controller.setScene(tableViewScene);
        controller.setUpSeatLabels();
        controller.getShowtimes();
        controller.setSelectedShowtime(newShow);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

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

        // Check if we're in the middle of deleting a show
        // This event gets triggered when resetting the show select combo box for some reason
        if (!deletingShow) {

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

    }

    public void validateFields(ActionEvent event) throws IOException, ParseException {

        boolean isValid = true;

        hideErrorLabels();

        if ((editMode || deleteMode) && cmboBxSelectShow.getValue() == null) {
            lblShowSelectError.setText(SHOW_NOT_SELECTED);
            lblShowSelectError.setVisible(true);
            return;
        }

        if (deleteMode) {
            deleteShowtime();
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
        } else if (showName.length() > 255) {
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
        } else if (showGroup.length() > 255) {
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
        } else if (hour.isEmpty()) {
            lblTimeError.setText(HOUR_MISSING_ERROR);
            lblTimeError.setVisible(true);
            isValid = false;
        } else if (minute.isEmpty()) {
            lblTimeError.setText(MINUTE_MISSING_ERROR);
            lblTimeError.setVisible(true);
            isValid = false;
        } else if (!isValidHour(hour)) {
            lblTimeError.setText(INVALID_HOUR);
            lblTimeError.setVisible(true);
            isValid = false;
        } else if (!isValidMinute(minute)) {
            lblTimeError.setText(INVALID_MINUTE);
            lblTimeError.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            if (editMode) {
                updateShowtime(showName, showGroup, showDate, hour, minute, amPm);
            } else {
                Showtime newShow = saveShowtime(showName, showGroup, showDate, hour, minute, amPm);
                showChart(event, newShow);
            }
        }

    }

    private void setDefaultTime() {
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
        } else {
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
        } else {
            int minute = Integer.parseInt(minuteString);
            if (minute < 0 || minute > 59) {
                isValid = false;
            }
        }
        return isValid;
    }

    private Showtime saveShowtime(String name, String group, LocalDate showDate, String hour, String minute, String amPm) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");

        // Parse the date fields into the full datetime
        String dateString = showDate.toString() + " " + hour + ":" + minute + " " + amPm;

        Showtime newShow = new Showtime();
        newShow.setShowName(name);
        newShow.setTheatreGroup(group);
        newShow.setShowDate(df.parse(dateString));
        newShow.setLastUpdated(new Date());

        return theatreService.addShowtime(newShow);

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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Show has been updated");
        alert.show();

    }

    private void deleteShowtime() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete this show?");
        alert.setContentText("This can not be undone");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deletingShow = true;
            theatreService.deleteShowtime(cmboBxSelectShow.getValue().getShowId());
            cmboBxSelectShow.getItems().remove(cmboBxSelectShow.getValue());
            cmboBxSelectShow.getSelectionModel().clearSelection();
            resetForm();
        }
    }

    private void toggleShowSelect(boolean isVisible) {
        // Show/Hide show selector combo box
        lblSelectShow.setVisible(isVisible);
        cmboBxSelectShow.setVisible(isVisible);
    }

    private void hideErrorLabels() {
        lblShowSelectError.setVisible(false);
        lblNameError.setVisible(false);
        lblGroupError.setVisible(false);
        lblDateError.setVisible(false);
        lblTimeError.setVisible(false);
    }

    private void resetForm() {
        txtBxShowName.setText("");
        txtBxGroup.setText("");
        showDatePicker.setValue(null);
        txtBxTimeHour.setText("");
        txtBxTimeMinute.setText("");
        deletingShow = false;
    }

    private void setFieldsReadOnly() {
        txtBxShowName.setDisable(true);
        txtBxGroup.setDisable(true);
        showDatePicker.setDisable(true);
        txtBxTimeHour.setDisable(true);
        txtBxTimeMinute.setDisable(true);
        cmboBxAmPm.setDisable(true);
    }

}
