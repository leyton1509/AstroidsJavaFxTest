package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class EnemyShip extends DefaultAsset{

    private double amountRotated = 0;
    private int degreeOfRotation = 10;

    private Ship ship;

    private long timeSinceLastFire = 0;

    public long getTimeSinceLastFire() {
        return timeSinceLastFire;
    }

    public void setTimeSinceLastFire(long timeSinceLastFire) {
        this.timeSinceLastFire = timeSinceLastFire;
    }

    private final int ranBullet;

    public EnemyShip(String _name, int _height, int _width, String _filepath, double _moveSpeed, Ship playerShip, int LEVEL_WIDTH, int LEVEL_HEIGHT) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, 0, 0);
        getRandomStartPosition(LEVEL_WIDTH, LEVEL_HEIGHT);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(getWidth() * 0.8);
        setDamage(getWidth() * 0.4);
        ship = playerShip;
        ranBullet = (int) (Math.random() * (3) + 0);
    }

    private void getRandomStartPosition(int LEVEL_WIDTH, int LEVEL_HEIGHT){
        int ranX = 0;
        int ranY = 0;

        ranX = (int) (Math.random() * ((LEVEL_WIDTH + 100) - (-100)) + (-100) );

        if(ranX < 0 || ranX > LEVEL_WIDTH){
            ranY =  (int) (Math.random() * ((LEVEL_HEIGHT + 90) - 90)-90);
        }
        else{
            if((int) Math.round(Math.random()) == 0){
                ranY =  (int) (Math.random() * ((LEVEL_HEIGHT + 90) - LEVEL_HEIGHT) + LEVEL_HEIGHT);
            }
            else{
                ranY = (int) (Math.random() * (90));
                ranY = ranY - 90;
            }

        }
        getImageView().setX(ranX);
        getImageView().setY(ranY);

    }

    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }

    public void rotateRight(){
        getImageView().setRotate(getImageView().getRotate() + degreeOfRotation);
        updateRotation(degreeOfRotation, true);
    }

    public void rotateLeft(){
        getImageView().setRotate(getImageView().getRotate() - degreeOfRotation);
        updateRotation(degreeOfRotation, false);
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

    public void updateAngleToFindPlayer(){
        double theta = Math.atan2(ship.getImageView().getY() - getImageView().getY(), ship.getImageView().getX() - getImageView().getX());
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        getImageView().setRotate(angle);
    }

    public void onGameTick(){
        //System.out.println("Angle : " + getImageView().getRotate() + " X : " + getImageView().getX() + " Y : " + getImageView().getY());
        updateAngleToFindPlayer();
        moveForward();
    }

    public String getShipFilePath(){
        return switch (ranBullet) {
            case 1 -> "imgs/enemyBullet.png";
            case 2 -> "imgs/enemyBullet2.png";
            default -> "imgs/enemyBullet3.png";
        };
    }

    public Projectile fireBasicProjectile() throws FileNotFoundException {
        double basicProjectileDamage = 4;
        return new Projectile("EnemyBullet", 12, 5, getShipFilePath(), (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(), 0.8, basicProjectileDamage);
    }

}
