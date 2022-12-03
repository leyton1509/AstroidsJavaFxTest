package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.EnemyShip;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;
import java.util.Random;

public class EnemyShipController {

    private long timeSinceLastShipIncrease = 0;

    private int currentAmountOfEnemyShips = 0;

    private int maxNumberOfEnemyShips = 0;
    private Ship playerShip;
    private final int LEVEL_WIDTH;
    private final int LEVEL_HEIGHT;
    private double currentMoveSpeed = 0.3;
    private double currentMaxMoveSpeed = 0.3;

    public EnemyShipController(Ship _playerShip, int _LEVEL_WIDTH, int _LEVEL_HEIGHT){
        LEVEL_WIDTH = _LEVEL_WIDTH;
        LEVEL_HEIGHT = _LEVEL_HEIGHT;
        playerShip = _playerShip;
    }

    public int getCurrentAmountOfEnemyShips() {
        return currentAmountOfEnemyShips;
    }

    public int getMaxNumberOfEnemyShips() {
        return maxNumberOfEnemyShips;
    }
    public String getShipFilePath(){
        int ranShip = (int) (Math.random() * (4) + 0);
        return switch (ranShip) {
            case 1 -> "imgs/enemyShip.png";
            case 2 -> "imgs/enemyShip1.png";
            case 3 -> "imgs/enemyShip2.png";
            default -> "imgs/enemyShip3.png";
        };
    }


    public EnemyShip createNewShip() throws FileNotFoundException {
        currentAmountOfEnemyShips++;
        Random r = new Random();
        double randomSpeed = currentMoveSpeed + (currentMaxMoveSpeed - currentMoveSpeed) * r.nextDouble();
        return new EnemyShip("EnemyShip", 25, 25, getShipFilePath(), randomSpeed, playerShip, LEVEL_WIDTH, LEVEL_HEIGHT);

    }

    public long getTimeSinceLastShipIncrease() {
        return timeSinceLastShipIncrease;
    }

    public void setTimeSinceLastShipIncrease(long timeSinceLastShipIncrease) {
        this.timeSinceLastShipIncrease = timeSinceLastShipIncrease;
    }

    public void shipDestroyed(){
        currentAmountOfEnemyShips--;
    }

    public void increaseMaxShips(){
        if(maxNumberOfEnemyShips < 7){
            currentMaxMoveSpeed = currentMaxMoveSpeed + 0.05;
            maxNumberOfEnemyShips++;
        }

    }


}
