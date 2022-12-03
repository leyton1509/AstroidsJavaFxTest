package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

/**
 * Health Pack
 * Extends Default asset
 */
public class HealthPack extends DefaultAsset {

    /**
     * The size of the astroid
     */
    private final double healthIncrease;

    /**
     * @return The amount to increase health by
     */
    public double getHealthIncrease() {
        return healthIncrease;
    }

    /**
     * The filepath of the astroid image
     */
    private final String filepath;

    /**
     * @param _name The name of asset
     * @param _height The height of asset
     * @param _width The width of asset
     * @param _filepath The file path of image
     * @param _startX The starting x coordinate
     * @param _startY The starting y coordinate
     * @param _moveSpeed The starting speed
     * @throws FileNotFoundException e
     */

    public HealthPack(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed, double _healthIncrease) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(getWidth() * 0.4);
        setDamage(getWidth() * 0.5);
        filepath = _filepath;
        healthIncrease = _healthIncrease;
    }



    /**
     * Moves the rock forward
     */
    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    /**
     * On game tick move the rock forward
     */
    public void onGameTick(){
        moveForward();
    }

    /**
     * @return The filepath of image used
     */
    public String getFilePath(){
        return filepath;
    }


}
