/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.sawmill.theater.ticketing.model.ShowSeating;
import org.sawmill.theater.ticketing.model.Showtime;
import org.sawmill.theater.ticketing.service.TheatreService;
import org.sawmill.theater.ticketing.service.TheatreServiceImpl;

/**
 * FXML Controller class
 *
 * @author jeremy
 */
public class ChartController implements Initializable {

    private static final String[] SECTION_ONE_SEAT_LABELS
            = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "b1", "b2", "b3", "b4", "b5", "b6",
                "b7", "b8", "b9", "b10", "b11", "b12", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11",
                "c12", "c13", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "d11", "d12", "d13", "d14",
                "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10", "e11", "e12", "e13", "e14", "e15", "h1", "h2"};

    private static final String[] SECTION_TWO_SEAT_LABELS
            = {"a12", "a13", "a14", "a15", "b13", "b14", "b15", "b16", "b17", "c14", "c15", "c16", "c17", "c18", "c19",
                "d15", "d16", "d17", "d18", "d19", "d20", "d21", "e16", "e17", "e18", "h3", "h4"};

    private static final String[] SECTION_THREE_SEAT_LABELS
            = {"a16", "a17", "a18", "a19", "b18", "b19", "b20", "b21", "b22", "c20", "c21", "c22", "c23", "c24", "c25",
                "d22", "d23", "d24", "d25", "d26", "d27", "d28", "e19", "e20", "e21", "h5", "h6"};

    private static final String[] SECTION_FOUR_SEAT_LABELS
            = {"a20", "a21", "a22", "a23", "a24", "a25", "a26", "a27", "a28", "a29", "a30", "b23", "b24", "b25", "b26",
                "b27", "b28", "b29", "b30", "b31", "b32", "b33", "b34", "c26", "c27", "c28", "c29", "c30", "c31", "c32",
                "c33", "c34", "c35", "c36", "c37", "c38", "d29", "d30", "d31", "d32", "d33", "d34", "d35", "d36", "d37",
                "d38", "d39", "d40", "d41", "d42", "e22", "e23", "e24", "e25", "e26", "e27", "e28", "e29", "e30", "e31",
                "e32", "e33", "e34", "e35", "e36", "h7", "h8"};

    // Validation error messages
    private static final String FULL_NAME_MISSING_ERROR = "* Please enter a name";
    private static final String FIRST_NAME_MISSING_ERROR = "* Please enter first name";
    private static final String LAST_NAME_MISSING_ERROR = "* Please enter last name";
    private static final String FIRST_NAME_TOO_LONG = "* First name is too long";
    private static final String LAST_NAME_TOO_LONG = "* Last name is too long";

    private static final String SEAT_ADDED = "-- Seat Added --";
    private static final String SEATS_ADDED = "-- Seats Added --";
    private static final String SEAT_UPDATED = "-- Seat Updated --";
    private static final String SEATS_UPDATED = "-- Seats Updated --";
    private static final String SEAT_REMOVED = "-- Seat Removed --";
    private static final String SEATS_REMOVED = "-- Seats Removed --";

    private TheatreService theatreService = new TheatreServiceImpl();

    private Scene parentScene;

    private final List<String> sectionOneSeats = Arrays.asList(SECTION_ONE_SEAT_LABELS);
    private final List<String> sectionTwoSeats = Arrays.asList(SECTION_TWO_SEAT_LABELS);
    private final List<String> sectionThreeSeats = Arrays.asList(SECTION_THREE_SEAT_LABELS);
    private final List<String> sectionFourSeats = Arrays.asList(SECTION_FOUR_SEAT_LABELS);

    @FXML
    private ComboBox<Showtime> cmboBxSelectShow;
    @FXML
    private Label lblGroupOutput;
    @FXML
    private Label lblDateOutput;
    @FXML
    private Label lblSectionOutput;
    @FXML
    private Label lblRowOutput;
    @FXML
    private Label lblSeatOutput;

    @FXML
    private Label lblNameError;
    @FXML
    private Label lblSeatStatus;

    @FXML
    private CheckBox chkBxSelectMultiple;

    @FXML
    private TextField txtBxFirstName;
    @FXML
    private TextField txtBxLastName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;

    private Region previousSelectedSeat;
    private String previousSelectedSeatStyle;

    private int seatSection;
    private String seatRow;
    private int seatNumber;

    private boolean showSelected = false;
    private boolean editMode = false;
    private boolean selectMultiple = false;

    private String selectedShowName;
    private String selectedShowGroup;
    private String selectedShowDate;
    private String selectedShowDateShortened;
    private Map<String, ShowSeating> occupiedSeats;
    private Map<String, ShowSeating> selectedSeats;

    public void setScene(Scene scene) {
        parentScene = scene;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideErrorLabel();
        hideSeatStatusLabel();
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);
        txtBxFirstName.setEditable(false);
        txtBxLastName.setEditable(false);
        chkBxSelectMultiple.setSelected(false);
        occupiedSeats = new HashMap<>();
        selectedSeats = new HashMap<>();
    }

    public void toggleMultiSelect(ActionEvent event) {
        CheckBox selectMultipleChk = (CheckBox) event.getSource();
        selectMultiple = selectMultipleChk.isSelected();

        clearSelectedSeats(false);
        resetSeatControlButtons();
        hideErrorLabel();
        hideSeatStatusLabel();
    }

    public void showMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent chartParent = loader.load();
        MainController controller = loader.getController();
        controller.checkDatabaseStatus();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = ((Node) event.getSource()).getScene();
        Scene mainScene = new Scene(chartParent, currentScene.getWidth(), currentScene.getHeight());

        window.setScene(mainScene);
        window.show();
    }

    public void setUpSeatLabels() {
        addLabelsForSeatSection(SECTION_ONE_SEAT_LABELS);
        addLabelsForSeatSection(SECTION_TWO_SEAT_LABELS);
        addLabelsForSeatSection(SECTION_THREE_SEAT_LABELS);
        addLabelsForSeatSection(SECTION_FOUR_SEAT_LABELS);
    }

    private void addLabelsForSeatSection(String[] sectionSeats) {
        for (int i = 0; i < sectionSeats.length; i++) {
            // Don't add labels to the handicap seats
            if (!sectionSeats[i].substring(0, 1).equals("h")) {
                Region seat = (Region) parentScene.lookup("#" + sectionSeats[i]);
                StackPane parent = (StackPane) seat.getParent();
                Label seatLabel = new Label(sectionSeats[i].toUpperCase());
                seatLabel.getStyleClass().clear();
                seatLabel.getStyleClass().add("seat-label");
                parent.getChildren().add(seatLabel);
            }
        }
    }

    public void getShowtimes() {

        List<Showtime> showtimeList = theatreService.getShowtimes();

        // Set the combo box to the list of showtimes and give it a custom display
        Callback<ListView<Showtime>, ListCell<Showtime>> factory = lv -> new ListCell<Showtime>() {
            @Override
            protected void updateItem(Showtime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getShowName());
            }
        };
        cmboBxSelectShow.setCellFactory(factory);
        cmboBxSelectShow.setButtonCell(factory.call(null));
        cmboBxSelectShow.getItems().addAll(showtimeList);
    }

    public void setSelectedShowtime(Showtime newShow) {
        List<Showtime> showtimes = cmboBxSelectShow.getItems();
        for (Showtime showtime : showtimes) {
            if (showtime.getShowId() == newShow.getShowId()) {
                cmboBxSelectShow.setValue(showtime);
            }
        }
        showSelected();
    }

    public void seatSelected(MouseEvent event) throws IOException {

        hideErrorLabel();
        hideSeatStatusLabel();

        StackPane seatPane = ((StackPane) event.getSource());
        Region selectedSeat = (Region) seatPane.getChildren().get(0);
        String seatId = selectedSeat.getId();

        seatRow = seatId.substring(0, 1).toUpperCase();
        seatNumber = Integer.parseInt(seatId.substring(1));

        boolean isHandicapped = seatRow.equals("H");

        // Find out which section the seat is located
        if (sectionOneSeats.contains(seatId)) {
            seatSection = 1;
        } else if (sectionTwoSeats.contains(seatId)) {
            seatSection = 2;
        } else if (sectionThreeSeats.contains(seatId)) {
            seatSection = 3;
        } else {
            seatSection = 4;
        }

        // If multi-select is off, then save the current style of the select seat
        if (!selectMultiple) {
            if (previousSelectedSeat != null) {
                previousSelectedSeat.getStyleClass().clear();
                previousSelectedSeat.getStyleClass().add(previousSelectedSeatStyle);
            }
            previousSelectedSeat = selectedSeat;
            previousSelectedSeatStyle = selectedSeat.getStyleClass().get(0);
        }

        // Only select seats after a show is selected
        if (showSelected) {

            // Handle selecting seats with mult-select on
            // If the seat selected is open, then add it to the list of selected seats
            if (!occupiedSeats.containsKey(seatId) && !editMode
                    || (!occupiedSeats.containsKey(seatId) && editMode && !selectMultiple)) {

                if (selectMultiple) {
                    // If the seat is already added, then remove it (toggle) and un-select it
                    if (selectedSeats.containsKey(seatId)) {
                        selectedSeat.getStyleClass().clear();
                        if (isHandicapped) {
                            selectedSeat.getStyleClass().add("seat-open-handicapped");
                        } else {
                            selectedSeat.getStyleClass().add("seat-open");
                        }
                        selectedSeats.remove(seatId);
                    } else {
                        selectedSeat.getStyleClass().clear();
                        if (isHandicapped) {
                            selectedSeat.getStyleClass().add("seat-selected-handicapped");
                        } else {
                            selectedSeat.getStyleClass().add("seat-selected");
                        }
                        ShowSeating seat = new ShowSeating();
                        seat.setSection(seatSection);
                        seat.setRow(seatRow);
                        seat.setSeatNumber(seatNumber);
                        selectedSeats.put(seatId, seat);
                    }
                } else {
                    // The selected list should only contain one seat
                    // so remove any previous selected seat and add the new one
                    selectedSeats.clear();
                    selectedSeat.getStyleClass().clear();
                    if (isHandicapped) {
                        selectedSeat.getStyleClass().add("seat-selected-handicapped");
                    } else {
                        selectedSeat.getStyleClass().add("seat-selected");
                    }
                    ShowSeating seat = new ShowSeating();
                    seat.setSection(seatSection);
                    seat.setRow(seatRow);
                    seat.setSeatNumber(seatNumber);
                    selectedSeats.put(seatId, seat);

                    // Clear name from text field
                    txtBxFirstName.setText("");
                    txtBxLastName.setText("");
                }

                if (selectedSeats.size() > 0) {
                    btnAdd.setText("Add");
                    btnAdd.setDisable(false);
                    btnDelete.setDisable(true);
                    btnPrint.setDisable(true);
                    txtBxFirstName.setEditable(true);
                    txtBxLastName.setEditable(true);
                    editMode = false;
                } else {
                    txtBxFirstName.setEditable(false);
                    txtBxLastName.setEditable(false);
                    btnAdd.setDisable(true);
                }

                lblSectionOutput.setText(String.valueOf(seatSection));
                lblRowOutput.setText(seatRow);
                lblSeatOutput.setText(String.valueOf(seatNumber));

            } else if (occupiedSeats.containsKey(seatId)) {
                // If the selected seat list is empty or if edit mode is already set,
                // then add the occupied seat
                if (selectedSeats.isEmpty() || editMode || (!selectedSeats.isEmpty() && !selectMultiple)) {

                    if (selectMultiple) {
                        // If the seat is already added, then remove it (toggle) and un-select it
                        if (selectedSeats.containsKey(seatId)) {
                            selectedSeat.getStyleClass().clear();
                            if (isHandicapped) {
                                selectedSeat.getStyleClass().add("seat-occupied-handicapped");
                            } else {
                                selectedSeat.getStyleClass().add("seat-occupied");
                            }
                            selectedSeats.remove(seatId);
                        } else {
                            selectedSeat.getStyleClass().clear();
                            if (isHandicapped) {
                                selectedSeat.getStyleClass().add("seat-selected-handicapped");
                            } else {
                                selectedSeat.getStyleClass().add("seat-selected");
                            }
                            ShowSeating seat = occupiedSeats.get(seatId);
                            txtBxFirstName.setText(seat.getFirstName());
                            txtBxLastName.setText(seat.getLastName());
                            selectedSeats.put(seatId, seat);
                        }
                    } else {
                        // The selected list should only contain one seat
                        // so remove any previous selected seat and add the new one
                        selectedSeats.clear();
                        selectedSeat.getStyleClass().clear();
                        if (isHandicapped) {
                            selectedSeat.getStyleClass().add("seat-selected-handicapped");
                        } else {
                            selectedSeat.getStyleClass().add("seat-selected");
                        }
                        ShowSeating seat = occupiedSeats.get(seatId);
                        txtBxFirstName.setText(seat.getFirstName());
                        txtBxLastName.setText(seat.getLastName());
                        selectedSeats.put(seatId, seat);
                    }

                    if (selectedSeats.size() > 0) {
                        btnAdd.setText("Update");
                        btnAdd.setDisable(false);
                        btnDelete.setDisable(false);
                        btnPrint.setDisable(false);

                        txtBxFirstName.setEditable(true);
                        txtBxLastName.setEditable(true);

                        editMode = true;
                    } else {
                        btnAdd.setText("Add");
                        btnAdd.setDisable(true);
                        btnDelete.setDisable(true);
                        btnPrint.setDisable(true);

                        txtBxFirstName.setEditable(false);
                        txtBxLastName.setEditable(false);
                        // Clear name from text fields
                        txtBxFirstName.setText("");
                        txtBxLastName.setText("");

                        editMode = false;
                    }

                    lblSectionOutput.setText(String.valueOf(seatSection));
                    lblRowOutput.setText(seatRow);
                    lblSeatOutput.setText(String.valueOf(seatNumber));
                }
            }
        }
    }

    public void showSelected() {

        hideErrorLabel();
        hideSeatStatusLabel();

        selectMultiple = false;
        chkBxSelectMultiple.setSelected(false);

        editMode = false;

        // Clear occupied seats (if any)
        for (String seatId : occupiedSeats.keySet()) {
            Region occupiedSeat = (Region) parentScene.lookup("#" + seatId);
            occupiedSeat.getStyleClass().clear();
            if (seatId.substring(0, 1).equals("h")) {
                occupiedSeat.getStyleClass().add("seat-open-handicapped");
            } else {
                occupiedSeat.getStyleClass().add("seat-open");
            }
        }

        clearSelectedSeats(false);

        DateFormat shortDateFormat = new SimpleDateFormat("M/dd/yyyy h:mm aaa");
        DateFormat longDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy, h:mm aaaa");

        Showtime selectedShow = cmboBxSelectShow.getValue();

        selectedShowName = selectedShow.getShowName();
        selectedShowGroup = selectedShow.getTheatreGroup();
        selectedShowDateShortened = shortDateFormat.format(selectedShow.getShowDate());
        selectedShowDate = longDateFormat.format(selectedShow.getShowDate());

        lblGroupOutput.setText(selectedShowGroup);
        lblDateOutput.setText(selectedShowDateShortened);

        showSelected = true;

        resetSeatControlButtons();

        setOccupiedSeats();
    }

    private void setOccupiedSeats() {

        occupiedSeats = new HashMap<>();

        List<ShowSeating> seatList = theatreService.getShowtimeSeats(cmboBxSelectShow.getValue().getShowId());
        for (ShowSeating seat : seatList) {
            String seatId = seat.getRow().toLowerCase() + String.valueOf(seat.getSeatNumber());
            Region occupiedSeat = (Region) parentScene.lookup("#" + seatId);
            occupiedSeat.getStyleClass().clear();
            if (seatId.substring(0, 1).equals("h")) {
                occupiedSeat.getStyleClass().add("seat-occupied-handicapped");
            } else {
                occupiedSeat.getStyleClass().add("seat-occupied");
            }

            occupiedSeats.put(seatId, seat);
        }
    }

    public void modifySeat(ActionEvent event) {

        boolean isValid = true;

        hideErrorLabel();
        hideSeatStatusLabel();

        String firstName = txtBxFirstName.getText();
        String lastName = txtBxLastName.getText();

        // Validate first and last name
        if (firstName.isEmpty() && lastName.isEmpty()) {
            // Check for empty fields for both
            lblNameError.setText(FULL_NAME_MISSING_ERROR);
            lblNameError.setVisible(true);
            isValid = false;
        } else if (firstName.isEmpty()) {
            // Check for empty field in first name
            lblNameError.setText(FIRST_NAME_MISSING_ERROR);
            lblNameError.setVisible(true);
            isValid = false;
        } else if (lastName.isEmpty()) {
            // Check for empty field in last name
            lblNameError.setText(LAST_NAME_MISSING_ERROR);
            lblNameError.setVisible(true);
            isValid = false;
        } else if (firstName.length() > 255) {
            // Check if first name is too long
            lblNameError.setText(FIRST_NAME_TOO_LONG);
            lblNameError.setVisible(true);
            isValid = false;
        } else if (lastName.length() > 255) {
            // Check if first name is too long
            lblNameError.setText(LAST_NAME_TOO_LONG);
            lblNameError.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            if (editMode) {
                updateSeat(firstName, lastName);
            } else {
                addSeat(firstName, lastName);
            }
        }
    }

    private void addSeat(String firstName, String lastName) {

        if (selectMultiple) {
            for (ShowSeating newSeat : selectedSeats.values()) {
                // Add the entered name to the list of selected seats
                newSeat.setFirstName(firstName);
                newSeat.setLastName(lastName);

                newSeat.setShowId(cmboBxSelectShow.getValue().getShowId());

                // Returned seat will have its generated ID
                newSeat = theatreService.addShowSeat(newSeat);

                String newSeatId = newSeat.getRow().toLowerCase() + newSeat.getSeatNumber();
                occupiedSeats.put(newSeatId, newSeat);

                lblSeatStatus.setText(SEATS_ADDED);
                lblSeatStatus.setVisible(true);
            }
            clearSelectedSeats(true);
        } else {
            ShowSeating newSeat = new ShowSeating();
            newSeat.setShowId(cmboBxSelectShow.getValue().getShowId());
            newSeat.setSection(seatSection);
            newSeat.setRow(seatRow);
            newSeat.setSeatNumber(seatNumber);
            newSeat.setFirstName(firstName);
            newSeat.setLastName(lastName);

            // Returned seat will have its generated ID
            newSeat = theatreService.addShowSeat(newSeat);

            // Set this as the previous selected seat so it shows as occupied when selecting another
            String seatId = seatRow.toLowerCase() + seatNumber;
            Region occupiedSeat = (Region) parentScene.lookup("#" + seatId);
            previousSelectedSeat = occupiedSeat;
            if (seatRow.equals("H")) {
                previousSelectedSeatStyle = "seat-occupied-handicapped";
            } else {
                previousSelectedSeatStyle = "seat-occupied";
            }

            occupiedSeats.put(seatId, newSeat);

            btnAdd.setText("Update");
            btnDelete.setDisable(false);
            btnPrint.setDisable(false);
            editMode = true;

            lblSeatStatus.setText(SEAT_ADDED);
            lblSeatStatus.setVisible(true);
        }
    }

    private void updateSeat(String firstName, String lastName) {

        if (selectMultiple) {
            for (ShowSeating updatedSeat : selectedSeats.values()) {
                // Add the entered name to the list of selected seats
                updatedSeat.setFirstName(firstName);
                updatedSeat.setLastName(lastName);

                theatreService.updateShowSeat(updatedSeat);
            }
            lblSeatStatus.setText(SEATS_UPDATED);
            lblSeatStatus.setVisible(true);

            clearSelectedSeats(true);

        } else {
            ShowSeating seat = occupiedSeats.get(seatRow.toLowerCase() + seatNumber);
            seat.setFirstName(firstName);
            seat.setLastName(lastName);

            theatreService.updateShowSeat(seat);

            lblSeatStatus.setText(SEAT_UPDATED);
            lblSeatStatus.setVisible(true);
        }

    }

    public void deleteSeat(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        if (selectMultiple) {
            alert.setHeaderText("Are you sure you want to remove these seats?");
        } else {
            alert.setHeaderText("Are you sure you want to remove this seat?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            editMode = false;

            if (selectMultiple) {
                for (ShowSeating deletedSeat : selectedSeats.values()) {
                    theatreService.deleteShowSeat(deletedSeat.getSeatId());
                    String seatId = deletedSeat.getRow().toLowerCase() + deletedSeat.getSeatNumber();
                    occupiedSeats.remove(seatId);
                }
                lblSeatStatus.setText(SEATS_REMOVED);
                lblSeatStatus.setVisible(true);

                clearSelectedSeats(false);

            } else {
                String seatId = seatRow.toLowerCase() + seatNumber;
                ShowSeating seat = occupiedSeats.get(seatId);
                theatreService.deleteShowSeat(seat.getSeatId());

                // Set this as the previous selected seat so it shows as open when selecting another
                Region occupiedSeat = (Region) parentScene.lookup("#" + seatId);
                previousSelectedSeat = occupiedSeat;
                if (seatRow.equals("H")) {
                    previousSelectedSeatStyle = "seat-open-handicapped";
                } else {
                    previousSelectedSeatStyle = "seat-open";
                }

                occupiedSeats.remove(seatId);

                lblSeatStatus.setText(SEAT_REMOVED);
                lblSeatStatus.setVisible(true);
            }

            txtBxFirstName.setText("");
            txtBxLastName.setText("");
            btnAdd.setText("Add");
            btnDelete.setDisable(true);
            btnPrint.setDisable(true);
        }
    }

    private void clearSelectedSeats(boolean updatingSeat) {

        // Reset selected seats back to their previous style
        for (String seatId : selectedSeats.keySet()) {
            Region seat = (Region) parentScene.lookup("#" + seatId);
            seat.getStyleClass().clear();
            if (editMode || updatingSeat) {
                if (seatId.substring(0, 1).equals("h")) {
                    seat.getStyleClass().add("seat-occupied-handicapped");
                } else {
                    seat.getStyleClass().add("seat-occupied");
                }
            } else {
                if (seatId.substring(0, 1).equals("h")) {
                    seat.getStyleClass().add("seat-open-handicapped");
                } else {
                    seat.getStyleClass().add("seat-open");
                }
            }
        }

        // Clear seats selected
        selectedSeats.clear();

        previousSelectedSeat = null;
        editMode = false;

        // Clear selected seat labels and name labels
        lblSectionOutput.setText("");
        lblRowOutput.setText("");
        lblSeatOutput.setText("");
        txtBxFirstName.setText("");
        txtBxLastName.setText("");
        txtBxFirstName.setEditable(false);
        txtBxLastName.setEditable(false);

        btnAdd.setText("Add");
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);
    }

    private void hideErrorLabel() {
        lblNameError.setVisible(false);
    }

    private void hideSeatStatusLabel() {
        lblSeatStatus.setVisible(false);
    }

    private void resetSeatControlButtons() {
        btnAdd.setText("Add");
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);
    }

    public void printSetup(ActionEvent event) throws IOException {

        List<Parent> ticketList = new ArrayList<>();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        for (ShowSeating seat : selectedSeats.values()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ticket.fxml"));
            Parent ticketParent = loader.load();
            TicketController controller = loader.getController();

            controller.setShowName(selectedShowName);
            controller.setGroupName("BY: " + selectedShowGroup);
            controller.setHolder("Reserved for: " + seat.getFirstName() + " " + seat.getLastName());
            controller.setDate(selectedShowDate);
            controller.setSection("Section: " + seat.getSection());
            controller.setRow("Row: " + seat.getRow());
            controller.setSeat("Seat: " + seat.getSeatNumber());

            // Add ticket stub with rotated labels to the ticket template
            Label showTitleLabel = new Label(selectedShowName);
            showTitleLabel.setRotate(-90);
            showTitleLabel.setPrefSize(200.0, 50.0);
            showTitleLabel.setAlignment(Pos.CENTER);
            showTitleLabel.setWrapText(true);
            showTitleLabel.setTextAlignment(TextAlignment.CENTER);
            Label showDateLabel = new Label(selectedShowDateShortened);
            showDateLabel.setRotate(-90);
            showDateLabel.setPrefSize(200.0, 50.0);
            showDateLabel.setAlignment(Pos.CENTER);

            Group titleHolder = new Group(showTitleLabel);
            Group dateHolder = new Group(showDateLabel);

            HBox ticketStub = controller.getTicketStub();
            ticketStub.getChildren().add(titleHolder);
            ticketStub.getChildren().add(dateHolder);

            ticketList.add(ticketParent);

        }

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job == null) {
            //Unable to print
            return;
        }

        boolean proceed = job.showPrintDialog(window);

        if (proceed) {
            Printer printer = job.getPrinter();
            JobSettings jobSettings = job.getJobSettings();
            PageLayout pageLayout = jobSettings.getPageLayout();
            Paper selectedPaper = pageLayout.getPaper();
            PageLayout layout = printer.createPageLayout(selectedPaper, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
            print(job, layout, ticketList);
        }
    }

    private void print(PrinterJob job, PageLayout layout, List<Parent> ticketList) {

        // Print each ticket selected on their own page
        for (Parent ticket : ticketList) {
            boolean printed = job.printPage(layout, ticket);
            if (!printed) {
                job.cancelJob();
                break;
            }
        }

        // All tickets printed so, end the job
        job.endJob();
    }

}
