package sample;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.text.*;

import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {
    private static Font titleFont;
    private static Font subTitle;
    private static Font subFont = new Font("Consolas", 37.333333 * 1.5);
    private static Font smallFont = new Font ("Consolas", 30);
    private static Font popFont;
    private static Color p1;
    private static Color p2;
<<<<<<< HEAD
<<<<<<< HEAD
=======
    private static int p1S;
    private static int p2S;
    private static String p1N = "Liam";
    private static String p2N = "Bob";


>>>>>>> parent of 1cb9d11... rwesdf
=======
>>>>>>> parent of c086a99... Merge branch 'master' of https://github.com/liamhebert/BoysInMotion

    public void start(Stage primaryStage) throws Exception{
        titleFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),106.66667 * 1.5);
        subTitle = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),42.6666667 * 1.5);
        popFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),30);
        p1 = Color.rgb(255,0,0);
        p2 = Color.rgb(0,176,240);
        primaryStage.setTitle("Loading...");
        primaryStage.setResizable(false);
        primaryStage.setScene(customizeBike());
        //primaryStage.setScene(results("Liam", p2)); //second variable lets the screen know who won
        //primaryStage.setScene(credits());
        primaryStage.show();
        primaryStage.setTitle("results");
        Thread.sleep(1000);
        int input;
        //System.out.println("1 : Customise bikes, 2: Game Screen, 3: Main, 4: quit");
        //Scanner kb = new Scanner(System.in);
        //input = kb.nextInt();
//        if (input == 1) {
//            primaryStage.setTitle("Loading...");
//            primaryStage.setScene(customizeBike());
//            primaryStage.setTitle("Customize Bike");
//            primaryStage.show();
//        }
//        else if (input == 2) {
//            primaryStage.setTitle("Loading...");
//            //primaryStage.setScene(game());
//            primaryStage.setTitle("Game");
//            primaryStage.show();
//        }
//        else if (input == 3) {
//            primaryStage.setTitle("Loading...");
//            primaryStage.setScene(mainMenu());
//            primaryStage.setTitle("Main Menu");
//            primaryStage.show();
//        }
    }
    public Scene mainMenu() {
        Text title = new Text("Tron.");
        title.setFont(titleFont);
        title.setTextAlignment(TextAlignment.valueOf("CENTER"));
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
        VBox optionsLayout = new VBox(10);
        VBox layout = new VBox(30);
        optionsLayout.getChildren().addAll(option1,option2,option3);
        optionsLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, optionsLayout);
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
        Rectangle selectedColorP1 = new Rectangle(812,375 - 250);
        selectedColorP1.setFill(p1);
        selectedColorP1.setY(250);
        Rectangle selectedColorP2 = new Rectangle(812,375-250);
        selectedColorP2.setFill(p2);
        selectedColorP2.setY(375);
        layout.getChildren().addAll(playerRow("player 1 :", 0), playerRow("player 2 :", 2));

        Rectangle startButton = new Rectangle(600, 125);
        startButton.setFill(Paint.valueOf("RED"));
        VBox.setMargin(startButton, new Insets(50,0,0,0));
        layout.getChildren().add(startButton);
        Text buttonGo = new Text("GO!");
        buttonGo.setFont(subTitle);
        buttonGo.setFill(Paint.valueOf("WHITE"));
        buttonGo.setX(270);
        buttonGo.setY(650);
        root.getChildren().addAll(selectedColorP1,selectedColorP2,layout, buttonGo);
        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }
    private HBox playerRow(String player, int color){
        HBox player1 = new HBox(20);
        Text pT = new Text(player);
        pT.setFont(smallFont);
        pT.setFill(Paint.valueOf("WHITE"));
        player1.getChildren().add(pT);

        VBox inputAndColor = new VBox(10);
        TextField p1NameInput = new TextField("Name");
        inputAndColor.getChildren().add(p1NameInput);

        HBox colors = new HBox(10);
        Paint[] allColors = {p1, Paint.valueOf("ORANGE"), p2, Paint.valueOf("GREEN"), Paint.valueOf("Purple")}; //p1 and p2 are only there for demo purposes
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
        player1.getChildren().add(inputAndColor);


        //make sure that Paint p1/p2 equal the selected color;
        return player1;
    }

    public Scene results(String Pwinner, Color winnerC){
        Group root = new Group();
        Rectangle colorWinner = new Rectangle(0,0,810,280);
        colorWinner.setFill(winnerC); //color of the winner
        root.getChildren().add(colorWinner);
        VBox display = new VBox(20);
        display.setLayoutX(50);
        display.setLayoutY(200);
        root.getChildren().add(display);

        Text winner = new Text(Pwinner + " Wins!");
        winner.setFont(subTitle);
        winner.setFill(Paint.valueOf("WHITE"));
        display.getChildren().add(winner);

        HBox score = scoreDisplay(1,3);
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
        options.getChildren().addAll(yes,no);
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

    public HBox scoreDisplay(int p1S, int p2S){
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
