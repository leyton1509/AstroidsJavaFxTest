package com.example.gamenewjava.Scenes;

import com.example.gamenewjava.Assets.DefaultAsset;
import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Rock;
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
import javafx.scene.image.ImageView;
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
    private final int LEVEL_WIDTH = 900;

    private int numberOfAstroids = 10;

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
        LinkedList<Rock> astroids = new LinkedList<>();
        try {
            ship = new Ship("Ship", 75, 75, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\ship.png", (LEVEL_WIDTH / 2) -(75/2), (LEVEL_HEIGHT / 2) -(75/2), 8);
            background = new DefaultAsset("Background", LEVEL_HEIGHT, LEVEL_WIDTH, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\bg.png", 0, 0);
            for (int i = 0; i < numberOfAstroids; i++) {
                int ranAstroid = (int) (Math.random() * (4 - 0) + 0);
                String filepath;

                switch (ranAstroid){
                    case 0:
                        filepath = "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid1.png";
                        break;
                    case 1:
                        filepath = "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid2.png";
                        break;
                    case 2:
                        filepath = "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid3.png";
                        break;
                    case 3:
                        filepath = "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid4.png";
                        break;
                    default:
                        filepath = "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid1.png";

                }

                int ranSize = (int) (Math.random() * (40 - 10) + 10);
                astroids.add(new Rock("Rock", ranSize, ranSize, filepath,(int) (Math.random() * (LEVEL_WIDTH - ((LEVEL_WIDTH * 0.1) -1)) + 1), (int) (Math.random() * (LEVEL_HEIGHT - (LEVEL_HEIGHT * 0.1)) -1 + 1), 0.5));

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assetsList.add(background);
        newBox.getChildren().add(background.getImageView());


        for (Rock ast: astroids) {
            assetsList.add(ast);
            newBox.getChildren().add(ast.getImageView());
        }

        assetsList.add(ship);
        newBox.getChildren().add(ship.getImageView());


        Scene scene = new Scene(newBox, LEVEL_WIDTH, LEVEL_HEIGHT);
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
            public void handle(long currentNanoTsime)
            {
                LinkedList<ImageView> removeViews = new LinkedList<>();
                LinkedList<DefaultAsset> removeAssets = new LinkedList<>();
                for (DefaultAsset asset:assetsList) {

                    collisionDetection(assetsList, asset, removeViews, removeAssets);

                    if(asset.getName().equals("BasicBullet") || asset.getName().equals("Rock")){

                        if(asset.getImageView().getX() < 0 || asset.getImageView().getX() > LEVEL_WIDTH || asset.getImageView().getY() < 0 || asset.getImageView().getY() > LEVEL_HEIGHT){
                            removeViews.add(asset.getImageView());
                            removeAssets.add(asset);
                        }

                    }

                    asset.onGameTick();
                }
                newBox.getChildren().removeAll(removeViews);
                assetsList.removeAll(removeAssets);

                //System.out.println(newBox.getChildren().toString());
            }
        }.start();

    }

    public void collisionDetection(LinkedList<DefaultAsset> assetList, DefaultAsset asset, LinkedList<ImageView> removeViews, LinkedList<DefaultAsset> removeAssets){
        for (DefaultAsset assetInList: assetList){
            if(asset != assetInList){
                if(asset.getImageView().intersects(assetInList.getImageView().getBoundsInLocal())){

                    if(asset.getName().equals("BasicBullet")){
                        if(assetInList.getName().equals("Rock")){
                            assetInList.setHealth(assetInList.getHealth() - asset.getDamage());
                            if(assetInList.getHealth() <= 0){
                                removeViews.add(assetInList.getImageView());
                                removeAssets.add(assetInList);
                            }
                            removeViews.add(asset.getImageView());
                            removeAssets.add(asset);
                        }
                        
                    } else if (asset.getName().equals("Ship")) {
                        
                    }
                    else if (asset.getName().equals("Rock")){
                        if(assetInList.getName().equals("Rock")){

                            double angleRock1 = asset.getImageView().getRotate();
                            double angleRock2 = assetInList.getImageView().getRotate();
                            double speedRock1 = asset.getMoveSpeed();
                            double speedRock2 = assetInList.getMoveSpeed();
                            double sizeRock1 = asset.getWidth();
                            double sizeRock2 = assetInList.getWidth();

                            assetInList.setMoveSpeed(speedRock2 * 0.9 );
                            asset.setMoveSpeed(speedRock1 * 0.9 );
                            assetInList.getImageView().setRotate(angleRock1);
                            asset.getImageView().setRotate(angleRock2);


                        }

                    }


                }
            }
        }

    }
}
