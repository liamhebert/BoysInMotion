package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.io.FileInputStream;


public class Main extends Application {
    private static Font titleFont;
    private static Font subTitle;
    private static Font popFont;
    private static final Font subFont = new Font("Consolas", 37.333333 * 1.5); //these two fonts are windows exclusive, sorry
    private static final Font smallFont = new Font ("Consolas", 30);

    //these four static variables contain the customizable aspect of the game and the score. Things that change often
    private static Color p1;
    private static Color p2;

    private static int p1S;
    private static int p2S;
    private static String p1N = "Player 1";
    private static String p2N = "Player 2";
    private Stage stage;


    public void start(Stage primaryStage) throws Exception{
        //since exceptions have to be handled, have to be assigned here
        titleFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),106.66667 * 1.5);
        subTitle = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),42.6666667 * 1.5);
        popFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),30);
        //these two variables here are for testing purpose only, will be assigned during the customize screen
        p1 = Color.rgb(25,255,40);
        p2 = Color.rgb(255,252,25);
        primaryStage.setTitle("Loading...");
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        //following block is for testing purposes only. Normally, game would start on the main menu.
        //primaryStage.setScene(customizeBike());
        primaryStage.setScene(mainMenu());
        //primaryStage.setScene(results());
        //primaryStage.setScene(credits());
        //primaryStage.setScene(game());
        primaryStage.show();
        //primaryStage.setFullScreen(true); //testing purpose only for lower rez screens
        primaryStage.setTitle("results");
    }
    public Scene mainMenu() { //this is where the game should start. Shows the main menu and options
        Text title = new Text("Tron.");
        title.setFont(titleFont);
        title.setFill(Paint.valueOf("WHITE"));

        Text option1 = new Text("start");//this will need to be changed
        option1.setFont(subFont);
        option1.setFill(Paint.valueOf("WHITE"));
        Text option2 = new Text("credits");//this will need to be changed
        option2.setFont(subFont);
        option2.setFill(Paint.valueOf("WHITE"));
        Text option3 = new Text("quit");//this will need to be changed
        option3.setFont(subFont);
        option3.setFill(Paint.valueOf("WHITE"));
        //normally black, selected would have color
        Rectangle op1 = new Rectangle(450, 70, Paint.valueOf("RED"));
        Rectangle op2 = new Rectangle(450, 70, Paint.valueOf("PURPLE"));
        Rectangle op3 = new Rectangle(450, 70, Paint.valueOf("ORANGE"));
        StackPane op1S = new StackPane(op1, option1);
        StackPane op2S = new StackPane(op2, option2);
        StackPane op3S = new StackPane(op3, option3);
        op1S.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(customizeBike());
            }
        });
        op2S.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(credits());
            }
        });
        op3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
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
        return new Scene (root,800,800, Paint.valueOf("BLACK"));
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

        layout.getChildren().addAll(playerRow("player 1 :"), playerRow("player 2 :"));

        Rectangle selectedColorP1 = new Rectangle(812,375 - 250);
        selectedColorP1.setFill(p1);
        selectedColorP1.setY(250);
        Rectangle selectedColorP2 = new Rectangle(812,375-250);
        selectedColorP2.setFill(p2);
        selectedColorP2.setY(375);



        HBox roundSelection = new HBox(15);
        layout.getChildren().add(3,roundSelection);
        Text roundText = new Text("rounds:");
        roundText.setFont(smallFont);
        roundText.setFill(Paint.valueOf("WHITE"));
        HBox.setMargin(roundText, new Insets(10,0,0,0));
        roundSelection.getChildren().add(roundText);

        HBox rects = new HBox(10);
        Rectangle option1 = new Rectangle(50,50, Paint.valueOf("RED"));
        Rectangle option2 = new Rectangle(50,50, Paint.valueOf("RED"));
        Rectangle option3 = new Rectangle(50,50, Paint.valueOf("RED"));
        Rectangle option4 = new Rectangle(50,50, Paint.valueOf("RED"));
        Text option1T = new Text("1");
        Text option3T = new Text("3");
        Text option5T = new Text("5");
        Text option7T = new Text("7");
        StackPane.setMargin(option1T, new Insets(5,0,0,0));
        StackPane.setMargin(option3T, new Insets(5,0,0,0));
        StackPane.setMargin(option5T, new Insets(5,0,0,0));
        StackPane.setMargin(option7T, new Insets(5,0,0,0));
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
        option2.setFill(Paint.valueOf("GREEN")); //selected option, I want to fix this

        rects.getChildren().addAll(option1S, option3S, option5S, option7S);
        HBox.setMargin(rects, new Insets(0,0,0,54));
        roundSelection.getChildren().add(rects);

        Text buttonGo = new Text("GO!");
        buttonGo.setFont(subTitle);
        buttonGo.setFill(Paint.valueOf("WHITE"));
        Rectangle startButton = new Rectangle(600, 125);
        startButton.setFill(Paint.valueOf("RED"));
        StackPane start = new StackPane(startButton, buttonGo);
        StackPane.setMargin(buttonGo, new Insets(10,0,0,0));
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(game());
            }
        });
        layout.getChildren().add(start);

        root.getChildren().addAll(selectedColorP1,selectedColorP2,layout);
        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }
    private HBox playerRow(String player){
        HBox root = new HBox(20);
        Text pT = new Text(player);
        pT.setFont(smallFont);
        pT.setFill(Paint.valueOf("WHITE"));
        root.getChildren().add(pT);

        VBox inputAndColor = new VBox(10);
        TextField p1NameInput = new TextField("Name");
        inputAndColor.getChildren().add(p1NameInput);

        HBox colors = new HBox(10);
        Color[] allColors = {Color.rgb(255,0,0), Color.rgb(255,252,25), Color.rgb(20,133,204), Color.rgb(255,120,0), Color.rgb(25,255,40) };
        //testing purposes, will be reassigned based on selection
        p1 = allColors[1];
        p2 = allColors[4];
        Rectangle color1 = new Rectangle(50,50);
        color1.setFill(allColors[0]);
        Rectangle color2 = new Rectangle(50,50);
        color2.setFill(allColors[1]);
        Rectangle color3 = new Rectangle(50,50);
        color3.setFill(allColors[2]);
        Rectangle color4 = new Rectangle(50, 50);
        color4.setFill(allColors[3]);
        Rectangle color5 = new Rectangle(50, 50);
        color5.setFill(allColors[4]);
        colors.getChildren().addAll(color1,color2,color3,color4,color5);
        inputAndColor.getChildren().add(colors);
        root.getChildren().add(inputAndColor);


        //make sure that Paint p1/p2 equal the selected color;
        return root;
    }

    public Scene results(){
        Group root = new Group();
        Rectangle colorWinner = new Rectangle(0,0,810,280); //little bigger then the bounds since the
        colorWinner.setFill(p1); //color of the winner, will be automatic in the future
        root.getChildren().add(colorWinner);
        VBox display = new VBox(20);
        display.setLayoutX(50);
        display.setLayoutY(200);
        root.getChildren().add(display);
        Text winner = new Text();
        if (p1S > p2S)
            winner.setText(p1N + " Wins!");
        else
            winner.setText(p2N + " Wins!");

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
        VBox.setMargin(playAgain, new Insets(50,0,0,0));
        display.getChildren().add(playAgain);

        HBox options = new HBox(30);
        Text yes = new Text("yes");
        yes.setFont(smallFont);
        yes.setFill(Paint.valueOf("WHITE"));
        Text no = new Text("no");
        no.setFont(smallFont);
        no.setFill(Paint.valueOf("WHITE"));
        Rectangle option1 = new Rectangle(80, 50, Paint.valueOf("BLACK"));
        Rectangle option2 = new Rectangle(80,50, Paint.valueOf("BLACK"));
        StackPane op1S = new StackPane(option1, yes);
        StackPane op2S = new StackPane(option2,no);


        options.getChildren().addAll(op1S, op2S);
        display.getChildren().add(options);

        return new Scene(root, 800,800, Paint.valueOf("BLACK"));
    }

    public Scene credits(){
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
        theBoys.setFont(smallFont);
        Text fontUsed = new Text("Title Font: League Spartan - The League of Moveable Type");
        fontUsed.setFill(Paint.valueOf("WHITE"));
        fontUsed.setFont(smallFont);
        layout.getChildren().addAll(thanksTitle, theBoys,fontUsed);
        root.getChildren().add(layout);
        return new Scene(root,1200,800,Paint.valueOf("BLACK"));
    }

    public Scene game(){
        TronPane tronPane = new TronPane(popFont,p1N,p2N,p1,p2);//main game pane class
        Scene scene = new Scene(tronPane, 800, 800, Paint.valueOf("BLACK"));
        tronPane.requestFocus();
        tronPane.setOnKeyReleased(e -> { //lambda, oooooof. This does the same thing as new EventHandler(KeyEvent e)
            if (e.getCode() == KeyCode.A){
                tronPane.setDirectionPlayerOne("l");
            } else if (e.getCode() == KeyCode.D){
                tronPane.setDirectionPlayerOne("r");
            } else if (e.getCode() == KeyCode.LEFT){
                tronPane.setDirectionPlayerTwo("l");
            } else if (e.getCode() == KeyCode.RIGHT){
                tronPane.setDirectionPlayerTwo("r");
            }
        });

        return scene;
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

    public static void main(String[] args) {
        launch(args);
    }

}
