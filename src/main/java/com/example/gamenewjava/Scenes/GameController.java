package com.example.gamenewjava.Scenes;

import com.example.gamenewjava.Assets.*;
import com.example.gamenewjava.AssetControllers.AstroidController;
import com.example.gamenewjava.Driver;
import com.example.gamenewjava.AssetControllers.EnemyShipController;
import com.example.gamenewjava.GUI.GraphicInterface;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Controls the game
 * Handles all inputs and game loop
 */
public class GameController {

    /**
     * Button for exiting program
     */
    @FXML
    public Button exitButton;

    /**
     * The start button to start game
     */
    @FXML
    private Button startButton;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT = 600;

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH = 900;


    /**
     * Boolean for if left key is pressed
     */
    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);

    /**
     * Boolean for if right key is pressed
     */
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);

    /**
     * Boolean for if up key is pressed
     */
    final BooleanProperty upPressed = new SimpleBooleanProperty(false);

    /**
     * Boolean for if down key is pressed
     */
    final BooleanProperty downPressed = new SimpleBooleanProperty(false);

    /**
     * Boolean for if down key is pressed
     */
    final BooleanProperty spacePressed = new SimpleBooleanProperty(false);

    /**
     * The main pane for the game
     */
    private final Pane newBox = new Pane();

    /**
     * The players ship
     */
    private Ship ship;

    /**
     * A linked list of assets that are on screen
     */
    private final LinkedList<DefaultAsset> assetsList = new LinkedList<>();

    /**
     * The FXML scene
     */
    private Scene scene;

    /**
     * How long since the frame was updates
     */
    private long lastUpdate = 0;

    /**
     * The score of the user
     */
    private int userScore = 0;

    /**
     * The controller for the astroids
     */
    private AstroidController astroidController;

    /**
     * Whether to run or not
     */
    private boolean run = true;

    /**
     * The graphical interface overlay components
     */
    private final GraphicInterface gi = new GraphicInterface(LEVEL_WIDTH,LEVEL_HEIGHT);

    /**
     * The enemy ships controller
     */
    private EnemyShipController enemyShipC;

    /**
     * An arraylist of split astroids
     */
    private final ArrayList<Rock> splitAstroids = new ArrayList<>();

    /**
     * Exits the stage
     */
    public void exit(){
        Platform.exit();
    }


    /**
     * Sets up the scene and all initial assets
     * Creates ship and initial astroids
     */
    public void initialSetUpOfScene(){
        // Gets the scene from the button
        // Creates the background and initialises astroid controller
        Stage stage = (Stage) startButton.getScene().getWindow();  //Pulls in the details of the current stage using the location
        DefaultAsset background;
        astroidController = new AstroidController(LEVEL_WIDTH, LEVEL_HEIGHT);
        LinkedList<Rock> astroids;

        // Loads the images in one try as it needs to catch file not found error

        try {
            ship = new Ship("Ship", 75, 75, "imgs/newUserShip.png", (LEVEL_WIDTH / 2) -(75/2), (LEVEL_HEIGHT / 2) -(75/2), 2);
            background = new DefaultAsset("Background", LEVEL_HEIGHT, LEVEL_WIDTH, "imgs/bg.png", 0, 0);
            astroids = astroidController.generateRandomAstroids(astroidController.getMaxNumberOfAstroids());
            astroidController.setCurrentAmountOfAstroids(astroidController.getCurrentAmountOfAstroids() + astroidController.getMaxNumberOfAstroids());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Initialises enemy ship controller

        enemyShipC = new EnemyShipController(ship, LEVEL_WIDTH, LEVEL_HEIGHT);

        // Adds the assets to the pane

        assetsList.add(background);
        newBox.getChildren().add(background.getImageView());

        for (Rock ast: astroids) {
            assetsList.add(ast);
            newBox.getChildren().add(ast.getImageView());
        }

        // Sets up the health and score

        gi.setUpHealthBars();
        gi.setUpScoreText();

        // Adds the rest of the assets to the pane

        assetsList.add(ship);
        newBox.getChildren().add(ship.getImageView());
        newBox.getChildren().add(gi.getHealthBarWhite());
        newBox.getChildren().add(gi.getHealthBarRed());
        newBox.getChildren().add(gi.getUserScoreText());

        scene = new Scene(newBox, LEVEL_WIDTH, LEVEL_HEIGHT);
        stage.setTitle("Level One");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Returns to the title screen
     * @return null
     * @throws IOException exception
     */
    public EventHandler<ActionEvent> returnToTileScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("TitleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        return null;
    }

    /**
     * Creates a popup telling the user their score and returns to title screen
     * @throws IOException exception
     */
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

    /**
     * Main game play loop
     * Handles key presses
     * Handles game ticks
     */
    @FXML
    protected void play(){
        // Sets up scene
        initialSetUpOfScene();

        // Listens for left / right / up / down / shift / space pressed
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
                case Z:
                    try {
                        // Loads ultimate
                        if (System.currentTimeMillis() > (ship.getTimeSinceUltimateFired() + (6 * 1000))) {
                            ArrayList<Projectile> projs = ship.ultimateFired();
                            for (Projectile proj: projs) {
                                assetsList.add(proj);
                                newBox.getChildren().add(proj.getImageView());
                            }
                            ship.setTimeSinceUltimateFired(System.currentTimeMillis());
                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                     break;
                case SHIFT:
                    try {
                        // Loads and fires a missile
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
                    // Loads and fires a bullet
                spacePressed.set(true);
            }

            // Movement upon key press

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
            if(spacePressed.get()){
                try {
                    Projectile proj = ship.fireBasicProjectile();
                    assetsList.add(proj);
                    newBox.getChildren().add(proj.getImageView());

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        // Sets keys to false once released

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed.set(false);
                case RIGHT -> rightPressed.set(false);
                case UP -> upPressed.set(false);
                case DOWN -> downPressed.set(false);
                case SPACE -> spacePressed.set(false);
            }
        });


        // Creates a new animation timer for the game loop elements
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                // If running and updating is necessary

                if(run){
                    if (currentNanoTime - lastUpdate > 2_000_000) {
                        // Creates a set of Linked Lists for assets to remove and add assets / views
                        LinkedList<ImageView> removeViews = new LinkedList<>();
                        LinkedList<DefaultAsset> removeAssets = new LinkedList<>();
                        LinkedList<DefaultAsset> addAssets = new LinkedList<>();
                        LinkedList<ImageView> addViews = new LinkedList<>();

                        // Loop through each asset

                        for (DefaultAsset asset : assetsList) {

                            // Handle collision detections

                            try {
                                run = collisionDetection(assetsList, asset, removeViews, removeAssets);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            // If the user died, close the stage and create death popup

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

                            // If the bullets or rocks go out of bounds remove them

                            if (asset.getName().equals("Bullet") || asset.getName().equals("Rock") || asset.getName().equals("EnemyBullet")) {
                                if (asset.getImageView().getX() < -100 || asset.getImageView().getX() > LEVEL_WIDTH + 100 || asset.getImageView().getY() < -100 || asset.getImageView().getY() > LEVEL_HEIGHT + 100) {
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);
                                    // Decreases rock count and increases user score
                                    if (asset.getName().equals("Rock")) {
                                        astroidController.decreaseAstroidCount();
                                        userScore++;
                                    }
                                }
                            // If the ship goes out of bounds wrap it around
                            } else if (asset.getName().equals("Ship")) {

                                    if(asset.getImageView().getX() < -70){
                                        asset.getImageView().setX(LEVEL_WIDTH+30);
                                    }else if(asset.getImageView().getX() > LEVEL_WIDTH + 50){
                                        asset.getImageView().setX(-40);
                                    }

                                    if(asset.getImageView().getY() < -50){
                                        asset.getImageView().setY(LEVEL_HEIGHT+30);
                                    }else if(asset.getImageView().getY() > LEVEL_HEIGHT + 50){
                                        asset.getImageView().setY(-30);
                                    }
                            }

                            // Handles the enemy ship firing at the player
                            if(asset.getName().equals("EnemyShip")){
                                EnemyShip esp = (EnemyShip) asset;
                                if(System.currentTimeMillis() > esp.getTimeSinceLastFire() + 2 * 1000){
                                    try {
                                        Projectile proj = esp.fireBasicProjectile();
                                        addAssets.add(proj);
                                        addViews.add(proj.getImageView());
                                        esp.setTimeSinceLastFire(System.currentTimeMillis());
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }

                            // runs the game tick of that class
                            asset.onGameTick();

                        }


                        // Creates a list for removing any counted split rocks
                        ArrayList<Rock> removeAstroidList = new ArrayList<>();

                        // Adds the rocks to the scene and removes them

                        for(Rock rock : splitAstroids) {
                            assetsList.add(rock);
                            newBox.getChildren().addAll(rock.getImageView());
                            removeAstroidList.add(rock);
                        }

                        splitAstroids.removeAll(removeAstroidList);

                        // Remove and add necessary items to scene

                        newBox.getChildren().removeAll(removeViews);
                        assetsList.removeAll(removeAssets);
                        assetsList.addAll(addAssets);
                        newBox.getChildren().addAll(addViews);

                        // If a new astroid needs to be generated create it

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

                        // If an enemy ship needs to be created, create it
                        if (enemyShipC.getCurrentAmountOfEnemyShips() < enemyShipC.getMaxNumberOfEnemyShips()) {
                            try {
                                EnemyShip s = enemyShipC.createNewShip();
                                assetsList.add(s);
                                newBox.getChildren().add(s.getImageView());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Updates user score

                        gi.updateScoreText(userScore);

                        // Increase difficulty by increasing the max amount of astroids and ships every N seconds

                        if (System.currentTimeMillis() > astroidController.getTimeSinceLastAstroidIncrease() + (15 * 1000)) {
                            astroidController.increaseMaxNumberOfAstroids();
                            astroidController.setTimeSinceLastAstroidIncrease(System.currentTimeMillis());
                        }

                        if (System.currentTimeMillis() > enemyShipC.getTimeSinceLastShipIncrease() + (10 * 1000)) {
                            enemyShipC.increaseMaxShips();
                            enemyShipC.setTimeSinceLastShipIncrease(System.currentTimeMillis());
                        }

                        lastUpdate = currentNanoTime;
                    }
                }
            }
        }.start();
        //}
    }

    /**
     * Once damage is taken update health bar
     */
    public void damageTaken(){
        if(ship.getHealth() <= 0){
            gi.getHealthBarRed().setWidth(0);
        }
        else{
            gi.getHealthBarRed().setWidth((int) gi.getMaxWidth() * (ship.getHealth() / ship.getMaxHealth()));
        }
    }


    /**
     * Detects all collisions in the game
     * @param assetList The list of all assets
     * @param asset The current asset
     * @param removeViews ImageViews to remove to add to
     * @param removeAssets Assets to remove to add tp
     * @return whether the use died or not
     * @throws FileNotFoundException e
     */
    public boolean collisionDetection(LinkedList<DefaultAsset> assetList, DefaultAsset asset, LinkedList<ImageView> removeViews, LinkedList<DefaultAsset> removeAssets) throws FileNotFoundException {

        // Loops through the assets and as long as it is not itself
        for (DefaultAsset assetInList: assetList){
            if(asset != assetInList){
                // If the asset is collided with the looped asset
                if(asset.getImageView().intersects(assetInList.getImageView().getBoundsInLocal())){
                    // If it's a bullet
                    switch (asset.getName()) {
                        case "Bullet":
                            // If the bullet collides with a rock
                            // Decrease the rock health
                            // Check if it dies and removes it if it does, adds to score and decreases astroid count
                            // Adds any rocks created from destroying it to the split astroid array
                            // Removes the bullet from scene
                            switch (assetInList.getName()) {
                                case "Rock" -> {
                                    assetInList.decreaseHealth(asset.getDamage());
                                    if (assetInList.getHealth() <= 0) {
                                        removeViews.add(assetInList.getImageView());
                                        removeAssets.add(assetInList);
                                        astroidController.decreaseAstroidCount();
                                        userScore = userScore + assetInList.getWidth();
                                        splitAstroids.addAll(astroidController.checkForSplitAstroid((Rock) assetInList));
                                    }
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);
                                }
                                // If the bullet collides with an enemy ship
                                // Decrease the shi[ health
                                // Check if it dies and removes it if it does, adds to score and decreases ship count
                                // Removes the bullet from scene
                                case "EnemyShip" -> {
                                    assetInList.decreaseHealth(asset.getDamage());
                                    if (assetInList.getHealth() <= 0) {
                                        removeViews.add(assetInList.getImageView());
                                        removeAssets.add(assetInList);
                                        userScore = userScore + assetInList.getWidth();
                                        enemyShipC.shipDestroyed();
                                    }
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);
                                }
                                // If it's a split rock
                                // Damage it and remove if dead
                                // Don't decrease astroid count
                                // Removes bullet
                                case "SplitRock" -> {
                                    assetInList.decreaseHealth(asset.getDamage());
                                    if (assetInList.getHealth() <= 0) {
                                        removeViews.add(assetInList.getImageView());
                                        removeAssets.add(assetInList);
                                        userScore = userScore + assetInList.getWidth();
                                        splitAstroids.addAll(astroidController.checkForSplitAstroid((Rock) assetInList));
                                    }
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);
                                }
                                // If it hits enemy bullet, remove the bullet
                                case "EnemyBullet" -> {
                                    removeViews.add(assetInList.getImageView());
                                    removeAssets.add(assetInList);
                                }
                            }
                            // If it's the ship
                            break;
                        case "Ship":
                            // See if it collides with rock
                            if (assetInList.getName().equals("Rock") || assetInList.getName().equals("SplitRock")) {
                                // Only allow collisions every 0.7 seconds
                                // Takes damage
                                // returns false if dead
                                // Sets the rock on a new collision
                                if (System.currentTimeMillis() > (asset.getTimeLast() + (0.7 * 1000))) {

                                    asset.decreaseHealth(assetInList.getDamage());
                                    damageTaken();
                                    if (asset.getHealth() <= 0) {
                                        return false;
                                    }
                                    assetInList.setMoveSpeed(assetInList.getMoveSpeed() * 2);
                                    double angleOfCollisionRad = Math.atan2(assetInList.getImageView().getY() - ship.getImageView().getY(), assetInList.getImageView().getX() - ship.getImageView().getX());
                                    angleOfCollisionRad += Math.PI / 2.0;
                                    double angleOfCollision = Math.toDegrees(angleOfCollisionRad);
                                    if (angleOfCollision < 0) {
                                        angleOfCollision += 360;
                                    }
                                    assetInList.getImageView().setRotate(angleOfCollision);
                                    asset.setTimeLast(System.currentTimeMillis());

                                    if (assetInList.getWidth() < 16) {
                                        removeViews.add(assetInList.getImageView());
                                        removeAssets.add(assetInList);
                                    }

                                }
                            }
                            // If it collides with enemy ship
                            // Take damage and destroy enemy ship
                            else if (assetInList.getName().equals("EnemyShip") || assetInList.getName().equals("EnemyBullet")) {
                                asset.decreaseHealth(assetInList.getDamage());
                                removeViews.add(assetInList.getImageView());
                                removeAssets.add(assetInList);
                                damageTaken();
                                if (asset.getHealth() <= 0) {
                                    return false;
                                }
                            }

                            break;
                        // If two rocks collide use collision circle method
                        case "Rock":
                            if (assetInList.getName().equals("Rock")) {
                                circleCollision((Rock) asset, (Rock) assetInList);
                            }
                            break;
                        // If two rocks collide use collision circle method
                        case "SplitRock":
                            if (assetInList.getName().equals("SplitRock") || assetInList.getName().equals("Rock")) {
                                circleCollision((Rock) asset, (Rock) assetInList);
                            }
                            break;
                    }


                }
            }
        }
        return true;
    }


    /**
     * @param rock1 The first rock
     * @param rock2 The second rock
     */
    public void circleCollision(Rock rock1, Rock rock2){
        // Calculates the angle of collision
        double angleOfCollisionRad = Math.atan2(rock1.getImageView().getY() - rock2.getImageView().getY(), rock1.getImageView().getX() - rock2.getImageView().getX());
        angleOfCollisionRad += Math.PI/2.0;
        double angleOfCollision = Math.toDegrees(angleOfCollisionRad);
        if (angleOfCollision < 0) {
            angleOfCollision += 360;
        }

        // Sets the speed depending on the size

        if(rock2.getSize() < rock1.getSize() / 2){
            rock1.setMoveSpeed(rock1.getMoveSpeed() * 0.9 );
            rock2.setMoveSpeed(rock2.getMoveSpeed() * 1.2 );
        }
        else if(rock1.getSize() < rock2.getSize() / 2){
            rock1.setMoveSpeed(rock1.getMoveSpeed() * 1.2 );
            rock2.setMoveSpeed(rock2.getMoveSpeed() * 0.9 );
        }
        else{
            rock1.setMoveSpeed(rock1.getMoveSpeed() * 0.5);
            rock2.setMoveSpeed(rock2.getMoveSpeed() * 0.5);
        }

        // Sets the angles
        rock1.getImageView().setRotate(angleOfCollision);
        rock2.getImageView().setRotate(angleOfCollision-180);





    }
}
