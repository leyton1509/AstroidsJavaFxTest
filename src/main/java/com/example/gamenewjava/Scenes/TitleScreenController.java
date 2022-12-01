package com.example.gamenewjava.Scenes;

import com.example.gamenewjava.Assets.DefaultAsset;
import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Ship;
import com.example.gamenewjava.Driver;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class TitleScreenController {

    @FXML
    private Button startButton;

    @FXML
    private VBox mainVBox;

    private final int LEVEL_HEIGHT = 600;
    private final int LEVEL_WIDTH = 600;

    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);

    final BooleanProperty upPressed = new SimpleBooleanProperty(false);
    final BooleanProperty downPressed = new SimpleBooleanProperty(false);

    @FXML
    protected void play(Event event) throws IOException {
        Pane newBox = new Pane();
        Stage stage = (Stage) startButton.getScene().getWindow();  //Pulls in the details of the current stage using the location
        // mainVBox.getChildren().remove(startButton);
        LinkedList<DefaultAsset> assetsList = new LinkedList<>();
        Ship ship;
        DefaultAsset background;
        try {
            ship = new Ship("Ship", 75, 75, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\ship.png", (LEVEL_WIDTH / 2) -(75/2), (LEVEL_HEIGHT / 2) -(75/2));
            background = new DefaultAsset("Background", LEVEL_HEIGHT, LEVEL_WIDTH, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\bg.png", 0, 0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assetsList.add(background);
        assetsList.add(ship);
        // Group assets = new Group(background.getImageView(), ship.getImageView());

        newBox.getChildren().add(background.getImageView());
        newBox.getChildren().add(ship.getImageView());


        Scene scene = new Scene(newBox, 600, 600);
        stage.setTitle("Level One");
        stage.setScene(scene);
        stage.show();



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        leftPressed.set(true);
                        break;
                    case RIGHT:
                        rightPressed.set(true);
                        break;
                    case UP:
                        upPressed.set(true);
                        break;
                    case DOWN:
                        downPressed.set(true);
                        break;
                    // case SHIFT: running = true; break;
                    case SPACE:
                        System.out.println("Space clicked");
                        try {
                            Projectile proj = ship.fireBasicProjectile();
                            assetsList.add(proj);
                            newBox.getChildren().add(proj.getImageView());

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                }

                if(leftPressed.get()){
                    ship.rotateLeft();
                }
                if(rightPressed.get()){
                    ship.rotateRight();
                }
                if(upPressed.get()){
                    ship.moveForward();
                }
                if(downPressed.get()){
                    ship.moveBackwards();
                }

            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        leftPressed.set(false);
                        break;
                    case RIGHT:
                        rightPressed.set(false);
                        break;
                    case UP:
                        upPressed.set(false);
                        break;
                    case DOWN:
                        downPressed.set(false);
                        break;
                    // case SHIFT: running = true; break;
                }
            }
        });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                for (DefaultAsset asset:assetsList) {
                    asset.onGameTick();
                }
            }
        }.start();

    }
}
