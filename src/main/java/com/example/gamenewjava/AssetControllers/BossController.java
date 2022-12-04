package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.Bosses.BossOne;
import com.example.gamenewjava.Assets.Bosses.BossShip;
import com.example.gamenewjava.Assets.Bosses.BossThree;
import com.example.gamenewjava.Assets.Bosses.BossTwo;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;

public class BossController {

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT;

    /**
     * Time since last fired
     */
    private long timeCreated = System.currentTimeMillis();

    /**
     * @return the time since last fired
     */
    public long getTimeCreated() {
        return timeCreated;
    }

    /**
     * @param timeSinceLastFire Sets the time since last fired
     */
    public void setTimeCreated(long timeSinceLastFire) {
        this.timeCreated = timeSinceLastFire;
    }

    /**
     * @param width Width of level
     * @param height height of level
     */
    public BossController(int width, int height){
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
    }


    /**
     * Creates a boss ship
     * @return Boss ship one
     * @throws FileNotFoundException e
     */
    public BossOne createBossShipOne() throws FileNotFoundException {
        int bossNum = 1;
        String filepath = getShipFilePath(bossNum);
        BossOne bs = new BossOne("BossShip", getShipHeight(bossNum), getShipWidth(bossNum), filepath,  1,getShipHealth(bossNum), getShipDamage(bossNum), bossNum);
        bs.getImageView().setX((double) (LEVEL_WIDTH / 2 - getShipWidth(bossNum) /2));
        bs.getImageView().setY(-400);
        bs.getImageView().setRotate(180);
        bs.setMaxYPos(-30);
        return bs;
    }

    /**
     * Creates a boss ship
     * @return Boss ship two
     * @param _playerShip the ship of the player
     * @throws FileNotFoundException e
     */
    public BossTwo createBossShipTwo(Ship _playerShip) throws FileNotFoundException {
        int bossNum = 2;
        String filepath = getShipFilePath(bossNum);
        BossTwo bs = new BossTwo("BossShip", getShipHeight(bossNum), getShipWidth(bossNum), filepath,  1,getShipHealth(bossNum), getShipDamage(bossNum), bossNum, _playerShip);
        bs.getImageView().setX((double) (LEVEL_WIDTH / 2 - getShipWidth(bossNum) /2));
        bs.getImageView().setY(-400);
        bs.getImageView().setRotate(180);
        bs.setMaxYPos(-30);
        return bs;
    }

    /**
     * Creates a boss ship
     * @return Boss ship three
     * @param _playerShip the ship of the player
     * @throws FileNotFoundException e
     */
    public BossThree createBossShipThree(Ship _playerShip) throws FileNotFoundException {
        int bossNum = 3;
        String filepath = getShipFilePath(bossNum);
        BossThree bs = new BossThree("BossShip", getShipHeight(bossNum), getShipWidth(bossNum), filepath,  1,getShipHealth(bossNum), getShipDamage(bossNum), bossNum,_playerShip);
        bs.getImageView().setX((double) (LEVEL_WIDTH / 2 - getShipWidth(bossNum) /2));
        bs.getImageView().setY(-400);
        bs.getImageView().setRotate(180);
        bs.setMaxYPos(-30);
        return bs;
    }

    /**
     * @return String of the bullet filepath
     */
    public String getShipFilePath(int selectWhichBossToUse){
        return switch (selectWhichBossToUse) {
            case 1 -> "imgs/enemyBossShip1.png";
            case 2 -> "imgs/enemyBossShip2.png";
            case 3 -> "imgs/enemyBossShip3.png";
            default -> "";
        };
    }

    /**
     * @param selectWhichBossToUse The boss to use
     * @return The height of the ship
     */
    public int getShipHeight(int selectWhichBossToUse){
        return switch (selectWhichBossToUse) {
            case 1 -> 170;
            case 2 -> 190;
            case 3 -> 195;
            default -> 200;
        };
    }

    /**
     * @param selectWhichBossToUse The boss to use
     * @return The width of the ship
     */
    public int getShipWidth(int selectWhichBossToUse){
        return switch (selectWhichBossToUse) {
            case 1 -> 573;
            case 2 -> 580;
            case 3 -> 400;
            default -> 600;
        };
    }

    /**
     * @param selectWhichBossToUse The boss to use
     * @return The health of the ship
     */
    public int getShipHealth(int selectWhichBossToUse){
        return switch (selectWhichBossToUse) {
            case 1 -> 2500;
            case 2 -> 3000;
            case 3 -> 4000;
            default -> 1001;
        };
    }

    /**
     * @param selectWhichBossToUse The boss to use
     * @return The damage of the ship
     */
    public int getShipDamage(int selectWhichBossToUse){
        return switch (selectWhichBossToUse) {
            case 1 -> 9;
            case 2 -> 10;
            case 3 -> 11;
            default -> 11;
        };
    }





}
