package com.example.gamenewjava.Assets.Bosses;

import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Boss Two
 */
public class BossTwo extends BossShip{
    /**
     * @param _name       the name of asset
     * @param _height     the height of asset
     * @param _width      the width of asset
     * @param _filepath   the filepath of image
     * @param _moveSpeed  the speed of the asset
     * @param health the health of the ship
     * @param damage the damage of the ship
     * @param _bossNumber the number of the boss
     * @throws FileNotFoundException e
     */
    public BossTwo(String _name, int _height, int _width, String _filepath, double _moveSpeed, double health, double damage, int _bossNumber, Ship _playerShip) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _moveSpeed, health, damage, _bossNumber, _playerShip);
    }

    /**
     * Adds the projectiles to the fire projectiles array list
     * @param projs The array of projectiles to add to
     * @throws FileNotFoundException e
     */
    public void shoot(ArrayList<Projectile> projs) throws FileNotFoundException {

        String filePath = getShipFilePath();
        int x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) - 2.5);
        int y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 2.5));

        for (int i = 60; i < 300; i = i+8) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 12));
        }

        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) + (getImageView().getFitWidth() *0.19) );

        y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 1.7));

        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 6, 15));
        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 6, 15));


        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) - (getImageView().getFitWidth() *0.19));

        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 6, 11));
        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 6, 11));

    }
}
