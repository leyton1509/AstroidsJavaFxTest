package com.example.gamenewjava.Assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DefaultAsset {

    /**
     *
     */
    private final String name;

    /**
     *
     */
    private final int height;

    /**
     *
     */
    private final int width;

    /**
     *
     */
    private final Image image;

    /**
     *
     */
    private final String filePath;


    /**
     * The image view of the asset
     */
    private final ImageView imageView;

    /**
     * The moving speed of asset
     */
    private double moveSpeed = 0;

    /**
     * How much damage the asset does
     */
    private double damage = 0;

    /**
     * The current health of asset
     */
    private double health = 0;

    /**
     * The max health of asset
     */
    private double maxHealth = 0;

    /**
     * @return The time since last hit
     */
    public long getTimeLast() {
        return timeSinceLastHit;
    }

    /**
     * @param test Set the time since last hit
     */
    public void setTimeLast(long test) {
        this.timeSinceLastHit = test;
    }

    /**
     * The time since last hit
     */
    private long timeSinceLastHit = System.currentTimeMillis();

    /**
     * @return The health of asset
     */
    public double getHealth() {
        return health;
    }

    /**
     * @param health Setting the health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * @return Gets the damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * @param damage Sets the damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * @return Gets the max health
     */
    public double getMaxHealth(){
        return maxHealth;
    }

    /**
     * @param _maxHealth Sets the max health
     */
    public void setMaxHealth(double _maxHealth){
        maxHealth = _maxHealth;
    }

    /**
     * @param _health Decreases the health by x
     */
    public void decreaseHealth(double _health) {
        this.health = this.health - _health;
    }

    /**
     * @param _name The name of asset
     * @param _height The height of asset
     * @param _width The width of asset
     * @param _filepath The file path of image
     * @param _startX The starting x coordinate
     * @param _startY The starting y coordinate
     * @throws FileNotFoundException e
     */
    public DefaultAsset(String _name, int _height, int _width, String _filepath, int _startX, int _startY) throws FileNotFoundException {
        name = _name;
        height = _height;
        width = _width;
        filePath = _filepath;
        image = new Image(new FileInputStream(filePath));
        imageView = new ImageView(image);
        imageView.setX(_startX);
        imageView.setY(_startY);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }

    /**
     * @param _name The name of asset
     * @param _height The height of asset
     * @param _width The width of asset
     * @param _filepath The file path of image
     * @param _startX The starting x coordinate
     * @param _startY The starting y coordinate
     * @param _moveSpeed the move speed
     * @throws FileNotFoundException e
     */

    public DefaultAsset(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        name = _name;
        height = _height;
        width = _width;
        filePath = _filepath;
        image = new Image(new FileInputStream(filePath));
        imageView = new ImageView(image);
        imageView.setX(_startX);
        imageView.setY(_startY);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        setMoveSpeed(_moveSpeed);
    }

    /**
     * On game tick
     */
    public void onGameTick(){

    }

    /**
     * @return the name of the asset
     */
    public String getName() {
        return name;
    }


    /**
     * @return Width of asset
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The Image View of asset
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @return The move speed
     */
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @param moveSpeed Sets the move speed and makes sure it cant be negative
     */
    public void setMoveSpeed(double moveSpeed) {
        if (moveSpeed != 0)
            if (moveSpeed > 0.1) {
                this.moveSpeed = moveSpeed;
            }
    }
}
