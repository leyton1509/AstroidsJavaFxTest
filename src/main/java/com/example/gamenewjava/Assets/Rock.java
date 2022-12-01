package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Rock extends DefaultAsset {

    private double moveSpeed = 0.5;

    public Rock(String _name, int _height, int _width, String _filepath, int _startX, int _startY) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed);
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed);
    }

    public void onGameTick(){
        moveForward();
    }
}
