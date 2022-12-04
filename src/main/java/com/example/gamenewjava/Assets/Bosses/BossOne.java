package com.example.gamenewjava.Assets.Bosses;

import com.example.gamenewjava.Assets.Projectile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BossOne extends BossShip{
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
    public BossOne(String _name, int _height, int _width, String _filepath, double _moveSpeed, double health, double damage, int _bossNumber) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _moveSpeed, health, damage, _bossNumber);
    }

    public void shoot(ArrayList<Projectile> projs) throws FileNotFoundException {
        String filePath = getShipFilePath();
        int x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 3.55));
        int y = (int) (getImageView().getY() + (getImageView().getFitHeight() / 1.4));

        for (int i = 60; i < 300; i = i+6) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 10));
        }
        x = (int) (getImageView().getX() + (getImageView().getFitWidth()  / 1.4));

        for (int i = 60; i < 300; i = i+6) {
            projs.add(new Projectile("EnemyBullet", 20, 8, filePath, x, y, i, 2.5, 10));
        }

    }
}
