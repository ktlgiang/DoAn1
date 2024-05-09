package org.example.doan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1005, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
