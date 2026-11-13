package com.campusconnect;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistrationScreenTest extends Application {

    @Override
    public void start(Stage stage) {

        RegistrationScreen screen = new RegistrationScreen();

        Scene scene = new Scene(screen, 500, 600);

        stage.setTitle("Campus Event Registration");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}