package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.EnemyShip;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Controls enemy ship generation
 */
public class EnemyShipController {

    /**
     * Time since last increase of ships
     */
    private long timeSinceLastShipIncrease = 0;

    /**
     * The current amount of enemy ships alive
     */
    private int currentAmountOfEnemyShips = 0;

    /**
     * The max number of enemy ships
     */
    private int maxNumberOfEnemyShips = 0;

    /**
     * The players ship
     */
    private Ship playerShip;

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT;

    /**
     * The current move speed of the ship
     */
    private double currentMoveSpeed = 0.3;

    /**
     * The max move speed of the ship
     */
    private double currentMaxMoveSpeed = 0.3;

    /**
     * @param _playerShip The players ship
     * @param _LEVEL_WIDTH The width of level
     * @param _LEVEL_HEIGHT The height of level
     */
    public EnemyShipController(Ship _playerShip, int _LEVEL_WIDTH, int _LEVEL_HEIGHT){
        LEVEL_WIDTH = _LEVEL_WIDTH;
        LEVEL_HEIGHT = _LEVEL_HEIGHT;
        playerShip = _playerShip;
    }

    /**
     * @return Current amount of enemy ships
     */
    public int getCurrentAmountOfEnemyShips() {
        return currentAmountOfEnemyShips;
    }

    /**
     * @return Max number of enemy ships
     */
    public int getMaxNumberOfEnemyShips() {
        return maxNumberOfEnemyShips;
    }

    /**
     * @return Random filepath of ship
     */
    public String getShipFilePath(){
        int ranShip = (int) (Math.random() * (4) + 0);
        return switch (ranShip) {
            case 1 -> "imgs/enemyShip.png";
            case 2 -> "imgs/enemyShip1.png";
            case 3 -> "imgs/enemyShip2.png";
            default -> "imgs/enemyShip3.png";
        };
    }


    /**
     * Creates a new ship
     * @return Enemy Ship
     * @throws FileNotFoundException e
     */
    public EnemyShip createNewShip() throws FileNotFoundException {
        currentAmountOfEnemyShips++;
        Random r = new Random();
        double randomSpeed = currentMoveSpeed + (currentMaxMoveSpeed - currentMoveSpeed) * r.nextDouble();
        return new EnemyShip("EnemyShip", 25, 25, getShipFilePath(), randomSpeed, playerShip, LEVEL_WIDTH, LEVEL_HEIGHT);

    }

    /**
     * @return Gets the time since last increasing amount of ships
     */
    public long getTimeSinceLastShipIncrease() {
        return timeSinceLastShipIncrease;
    }

    /**
     * @param timeSinceLastShipIncrease The time to set the time since
     */
    public void setTimeSinceLastShipIncrease(long timeSinceLastShipIncrease) {
        this.timeSinceLastShipIncrease = timeSinceLastShipIncrease;
    }

    /**
     * Decreases amount of ships
     */
    public void shipDestroyed(){
        currentAmountOfEnemyShips--;
    }

    /**
     * Increases number of ship as long as it's less than max ships
     */
    public void increaseMaxShips(){
        if(maxNumberOfEnemyShips < 7){
            currentMaxMoveSpeed = currentMaxMoveSpeed + 0.05;
            maxNumberOfEnemyShips++;
        }

    }


}
