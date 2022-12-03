package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

/**
 * Represents player ship
 * Extends default asset
 */
public class Ship extends DefaultAsset{


    /**
     * How much to rotate ship per click
     */
    private final int degreeOfRotation = 8;

    /**
     * How far the ship has rotated
     */
    private double amountRotated = 0;

    /**
     * Time since last advanced bullet fire
     */
    private long timeSinceLastFiredAdvanced = System.currentTimeMillis();

    /**
     * The previous angle of ship
     */
    private double lastAngle = 0;

    /**
     * The amount left to move used in moving after key release
     */
    private double amountLeftToMove = 0;


    /**
     * The acceleration of ship
     */
    private double acceleration = 0.1;


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
    public Ship(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY,_moveSpeed);
        setHealth(100);
        setMaxHealth(100);
    }


    /**
     * Updates rotation to be between 0 and 360
     * @param amount The amount to increase or decrease rotation
     * @param positive Whether to increase or decrease
     */
    public void updateRotation(double amount, boolean positive){
        if(positive){
            if(amountRotated + amount > 360){
                amountRotated = 360 - amountRotated + amount;
            }
            else{
                amountRotated = amountRotated + amount;
            }
        }else{
            if(amountRotated - amount < -360){
                amountRotated = 0 - (-360 + Math.abs(amountRotated)) - amount;
            }
            else{
                amountRotated = amountRotated - amount;
            }
        }
    }

    /**
     * Rotates the ship right
     */
    public void rotateRight(){
        getImageView().setRotate(getImageView().getRotate() + degreeOfRotation);
        lastAngle = degreeOfRotation;
        updateRotation(degreeOfRotation, true);
    }

    /**
     * Rotates the ship left
     */
    public void rotateLeft(){
        getImageView().setRotate(getImageView().getRotate() - degreeOfRotation);
        lastAngle = degreeOfRotation;
        updateRotation(degreeOfRotation, false);
    }


    /**
     * Increases the acceleration
     */
    public void increaseAcceleration(){
        if(acceleration < 0){
            acceleration = 0.1;
        }

        if(acceleration < 2){
            acceleration = acceleration+0.1;
        }
    }

    /**
     * Decreases the acceleration
     */
    public void decreaseAcceleration(){
        if(acceleration > 0){
            acceleration = -0.1;
        }
        if(acceleration > -2){
            acceleration = acceleration-0.1;
        }
    }

    /**
     * Moves the ship forward
     * Calculates the x and y based on the direction
     */
    public void moveForward(){
        amountLeftToMove = 0.3;
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * (getMoveSpeed()));
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * (getMoveSpeed()));
        increaseAcceleration();
    }

    /**
     * Moves the ship backwards
     * Calculates the x and y based on the direction
     */
    public void moveBackwards(){
        amountLeftToMove = -0.3;
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getX())));
        decreaseAcceleration();
    }

    /**
     * Creates a basic bullet
     * @return Projectile to add
     * @throws FileNotFoundException e
     */
    public Projectile fireBasicProjectile() throws FileNotFoundException {
        double basicProjectileDamage = 5;
        return new Projectile("Bullet", 10, 5, "imgs/basicbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 3, basicProjectileDamage);
    }

    /**
     * Creates an advanced bullet
     * @return Projectile to add
     * @throws FileNotFoundException e
     */
    public Projectile fireAdvancedProjectile() throws FileNotFoundException {
        double advancedProjectileDamage = 30;
        return new Projectile("Bullet", 35, 12, "imgs/advancedbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 1.5, advancedProjectileDamage);
    }

    /**
     * @param timeSinceLastFiredAdvanced Set time of last fired advanced bullet
     */
    public void setTimeSinceLastFiredAdvanced(long timeSinceLastFiredAdvanced) {
        this.timeSinceLastFiredAdvanced = timeSinceLastFiredAdvanced;
    }

    /**
     * @return The time since last fired
     */
    public long getTimeSinceLastFiredAdvanced(){
        return timeSinceLastFiredAdvanced;
    }

    /**
     * Every game tick, move the ship based on how much its accelerated in the correct direction
     */
    public void onGameTick(){
        if(amountLeftToMove > 0){
            getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * (amountLeftToMove + acceleration));
            getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * (amountLeftToMove + acceleration));
            amountLeftToMove = amountLeftToMove -0.0002;
            if(acceleration > 0){
                acceleration = acceleration-0.005;
            }
            else{
                acceleration = 0;
            }

        } else if (amountLeftToMove < 0) {
            getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * (amountLeftToMove + acceleration));
            getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * (amountLeftToMove + acceleration));
            amountLeftToMove = amountLeftToMove +0.0002;
            if(acceleration < 0){
                acceleration = acceleration+0.005;
            }
            else{
                acceleration = 0;
            }
        }

    }


}
