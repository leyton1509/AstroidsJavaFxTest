package com.example.gamenewjava.Scenes;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TitleScreenController {

    @FXML
    private Button startButton;

    @FXML
    protected void play(Event event) throws IOException {

        // Loads the quiz scene (spelt wrong accidentally)
        Parent root =  FXMLLoader.load(getClass().getResource("Level.fxml"));
        // Change the size of the window, stage is controlled by the scene
        Stage quizStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene quizScene = new Scene(root, 600, 600);
        quizStage.setScene(quizScene);
        quizStage.show();

    }
}
