package com.example.gamenewjava.Assets.Bosses;

import com.example.gamenewjava.Assets.DefaultAsset;
import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a boss ship
 */
public class BossShip extends DefaultAsset {


    /**
     * Time since last fired
     */
    private long timeSinceLastFire = 0;

    /**
     * @return the time since last fired
     */
    public long getTimeSinceLastFire() {
        return timeSinceLastFire;
    }

    /**
     * @param timeSinceLastFire Sets the time since last fired
     */
    public void setTimeSinceLastFire(long timeSinceLastFire) {
        this.timeSinceLastFire = timeSinceLastFire;
    }

    /**
     * The max y the boss should travel to
     */
    private double maxYPos = 0;

    /**
     * The number of the boss
     */
    private int bossNumber;

    /**
     * The integer of the coloured bullet to use
     */
    private final int ranBullet;


    /**
     * The boolean of if the ship should be firing
     */
    private boolean shouldFire = false;

    /**
     * The ship of the player
     */
    private Ship playerShip;


    /**
     * @param _name the name of asset
     * @param _height the height of asset
     * @param _width the width of asset
     * @param _filepath the filepath of image
     * @param _moveSpeed the speed of the asset
     * @param _bossNumber the number of the boss
     * @param damage the dame of ship
     * @param health the health of the ship
     * @param _playerShip The Ship of the player
     * @throws FileNotFoundException e
     */
    public BossShip(String _name, int _height, int _width, String _filepath, double _moveSpeed, double health, double damage, int _bossNumber, Ship _playerShip) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, 0, 0);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(health);
        setDamage(damage);
        bossNumber = _bossNumber;
        ranBullet = (int) (Math.random() * (3) + 0);
        playerShip = _playerShip;
    }

    /**
     * @param _name the name of asset
     * @param _height the height of asset
     * @param _width the width of asset
     * @param _filepath the filepath of image
     * @param _moveSpeed the speed of the asset
     * @param damage the dame of ship
     * @param health the health of the ship
     * @param _bossNumber the number of the boss
     * @throws FileNotFoundException e
     */
    public BossShip(String _name, int _height, int _width, String _filepath, double _moveSpeed, double health, double damage, int _bossNumber) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, 0, 0);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(health);
        setDamage(damage);
        bossNumber = _bossNumber;
        ranBullet = (int) (Math.random() * (3) + 0);
    }

    /**
     * Moves the ship forward
     */
    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    /**
     * @return If the ship should fire
     */
    public boolean isShouldFire() {
        return shouldFire;
    }

    /**
     * moves boss into position
     */
    public void onGameTick(){
        if(getImageView().getY() != getMaxYPos()){
            moveForward();
        }
        else{
            if(getHealth() > 0){
                shouldFire = true;
            }
            else{
                shouldFire = false;
            }
        }
    }

    /**
     * @return The file path of the bullet to use
     */
    public String getShipFilePath(){
        return switch (ranBullet) {
            case 1 -> "imgs/enemyBullet.png";
            case 2 -> "imgs/enemyBullet2.png";
            default -> "imgs/enemyBullet3.png";
        };
    }

    /**
     * @return The boss number
     */
    public int getBossNumber() {
        return bossNumber;
    }

    /**
     * The shooting method for each boss to override
     * @param projs The array of projectiles to add to
     * @throws FileNotFoundException e
     */
    public void shoot(ArrayList<Projectile> projs) throws FileNotFoundException {}


    /**
     * @return The max y pos of the ship
     */
    public double getMaxYPos() {
        return maxYPos;
    }

    /**
     * @param maxYPos Sets the max y position
     */
    public void setMaxYPos(double maxYPos) {
        this.maxYPos = maxYPos;
    }

    /**
     * @param x The X position of object
     * @param y The Y position of object
     * @return The angle to get to the player
     */
    public double getAngleToPlayer(double x, double y){
        double theta = Math.atan2(playerShip.getImageView().getY() - y, playerShip.getImageView().getX() - x);
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * @return The ship of the player
     */
    public Ship getPlayerShip() {
        return playerShip;
    }
}
