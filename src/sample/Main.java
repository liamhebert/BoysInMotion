package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.util.Duration;


public class Main extends Application {
    private static Font titleFont;
    private static Font subTitle;
    private static Font popFont;
    private static final Font subFont = new Font("Consolas", 37.333333 * 1.5); //these two fonts are windows exclusive, sorry
    private static final Font smallFont = new Font("Consolas", 30);

    //these four static variables contain the customizable aspect of the game and the score. Things that change often


    private static int p1S;
    private static int p2S;
    private static String p1N = "Player 1";
    private static String p2N = "Player 2";
    private Stage stage;
    private static int p1Flicks = 0;
    private static int p2Flicks = 0;
    private static Color[] allColors = {Color.rgb(255, 0, 0), Color.valueOf("ORANGE"), Color.rgb(20, 133, 204), Color.rgb(178, 24, 95), Color.rgb(25, 255, 40)};
    private static Color p1 = allColors[0];
    private static Color p2 = allColors[1];

    private static int gamerounds = 1;//defaults rounds to 1 so the game doesn't crash

    private static boolean playing = false;//set for startGame method

    //this method is used to restart the game after each round, space bar must be pressed in order to reset
    //possibly use space at the end of the game to exit to menu?
    public void startGame(int rounds) {

        playing = true;
        stage.setScene(game());
        playing = false;
        gamerounds--;

    }


    public void start(Stage primaryStage) throws Exception {
        //since exceptions have to be handled, have to be assigned here
        titleFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"), 106.66667 * 1.5);
        subTitle = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"), 42.6666667 * 1.5);
        popFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"), 30);
        primaryStage.setTitle("Loading...");
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        //following block is for testing purposes only. Normally, game would start on the main menu.
        //primaryStage.setScene(customizeBike());

        //primaryStage.setScene(results());
        //primaryStage.setScene(credits());
        // primaryStage.setScene(game());
        primaryStage.setScene(mainMenu());
        primaryStage.show();
        //primaryStage.setFullScreen(true); //testing purpose only for lower rez screens
        primaryStage.setTitle("results");
    }

