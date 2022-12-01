package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Projectile extends DefaultAsset{

    private double moveSpeed = 5;
    private double angleOfFire;

    public Projectile(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _angle) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        angleOfFire = _angle;
        getImageView().setRotate(angleOfFire);
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed);
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed);
    }

    public void moveBackwards(){
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getX())));
    }

    public void onGameTick(){
        moveForward();
        //System.out.println("X " + getImageView().getX() + " Y : "+ getImageView().getY());
    }

}
