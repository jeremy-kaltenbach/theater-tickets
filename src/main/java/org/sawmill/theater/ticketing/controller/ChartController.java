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
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
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
                "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10", "e11", "e12", "e13", "e14", "e15"};
    
    private static final String[] SECTION_TWO_SEAT_LABELS
            = {"a12", "a13", "a14", "a15", "b13", "b14", "b15", "b16", "b17", "c14", "c15", "c16", "c17", "c18", "c19",
                "d15", "d16", "d17", "d18", "d19", "d20", "d21", "e16", "e17", "e18"};
    
    private static final String[] SECTION_THREE_SEAT_LABELS
            = {"a16", "a17", "a18", "a19", "b18", "b19", "b20", "b21", "b22", "c20", "c21", "c22", "c23", "c24", "c25",
                "d22", "d23", "d24", "d25", "d26", "d27", "d28", "e19", "e20", "e21"};
    
    private static final String[] SECTION_FOUR_SEAT_LABELS
            = {"a20", "a21", "a22", "a23", "a24", "a25", "a26", "a27", "a28", "a29", "a30", "b23", "b24", "b25", "b26",
                "b27", "b28", "b29", "b30", "b31", "b32", "b33", "b34", "c26", "c27", "c28", "c29", "c30", "c31", "c32",
                "c33", "c34", "c35", "c36", "c37", "c38", "d29", "d30", "d31", "d32", "d33", "d34", "d35", "d36", "d37",
                "d38", "d39", "d40", "d41", "d42", "e22", "e23", "e24", "e25", "e26", "e27", "e28", "e29", "e30", "e31",
                "e32", "e33", "e34", "e35", "e36"};

    // Validation error messages
    private static final String FULL_NAME_MISSING_ERROR = "* Please enter a name";
    private static final String FIRST_NAME_MISSING_ERROR = "* Please enter first name";
    private static final String LAST_NAME_MISSING_ERROR = "* Please enter last name";
    private static final String FIRST_NAME_TOO_LONG = "* First name is too long";
    private static final String LAST_NAME_TOO_LONG = "* Last name is too long";
    private static final String SEAT_OPEN_ERROR = "* Seat is open";
    
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
    private TextField txtBxFirstName;
    @FXML
    private TextField txtBxLastName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;
    
    private Rectangle previousSelectedSeat;
    private String previousSelectedSeatStyle;
    
    private int seatSection;
    private String seatRow;
    private int seatNumber;
    
    private boolean showSelected = false;
    private boolean editMode = false;
    private boolean printMode = false;
    
    private String selectedShowName;
    private String selectedShowGroup;
    private String selectedShowDate;
    private Map<String, ShowSeating> occupiedSeats;
    private List<ShowSeating> seatsToPrint;
    
    public void setScene(Scene scene) {
        parentScene = scene;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideErrorLabel();
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);
        txtBxFirstName.setEditable(false);
        txtBxLastName.setEditable(false);
        occupiedSeats = new HashMap<String, ShowSeating>();
        seatsToPrint = new ArrayList<ShowSeating>();
    }
    
    public void setPrintMode(boolean printMode) {
        this.printMode = printMode;
    }
    
    public void showMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
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
            Rectangle seat = (Rectangle) parentScene.lookup("#" + sectionSeats[i]);
            StackPane parent = (StackPane) seat.getParent();
            Label seatLabel = new Label(sectionSeats[i].toUpperCase());
            seatLabel.getStyleClass().clear();
            seatLabel.getStyleClass().add("seat-label");
            parent.getChildren().add(seatLabel);
        }
    }
    
    public void getShowtimes() {
        
        DateFormat df = new SimpleDateFormat("M/dd/yyyy h:mm aaa");
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
        
        StackPane seatPane = ((StackPane) event.getSource());
        Rectangle selectedSeat = (Rectangle) seatPane.getChildren().get(0);
        String seatId = selectedSeat.getId();
        
        if (!printMode) {
            if (previousSelectedSeat != null) {
                previousSelectedSeat.getStyleClass().clear();
                previousSelectedSeat.getStyleClass().add(previousSelectedSeatStyle);
            }
            previousSelectedSeat = selectedSeat;
            previousSelectedSeatStyle = selectedSeat.getStyleClass().get(0);
            
            System.out.println("Selected seat: " + seatId);
            selectedSeat.getStyleClass().clear();
            selectedSeat.getStyleClass().add("seat-selected");
        }

        //Find out which section the seat is located
        if (sectionOneSeats.contains(seatId)) {
            seatSection = 1;
        } else if (sectionTwoSeats.contains(seatId)) {
            seatSection = 2;
        } else if (sectionThreeSeats.contains(seatId)) {
            seatSection = 3;
        } else {
            seatSection = 4;
        }
        
        seatRow = seatId.substring(0, 1).toUpperCase();
        seatNumber = Integer.parseInt(seatId.substring(1));
        
        lblSectionOutput.setText(String.valueOf(seatSection));
        lblRowOutput.setText(seatRow);
        lblSeatOutput.setText(String.valueOf(seatNumber));

        // Enable buttons for adding/updating and deleting seat (unless in print mode)
        if (showSelected) {
            
            if (printMode) {
                // If the seat selected is occupied, then add it to the list of seats to print
                if (occupiedSeats.containsKey(seatId)) {
                    ShowSeating seat = occupiedSeats.get(seatId);
                    txtBxFirstName.setText(seat.getFirstName());
                    txtBxLastName.setText(seat.getLastName());

                    // If the seat is already added, then remove it (toggle) and un-select it
                    if (seatsToPrint.contains(seat)) {
                        selectedSeat.getStyleClass().clear();
                        selectedSeat.getStyleClass().add("seat-occupied");
                        seatsToPrint.remove(seat);
                        System.out.println("Removing seat: " + seatId + " from print list");
                    } else {
                        selectedSeat.getStyleClass().clear();
                        selectedSeat.getStyleClass().add("seat-selected-printing");
                        seatsToPrint.add(seat);
                        System.out.println("Adding seat: " + seatId + " to print list");
                    }
                    
                    System.out.println(seatsToPrint.size() + " seats ready to print");
                    
                    if (seatsToPrint.size() > 0) {
                        btnPrint.setDisable(false);
                    } else {
                        btnPrint.setDisable(true);
                    }
                } else {
                    // Clear name from text field
                    txtBxFirstName.setText("");
                    txtBxLastName.setText("");
                }
            } else {
                // If seat is occupied, then show the name of the person
                if (occupiedSeats.containsKey(seatId)) {
                    ShowSeating seat = occupiedSeats.get(seatId);
                    txtBxFirstName.setText(seat.getFirstName());
                    txtBxLastName.setText(seat.getLastName());
                    
                    btnAdd.setText("Update");
                    btnDelete.setDisable(false);
                    editMode = true;
                } else {
                    txtBxFirstName.setText("");
                    txtBxLastName.setText("");
                    btnAdd.setText("Add");
                    btnDelete.setDisable(true);
                    editMode = false;
                }
                txtBxFirstName.setEditable(true);
                txtBxLastName.setEditable(true);
                btnAdd.setDisable(false);
            }
        }
    }
    
    public void showSelected() {
        
        hideErrorLabel();

        // Clear occupied seats (if any)
        for (String seatId : occupiedSeats.keySet()) {
            Rectangle occupiedSeat = (Rectangle) parentScene.lookup("#" + seatId);
            occupiedSeat.getStyleClass().clear();
            occupiedSeat.getStyleClass().add("seat-open");
        }

        // Clear seats selected to print
        seatsToPrint.clear();

        // Remove previously selected seat
        if (previousSelectedSeat != null) {
            previousSelectedSeat.getStyleClass().clear();
            previousSelectedSeat.getStyleClass().add("seat-open");
        }
        previousSelectedSeat = null;
        
        // Clear selected seat labels and name labels
        lblSectionOutput.setText("");
        lblRowOutput.setText("");
        lblSeatOutput.setText("");
        txtBxFirstName.setText("");
        txtBxLastName.setText("");
        
        DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy h:mm aaa");
        DateFormat ticketDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy, h:mm aaaa");
        
        Showtime selectedShow = cmboBxSelectShow.getValue();
        
        selectedShowName = selectedShow.getShowName();
        selectedShowGroup = selectedShow.getTheatreGroup();
        selectedShowDate = ticketDateFormat.format(selectedShow.getShowDate());
        
        lblGroupOutput.setText(selectedShowGroup);
        lblDateOutput.setText(dateFormat.format(selectedShow.getShowDate()));
        
        showSelected = true;
        
        setOccupiedSeats();
    }
    
    private void setOccupiedSeats() {
        
        occupiedSeats = new HashMap<String, ShowSeating>();
        
        List<ShowSeating> seatList = theatreService.getShowtimeSeats(cmboBxSelectShow.getValue().getShowId());
        for (ShowSeating seat : seatList) {
            String seatId = seat.getRow().toLowerCase() + String.valueOf(seat.getSeatNumber());
            Rectangle occupiedSeat = (Rectangle) parentScene.lookup("#" + seatId);
            occupiedSeat.getStyleClass().clear();
            occupiedSeat.getStyleClass().add("seat-occupied");
            
            occupiedSeats.put(seatId, seat);
        }
    }
    
    public void modifySeat(ActionEvent event) {
        
        boolean isValid = true;
        
        hideErrorLabel();
        
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
            System.out.println("Name is valid");
            if (editMode) {
                updateSeat(firstName, lastName);
            } else {
                addSeat(firstName, lastName);
            }
        }
    }
    
    private void addSeat(String firstName, String lastName) {
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
        Rectangle occupiedSeat = (Rectangle) parentScene.lookup("#" + seatId);
        previousSelectedSeat = occupiedSeat;
        previousSelectedSeatStyle = "seat-occupied";
        
        occupiedSeats.put(seatId, newSeat);
        
        btnAdd.setText("Update");
        editMode = true;
        
    }
    
    private void updateSeat(String firstName, String lastName) {
        
        ShowSeating seat = occupiedSeats.get(seatRow.toLowerCase() + seatNumber);
        seat.setFirstName(firstName);
        seat.setLastName(lastName);
        
        theatreService.updateShowSeat(seat);
    }
    
    public void deleteSeat(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to remove this seat?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            String seatId = seatRow.toLowerCase() + seatNumber;
            ShowSeating seat = occupiedSeats.get(seatId);
            theatreService.deleteShowSeat(seat.getSeatId());

            // Set this as the previous selected seat so it shows as open when selecting another
            Rectangle occupiedSeat = (Rectangle) parentScene.lookup("#" + seatId);
            previousSelectedSeat = occupiedSeat;
            previousSelectedSeatStyle = "seat-open";
            
            occupiedSeats.remove(seatId);
            
            txtBxFirstName.setText("");
            txtBxLastName.setText("");
            btnAdd.setText("Add");
            editMode = false;
        }
    }
    
    private void hideErrorLabel() {
        lblNameError.setVisible(false);
    }
    
    public void printSetup(ActionEvent event) throws IOException {
        
        HBox ticketBox = new HBox();
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        for (ShowSeating seat : seatsToPrint) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ticket.fxml"));
            Parent ticketParent = loader.load();
            TicketController controller = loader.getController();

            controller.setShowName(selectedShowName);
            controller.setGroupName(selectedShowGroup);
            controller.setDate(selectedShowDate);
            controller.setSection("Section: " + seat.getSection());
            controller.setRow("Row: " + seat.getRow());
            controller.setSeat("Seat: " + seat.getSeatNumber());  
            
            ticketBox.getChildren().add(ticketParent);
        }

        
        PrinterJob job = PrinterJob.createPrinterJob();
        
        if (job == null) {
            //Unable to print
            return;
        }
        
        boolean proceed = job.showPrintDialog(window);
        
        if (proceed) {
            print(job, ticketBox);
        }
    }
    
    private void print(PrinterJob job, Node node) {

        // Print the node
        boolean printed = job.printPage(node);
        
        if (printed) {
            job.endJob();
        }
    }
    
}
