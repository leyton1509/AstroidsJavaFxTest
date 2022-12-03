package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

/**
 * Represents an enemy ship
 */
public class EnemyShip extends DefaultAsset{


    /**
     * Random speed that the projectile of this ship will fire at
     */
    private final double projSpeed;
    /**
     * The player ship to track
     */
    private final Ship ship;

    /**
     * Time since last fired
     */
    private long timeSinceLastFire = 0;

    /**
     * @return the time since last fired
     */
    public long getTimeSinceLastFire() {
        return timeSinceLastFire;
    }

    /**
     * @param timeSinceLastFire Sets the time since last fired
     */
    public void setTimeSinceLastFire(long timeSinceLastFire) {
        this.timeSinceLastFire = timeSinceLastFire;
    }

    /**
     * The integer of the coloured bullet to use
     */
    private final int ranBullet;

    /**
     * @param _name the name of asset
     * @param _height the height of asset
     * @param _width the width of asset
     * @param _filepath the filepath of image
     * @param _moveSpeed the speed of the asset
     * @param playerShip the player ship to track
     * @param LEVEL_WIDTH the width of level
     * @param LEVEL_HEIGHT the height of level
     * @throws FileNotFoundException e
     */
    public EnemyShip(String _name, int _height, int _width, String _filepath, double _moveSpeed, Ship playerShip, int LEVEL_WIDTH, int LEVEL_HEIGHT) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, 0, 0);
        getRandomStartPosition(LEVEL_WIDTH, LEVEL_HEIGHT);
        getImageView().setRotate((Math.random() * (360 - (-360)) + (-360)));
        setMoveSpeed(_moveSpeed);
        setHealth(getWidth() * 0.9);
        setDamage(getWidth() * 0.4);
        ship = playerShip;
        ranBullet = (int) (Math.random() * (3) + 0);
        projSpeed = (double) (Math.random() * (1.9) + 0.7);
    }

    /**
     * Generates a random start position out off-screen
     * @param LEVEL_WIDTH The height of level
     * @param LEVEL_HEIGHT The width of level
     */
    private void getRandomStartPosition(int LEVEL_WIDTH, int LEVEL_HEIGHT){
        int ranX;
        int ranY;

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


    /**
     * Moves the ship forward
     */
    public void moveForward(){
        getImageView().setY(getImageView().getY() - Math.cos(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
        getImageView().setX(getImageView().getX() + Math.sin(Math.toRadians(getImageView().getRotate())) * getMoveSpeed());
    }


    /**
     * Updates the angle of the ship to find the player
     */
    public void updateAngleToFindPlayer(){
        double theta = Math.atan2(ship.getImageView().getY() - getImageView().getY(), ship.getImageView().getX() - getImageView().getX());
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        getImageView().setRotate(angle);
    }

    /**
     * On game tick adjust angle and move
     */
    public void onGameTick(){
        //System.out.println("Angle : " + getImageView().getRotate() + " X : " + getImageView().getX() + " Y : " + getImageView().getY());
        updateAngleToFindPlayer();
        moveForward();
    }

    /**
     * @return String of the bullet filepath
     */
    public String getShipFilePath(){
        return switch (ranBullet) {
            case 1 -> "imgs/enemyBullet.png";
            case 2 -> "imgs/enemyBullet2.png";
            default -> "imgs/enemyBullet3.png";
        };
    }

    /**
     * @return Projectile enemy bullet that can damage the player
     * @throws FileNotFoundException e
     */
    public Projectile fireBasicProjectile() throws FileNotFoundException {
        double basicProjectileDamage = 4;
        return new Projectile("EnemyBullet", 20, 8, getShipFilePath(), (int) (getImageView().getX() + getImageView().getFitWidth()  / 2), (int) (getImageView().getY() + getImageView().getFitHeight() / 2), getImageView().getRotate(),  projSpeed, basicProjectileDamage);
    }

}
