package com.example.gamenewjava.Scenes;

import com.example.gamenewjava.AssetControllers.*;
import com.example.gamenewjava.Assets.*;
import com.example.gamenewjava.Assets.Bosses.BossOne;
import com.example.gamenewjava.Assets.Bosses.BossShip;
import com.example.gamenewjava.Assets.Bosses.BossThree;
import com.example.gamenewjava.Assets.Bosses.BossTwo;
import com.example.gamenewjava.Assets.PowerUps.BasePowerUp;
import com.example.gamenewjava.Driver;
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
     * Boolean for if space key is pressed
     */
    final BooleanProperty spacePressed = new SimpleBooleanProperty(false);

    /**
     * Boolean for if shift key is pressed
     */
    final BooleanProperty shiftPressed = new SimpleBooleanProperty(false);

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
     * The controller for the Health Kits
     */
    private HealthKitController hkController;

    /**
     * Whether to run or not
     */
    private boolean run = true;

    /**
     * The graphical interface overlay components
     */
    private final GraphicInterface gi = new GraphicInterface(LEVEL_WIDTH,LEVEL_HEIGHT);

    /**
     * The graphical interface overlay components
     */
    private final BossController bossController = new BossController(LEVEL_WIDTH,LEVEL_HEIGHT);

    /**
     * The enemy ships controller
     */
    private EnemyShipController enemyShipC;

    /**
     * The power ups ships controller
     */
    private PowerUpsController powerUpsController = new PowerUpsController(LEVEL_WIDTH, LEVEL_HEIGHT);

    /**
     * An arraylist of split astroids
     */
    private final ArrayList<Rock> splitAstroids = new ArrayList<>();

    /**
     * An arraylist of boss projectiles
     */
    private final ArrayList<Projectile> bossProjectiles = new ArrayList<>();


    /**
     * Check to see if first boss has been spawned
     */
    private boolean firstBossSpawned = false;


    /**
     * Check to see if first boss killed
     */
    private boolean firstBossKilled = false;

    /**
     * Check to see if second boss has been spawned
     */
    private boolean secondBossSpawned = false;


    /**
     * Check to see if second boss killed
     */
    private boolean secondBossKilled = false;

    /**
     * Check to see if third boss killed
     */
    private boolean thirdBossSpawned = false;


    /**
     * Check to see if third boss killed
     */
    private boolean thirdBossKilled = false;



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
            ship = new Ship("Ship", 35, 35, "imgs/newUserShip.png", (LEVEL_WIDTH / 2) -(75/2), (LEVEL_HEIGHT / 2) -(75/2), 2);
            background = new DefaultAsset("Background", LEVEL_HEIGHT, LEVEL_WIDTH, "imgs/bg.png", 0, 0);
            astroids = astroidController.generateRandomAstroids(astroidController.getMaxNumberOfAstroids());
            astroidController.setCurrentAmountOfAstroids(astroidController.getCurrentAmountOfAstroids() + astroidController.getMaxNumberOfAstroids());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Initialises enemy ship controller

        enemyShipC = new EnemyShipController(ship, LEVEL_WIDTH, LEVEL_HEIGHT);

        hkController = new HealthKitController(LEVEL_WIDTH, LEVEL_HEIGHT);

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
                    shiftPressed.set(true);
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
                    if (System.currentTimeMillis() > (ship.getTimeSinceBasicBulletFired() + (0.04 * 1000))) {
                        Projectile proj = ship.fireBasicProjectile();
                        assetsList.add(proj);
                        newBox.getChildren().add(proj.getImageView());
                        ship.setTimeSinceBasicBulletFired(System.currentTimeMillis());
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            if(shiftPressed.get()){
                try {
                    // Loads and fires a missile
                    if (System.currentTimeMillis() > (ship.getTimeSinceLastFiredAdvanced() + (1.2 * 1000))) {
                        Projectile proj = ship.fireAdvancedProjectile();
                        assetsList.add(proj);
                        newBox.getChildren().add(proj.getImageView());
                        ship.setTimeSinceLastFiredAdvanced(System.currentTimeMillis());
                    }

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
                case SHIFT -> shiftPressed.set(false);
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

                            if (asset.getName().equals("Bullet") || asset.getName().equals("Rock") || asset.getName().equals("EnemyBullet") || asset.getName().equals("HealthKit") || asset.getName().equals("PowerUp")) {
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

                                    if(asset.getImageView().getX() < -25){
                                        asset.getImageView().setX(LEVEL_WIDTH+15);
                                    }else if(asset.getImageView().getX() > LEVEL_WIDTH + 25){
                                        asset.getImageView().setX(-20);
                                    }

                                    if(asset.getImageView().getY() < -25){
                                        asset.getImageView().setY(LEVEL_HEIGHT+15);
                                    }else if(asset.getImageView().getY() > LEVEL_HEIGHT + 25){
                                        asset.getImageView().setY(-15);
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

                            if(asset.getName().equals("BossShip")){
                                assert asset instanceof BossShip;
                                BossShip bossShip = (BossShip) asset;
                                if(bossShip.getBossNumber() == 1){
                                    BossOne bs = (BossOne) asset;
                                    if(System.currentTimeMillis() > bs.getTimeSinceLastFire() + 1.2 * 1000 && bs.isShouldFire()){
                                        try {
                                            bs.shoot(bossProjectiles);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                        bs.setTimeSinceLastFire(System.currentTimeMillis());
                                    }
                                }
                                if(bossShip.getBossNumber() == 2){
                                    BossTwo bs = (BossTwo) asset;
                                    if(System.currentTimeMillis() > bs.getTimeSinceLastFire() + 0.8 * 1000 && bs.isShouldFire()){
                                        try {
                                            bs.shoot(bossProjectiles);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                        bs.setTimeSinceLastFire(System.currentTimeMillis());
                                    }
                                }
                                if(bossShip.getBossNumber() == 3){
                                    BossThree bs = (BossThree) asset;
                                    if(System.currentTimeMillis() > bs.getTimeSinceLastFire() + 1000 && bs.isShouldFire()){
                                        try {
                                            bs.shoot(bossProjectiles);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                        bs.setTimeSinceLastFire(System.currentTimeMillis());
                                    }
                                }
                            }
                            // runs the game tick of that class
                            asset.onGameTick();
                        }

                        // if triple bullet is active then fire 3 projectiles every 0.25 seconds

                        if(ship.isTripleBullet()){
                            if (System.currentTimeMillis() > (ship.getTimeSinceBasicBulletFired() + (0.25 * 1000))) {
                                try {
                                    ArrayList<Projectile> asp = ship.tripleFire();
                                    for (Projectile proj : asp) {
                                        assetsList.add(proj);
                                        newBox.getChildren().add(proj.getImageView());
                                    }
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                ship.setTimeSinceBasicBulletFired(System.currentTimeMillis());
                            }
                        }

                        // Creates a list for removing any counted split rocks
                        ArrayList<Projectile> removeBossProjectiles = new ArrayList<>();

                        // Adds the rocks to the scene and removes them

                        for(Projectile pr : bossProjectiles) {
                            assetsList.add(pr);
                            newBox.getChildren().addAll(pr.getImageView());
                            removeBossProjectiles.add(pr);
                        }

                        bossProjectiles.removeAll(removeBossProjectiles);

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

                        if (astroidController.getCurrentAmountOfAstroids() < astroidController.getMaxNumberOfAstroids() && astroidController.isShouldGenerateAsteroids()) {
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
                        if (enemyShipC.getCurrentAmountOfEnemyShips() < enemyShipC.getMaxNumberOfEnemyShips() && enemyShipC.isShouldGenerateShips()) {
                            try {

                                EnemyShip s = enemyShipC.createNewShip();
                                assetsList.add(s);
                                newBox.getChildren().add(s.getImageView());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Spawns new med kit every 25 s

                        if (System.currentTimeMillis() > hkController.getTimeSinceLastHealthKit() + (25 * 1000)) {
                            try {
                                HealthPack hp = hkController.spawnNewHealthKit();
                                assetsList.add(hp);
                                newBox.getChildren().add(hp.getImageView());
                                hkController.setTimeSinceLastHealthKit(System.currentTimeMillis());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Spawns new power ups every 35 s

                        if (System.currentTimeMillis() > powerUpsController.getTimeSinceLastPowerUp() + (35 * 1000)) {
                            try {
                                BasePowerUp pu = powerUpsController.spawnNewPowerUp();
                                assetsList.add(pu);
                                newBox.getChildren().add(pu.getImageView());
                                powerUpsController.setTimeSinceLastPowerUp(System.currentTimeMillis());
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

                        if (System.currentTimeMillis() > enemyShipC.getTimeSinceLastShipIncrease() + (25 * 1000)) {
                            enemyShipC.increaseMaxShips();
                            enemyShipC.setTimeSinceLastShipIncrease(System.currentTimeMillis());

                        }

                        // Spawns the first boss after 50 seconds

                        if(System.currentTimeMillis() > bossController.getTimeCreated() + (50 * 1000) && !firstBossSpawned){
                            // Loads the boss ship
                            // Adds the ship to the array lists and scene
                            // Sets spawned to true
                            // And turns off generating new astroids and ships
                            BossOne bs;
                            try {
                                bs = bossController.createBossShipOne();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            enemyShipC.setMaxNumberOfEnemyShips(3);
                            assetsList.add(bs);
                            newBox.getChildren().add(bs.getImageView());
                            firstBossSpawned = true;
                            astroidController.setShouldGenerateAsteroids(false);
                            enemyShipC.setShouldGenerateShips(false);
                            gi.getUserScoreText().setVisible(false);
                        }

                        // Sets the generators back one once boss is dead
                        if(firstBossKilled && !astroidController.isShouldGenerateAsteroids() && !secondBossSpawned && !thirdBossSpawned){
                            astroidController.setShouldGenerateAsteroids(true);
                            enemyShipC.setShouldGenerateShips(true);
                        }

                        // Spawns the second boss after 190 seconds

                        if(System.currentTimeMillis() > bossController.getTimeCreated() + (190 * 1000) && !secondBossSpawned && firstBossKilled){
                            BossShip bs;
                            try {
                                bs = bossController.createBossShipTwo(ship);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            assetsList.add(bs);
                            newBox.getChildren().add(bs.getImageView());
                            secondBossSpawned = true;
                            astroidController.setShouldGenerateAsteroids(false);
                            enemyShipC.setShouldGenerateShips(false);
                            gi.getUserScoreText().setVisible(false);
                        }

                        // Sets the generators back one once boss is dead

                        if(secondBossKilled && !astroidController.isShouldGenerateAsteroids() && !thirdBossSpawned){
                            astroidController.setShouldGenerateAsteroids(true);
                            enemyShipC.setShouldGenerateShips(true);
                        }

                        // Spawns the third boss after 390 seconds

                        if(System.currentTimeMillis() > bossController.getTimeCreated() + (390 * 1000) && !thirdBossSpawned && secondBossKilled){
                            BossShip bs;
                            try {
                                bs = bossController.createBossShipThree(ship);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            assetsList.add(bs);
                            newBox.getChildren().add(bs.getImageView());
                            thirdBossSpawned = true;
                            astroidController.setShouldGenerateAsteroids(false);
                            enemyShipC.setShouldGenerateShips(false);
                            gi.getUserScoreText().setVisible(false);
                        }

                        // Sets the generators back one once boss is dead

                        if(thirdBossKilled && !astroidController.isShouldGenerateAsteroids()){
                            astroidController.setShouldGenerateAsteroids(true);
                            enemyShipC.setShouldGenerateShips(true);
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
                                case "BossShip" -> {
                                    assetInList.decreaseHealth(asset.getDamage());
                                    if (assetInList.getHealth() <= 0) {
                                        removeViews.add(assetInList.getImageView());
                                        removeAssets.add(assetInList);
                                        enemyShipC.setMaxNumberOfEnemyShips(3);
                                        gi.getUserScoreText().setVisible(true);
                                        BossShip bs = (BossShip) assetInList;
                                        if(bs.getBossNumber() == 1){
                                            firstBossKilled = true;
                                            userScore = userScore + 3000;
                                        }else if(bs.getBossNumber() == 2){
                                            secondBossKilled = true;
                                            userScore = userScore + 5000;
                                        }
                                        else if(bs.getBossNumber() == 3){
                                            thirdBossKilled = true;
                                            userScore = userScore + 10000;
                                        }

                                    }
                                    removeViews.add(asset.getImageView());
                                    removeAssets.add(asset);
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
                                if (System.currentTimeMillis() > (asset.getTimeLast() + (0.5 * 1000))) {

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
                            else if (assetInList.getName().equals("HealthKit")){
                                HealthPack hp = (HealthPack) assetInList;
                                asset.increaseHealth(hp.getHealthIncrease());
                                damageTaken();
                                removeViews.add(assetInList.getImageView());
                                removeAssets.add(assetInList);
                            }
                            else if (assetInList.getName().equals("PowerUp")){
                                BasePowerUp bpu = (BasePowerUp) assetInList;
                                ship.updatePowerUp(bpu);
                                removeViews.add(assetInList.getImageView());
                                removeAssets.add(assetInList);
                            }

                            else if (assetInList.getName().equals("BossShip")){
                                if (System.currentTimeMillis() > (asset.getTimeLast() + (0.4 * 1000))) {
                                    asset.decreaseHealth(15);
                                    damageTaken();
                                    if (asset.getHealth() <= 0) {
                                        return false;
                                    }
                                    double angleOfCollisionRad = Math.atan2(ship.getImageView().getY() - assetInList.getImageView().getY(), ship.getImageView().getX() - assetInList.getImageView().getX());
                                    angleOfCollisionRad += Math.PI / 2.0;
                                    double angleOfCollision = Math.toDegrees(angleOfCollisionRad);
                                    if (angleOfCollision < 0) {
                                        angleOfCollision += 360;
                                    }
                                    ship.getImageView().setRotate(angleOfCollision);
                                    asset.setTimeLast(System.currentTimeMillis());
                                }

                            }


                            break;
                        // If two rocks collide use collision circle method
                        case "Rock":
                            if (assetInList.getName().equals("Rock")) {
                                circleCollision((Rock) asset, (Rock) assetInList);
                            }
                            else if(assetInList.getName().equals("BossShip")){
                                removeViews.add(asset.getImageView());
                                removeAssets.add(asset);
                            }
                            break;
                        // If two rocks collide use collision circle method
                        case "SplitRock":
                            if (assetInList.getName().equals("SplitRock") || assetInList.getName().equals("Rock")) {
                                circleCollision((Rock) asset, (Rock) assetInList);
                            }
                            else if(assetInList.getName().equals("BossShip")){
                                removeViews.add(asset.getImageView());
                                removeAssets.add(asset);
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
