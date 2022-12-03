package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Ship extends DefaultAsset{


    private final int degreeOfRotation = 8;
    private double amountRotated = 0;

    private long timeSinceLastFiredAdvanced = System.currentTimeMillis();

    private double lastAngle = 0;

    private double amountLeftToMove = 0;
    private double currentMoveSpeedX = 0;
    private double currentMoveSpeedY = 0;

    private double acceleration = 0.1;


    public Ship(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY,_moveSpeed);
        setHealth(100);
        setMaxHealth(100);
    }


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

    public void rotateRight(){
        getImageView().setRotate(getImageView().getRotate() + degreeOfRotation);
        lastAngle = degreeOfRotation;
        updateRotation(degreeOfRotation, true);
    }

    public void rotateLeft(){
        getImageView().setRotate(getImageView().getRotate() - degreeOfRotation);
        lastAngle = degreeOfRotation;
        updateRotation(degreeOfRotation, false);
    }



    public void increaseAcceleration(){
        if(acceleration < 0){
            acceleration = 0.1;
        }

        if(acceleration < 2){
            acceleration = acceleration+0.1;
        }
    }

    public void decreaseAcceleration(){
        if(acceleration > 0){
            acceleration = -0.1;
        }
        if(acceleration > -2){
            acceleration = acceleration-0.1;
        }
    }

    public void moveForward(){
        amountLeftToMove = 0.3;
        currentMoveSpeedX = getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * (getMoveSpeed());
        currentMoveSpeedY = getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * (getMoveSpeed());
        getImageView().setY(currentMoveSpeedY);
        getImageView().setX(currentMoveSpeedX);
        increaseAcceleration();
    }

    public void moveBackwards(){
        amountLeftToMove = -0.3;
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getX())));
        decreaseAcceleration();
    }

    public Projectile fireBasicProjectile() throws FileNotFoundException {
        double basicProjectileDamage = 4;
        return new Projectile("Bullet", 10, 5, "imgs/basicbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 3, basicProjectileDamage);
    }

    public Projectile fireAdvancedProjectile() throws FileNotFoundException {
        double advancedProjectileDamage = 12;
        return new Projectile("Bullet", 25, 12, "imgs/advancedbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 1.5, advancedProjectileDamage);
    }

    public void setTimeSinceLastFiredAdvanced(long timeSinceLastFiredAdvanced) {
        this.timeSinceLastFiredAdvanced = timeSinceLastFiredAdvanced;
    }

    public long getTimeSinceLastFiredAdvanced(){
        return timeSinceLastFiredAdvanced;
    }

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
