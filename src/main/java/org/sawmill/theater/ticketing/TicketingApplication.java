package org.sawmill.theater.ticketing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TicketingApplication extends Application {
    
    private ConfigurableApplicationContext springContext;
    private Parent root;


    public static void main(String[] args) {
        launch(TicketingApplication.class, args);
    }
    
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(TicketingApplication.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cook Forest Sawmill Theatre");
        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void stop() throws Exception {
        springContext.stop();
    }
}
