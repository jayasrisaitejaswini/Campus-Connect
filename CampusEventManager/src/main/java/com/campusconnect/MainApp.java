
package com.campusconnect;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Dashboard dashboard = new Dashboard();

        Scene scene = new Scene(dashboard, 1400, 850);

        stage.setTitle("Campus Connect | Event Management System");
        stage.setScene(scene);

        stage.setMinWidth(1100);
        stage.setMinHeight(700);

        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
