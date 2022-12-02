package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Ship extends DefaultAsset{


    private int degreeOfRotation = 10;
    private double amountRotated = 0;

    private long timeSinceLastFiredAdvanced = System.currentTimeMillis();


    public Ship(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY,_moveSpeed);
        setHealth(1000);
        setMaxHealth(1000);
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
        updateRotation(degreeOfRotation, true);
    }

    public void rotateLeft(){
        getImageView().setRotate(getImageView().getRotate() - degreeOfRotation);
        updateRotation(degreeOfRotation, false);
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    public void moveBackwards(){
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getX())));
    }

    public Projectile fireBasicProjectile() throws FileNotFoundException {
        double basicProjectileDamage = 40;
        return new Projectile("Bullet", 10, 5, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\basicbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 3, basicProjectileDamage);
    }

    public Projectile fireAdvancedProjectile() throws FileNotFoundException {
        double advancedProjectileDamage = 120;
        return new Projectile("Bullet", 25, 12, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\advancedbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 1.5, advancedProjectileDamage);
    }

    public void setTimeSinceLastFiredAdvanced(long timeSinceLastFiredAdvanced) {
        this.timeSinceLastFiredAdvanced = timeSinceLastFiredAdvanced;
    }

    public long getTimeSinceLastFiredAdvanced(){
        return timeSinceLastFiredAdvanced;
    }


}
