package com.example.gamenewjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Path currentRelativePath = Paths.get("");
        String s = ((Path) currentRelativePath).toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("TitleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}