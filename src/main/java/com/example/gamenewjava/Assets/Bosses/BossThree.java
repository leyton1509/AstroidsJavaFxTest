package com.example.gamenewjava.Assets.Bosses;

import com.example.gamenewjava.Assets.Projectile;
import com.example.gamenewjava.Assets.Ship;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Boss three
 */
public class BossThree extends BossShip{

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
    public BossThree(String _name, int _height, int _width, String _filepath, double _moveSpeed, double health, double damage, int _bossNumber, Ship _playerShip) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _moveSpeed, health, damage, _bossNumber, _playerShip);
    }

    /**
     * Adds the projectiles to the fire projectiles array list
     * @param projs The array of projectiles to add to
     * @throws FileNotFoundException e
     */
    public void shoot(ArrayList<Projectile> projs) throws FileNotFoundException {
        String filePath = getShipFilePath();

        int y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 1.4));

        int x = (int) (getImageView().getX() + (getImageView().getFitWidth() / 2));

        for (int i = 70; i < 290; i = i+6) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 10));
        }

        y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 1.4));

        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) + (getImageView().getFitWidth() *0.33));

        for (int i = 70; i < 290; i = i+10) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 10));
        }

        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) - (getImageView().getFitWidth() *0.33));

        for (int i = 70; i < 290; i = i+10) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 10));
        }

        y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 2) + (getImageView().getFitHeight() * 0.05));

        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) + (getImageView().getFitWidth() *0.485));

        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 2.5, 10));

        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 2) - (getImageView().getFitWidth() *0.485));


        projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, getAngleToPlayer(x,y), 2.5, 10));



    }
}
