package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

/**
 * Represents a projectile fired by ship
 */
public class Projectile extends DefaultAsset{

    /**
     * How much damage the projectile does
     */
    private final double damage;

    public double getDamage() {
        return damage;
    }

    /**
     * Creates a projectile in a certain direction and speed
     * @param _name The name of asset
     * @param _height The height of asset
     * @param _width The width of asset
     * @param _filepath The file path of image
     * @param _startX The starting x coordinate
     * @param _startY The starting y coordinate
     * @param _angle the angle to shoot at
     * @param _moveSpeed The starting speed
     * @param _damage how much damage the projectile does
     * @throws FileNotFoundException e
     */

    public Projectile(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _angle, double _moveSpeed, double _damage) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        damage = _damage;
        getImageView().setRotate(_angle);
        setMoveSpeed(_moveSpeed);
    }

    /**
     * Moves projectile forward
     */
    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }


    /**
     * On game tick move the projectile forward
     */
    public void onGameTick(){
        moveForward();
    }



}
