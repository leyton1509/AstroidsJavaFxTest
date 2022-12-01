package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Ship extends DefaultAsset{

    private int degreeOfRotation = 10;
    private int moveSpeed = 5;

    private double amountRotated = 0;


    public Ship(String _name, int _height, int _width, String _filepath, int _startX, int _startY) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
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
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed);
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed);
    }

    public void moveBackwards(){
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getX())));
    }

    public Projectile fireBasicProjectile() throws FileNotFoundException {
        return new Projectile("BasicBullet", 10, 5, "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\basicbullet.png", (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate());
    }




}
