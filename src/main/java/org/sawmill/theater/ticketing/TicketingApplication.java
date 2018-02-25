package org.sawmill.theater.ticketing;

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

    public static void main(String[] args) {
        launch(TicketingApplication.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Cook Forest Sawmill Theatre");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add("/styles/Styles.css");
        MainController controller = loader.getController();
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.checkDatabaseStatus();
    }
    
}
