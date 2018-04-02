package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.ArrayList;

public class TronPane extends Pane{

    //attributes, also makes rectangle and animation timeline object
    private final double sizeX = 6;
    private double sizeY = 6; //default size of the players

    private double playerOneRecLocX = 200, playerOneRecLocY = 390; //start point of player 1
    private double playerOneDx = 1, playerOneDy = 0;//start direction of player one

    private double playerTwoRecLocX = 600, playerTwoRecLocY = 390; //start point of player 2
    private double playerTwoDx = -1, playerTwoDy = 0; //start direction of player two

    //player 1 rectangle
    private Rectangle playerOneRect = new Rectangle(playerOneRecLocX, playerOneRecLocY, sizeX, sizeY); //two for the front hit box, prevents weird situations

    //player 2 rectangle
    private Rectangle playerTwoRect = new Rectangle(playerTwoRecLocX, playerTwoRecLocY, sizeX, sizeY);

    //current line streaming from player one
    private Rectangle playerOneCurrRectLine = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), sizeX, playerOneRect.getHeight());

    //current line streaming from player two
    private Rectangle playerTwoCurrRectLine = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), sizeX, playerTwoRect.getHeight());


    //this is our "path" of rectangles, in the form of an arraylist
    private ArrayList<Rectangle> playerOneRectPath = new ArrayList<>();

    //path arraylist for player two
    private ArrayList<Rectangle> playerTwoRectPath = new ArrayList<>();
    private Paint p1;
    private Paint p2;

    private Timeline animation;
    private int p1S = 0;
    private int p2S = 0;
    private Font popFont;
    private String p1N;
    private String p2N;
    private int flicks;

    private Rectangle topArena, leftArena, bottomArena, rightArena;

    private HBox scoreDisplay;
    //constructor sets up animation and creates rectangle
    public TronPane(Font popFont, String p1N, String p2N, Paint p1, Paint p2){
        this.p1N = p1N;
        this.p2N = p2N;
        this.p1 = p1;
        this.p2 = p2;
        this.popFont = popFont;

        Text name1 = new Text(p1N);

        name1.setFont(popFont);
        name1.setFill(p1);
        name1.setY(40);
        name1.setX(150);
        name1.setTextAlignment(TextAlignment.CENTER);



        Text name2 = new Text(p2N);
        name2.setFont(popFont);
        name2.setFill(p2);
        name2.setY(40);
        name2.setX(570);
        name2.setTextAlignment(TextAlignment.CENTER);

        scoreDisplay = scoreDisplay();
        scoreDisplay.setLayoutX(350);
        scoreDisplay.setLayoutY(15);

        //new arena code, as rectangles
        topArena = new Rectangle(5, 60, 800, 8);
        topArena.setFill(Color.WHITE);
        bottomArena = new Rectangle(5, 795, 800, 8);
        bottomArena.setFill(Color.WHITE);
        rightArena = new Rectangle(795, 60, 8, 800);
        rightArena.setFill(Color.WHITE);
        leftArena = new Rectangle(5, 60, 8, 800);
        leftArena.setFill(Color.WHITE);

        getChildren().addAll(topArena, bottomArena, leftArena, rightArena, name1,name2, scoreDisplay);
        //sets up player one and their line
        playerOneRect.setFill(p1);
        playerTwoRect.setFill(p2);
        playerOneCurrRectLine.setFill(p1);
        playerTwoCurrRectLine.setFill(p2);

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
                new KeyFrame(Duration.millis(5), e -> movePlayer())
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
        boolean end = false;
        int boomP = 0;
        Timeline explosion;
        //did the players themselves hit eachother head on?
        if (playerOneRect.intersects(playerTwoRect.getBoundsInLocal())){
            pause();
            System.out.println("Player one and player two hit eachother");
            boomP = 3;
            end = true;
        }

            //did player one hit their own line?
        for (int i = 0; i < playerOneRectPath.size() - 2 && !end; i++) {
            if (playerOneRect.intersects(playerOneRectPath.get(i).getBoundsInLocal())) {
                pause();
                System.out.println("Player One just crashed into their own line smh");
                boomP = 1;
                end = true;
            }
        }

            //did player two hit their own line?
        for (int i = 0; i < playerTwoRectPath.size() - 2 && !end; i++){
            if (playerTwoRect.intersects(playerTwoRectPath.get(i).getBoundsInLocal())){
                pause();
                System.out.println("Player Two just crashed into their own line smh");
                boomP = 2;
                end = true;
            }
        }

            //did player two hit player one's path?
        for (int i = 0; i < playerOneRectPath.size() && !end; i++){
            if (playerTwoRect.intersects(playerOneRectPath.get(i).getBoundsInLocal())){
                pause();
                System.out.println("Player two crashed into player one's line");
                boomP = 2;
                end = true;
            }
        }

            //did player one hit player two's path?
        for (int i = 0; i < playerTwoRectPath.size() && !end; i++){
            if (playerOneRect.intersects(playerTwoRectPath.get(i).getBoundsInLocal())){
                pause();
                System.out.println("Player one crashed into player two's line");
                boomP = 1;
                end = true;
            }
        }

        if (playerOneRect.intersects(topArena.getBoundsInLocal())){
            pause();
            System.out.println("Player one hit the arena wall");
            boomP = 1;
            end = true;
        }
        if (playerOneRect.intersects(bottomArena.getBoundsInLocal())){
            pause();
            System.out.println("Player one hit the arena wall");
            boomP = 1;
            end = true;
        }
        if (playerOneRect.intersects(rightArena.getBoundsInLocal())){
            pause();
            System.out.println("Player one hit the arena wall");
            boomP = 1;
            end = true;
        }
        if (playerOneRect.intersects(leftArena.getBoundsInLocal())){
            pause();
            System.out.println("Player one hit the arena wall");
            boomP = 1;
            end = true;
        }

        if (playerTwoRect.intersects(topArena.getBoundsInLocal())){
            pause();
            System.out.println("Player two hit the arena wall");
            boomP = 2;
            end = true;
        }
        if (playerTwoRect.intersects(bottomArena.getBoundsInLocal())){
            pause();
            System.out.println("Player two hit the arena wall");
            boomP = 2;
            end = true;
        }
        if (playerTwoRect.intersects(rightArena.getBoundsInLocal())){
            pause();
            System.out.println("Player two hit the arena wall");
            boomP = 2;
            end = true;
        }
        if (playerTwoRect.intersects(leftArena.getBoundsInLocal())){
            pause();
            System.out.println("Player two hit the arena wall");
            boomP = 2;
            end = true;
        }

        if (end) {
            if (boomP == 1){
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(1)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(2));
                        updateScoreDisplay();
                    }
                });
            }
            else if (boomP == 2){
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(2)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(1));
                        updateScoreDisplay();
                    }
                });
            }
            else {
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(3)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(3));
                        updateScoreDisplay();
                    }
                });
            }
            explosion.setCycleCount(8);
            explosion.play();
        }

    }

    private void crash(int player){
        double startX;
        double startY;
        if (player == 1){
            startX = playerOneRecLocX;
            startY = playerOneRecLocY;
        }
        else if (player == 2){
            startX = playerTwoRecLocX;
            startY = playerTwoRecLocY;
        }
        else {
            startX = (playerOneRecLocX + playerTwoRecLocX) / 2;
            startY = (playerOneRecLocY + playerTwoRecLocY) / 2;
        }
        Group root;
        if (flicks % 2 == 0) {
            double distance = flicks * (35/2);
            /* layout
            1 2 3
            4   5
            6 7 8
             */
            Rectangle rect1 = new Rectangle(startX - distance,startY + distance,35,35);
            Rectangle rect2 = new Rectangle(startX,startY + distance,35,35);
            Rectangle rect3 = new Rectangle(startX + distance,startY + distance,35,35);
            Rectangle rect4 = new Rectangle(startX - distance,startY,35,35);
            Rectangle rect5 = new Rectangle(startX + distance,startY,35,35);
            Rectangle rect6 = new Rectangle(startX - distance,startY - distance,35,35);
            Rectangle rect7 = new Rectangle(startX,startY - distance,35,35);
            Rectangle rect8 = new Rectangle(startX + distance,startY - distance,35,35);

            //alternating colors
            if (flicks % 4 == 0){ //every second burst
                rect1.setFill(Paint.valueOf("YELLOW"));
                rect2.setFill(Paint.valueOf("YELLOW"));
                rect3.setFill(Paint.valueOf("YELLOW"));
                rect4.setFill(Paint.valueOf("YELLOW"));
                rect5.setFill(Paint.valueOf("YELLOW"));
                rect6.setFill(Paint.valueOf("YELLOW"));
                rect7.setFill(Paint.valueOf("YELLOW"));
                rect8.setFill(Paint.valueOf("YELLOW"));
            }
            else {
                rect1.setFill(Paint.valueOf("WHITE"));
                rect2.setFill(Paint.valueOf("WHITE"));
                rect3.setFill(Paint.valueOf("WHITE"));
                rect4.setFill(Paint.valueOf("WHITE"));
                rect5.setFill(Paint.valueOf("WHITE"));
                rect6.setFill(Paint.valueOf("WHITE"));
                rect7.setFill(Paint.valueOf("WHITE"));
                rect8.setFill(Paint.valueOf("WHITE"));
            }
            root = new Group(rect1,rect2,rect3,rect4,rect5,rect6,rect7,rect8);
        }
        else
            root = new Group(); //nothing
        if (flicks == 0)
            getChildren().add(root);
        else
            getChildren().set(getChildren().size() - 1, root);
        flicks++;
    }

    public void changeDirectionPlayerOne(){

        Rectangle newRec = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), playerOneRect.getWidth(), playerOneRect.getHeight());
        newRec.setFill(p1);
        playerOneRectPath.add(newRec);
        playerOneCurrRectLine = newRec;

        refreshPathAsChildPlayerOne();
    }

    public HBox scoreDisplay(){
        Text scoreP1 = new Text(p1S + "");
        scoreP1.setFill(p1);
        scoreP1.setFont(popFont);
        Text scoreP2 = new Text(p2S + "");
        scoreP2.setFill(p2);
        scoreP2.setFont(popFont);
        Text dash = new Text(" - ");
        dash.setFill(Paint.valueOf("WHITE"));
        dash.setFont(popFont);
        HBox display = new HBox(5);
        display.getChildren().addAll(scoreP1,dash,scoreP2);
        return display;
    }
    public void updateScoreDisplay(){
        Text scoreP1 = new Text(p1S + "");
        scoreP1.setFill(p1);
        scoreP1.setFont(popFont);
        Text scoreP2 = new Text(p2S + "");
        scoreP2.setFill(p2);
        scoreP2.setFont(popFont);
        scoreDisplay.getChildren().set(0,scoreP1);
        scoreDisplay.getChildren().set(2,scoreP2);
    }

    public void changeDirectionPlayerTwo(){

        Rectangle newRec = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), playerTwoRect.getWidth(), playerTwoRect.getHeight());
        newRec.setFill(p2);
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

    public StackPane printResults(int winner){ //after this is called, winner limit would need to be checked
        Text pWinner;
        if (winner == 1){
            pWinner = new Text(p1N + " Wins!");
            p1S++;
        }
        else if (winner == 2){
            pWinner = new Text(p2N + " Wins!");
            p2S++;
        }
        else
            pWinner = new Text("Tie. :(");
        pWinner.setFont(popFont);
        pWinner.setFill(Paint.valueOf("WHITE"));
        //pWinner.setX(350);
        //pWinner.setY(400);
        Rectangle background = new Rectangle(300, 70); //could be better, for different name sizes
        background.setFill(Paint.valueOf("RED"));
        StackPane result = new StackPane(background, pWinner);
        result.setLayoutX(250);
        result.setLayoutY(400);
        return result;
    }

}