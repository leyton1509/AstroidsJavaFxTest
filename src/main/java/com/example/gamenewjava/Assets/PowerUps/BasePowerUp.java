package com.example.gamenewjava.Assets.PowerUps;

import com.example.gamenewjava.Assets.DefaultAsset;

import java.io.FileNotFoundException;

public class BasePowerUp extends DefaultAsset {

    /**
     * Name of power up
     */
    private final String powerUpName;


    /**
     * @return The power up name
     */
    public String getPowerUpName() {
        return powerUpName;
    }



    /**
     * @param _name The name of asset
     * @param _height The height of asset
     * @param _width The width of asset
     * @param _filepath The file path of image
     * @param _startX The starting x coordinate
     * @param _startY The starting y coordinate
     * @param _moveSpeed The starting speed
     * @param _pname the name of power up
     * @throws FileNotFoundException e
     */

    public BasePowerUp(String _name, int _height, int _width, String _filepath, int _startX, int _startY,double _moveSpeed, String _pname) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(getWidth() * 0.4);
        setDamage(getWidth() * 0.5);
        powerUpName = _pname;

    }

    /**
     * Moves the power up forward
     */
    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    /**
     * On game tick move the power up forward
     */
    public void onGameTick(){
        moveForward();
    }



}
