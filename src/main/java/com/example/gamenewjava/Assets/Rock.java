package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Rock extends DefaultAsset {

    private final int size;
    private final String filepath;

    public Rock(String _name, int _height, int _width, String _filepath, int _startX, int _startY,double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(getWidth() * 0.4);
        setDamage(getWidth() * 0.5);
        size = _width;
        filepath = _filepath;
    }

    public int getSize(){
        return size;
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    public void onGameTick(){
        moveForward();
    }

    public String getFilePath(){
        return filepath;
    }


}
