package com.example.gamenewjava.GUI;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class for all onscreen components
 */
public class GraphicInterface {

    /**
     * The width of level
     */
    private static int LEVEL_WIDTH;

    /**
     * The height of level
     */
    private static int LEVEL_HEIGHT;

    /**
     * Rectangle for health bar
     */
    private final Rectangle healthBarRed;

    /**
     * Rectangle for health bar
     */
    private final Rectangle healthBarWhite;

    /**
     * The text to display user score
     */
    private final Text userScoreText = new Text("0");

    /**
     * The max width of the bar
     */
    private final double maxWidth;

    /**
     * @return Max width of bar
     */
    public double getMaxWidth() {
        return maxWidth;
    }

    /**
     * Sets the variables and creates rectangles
     * @param width the width of scene
     * @param height the height of scene
     */
    public GraphicInterface(int width, int height){
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
        healthBarRed = new Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.RED);
        healthBarWhite = new  Rectangle((float)(LEVEL_WIDTH / 3), (float)(LEVEL_HEIGHT / 30), Color.WHITE);
        maxWidth = (float)(LEVEL_WIDTH / 3);
        setUpScoreText();
        setUpHealthBars();
    }

    /**
     * @return The red health bar
     */
    public Rectangle getHealthBarRed() {
        return healthBarRed;
    }


    /**
     * @return The white health bar
     */
    public Rectangle getHealthBarWhite() {
        return healthBarWhite;
    }


    /**
     * @return The score text
     */
    public Text getUserScoreText() {
        return userScoreText;
    }


    /**
     * @param userScore The score to set the text to
     */
    public void updateScoreText(int userScore){
        userScoreText.setText(String.valueOf(userScore));
    }

    /**
     * Sets up the score text in correct place
     */
    public void setUpScoreText(){
        userScoreText.setX((float)LEVEL_WIDTH / 2);
        userScoreText.setY((float)LEVEL_HEIGHT * 0.05);
        userScoreText.setFill(Paint.valueOf("white"));
        userScoreText.setFont(Font.font("Verdana", 20));
    }

    /**
     * Sets up health bars in correct place
     */
    public void setUpHealthBars(){
        healthBarRed.setX((float)LEVEL_WIDTH / 2 - (healthBarRed.getWidth() / 2) );
        healthBarWhite.setX((float)LEVEL_WIDTH / 2 - (healthBarWhite.getWidth() / 2));
        healthBarRed.setY(LEVEL_HEIGHT * 0.95);
        healthBarWhite.setY(LEVEL_HEIGHT * 0.95);
    }



}
