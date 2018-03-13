package org.sawmill.theater.ticketing;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sawmill.theater.ticketing.controller.MainController;

public class TicketingApplication extends Application {
     
    @FXML
    public Stage primaryStage;
    
    public Logger logger;
    private static final int LOG_FILE_SIZE = 1024 * 1024;

    public static void main(String[] args) {
        launch(TicketingApplication.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUpLogger();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Cook Forest Sawmill Theatre");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add("/styles/Styles.css");
        MainController controller = loader.getController();
        controller.checkDatabaseStatus();
        primaryStage.setScene(scene);
        primaryStage.show();
        logger.info("Ticketing application started.");
    }
    
    private void setUpLogger() {
        logger = Logger.getLogger("SawmillTheatreLogger");
        try {
            new File(System.getProperty("user.home") + "/SawmillTheatreTickets/logs").mkdirs();
            FileHandler handler = new FileHandler(System.getProperty("user.home") + "/SawmillTheatreTickets/logs/theater_tickets.log", LOG_FILE_SIZE, 5, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setUseParentHandlers(true);
        } catch (IOException ex) {
            logger.warning("Failed to initialize logger handler.");
        } catch (SecurityException ex) {
            logger.warning("Failed to initialize logger handler.");
        }
        
    }
    
}