    public Scene mainMenu() { //this is where the game should start. Shows the main menu and options
        Text title = new Text("Tron.");
        title.setFont(titleFont);
        title.setFill(Paint.valueOf("WHITE"));

        Text option1 = new Text("start");//this will need to be changed
        option1.setFont(popFont);
        option1.setFill(Paint.valueOf("WHITE"));
        Text option2 = new Text("credits");//this will need to be changed
        option2.setFont(popFont);
        option2.setFill(Paint.valueOf("WHITE"));
        Text option3 = new Text("quit");//this will need to be changed
        option3.setFont(popFont);
        option3.setFill(Paint.valueOf("WHITE"));
        //normally black, selected would have color
        Rectangle op1 = new Rectangle(450, 70, Paint.valueOf("BLACK"));
        Rectangle op2 = new Rectangle(450, 70, Paint.valueOf("BLACK"));
        Rectangle op3 = new Rectangle(450, 70, Paint.valueOf("BLACK"));
        StackPane op1S = new StackPane(op1, option1);
        StackPane op2S = new StackPane(op2, option2);
        StackPane op3S = new StackPane(op3, option3);
        op1S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when start is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(customizeBike());//go to customize screen
            }
        });
        op1S.setOnMouseEntered(new EventHandler<MouseEvent>() {//when button is moused over
            @Override
            public void handle(MouseEvent event) {
                op1.setFill(Paint.valueOf("RED"));
            }
        });
        op1S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                op1.setFill(Paint.valueOf("BLACK"));
            }
        });
        op2S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when credits is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(credits());//go to credits scene
            }
        });
        op2S.setOnMouseEntered(new EventHandler<MouseEvent>() {//when button is moused over
            @Override
            public void handle(MouseEvent event) {
                op2.setFill(Paint.valueOf("PURPLE"));
            }
        });
        op2S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                op2.setFill(Paint.valueOf("BLACK"));
            }
        });
        op3S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when quit is clicked
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();//quit
            }
        });
        op3S.setOnMouseEntered(new EventHandler<MouseEvent>() {//When button is moused over
            @Override
            public void handle(MouseEvent event) {
                op3.setFill(Paint.valueOf("ORANGE"));
            }
        });
        op3S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                op3.setFill(Paint.valueOf("BLACK"));
            }
        });

        VBox optionsLayout = new VBox(5); //contains the text options
        VBox layout = new VBox(15); //overall layout
        optionsLayout.getChildren().addAll(op1S, op2S, op3S);
        layout.getChildren().addAll(title, optionsLayout);
        layout.setAlignment(Pos.CENTER); //centers all the text
        layout.setLayoutX(180);
        layout.setLayoutY(150);

        Group root = new Group(layout);
        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }

    public Scene customizeBike() {
        Group root = new Group();
        VBox layout = new VBox(40);
        layout.setLayoutY(150);
        layout.setLayoutX(50);
        Text title = new Text("Customize Bikes.");
        title.setFont(subTitle);
        title.setFill(Paint.valueOf("WHITE"));
        layout.getChildren().add(title);

        Text buttonGo = new Text("GO!");
        buttonGo.setFont(subTitle);
        buttonGo.setFill(Paint.valueOf("WHITE"));
        Rectangle startButton = new Rectangle(600, 125);
        startButton.setFill(Paint.valueOf("RED"));
        StackPane start = new StackPane(startButton, buttonGo);
        Rectangle selectedColorP1 = new Rectangle(812, 375 - 250);
        selectedColorP1.setFill(p1);
        selectedColorP1.setY(250);


        Rectangle selectedColorP2 = new Rectangle(812, 375 - 250);

        selectedColorP2.setFill(p2);
        selectedColorP2.setY(375);

        Group p1FlickRects = new Group();
        Timeline selectP1 = new Timeline(new KeyFrame(Duration.millis(20), e -> flick(1, p1FlickRects)));
        selectP1.setCycleCount(32);
        selectP1.setAutoReverse(false);
        selectP1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p1Flicks = 0;
                selectedColorP1.setFill(p1);
                p1FlickRects.getChildren().clear();
            }
        });


        Group p2FlickRects = new Group();
        Timeline selectP2 = new Timeline(new KeyFrame(Duration.millis(20), e -> flick(2, p2FlickRects)));
        selectP2.setCycleCount(32);
        selectP2.setAutoReverse(false);
        selectP2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p2Flicks = 0;
                selectedColorP2.setFill(p2);
                p2FlickRects.getChildren().clear();
            }
        });

        //input for each player
        //p1
        HBox player1 = new HBox(20);
        Text p1pT = new Text("player 1 :");
        p1pT.setFont(popFont);
        p1pT.setFill(Paint.valueOf("WHITE"));
        player1.getChildren().add(p1pT);

        VBox inputAndColor1 = new VBox(10);
        TextField p1NameInput = new TextField("Enter Name and Press Enter");
        p1NameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                p1N = p1NameInput.getText();
            }
        });
        inputAndColor1.getChildren().add(p1NameInput);
        HBox.setMargin(inputAndColor1, new Insets(0, 0, 0, 10));
        HBox p1colors = new HBox(10);
        Rectangle p1color1 = new Rectangle(50, 50);
        p1color1.setFill(allColors[0]);
        p1color1.setOnMouseClicked(e -> {
            if (!p2.equals(allColors[0])) {
                p1 = allColors[0];
                selectP1.play();
            }
            /*p1 = allColors[0];
            selectP1.play();*/
        });
        Rectangle p1color2 = new Rectangle(50, 50);
        p1color2.setFill(allColors[1]);
        p1color2.setOnMouseClicked(e -> {
            if (!p2.equals(allColors[1])) {
                p1 = allColors[1];
                selectP1.play();
            }
            /*p1 = allColors[1];
            selectP1.play();*/
        });
        Rectangle p1color3 = new Rectangle(50, 50);
        p1color3.setFill(allColors[2]);
        p1color3.setOnMouseClicked(e -> {
            if (!p2.equals(allColors[2])) {
                p1 = allColors[2];
                selectP1.play();
            }
            /*p1 = allColors[2];
            selectP1.play();*/
        });
        Rectangle p1color4 = new Rectangle(50, 50);
        p1color4.setFill(allColors[3]);
        p1color4.setOnMouseClicked(e -> {
            if (!p2.equals(allColors[3])) {
                p1 = allColors[3];
                selectP1.play();
            }
            /*p1 = allColors[3];
            selectP1.play();*/
        });
        Rectangle p1color5 = new Rectangle(50, 50);
        p1color5.setFill(allColors[4]);
        p1color5.setOnMouseClicked(e -> {
            if (!p2.equals(allColors[4])) {
                p1 = allColors[4];
                selectP1.play();
            }
            /*p1 = allColors[4];
            selectP1.play();*/
        });
        p1colors.getChildren().addAll(p1color1, p1color2, p1color3, p1color4, p1color5);
        inputAndColor1.getChildren().add(p1colors);
        player1.getChildren().add(inputAndColor1);

        //p2
        HBox player2 = new HBox(20);
        Text p2pT = new Text("player 2 :");
        p2pT.setFont(popFont);
        p2pT.setFill(Paint.valueOf("WHITE"));
        player2.getChildren().add(p2pT);

        //Allows player 2 to input their name and have it displayed
        VBox inputAndColor2 = new VBox(10);
        TextField p2NameInput = new TextField("Enter Name and Press Enter");
        inputAndColor2.getChildren().add(p2NameInput);
        p2NameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                p2N = p2NameInput.getText();
            }
        });

        HBox p2colors = new HBox(10);
        Rectangle p2color1 = new Rectangle(50, 50);
        p2color1.setFill(allColors[0]);

        p2color1.setOnMouseClicked(e -> {
            if (!p1.equals(allColors[0])) {
                p2 = allColors[0];
                selectP2.play();
            }
        });


        Rectangle p2color2 = new Rectangle(50, 50);
        p2color2.setFill(allColors[1]);
        p2color2.setOnMouseClicked(e -> {
            if (!p1.equals(allColors[1])) {
                p2 = allColors[1];
                selectP2.play();
            }
            /*p2 = allColors[1];
            selectP2.play();*/
        });
        Rectangle p2color3 = new Rectangle(50, 50);
        p2color3.setFill(allColors[2]);
        p2color3.setOnMouseClicked(e -> {
            if (!p1.equals(allColors[2])) {
                p2 = allColors[2];
                selectP2.play();
            }
            /*p2 = allColors[2];
            selectP2.play();*/
        });


        Rectangle p2color4 = new Rectangle(50, 50);
        p2color4.setFill(allColors[3]);
        p2color4.setOnMouseClicked(e -> {
            if (!p1.equals(allColors[3])) {
                p2 = allColors[3];
                selectP2.play();
            }
            /*p2 = allColors[3];
            selectP2.play();*/
        });


        Rectangle p2color5 = new Rectangle(50, 50);
        p2color5.setFill(allColors[4]);
        p2color5.setOnMouseClicked(e -> {
            if (!p1.equals(allColors[4])) {
                p2 = allColors[4];
                selectP2.play();
            }
            /*p2 = allColors[4];
            selectP2.play();*/
        });
        p2colors.getChildren().addAll(p2color1, p2color2, p2color3, p2color4, p2color5);
        inputAndColor2.getChildren().add(p2colors);
        player2.getChildren().add(inputAndColor2);
        layout.getChildren().addAll(player1, player2);


        HBox roundSelection = new HBox(15);
        layout.getChildren().add(3, roundSelection);
        Text roundText = new Text("rounds:");
        roundText.setFont(popFont);
        roundText.setFill(Paint.valueOf("WHITE"));
        HBox.setMargin(roundText, new Insets(10, 0, 0, 0));
        roundSelection.getChildren().add(roundText);

        HBox rects = new HBox(10);
        Rectangle option1 = new Rectangle(50, 50, Paint.valueOf("RED"));
        Rectangle option2 = new Rectangle(50, 50, Paint.valueOf("RED"));
        Rectangle option3 = new Rectangle(50, 50, Paint.valueOf("RED"));
        Rectangle option4 = new Rectangle(50, 50, Paint.valueOf("RED"));
        Text option1T = new Text("1");
        Text option3T = new Text("3");
        Text option5T = new Text("5");
        Text option7T = new Text("7");
        StackPane.setMargin(option1T, new Insets(5, 0, 0, 0));
        StackPane.setMargin(option3T, new Insets(5, 0, 0, 0));
        StackPane.setMargin(option5T, new Insets(5, 0, 0, 0));
        StackPane.setMargin(option7T, new Insets(5, 0, 0, 0));
        StackPane option1S = new StackPane(option1, option1T);
        StackPane option3S = new StackPane(option2, option3T);
        StackPane option5S = new StackPane(option3, option5T);
        StackPane option7S = new StackPane(option4, option7T);
        option1T.setFont(popFont);
        option3T.setFont(popFont);
        option5T.setFont(popFont);
        option7T.setFont(popFont);
        option1T.setFill(Color.valueOf("WHITE"));
        option3T.setFill(Color.valueOf("WHITE"));
        option5T.setFill(Color.valueOf("WHITE"));
        option7T.setFill(Color.valueOf("WHITE"));

        //Next options change the color of the amount of rounds to be played.
        //Slightly long but if one color is chosen others will revert back to red.
        //This will do it for every option of the amount of rounds the player wants to play         //side note
        //It also sets the static variable gamerouds to the amount selected.                        //this could be done by having an arrayList with each rectangle to reduce redundancy, but this works too :)
        option1S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("GREEN"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds = 1;
        });
        option3S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("GREEN"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds = 3;
        });
        option5S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("GREEN"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds = 5;
        });
        option7S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("GREEN"));
            gamerounds = 7;
        });


        rects.getChildren().addAll(option1S, option3S, option5S, option7S);
        HBox.setMargin(rects, new Insets(0, 0, 0, 54));
        roundSelection.getChildren().add(rects);


        StackPane.setMargin(buttonGo, new Insets(10, 0, 0, 0));
        layout.getChildren().add(start);
        root.getChildren().addAll(selectedColorP1, selectedColorP2, layout);
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //stage.setScene(game());
                startGame(gamerounds);//method is used to start game
            }
        });
        root.getChildren().add(2, p1FlickRects);
        root.getChildren().add(2, p2FlickRects);
        //selectP1.play(); //testing
        //selectP2.play(); //testing
        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }

    private static void flick(int player, Group container) {
        int startY;
        Color tempC;
        int flicks;
        if (player == 1) {
            startY = 250;
            tempC = p1; //this would be the static value, just testing
            flicks = p1Flicks;
        } else {
            startY = 375;
            tempC = p2;
            flicks = p2Flicks;
        }

        Rectangle temp = new Rectangle(flicks * 25, startY, 25, 125);
        temp.setFill(tempC);
        container.getChildren().add(temp);
        if (player == 1)
            p1Flicks++;
        else
            p2Flicks++;

    }

    public Scene results() {
        Group root = new Group();
        Rectangle colorWinner = new Rectangle(0, 0, 810, 280); //little bigger then the bounds since the
        root.getChildren().add(colorWinner);
        VBox display = new VBox(20);
        display.setLayoutX(50);
        display.setLayoutY(200);
        root.getChildren().add(display);
        Color winnerC;
        Color loserC;
        Text winner = new Text();
        if (p1S > p2S) {
            winner.setText(p1N + " Wins!");
            winnerC = p1;
            loserC = p2;
        } else {
            winner.setText(p2N + " Wins!");
            winnerC = p2;
            loserC = p1;
        }
        colorWinner.setFill(winnerC);

        winner.setFont(subTitle);
        winner.setFill(Paint.valueOf("WHITE"));
        display.getChildren().add(winner);
        //for demo purposes, will make this smarter in the future
        p1S = 2;
        p2S = 3;
        //end
        HBox score = scoreDisplay();
        display.getChildren().add(score);

        Text playAgain = new Text("Play again?");
        playAgain.setFont(popFont);
        playAgain.setFill(Paint.valueOf("WHITE"));
        VBox.setMargin(playAgain, new Insets(50, 0, 0, 0));
        display.getChildren().add(playAgain);

        HBox options = new HBox(30);
        Text yes = new Text("yes");
        yes.setFont(popFont);
        yes.setFill(Paint.valueOf("WHITE"));
        Text no = new Text("no");
        no.setFont(popFont);
        no.setFill(Paint.valueOf("WHITE"));
        Rectangle option1 = new Rectangle(80, 50, loserC);
        Rectangle option2 = new Rectangle(80, 50, loserC);
        StackPane op1S = new StackPane(option1, yes);
        StackPane op2S = new StackPane(option2, no);
        op1S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when yes is clicked


            @Override
            public void handle(MouseEvent event) {
                stage.setScene(customizeBike());//go to customize screen
            }
        });
        op2S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when no is clicked
            @Override
            public void handle(MouseEvent event) {//go to main menu
                stage.setScene(mainMenu());
            }
        });


        options.getChildren().addAll(op1S, op2S);
        display.getChildren().add(options);

        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }

    public Scene credits() {
        Group root = new Group();
        VBox layout = new VBox(30);
        layout.setLayoutX(50);
        layout.setLayoutY(200);
        Text thanksTitle = new Text("Thanks for Playing!");
        thanksTitle.setFont(subTitle);
        thanksTitle.setFill(Paint.valueOf("WHITE"));
        Text theBoys = new Text("The Boys in Motion:\n" +
                "Liam Hebert\n" +
                "Arjav Gupta\n" +
                "Bishal Dasputa\n" +
                "Logan Sutherland\n" +
                "Kenzie Dowie\n");
        theBoys.setFill(Paint.valueOf("WHITE"));
        theBoys.setFont(popFont);
        Text fontUsed = new Text("Title Font: League Spartan - The League of Moveable Type");
        fontUsed.setFill(Paint.valueOf("WHITE"));
        fontUsed.setFont(popFont);
        layout.getChildren().addAll(thanksTitle, theBoys, fontUsed);
        root.getChildren().add(layout);
        return new Scene(root, 1200, 800, Paint.valueOf("BLACK"));
    }

    public Scene game() {

        //so the tronPane needs a group to assign to a scene. Stage didn't like it when tron pane was pushed directly to a scene
        //so I gave it to a group
        //this caused problems because it was ignoring key inputs
        //resolved this by making the keyinputs part of the scene instead of the tronPane
        //this fixed the issue because there is no barrier between the stage and the tronPane since the scene processes the inputs
        //(the group was blocking this)

        Group root = new Group();//contains tron pane
        TronPane tronPane = new TronPane(popFont, p1N, p2N, p1, p2, gamerounds);//main game pane class
        root.getChildren().add(tronPane);
        Scene display = new Scene(root, 800, 800, Paint.valueOf("BLACK")); //scene needs to be built before adding keyinputs
        display.setOnKeyReleased(e -> { //lambda, oooooof. This does the same thing as new EventHandler(KeyEvent e) //attached directly to the scene
            //the key inputs directly affect the tronPane through the Scene, direct route
            if (e.getCode() == KeyCode.A) {
                tronPane.setDirectionPlayerOne("l");
            } else if (e.getCode() == KeyCode.D) {
                tronPane.setDirectionPlayerOne("r");
            } else if (e.getCode() == KeyCode.LEFT) {
                tronPane.setDirectionPlayerTwo("l");
            } else if (e.getCode() == KeyCode.RIGHT) {
                tronPane.setDirectionPlayerTwo("r");
            } else if (e.getCode() == KeyCode.SPACE && !playing) {//makes sure that space can't be used during the game
                System.out.println();
                if (gamerounds > 0) {//allows for game reset depending on the rounds selected
                    startGame(gamerounds);
                }
            }

        });
        return display;
    }


    public HBox scoreDisplay() {
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
        display.getChildren().addAll(scoreP1, dash, scoreP2);
        return display;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
