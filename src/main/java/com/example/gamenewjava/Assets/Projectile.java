package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class Projectile extends DefaultAsset{

    private double angleOfFire;
    private final double damage;

    public double getDamage() {
        return damage;
    }

    public Projectile(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _angle, double _moveSpeed, double _damage) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY);
        angleOfFire = _angle;
        damage = _damage;
        getImageView().setRotate(angleOfFire);
        setMoveSpeed(_moveSpeed);
    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    public void moveBackwards(){
        getImageView().setY(getImageView().getY() + (-1 * ((getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getY())));
        getImageView().setX(getImageView().getX() + (-1 * ((getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed()) - getImageView().getX())));
    }

    public void onGameTick(){
        moveForward();
        //System.out.println("X " + getImageView().getX() + " Y : "+ getImageView().getY());
    }



}
