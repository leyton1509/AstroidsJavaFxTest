package com.example.gamenewjava.Scenes;

import com.example.gamenewjava.Assets.DefaultAsset;
import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Rock;
import com.example.gamenewjava.Assets.Ship;
import com.example.gamenewjava.AstroidController;
import com.example.gamenewjava.Driver;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class TitleScreenController {

    @FXML
    private Button startButton;

    @FXML
    private VBox mainVBox;

    private final int LEVEL_HEIGHT = 600;
    private final int LEVEL_WIDTH = 900;

    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);

    final BooleanProperty upPressed = new SimpleBooleanProperty(false);
    final BooleanProperty downPressed = new SimpleBooleanProperty(false);

    private Rectangle healthBarRed = new Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.RED);

    private Rectangle healthBarWhite = new  Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.WHITE);

    private Text userScoreText = new Text();


    private Pane newBox = new Pane();

    private Ship ship;

    private LinkedList<DefaultAsset> assetsList = new LinkedList<>();

    private Scene scene;

    private long lastUpdate = 0;

    private int userScore = 0;

    private AstroidController astroidController;

    private boolean run = true;

    public void updateScoreText(){
        userScoreText.setText(String.valueOf(userScore));
    }

    public void setUpScoreText(){
        updateScoreText();
        userScoreText.setX((float)LEVEL_WIDTH / 2);
        userScoreText.setY((float)LEVEL_HEIGHT * 0.05);
        userScoreText.setFill(Paint.valueOf("white"));
        userScoreText.setFont(Font.font("Verdana", 20));
    }


    public void setUpHealthBar(){
        healthBarRed.setX((float)LEVEL_WIDTH / 2 - (healthBarRed.getWidth() / 2) );
        healthBarWhite.setX((float)LEVEL_WIDTH / 2 - (healthBarWhite.getWidth() / 2));
        healthBarRed.setY(LEVEL_HEIGHT * 0.95);
        healthBarWhite.setY(LEVEL_HEIGHT * 0.95);
    }

    public void initialSetUpOfScene(){
        Stage stage = (Stage) startButton.getScene().getWindow();  //Pulls in the details of the current stage using the location
        DefaultAsset background;
        astroidController = new AstroidController(LEVEL_WIDTH, LEVEL_HEIGHT);
        LinkedList<Rock> astroids;
        try {
            ship = new Ship("Ship", 75, 75, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\ship.png", (LEVEL_WIDTH / 2) -(75/2), (LEVEL_HEIGHT / 2) -(75/2), 8);
            background = new DefaultAsset("Background", LEVEL_HEIGHT, LEVEL_WIDTH, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\bg.png", 0, 0);
            astroids = astroidController.generateRandomAstroids(astroidController.getMaxNumberOfAstroids());
            astroidController.setCurrentAmountOfAstroids(astroidController.getCurrentAmountOfAstroids() + astroidController.getMaxNumberOfAstroids());
            System.out.println("Astroid num creation : " + astroidController.getCurrentAmountOfAstroids());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assetsList.add(background);
        newBox.getChildren().add(background.getImageView());

        for (Rock ast: astroids) {
            assetsList.add(ast);
            newBox.getChildren().add(ast.getImageView());
        }

        setUpHealthBar();
        setUpScoreText();

        assetsList.add(ship);
        newBox.getChildren().add(ship.getImageView());
        newBox.getChildren().add(healthBarWhite);
        newBox.getChildren().add(healthBarRed);
        newBox.getChildren().add(userScoreText);

        scene = new Scene(newBox, LEVEL_WIDTH, LEVEL_HEIGHT);
        stage.setTitle("Level One");
        stage.setScene(scene);
        stage.show();

    }

    public EventHandler<ActionEvent> returnToTileScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("TitleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        return null;
    }

    public void createDeathPopUp() throws IOException {
        final Stage dialog = new Stage();
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Score : " + userScore));
        Button okButton = new Button();
        okButton.setText("Finish");
        okButton.setOnAction(returnToTileScreen());
        Scene dialogScene = new Scene(dialogVbox, 100, 50);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML
    protected void play() {
        initialSetUpOfScene();
        scene.setOnKeyPressed(event -> {
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
                case SHIFT:
                    try {
                        if (System.currentTimeMillis() > (ship.getTimeSinceLastFiredAdvanced() + (1.5 * 1000))) {
                            Projectile proj = ship.fireAdvancedProjectile();
                            assetsList.add(proj);
                            newBox.getChildren().add(proj.getImageView());
                            ship.setTimeSinceLastFiredAdvanced(System.currentTimeMillis());
                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case SPACE:
                    try {
                        Projectile proj = ship.fireBasicProjectile();
                        assetsList.add(proj);
                        newBox.getChildren().add(proj.getImageView());

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
            }

            if (leftPressed.get()) {
                ship.rotateLeft();
            }
            if (rightPressed.get()) {
                ship.rotateRight();
            }
            if (upPressed.get()) {
                ship.moveForward();
            }
            if (downPressed.get()) {
                ship.moveBackwards();
            }

        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed.set(false);
                case RIGHT -> rightPressed.set(false);
                case UP -> upPressed.set(false);
                case DOWN -> downPressed.set(false);
            }
        });


        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                if(run){
                    if (currentNanoTime - lastUpdate > 2_000_000) {
                        LinkedList<ImageView> removeViews = new LinkedList<>();
                        LinkedList<DefaultAsset> removeAssets = new LinkedList<>();
                        for (DefaultAsset asset : assetsList) {

                            run = collisionDetection(assetsList, asset, removeViews, removeAssets);

                            if(!run){
                                System.out.println("You died!");
                                try {
                                    this.stop();
                                    Stage stage = (Stage) scene.getWindow();
                                    stage.close();
                                    createDeathPopUp();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            if (asset.getName().equals("Bullet") || asset.getName().equals("Rock")) {

                                if (asset.getImageView().getX() < -100 || asset.getImageView().getX() > LEVEL_WIDTH + 100 || asset.getImageView().getY() < -100 || asset.getImageView().getY() > LEVEL_HEIGHT + 100) {
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);

                                    if (asset.getName().equals("Rock")) {
                                        astroidController.decreaseAstroidCount();
                                        System.out.println("Astroid num creation : " + astroidController.getCurrentAmountOfAstroids());

                                        userScore++;
                                    }
                                }

                            }
                            asset.onGameTick();
                        }
                        newBox.getChildren().removeAll(removeViews);
                        assetsList.removeAll(removeAssets);


                        if (astroidController.getCurrentAmountOfAstroids() < astroidController.getMaxNumberOfAstroids()) {
                            try {
                                Rock r = astroidController.generateNewAstroid();
                                assetsList.add(r);
                                newBox.getChildren().add(r.getImageView());
                                astroidController.increaseAstroidCount();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        updateScoreText();

                        if (System.currentTimeMillis() > astroidController.getTimeSinceLastAstroidIncrease() + (15 * 1000)) {
                            astroidController.increaseMaxNumberOfAstroids();
                            astroidController.setTimeSinceLastAstroidIncrease(System.currentTimeMillis());
                        }

                        lastUpdate = currentNanoTime;

                    }
                }
            }
        }.start();
        //}
    }


    public boolean collisionDetection(LinkedList<DefaultAsset> assetList, DefaultAsset asset, LinkedList<ImageView> removeViews, LinkedList<DefaultAsset> removeAssets){
        for (DefaultAsset assetInList: assetList){
            if(asset != assetInList){
                if(asset.getImageView().intersects(assetInList.getImageView().getBoundsInLocal())){
                    if(asset.getName().equals("Bullet")){
                        if(assetInList.getName().equals("Rock")){
                            assetInList.setHealth(assetInList.getHealth() - asset.getDamage());
                            if(assetInList.getHealth() <= 0){
                                removeViews.add(assetInList.getImageView());
                                removeAssets.add(assetInList);
                                astroidController.decreaseAstroidCount();
                                userScore = userScore + assetInList.getWidth();
                            }
                            removeViews.add(asset.getImageView());
                            removeAssets.add(asset);
                        }
                    } else if (asset.getName().equals("Ship")) {
                        if(assetInList.getName().equals("Rock")){
                            if(System.currentTimeMillis() > (asset.getTimeLast() + (0.7*1000))){
                                asset.setHealth(asset.getHealth() - assetInList.getDamage());
                                if(asset.getHealth() <= 0){
                                    return false;
                                }
                                System.out.println("Current health : " + asset.getHealth());

                                assetInList.setMoveSpeed(assetInList.getMoveSpeed() * 2 );
                                assetInList.getImageView().setRotate( asset.getImageView().getRotate());

                                healthBarRed.setWidth(healthBarRed.getWidth() * (asset.getHealth() / asset.getMaxHealth()));

                                asset.setTimeLast(System.currentTimeMillis());

                            }
                        }
                    }
                    else if (asset.getName().equals("Rock")){
                        if(assetInList.getName().equals("Rock")){

                            double angleRock1 = asset.getImageView().getRotate();
                            double angleRock2 = assetInList.getImageView().getRotate();
                            double speedRock1 = asset.getMoveSpeed();
                            double speedRock2 = assetInList.getMoveSpeed();

                            assetInList.setMoveSpeed(speedRock2 * 0.9 );
                            asset.setMoveSpeed(speedRock1 * 0.9 );
                            assetInList.getImageView().setRotate(angleRock1);
                            asset.getImageView().setRotate(angleRock2);


                        }

                    }


                }
            }
        }
        return true;
    }
}
