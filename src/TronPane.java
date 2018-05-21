
/*
 * CSCI 1101 - Final Project (Tron) - Boys In Motion
 * This program uses JavaFx to have a customizable Tron game. This game is two player and uses A D and Left Right for controls
 * The features of this program can be found in the report.
 *
 * This class specifically extends the JavaFX pane class, to make a pane with our game elements.
 * All game logic, collisions, animations and displays are handled in this class
 * Key presses will be passed into the TronPane object in the "Main" class, and the responses are handled in this class
 */

//imports


//animation
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//event handlers
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//layout
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

//colors
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

//shapes and fonts
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TronPane extends Pane{

    //attributes, also makes rectangle and animation timeline object
    private final double sizeX = 6;
    private final double sizeY = 6; //default size of the players

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

    private Rectangle topArena, leftArena, bottomArena, rightArena;
    private HBox scoreDisplay;

    //this is our "path" of rectangles, in the form of an arraylist
    private ArrayList<Rectangle> playerOneRectPath = new ArrayList<>();

    //path arraylist for player two
    private ArrayList<Rectangle> playerTwoRectPath = new ArrayList<>();



    //keeping track of scores
    private int p1S = 0;
    private int p2S = 0;

    //custom variables
    private Paint p1; //color of lines
    private Paint p2;
    private String p1N; //names
    private String p2N;
    private int gameRounds; //max rounds

    //misc
    private Font popFont;
    private Timeline animation;
    private boolean popUpOnScreen = false; //used for methods
    private Timeline countdown = new Timeline(new KeyFrame(Duration.millis(1000), e -> countDown()));

    //constructor sets up animation and creates rectangle
    public TronPane(Font popFont, String p1N, String p2N, Paint p1, Paint p2, int gameRounds){
        //assigning the custom variables
        this.p1N = p1N;
        this.p2N = p2N;
        this.p1 = p1;
        this.p2 = p2;
        this.gameRounds = gameRounds;

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

        scoreDisplay = scoreDisplay(); //builds the score
        scoreDisplay.setLayoutX(350);
        scoreDisplay.setLayoutY(15);

        //building the arena
        topArena = new Rectangle(5, 60, 800, 8);
        topArena.setFill(Color.WHITE);
        bottomArena = new Rectangle(5, 795, 800, 8);
        bottomArena.setFill(Color.WHITE);
        rightArena = new Rectangle(795, 60, 8, 800);
        rightArena.setFill(Color.WHITE);
        leftArena = new Rectangle(5, 60, 8, 800);
        leftArena.setFill(Color.WHITE);

        //adding elements
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

        //moving the line
        animation = new Timeline(
                new KeyFrame(Duration.millis(2), e -> movePlayer())
        );
        animation.setCycleCount(Timeline.INDEFINITE);

        //countdown animation
        countdown.setCycleCount(4);
        countdown.setOnFinished(e -> {
            getChildren().remove(getChildren().size() - 1); //remove the countdown
            count = 3; //gets the countdown ready for another countdown
            play();
            popUpOnScreen = false;
        });
        Text temp = new Text(""); //replaceable text for the countdown
        getChildren().add(temp);

        popUpOnScreen = true; //ensures no unwanted controls
        countdown.play(); //starts the game with a countdown
    }

    //methods play/pause the animation
    public void play(){
        animation.play();
    }
    public void pause(){
        animation.pause();
    }

    //accessors
    public boolean isGameOver() {return (p1S + p2S == gameRounds);}
    public boolean isPopUpOnScreen() {return popUpOnScreen;}
    public int getP1S() {return p1S;}
    public int getP2S() {return p2S;}


    //method adds a "pixel" in the form of a 6x6 rectangle to the path
    private void addAPixelToPath(){

        //if player 1 is going right or left, add width to the current rectanlge line and move it accordingly (i.e. move it if they're going left)
        if (playerOneDx == 1)
            playerOneCurrRectLine.setWidth(playerOneCurrRectLine.getWidth() + 1);
        else if (playerOneDx == -1){
            playerOneCurrRectLine.setWidth(playerOneCurrRectLine.getWidth() + 1);
            playerOneCurrRectLine.setX(playerOneCurrRectLine.getX() - 1);
        }
        else if (playerOneDy == 1)
            playerOneCurrRectLine.setHeight(playerOneCurrRectLine.getHeight() + 1);
        else if (playerOneDy == -1) {
            playerOneCurrRectLine.setHeight(playerOneCurrRectLine.getHeight() + 1);
            playerOneCurrRectLine.setY(playerOneCurrRectLine.getY() - 1);
        }

        if (playerTwoDx == 1)
            playerTwoCurrRectLine.setWidth(playerTwoCurrRectLine.getWidth() + 1);
        else if (playerTwoDx == -1){
            playerTwoCurrRectLine.setWidth(playerTwoCurrRectLine.getWidth() + 1);
            playerTwoCurrRectLine.setX(playerTwoCurrRectLine.getX() - 1);
        }else if (playerTwoDy == 1)
            playerTwoCurrRectLine.setHeight(playerTwoCurrRectLine.getHeight() + 1);
        else if (playerTwoDy == -1) {
            playerTwoCurrRectLine.setHeight(playerTwoCurrRectLine.getHeight() + 1);
            playerTwoCurrRectLine.setY(playerTwoCurrRectLine.getY() - 1);
        }

    }

    public void collisionCheck(){ //checks for collisions
        boolean end = false;
        int boomP = 0; //is set to which player crashed. 3 means tie
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

        //checking the area walls
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

        //checking area wall collisions for player 2
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

        if (end) { //collision is detected
            if (boomP == 1){ //player one crashed
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(1)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(2)); //displays score popup with player two as winner
                        updateScoreDisplay(); //refresh popup
                        ticks = 0; //resets explosion animation
                    }
                });
            }
            else if (boomP == 2){ //player two crashed
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(2)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(1)); //player one wins
                        updateScoreDisplay();
                        ticks = 0;
                    }
                });
            }
            else { //tied / head-on
                explosion = new Timeline(new KeyFrame(Duration.millis(50), e -> crash(3)));
                explosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        getChildren().add(printResults(3)); //nobody wins
                        updateScoreDisplay();
                        ticks = 0;
                    }
                });
            }
            explosion.setCycleCount(8);
            getChildren().add(new Group()); //node to replace in the explosion animation
            explosion.play();
        }
    }

    private int ticks; //contains how big the explosion is
    private void crash(int player){ //explosion animation
        double startX;
        double startY;
        //ifs to decide where the explosion originates
        if (player == 1){
            startX = playerOneRecLocX;
            startY = playerOneRecLocY;
        }
        else if (player == 2){
            startX = playerTwoRecLocX;
            startY = playerTwoRecLocY;
        }
        else { //during a tie, in the middle of both lines
            startX = (playerOneRecLocX + playerTwoRecLocX) / 2;
            startY = (playerOneRecLocY + playerTwoRecLocY) / 2;
        }

        Group root; //contains the explosion rectangles
        if (ticks % 2 == 0) { //every even tick, display an explosion
            double distance = ticks * (17); //distances out the rectangles with every tick

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
            if (ticks % 4 == 0){ //every second burst, 4th and 8th tick
                rect1.setFill(Paint.valueOf("YELLOW"));
                rect2.setFill(Paint.valueOf("YELLOW"));
                rect3.setFill(Paint.valueOf("YELLOW"));
                rect4.setFill(Paint.valueOf("YELLOW"));
                rect5.setFill(Paint.valueOf("YELLOW"));
                rect6.setFill(Paint.valueOf("YELLOW"));
                rect7.setFill(Paint.valueOf("YELLOW"));
                rect8.setFill(Paint.valueOf("YELLOW"));
            }
            else {//2nd and 6th tick
                rect1.setFill(Paint.valueOf("WHITE"));
                rect2.setFill(Paint.valueOf("WHITE"));
                rect3.setFill(Paint.valueOf("WHITE"));
                rect4.setFill(Paint.valueOf("WHITE"));
                rect5.setFill(Paint.valueOf("WHITE"));
                rect6.setFill(Paint.valueOf("WHITE"));
                rect7.setFill(Paint.valueOf("WHITE"));
                rect8.setFill(Paint.valueOf("WHITE"));
            }
            root = new Group(rect1,rect2,rect3,rect4,rect5,rect6,rect7,rect8); //add all the explosion
        }
        else
            root = new Group(); //nothing
        if (ticks == 8) { //at the end of the animation, remove the explosion
            getChildren().remove(getChildren().size() - 1);
        }
        else { //add animation and increase tick
            getChildren().set(getChildren().size() - 1, root);
            ticks++;
        }
    }

    //method calls when player one switches direction
    public void changeDirectionPlayerOne(){

        //new rectangle is made at position of player 1
        Rectangle newRec = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), playerOneRect.getWidth(), playerOneRect.getHeight());
        newRec.setFill(p1);
        playerOneRectPath.add(newRec);

        //sets the current rectangle to the new rectangle and refreshes it to be added to the scene
        playerOneCurrRectLine = newRec;
        refreshPathAsChildPlayerOne();
    }

    public HBox scoreDisplay(){ //displays the score
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

    public void updateScoreDisplay(){ //refreshes the score display with the new scores
        Text scoreP1 = new Text(p1S + "");
        scoreP1.setFill(p1);
        scoreP1.setFont(popFont);
        Text scoreP2 = new Text(p2S + "");
        scoreP2.setFill(p2);
        scoreP2.setFont(popFont);
        //replaces score display numbers
        scoreDisplay.getChildren().set(0,scoreP1);
        scoreDisplay.getChildren().set(2,scoreP2);
    }

    //method calls when player two switches direction
    public void changeDirectionPlayerTwo(){

        //new rectangle is made at position of player 2
        Rectangle newRec = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), playerTwoRect.getWidth(), playerTwoRect.getHeight());
        newRec.setFill(p2);
        playerTwoRectPath.add(newRec);

        //sets the current rectangle to the new rectangle and refreshes it to be added to the scene
        playerTwoCurrRectLine = newRec;
        refreshPathAsChildPlayerTwo();
    }

    //adds current rectangle variable to the scene
    public void refreshPathAsChildPlayerOne() {
        getChildren().add(playerOneCurrRectLine);
    }

    //adds current rectangle variable to the scene (player 2)
    public void refreshPathAsChildPlayerTwo() {
        getChildren().add(playerTwoCurrRectLine);
    }

    //sets the direction of player one (passed in through keypresses)
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

    //sets the direction of player two (passed in through keypresses)
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

        //flag: prints how many rectangles there are, for optimization testing
        System.out.println(playerOneRectPath.size() + playerTwoRectPath.size());
    }

    public StackPane printResults(int winner) { //pop up for the score and updates score ints
        Text pWinner;
        if (winner == 1) {
            pWinner = new Text(p1N + " Wins!");
            p1S++;
        }
        else if (winner == 2) {
            pWinner = new Text(p2N + " Wins!");
            p2S++;
        }
        else
            pWinner = new Text("Tie. :(");
        pWinner.setFont(popFont);
        pWinner.setFill(Paint.valueOf("WHITE"));

        Rectangle background = new Rectangle(300, 70);
        background.setFill(Paint.valueOf("RED"));
        StackPane result = new StackPane(background, pWinner);
        result.setLayoutX(250);
        result.setLayoutY(400);

        popUpOnScreen = true; //allows spacebar to work
        return result;
    }

    private int count = 3; //this has to be static since countdown is being reused as an animation

    public void countDown(){ //ticks down through a popup
        popUpOnScreen = true;
        StackPane countdown = new StackPane();
        Rectangle countR = new Rectangle(60, 60, Color.RED);
        Text countP = new Text(count + "");

        countP.setFont(popFont);
        countP.setFill(Color.WHITE);
        countdown.getChildren().addAll(countR, countP);
        countdown.setLayoutX(370);
        countdown.setLayoutY(370);
        getChildren().set(getChildren().size() - 1, countdown);
        count--;
    }
    public void resetGame() { //resets the game with an animation and countdown. Only activate when the game keeps going on
        //on space bar press and game is not done, result would be removed
        while (getChildren().get(getChildren().size() - 1) != scoreDisplay){ //removes popup and all the lines
            System.out.println("boop");
            getChildren().remove(getChildren().size() - 1); //this might be bugged
        }
        //rebuilding all the starting rectangles and directions
        //initial variables
        playerOneRecLocX = 200;
        playerOneRecLocY = 390; //start point of player 1
        playerOneDx = 1;
        playerOneDy = 0;//start direction of player one
        playerTwoRecLocX = 600;
        playerTwoRecLocY = 390; //start point of player 2
        playerTwoDx = -1;
        playerTwoDy = 0; //start direction of player two

        playerTwoRectPath.clear();
        playerOneRectPath.clear();
        //clears the arraylists of rectangle paths

        playerOneRect = new Rectangle(playerOneRecLocX, playerOneRecLocY, sizeX, sizeY);
        //player 2 rectangle
        playerTwoRect = new Rectangle(playerTwoRecLocX, playerTwoRecLocY, sizeX, sizeY);
        //current line streaming from player one
        playerOneCurrRectLine = new Rectangle(playerOneRect.getX(), playerOneRect.getY(), sizeX, playerOneRect.getHeight());
        //current line streaming from player two
        playerTwoCurrRectLine = new Rectangle(playerTwoRect.getX(), playerTwoRect.getY(), sizeX, playerTwoRect.getHeight());

        //colors
        playerOneRect.setFill(p1);
        playerTwoRect.setFill(p2);
        playerOneCurrRectLine.setFill(p1);
        playerTwoCurrRectLine.setFill(p2);

        //adding elements
        playerOneRectPath.add(playerOneCurrRectLine);
        playerTwoRectPath.add(playerTwoCurrRectLine);
        getChildren().addAll(playerOneRect, playerTwoRect, playerTwoCurrRectLine, playerOneCurrRectLine);

        Text temp = new Text(""); //replaceable text for the countdown
        getChildren().add(temp);
        countdown.play();
    }
}