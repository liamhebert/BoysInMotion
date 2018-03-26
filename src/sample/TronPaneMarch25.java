package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;

public class TronPaneMarch25 extends Pane{

    //attributes, also makes rectangle and animation timeline object
    private double sizeX = 5, sizeY = 5; //default size of the players

    private double playerOneRecLocX = 50, playerOneRecLocY = 50; //start point of player 1
    private double playerOneDx = 0, playerOneDy = 1;//start direction of player one

    private double playerTwoRecLocX = 750, playerTwoRecLocY = 50; //start point of player 2
    private double playerTwoDx = 0, playerTwoDy = 1; //start direction of player two

    //player 1 rectangle
    private Rectangle playerOneRect = new Rectangle(playerOneRecLocX, playerOneRecLocY, sizeX, sizeY);

    //player 2 rectangle
    private Rectangle playerTwoRect = new Rectangle(playerTwoRecLocX, playerTwoRecLocY, sizeX, sizeY);

    //current line streaming from player one
    private Rectangle playerOneCurrRectLine = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), playerOneRect.getWidth(), playerOneRect.getHeight());

    //current line streaming from player two
    private Rectangle playerTwoCurrRectLine = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), playerTwoRect.getWidth(), playerTwoRect.getHeight());


    //this is our "path" of rectangles, in the form of an arraylist
    private ArrayList<Rectangle> playerOneRectPath = new ArrayList<Rectangle>();

    //path arraylist for player two
    private ArrayList<Rectangle> playerTwoRectPath = new ArrayList<Rectangle>();

    private Timeline animation;

    //constructor sets up animation and creates rectangle
    public TronPaneMarch25(){

        //sets up player one and their line
        playerOneRect.setFill(Color.BLUE);
        playerOneCurrRectLine.setFill(Color.BLUE);

        //sets up player two and their line
        playerTwoRect.setFill(Color.ORANGE);
        playerTwoCurrRectLine.setFill(Color.ORANGE);

        //adds player one player and initial line rectangle
        getChildren().add(playerOneRect);
        getChildren().add(playerOneCurrRectLine);

        //adds player two player and initial line rectangle
        getChildren().add(playerTwoRect);
        getChildren().add(playerTwoCurrRectLine);

        //adds the initial lines to the path arraylists
        playerOneRectPath.add(playerOneCurrRectLine);
        playerTwoRectPath.add(playerTwoCurrRectLine);

        animation = new Timeline(
                new KeyFrame(Duration.millis(10), e -> movePlayer())
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void play(){
        animation.play();
    }

    public void pause(){
        animation.pause();
    }

    public void addAPixelToPath(){

        if (playerOneDx == 1){
            playerOneCurrRectLine.setWidth(playerOneCurrRectLine.getWidth() + 1);
        }else if (playerOneDx == -1){
            playerOneCurrRectLine.setWidth(playerOneCurrRectLine.getWidth() + 1);
            playerOneCurrRectLine.setX(playerOneCurrRectLine.getX() - 1);
        }else if (playerOneDy == 1){
            playerOneCurrRectLine.setHeight(playerOneCurrRectLine.getHeight() + 1);
        }else if (playerOneDy == -1) {
            playerOneCurrRectLine.setHeight(playerOneCurrRectLine.getHeight() + 1);
            playerOneCurrRectLine.setY(playerOneCurrRectLine.getY() - 1);
        }

        if (playerTwoDx == 1){
            playerTwoCurrRectLine.setWidth(playerTwoCurrRectLine.getWidth() + 1);
        }else if (playerTwoDx == -1){
            playerTwoCurrRectLine.setWidth(playerTwoCurrRectLine.getWidth() + 1);
            playerTwoCurrRectLine.setX(playerTwoCurrRectLine.getX() - 1);
        }else if (playerTwoDy == 1){
            playerTwoCurrRectLine.setHeight(playerTwoCurrRectLine.getHeight() + 1);
        }else if (playerTwoDy == -1) {
            playerTwoCurrRectLine.setHeight(playerTwoCurrRectLine.getHeight() + 1);
            playerTwoCurrRectLine.setY(playerTwoCurrRectLine.getY() - 1);
        }

    }

    public void collisionCheck(){
        //FOR ANYONE EDITING
        //THIS IS WHERE SCORE UPDATING WILL PROBABLY EVENTUALLY GO

            //did player one hit their own line?
            for (int i = 0; i < playerOneRectPath.size() - 2; i++) {
                if (playerOneRect.intersects(playerOneRectPath.get(i).getBoundsInLocal())) {
                    pause();
                    System.out.println("Player One just crashed into their own line smh");
                }
            }

            //did player two hit their own line?
            for (int i = 0; i < playerTwoRectPath.size() - 2; i++){
                if (playerTwoRect.intersects(playerTwoRectPath.get(i).getBoundsInLocal())){
                    pause();
                    System.out.println("Player Two just crashed into their own line smh");
                }
            }

            //did player two hit player one's path?
            for (int i = 0; i < playerOneRectPath.size(); i++){
                if (playerTwoRect.intersects(playerOneRectPath.get(i).getBoundsInLocal())){
                    pause();
                    System.out.println("Player two crashed into player one's line");
                }
            }

            //did player one hit player two's path?
            for (int i = 0; i < playerTwoRectPath.size(); i++){
                if (playerOneRect.intersects(playerTwoRectPath.get(i).getBoundsInLocal())){
                    pause();
                    System.out.println("Player one crashed into player two's line");
                }
            }

            //did the players themselves hit eachother?
            if (playerOneRect.intersects(playerTwoRect.getBoundsInLocal())){
                pause();
                System.out.println("Player one and player two hit eachother");
            }


    }

    public void changeDirectionPlayerOne(){

        Rectangle newRec = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), playerOneRect.getWidth(), playerOneRect.getHeight());
        newRec.setFill(Color.BLUE);
        playerOneRectPath.add(newRec);
        playerOneCurrRectLine = newRec;

        refreshPathAsChildPlayerOne();
    }

    public void changeDirectionPlayerTwo(){

        Rectangle newRec = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), playerTwoRect.getWidth(), playerTwoRect.getHeight());
        newRec.setFill(Color.ORANGE);
        playerTwoRectPath.add(newRec);
        playerTwoCurrRectLine = newRec;

        refreshPathAsChildPlayerTwo();
    }

    public void refreshPathAsChildPlayerOne() {
        getChildren().add(playerOneCurrRectLine);
    }

    public void refreshPathAsChildPlayerTwo() {
        getChildren().add(playerTwoCurrRectLine);
    }

    public void setDirectionPlayerOne(String d){

        changeDirectionPlayerOne();
        //going left relative
        if (d.equals("l")){

            //going right rn
            if (playerOneDx == 1 && playerOneDy == 0){
                playerOneDx = 0;
                playerOneDy = -1;
                //relative left = go up

                //going left rn
            } else if (playerOneDx == -1 && playerOneDy == 0){
                playerOneDx = 0;
                playerOneDy = 1;
                //relative left = go down

                //going down rn
            } else if (playerOneDx == 0 && playerOneDy == 1){
                playerOneDx = 1;
                playerOneDy = 0;
                //relative left = go right

                //going up rn
            } else if (playerOneDx == 0 && playerOneDy == -1){
                playerOneDx = -1;
                playerOneDy = 0;
                //relative left = go left
            }
        } else if (d.equals("r")){

            //going right rn
            if (playerOneDx == 1 && playerOneDy == 0){
                playerOneDx = 0;
                playerOneDy = 1;
                //relative right = go down
                //going left rn
            } else if (playerOneDx == -1 && playerOneDy == 0){
                playerOneDx = 0;
                playerOneDy = -1;
                //relative right = go up

                //going down rn
            } else if (playerOneDx == 0 && playerOneDy == 1){
                playerOneDx = -1;
                playerOneDy = 0;
                //relative right = go left

                //going up rn
            } else if (playerOneDx == 0 && playerOneDy == -1){
                playerOneDx = 1;
                playerOneDy = 0;
                //relative right = go right
            }
        }
    }

    public void setDirectionPlayerTwo(String d){

        changeDirectionPlayerTwo();
        //going left relative
        if (d.equals("l")){

            //going right rn
            if (playerTwoDx == 1 && playerTwoDy == 0){
                playerTwoDx = 0;
                playerTwoDy = -1;
                //relative left = go up

                //going left rn
            } else if (playerTwoDx == -1 && playerTwoDy == 0){
                playerTwoDx = 0;
                playerTwoDy = 1;
                //relative left = go down

                //going down rn
            } else if (playerTwoDx == 0 && playerTwoDy == 1){
                playerTwoDx = 1;
                playerTwoDy = 0;
                //relative left = go right

                //going up rn
            } else if (playerTwoDx == 0 && playerTwoDy == -1){
                playerTwoDx = -1;
                playerTwoDy = 0;
                //relative left = go left
            }
        } else if (d.equals("r")){

            //going right rn
            if (playerTwoDx == 1 && playerTwoDy == 0){
                playerTwoDx = 0;
                playerTwoDy = 1;
                //relative right = go down
                //going left rn
            } else if (playerTwoDx == -1 && playerTwoDy == 0){
                playerTwoDx = 0;
                playerTwoDy = -1;
                //relative right = go up

                //going down rn
            } else if (playerTwoDx == 0 && playerTwoDy == 1){
                playerTwoDx = -1;
                playerTwoDy = 0;
                //relative right = go left

                //going up rn
            } else if (playerTwoDx == 0 && playerTwoDy == -1){
                playerTwoDx = 1;
                playerTwoDy = 0;
                //relative right = go right
            }
        }
    }

    protected void movePlayer(){

        //increments the positions of players 1 and 2 (first the variable, then the actual rectangle is updated)
        playerOneRecLocX += playerOneDx;
        playerOneRecLocY += playerOneDy;
        playerTwoRecLocX += playerTwoDx;
        playerTwoRecLocY += playerTwoDy;

        playerOneRect.setX(playerOneRecLocX);
        playerOneRect.setY(playerOneRecLocY);
        playerTwoRect.setX(playerTwoRecLocX);
        playerTwoRect.setY(playerTwoRecLocY);

        //updates the paths and checks for collisions
        addAPixelToPath();
        collisionCheck();

        //flag: prints how many player one rectangles there are, for optimization testing
        System.out.println(playerOneRectPath.size());
    }
}