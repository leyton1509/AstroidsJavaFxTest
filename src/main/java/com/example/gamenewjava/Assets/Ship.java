package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Ship extends DefaultAsset{

    private int degreeOfRotation = 10;
    private int moveSpeed = 5;


    public Ship(String _name, int _height, int _width, String _filepath, int _startX, int _startY) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
    }

    public void rotateRight(){
        getImageView().setRotate(getImageView().getRotate() + degreeOfRotation);
    }

    public void rotateLeft(){
        getImageView().setRotate(getImageView().getRotate() - degreeOfRotation);
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed);
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed);
    }

    public void moveBackwards(){
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * moveSpeed) - getImageView().getX())));
    }




}
