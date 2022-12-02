package com.example.gamenewjava;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphicInterface {

    private static int LEVEL_WIDTH;
    private static int LEVEL_HEIGHT;

    private Rectangle healthBarRed;

    private Rectangle healthBarWhite;

    private Text userScoreText = new Text("0");

    public GraphicInterface(int width, int height){
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
        healthBarRed = new Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.RED);
        healthBarWhite = new  Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.WHITE);
        setUpScoreText();
        setUpHealthBars();
    }

    public Rectangle getHealthBarRed() {
        return healthBarRed;
    }

    public void setHealthBarRed(Rectangle healthBarRed) {
        this.healthBarRed = healthBarRed;
    }

    public Rectangle getHealthBarWhite() {
        return healthBarWhite;
    }

    public void setHealthBarWhite(Rectangle healthBarWhite) {
        this.healthBarWhite = healthBarWhite;
    }

    public Text getUserScoreText() {
        return userScoreText;
    }

    public void setUserScoreText(Text userScoreText) {
        this.userScoreText = userScoreText;
    }

    public void updateScoreText(int userScore){
        userScoreText.setText(String.valueOf(userScore));
    }

    public void setUpScoreText(){
        userScoreText.setX((float)LEVEL_WIDTH / 2);
        userScoreText.setY((float)LEVEL_HEIGHT * 0.05);
        userScoreText.setFill(Paint.valueOf("white"));
        userScoreText.setFont(Font.font("Verdana", 20));
    }

    public void setUpHealthBars(){
        healthBarRed.setX((float)LEVEL_WIDTH / 2 - (healthBarRed.getWidth() / 2) );
        healthBarWhite.setX((float)LEVEL_WIDTH / 2 - (healthBarWhite.getWidth() / 2));
        healthBarRed.setY(LEVEL_HEIGHT * 0.95);
        healthBarWhite.setY(LEVEL_HEIGHT * 0.95);
    }

}
