package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.animation.ScaleTransition;
import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Line;
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
    public void start(Stage primaryStage) throws Exception{
        titleFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),106.66667 * 1.5);
        subTitle = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),42.6666667 * 1.5);
        primaryStage.setTitle("Loading...");
        primaryStage.setScene(customizeBike());
        primaryStage.setTitle("customizeBike");
        primaryStage.show();
        Thread.sleep(1000);
        int input = 0;
        System.out.println("1 : Customise bikes, 2: Game Screen, 3: Main, 4: quit");
        Scanner kb = new Scanner(System.in);
        input = kb.nextInt();
        if (input == 1) {
            primaryStage.setTitle("Loading...");
            primaryStage.setScene(customizeBike());
            primaryStage.setTitle("Customize Bike");
            primaryStage.show();
        }
        else if (input == 2) {
            primaryStage.setTitle("Loading...");
            //primaryStage.setScene(game());
            primaryStage.setTitle("Game");
            primaryStage.show();
        }
        else if (input == 3) {
            primaryStage.setTitle("Loading...");
            primaryStage.setScene(mainMenu());
            primaryStage.setTitle("Main Menu");
            primaryStage.show();
        }
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
        Scene mainMenu = new Scene (root,800,800);
        mainMenu.setFill(Paint.valueOf("BLACK"));
        return mainMenu;
    }

    public Scene customizeBike() {
        VBox layout = new VBox(40);
        layout.setLayoutY(150);
        layout.setLayoutX(50);
        Text title = new Text("Customize Bikes.");
        title.setFont(subTitle);
        title.setFill(Paint.valueOf("WHITE"));
        layout.getChildren().add(title);

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
        Group root = new Group(layout, buttonGo);
        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }
    private HBox playerRow(String player, int color){
        HBox player1 = new HBox(20);
        Text p1 = new Text(player);
        p1.setFont(smallFont);
        p1.setFill(Paint.valueOf("WHITE"));
        player1.getChildren().add(p1);

        VBox inputAndColor = new VBox(10);
        TextField p1NameInput = new TextField("Name");
        inputAndColor.getChildren().add(p1NameInput);

        HBox colors = new HBox(10);
        Paint[] allColors = {Paint.valueOf("RED"), Paint.valueOf("ORANGE"), Paint.valueOf("BLUE"), Paint.valueOf("GREEN"), Paint.valueOf("Purple")};
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

        Rectangle selectedColor = new Rectangle(85,85);
        selectedColor.setFill(allColors[color]);
        player1.getChildren().add(selectedColor);

        HBox.setMargin(selectedColor, new Insets(0,0,0,20));
        return player1;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
