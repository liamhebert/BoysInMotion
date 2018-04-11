package sample;

/*
* CSCI 1101 - Final Project (Tron) - Boys In Motion
* This program uses JavaFx to have a customizable Tron game. This game is two player and uses A D and Left Right for controls
* The features of this program can be found in the report
*/

//essential javaFx imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

//animations
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//input output and handlers
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;

//layout
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

//Visual elements
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

//color
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Main extends Application {
    private static Font titleFont; //Big title screen
    private static Font subTitle; //Customize screen and credits
    private static Font popFont; //all other fonts (options and pop-ups)

    private static Color[] allColors = { //color scheme of the program
            Color.rgb(255,0,0),
            Color.valueOf("ORANGE"),
            Color.rgb(20,133,204),
            Color.rgb(178,24,95),
            Color.rgb(25,255,40) };

    // *customizable aspects*
    //score
    private static int p1S;
    private static int p2S;
    //names
    private static String p1N = "Player 1";
    private static String p2N = "Player 2";
    //colors
    private static Color p1 = allColors[0]; //default red
    private static Color p2 = allColors[1]; //default orange
    private static int gamerounds=0; //contains round limit
    // *end*

    private Stage stage;

    public void start(Stage primaryStage) throws Exception{
        //since exceptions have to be handled, have to be assigned here
        //fonts
        titleFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),160);
        subTitle = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),64);
        popFont = Font.loadFont(new FileInputStream("resources/LeagueSpartan.ttf"),30);

        primaryStage.setTitle("Loading...");
        primaryStage.setResizable(false);
        this.stage = primaryStage; //makes primary stage static

        //following block is for testing purposes only. Normally, game would start on the main menu.
        //primaryStage.setScene(customizeBike());
        //primaryStage.setScene(results());
        //primaryStage.setScene(credits());
        //primaryStage.setScene(game());

        primaryStage.setScene(mainMenu());
        primaryStage.show();
        //primaryStage.setFullScreen(true); //testing purpose only for lower res screens
    }
    public Scene mainMenu() { //this is where the game should start. Shows the main menu and options
        stage.setTitle("Main Menu");
        //title
        Text title = new Text("Tron.");
        title.setFont(titleFont);
        title.setFill(Paint.valueOf("WHITE"));

        //three options
        Text option1 = new Text("start");
        option1.setFont(popFont);
        option1.setFill(Paint.valueOf("WHITE"));
        Text option2 = new Text("credits");
        option2.setFont(popFont);
        option2.setFill(Paint.valueOf("WHITE"));
        Text option3 = new Text("quit");
        option3.setFont(popFont);
        option3.setFill(Paint.valueOf("WHITE"));

        //backgrounds for the options
        Rectangle op1 = new Rectangle(450, 70, Paint.valueOf("BLACK"));
        Rectangle op2 = new Rectangle(450, 70, Paint.valueOf("BLACK"));
        Rectangle op3 = new Rectangle(450, 70, Paint.valueOf("BLACK"));

        //condensed
        StackPane op1S = new StackPane(op1, option1); //start
        StackPane op2S = new StackPane(op2, option2); //credits
        StackPane op3S = new StackPane(op3, option3); //quit

        //each button when clicked will go to their respective screen and show a color when moused over
        //start
        op1S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when start is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(customizeBike());//go to customize screen
            }
        });
        op1S.setOnMouseEntered(new EventHandler<MouseEvent>() {//when button is moused over
            @Override
            public void handle(MouseEvent event) {op1.setFill(allColors[0]);}
        });
        op1S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {op1.setFill(Paint.valueOf("BLACK"));}
        });

        //credits
        op2S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when credits is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(credits());//go to credits scene
            }
        });
        op2S.setOnMouseEntered(new EventHandler<MouseEvent>() {//when button is moused over
            @Override
            public void handle(MouseEvent event) {op2.setFill(allColors[1]);}
        });
        op2S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {op2.setFill(Paint.valueOf("BLACK")); }
        });

        //quit
        op3S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when quit is clicked
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();//quit
            }
        });
        op3S.setOnMouseEntered(new EventHandler<MouseEvent>() {//When button is moused over
            @Override
            public void handle(MouseEvent event) {op3.setFill(allColors[2]);}
        });
        op3S.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {op3.setFill(Paint.valueOf("BLACK"));}
        });


        VBox optionsLayout = new VBox(5); //contains the options
        optionsLayout.getChildren().addAll(op1S, op2S, op3S);

        //overall layout
        VBox layout = new VBox(15);
        layout.getChildren().addAll(title, optionsLayout);
        layout.setAlignment(Pos.CENTER); //centers all the text
        layout.setLayoutX(180);
        layout.setLayoutY(150);

        Group root = new Group(layout);
        return new Scene (root, 800,800, Paint.valueOf("BLACK"));
    }

    //used for the sliding animation
    private static int p1Ticks = 0;
    private static int p2Ticks = 0;
    public Scene customizeBike() { //displays all the options to customize the bikes, pre-game start
        Group root = new Group();
        VBox layout = new VBox(40); //contains the general layout of the scene
        layout.setLayoutY(150);
        layout.setLayoutX(50);

        Text title = new Text("Customize Bikes.");
        title.setFont(subTitle);
        title.setFill(Paint.valueOf("WHITE"));
        layout.getChildren().add(title);


        //background for the colors
        Rectangle selectedColorP1 = new Rectangle(812,125);
        selectedColorP1.setFill(p1);
        selectedColorP1.setY(250);
        Rectangle selectedColorP2 = new Rectangle(812,125);
        selectedColorP2.setFill(p2);
        selectedColorP2.setY(375);

        //animation for when colors are selected
        //p1
        Group p1WaveAnimation = new Group();
        Timeline selectP1 = new Timeline(new KeyFrame(Duration.millis(20), e -> wave(1, p1WaveAnimation)));
        selectP1.setCycleCount(32);
        selectP1.setAutoReverse(false);
        selectP1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p1Ticks = 0;
                selectedColorP1.setFill(p1); //when wave is done, replace the background
                p1WaveAnimation.getChildren().clear(); //and clear the wave
            }
        });

        //p2
        Group p2WaveAnimation = new Group();
        Timeline selectP2 = new Timeline(new KeyFrame(Duration.millis(20), e -> wave(2, p2WaveAnimation)));
        selectP2.setCycleCount(32);
        selectP2.setAutoReverse(false);
        selectP2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p2Ticks = 0;
                selectedColorP2.setFill(p2);
                p2WaveAnimation.getChildren().clear();
            }
        });

        // *player 1 options*
        HBox player1 = new HBox(20); //contains all the options
        Text p1pT = new Text("player 1 :");
        p1pT.setFont(popFont);
        p1pT.setFill(Paint.valueOf("WHITE"));
        player1.getChildren().add(p1pT);

        VBox inputAndColor1 = new VBox(10); //contains the name input and color options
        HBox.setMargin(inputAndColor1, new Insets(0,0,0,10)); //spacing

        //text input
        TextField p1NameInput = new TextField("Enter Name and Press Enter");
        p1NameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                p1N = p1NameInput.getText(); //sets the player name
            }
        });
        inputAndColor1.getChildren().add(p1NameInput);

        // * color options start *
        //contains the color options
        HBox p1colors = new HBox(10);

        //red
        Rectangle p1color1 = new Rectangle(50,50);
        p1color1.setFill(allColors[0]);
        p1color1.setOnMouseClicked(e -> {
            if(!p2.equals(allColors[0])) {
                p1 = allColors[0];
                selectP1.play();
            }
        });

        //orange
        Rectangle p1color2 = new Rectangle(50,50);
        p1color2.setFill(allColors[1]);
        p1color2.setOnMouseClicked(e -> {
            if(!p2.equals(allColors[1])) {
                p1 = allColors[1];
                selectP1.play();
            }
        });

        //blue
        Rectangle p1color3 = new Rectangle(50,50);
        p1color3.setFill(allColors[2]);
        p1color3.setOnMouseClicked(e -> {
            if(!p2.equals(allColors[2])) {
                p1 = allColors[2];
                selectP1.play();
            }
        });

        //purple
        Rectangle p1color4 = new Rectangle(50, 50);
        p1color4.setFill(allColors[3]);
        p1color4.setOnMouseClicked(e -> {
            if(!p2.equals(allColors[3])) {
                p1 = allColors[3];
                selectP1.play();
            }
        });

        //green
        Rectangle p1color5 = new Rectangle(50, 50);
        p1color5.setFill(allColors[4]);
        p1color5.setOnMouseClicked(e -> {
            if(!p2.equals(allColors[4])) {
                p1 = allColors[4];
                selectP1.play();
            }
        });
        // *color options end*

        //adding all the elements together
        p1colors.getChildren().addAll(p1color1,p1color2,p1color3,p1color4,p1color5);
        inputAndColor1.getChildren().add(p1colors);
        player1.getChildren().add(inputAndColor1);
        // *end player 1 options*

        // *player 2 options*
        HBox player2 = new HBox(20); //contains all the options
        Text p2pT = new Text("player 2 :");
        p2pT.setFont(popFont);
        p2pT.setFill(Paint.valueOf("WHITE"));
        player2.getChildren().add(p2pT);

        VBox inputAndColor2 = new VBox(10); //contains the name input and color options

        //text input
        TextField p2NameInput = new TextField("Enter Name and Press Enter");
        inputAndColor2.getChildren().add(p2NameInput);
        p2NameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                p2N = p2NameInput.getText();
            }
        });

        // * color options start *
        //contains the color options
        HBox p2colors = new HBox(10);

        //red
        Rectangle p2color1 = new Rectangle(50,50);
        p2color1.setFill(allColors[0]);
        p2color1.setOnMouseClicked(e -> {
            if(!p1.equals(allColors[0])) {
                p2 = allColors[0];
                selectP2.play();
            }
        });

        //orange
        Rectangle p2color2 = new Rectangle(50,50);
        p2color2.setFill(allColors[1]);
        p2color2.setOnMouseClicked(e -> {
            if(!p1.equals(allColors[1])) {
                p2 = allColors[1];
                selectP2.play();
            }
        });

        //blue
        Rectangle p2color3 = new Rectangle(50,50);
        p2color3.setFill(allColors[2]);
        p2color3.setOnMouseClicked(e -> {
            if(!p1.equals(allColors[2])) {
                p2 = allColors[2];
                selectP2.play();
            }
        });

        //purple
        Rectangle p2color4 = new Rectangle(50, 50);
        p2color4.setFill(allColors[3]);
        p2color4.setOnMouseClicked(e -> {
            if(!p1.equals(allColors[3])) {
                p2 = allColors[3];
                selectP2.play();
            }
        });

        //green
        Rectangle p2color5 = new Rectangle(50, 50);
        p2color5.setFill(allColors[4]);
        p2color5.setOnMouseClicked(e -> {
            if(!p1.equals(allColors[4])) {
                p2 = allColors[4];
                selectP2.play();
            }
        });
        // *color options end*

        //adding all the elements together
        p2colors.getChildren().addAll(p2color1,p2color2,p2color3,p2color4,p2color5);
        inputAndColor2.getChildren().add(p2colors);
        player2.getChildren().add(inputAndColor2);
        layout.getChildren().addAll(player1, player2);
        // *end player 2 options*

        // *round selection*
        HBox roundSelection = new HBox(15); //contains round options
        layout.getChildren().add(3,roundSelection);

        Text roundText = new Text("rounds:");
        roundText.setFont(popFont);
        roundText.setFill(Paint.valueOf("WHITE"));
        HBox.setMargin(roundText, new Insets(10,0,0,0));
        roundSelection.getChildren().add(roundText);

        // *boxes for selection*
        HBox rects = new HBox(10); //contains boxes

        Text option1T = new Text("1");
        Text option3T = new Text("3"); //text options
        Text option5T = new Text("5");
        Text option7T = new Text("7");
        StackPane.setMargin(option1T, new Insets(5,0,0,0));
        StackPane.setMargin(option3T, new Insets(5,0,0,0)); //adjustments
        StackPane.setMargin(option5T, new Insets(5,0,0,0));
        StackPane.setMargin(option7T, new Insets(5,0,0,0));
        Rectangle option1 = new Rectangle(50,50, Paint.valueOf("RED"));
        Rectangle option2 = new Rectangle(50,50, Paint.valueOf("RED")); //backgrounds
        Rectangle option3 = new Rectangle(50,50, Paint.valueOf("RED"));
        Rectangle option4 = new Rectangle(50,50, Paint.valueOf("RED"));
        StackPane option1S = new StackPane(option1, option1T);
        StackPane option3S = new StackPane(option2, option3T); //buttons
        StackPane option5S = new StackPane(option3, option5T);
        StackPane option7S = new StackPane(option4, option7T);
        option1T.setFont(popFont);
        option3T.setFont(popFont); //fonts of the buttons
        option5T.setFont(popFont);
        option7T.setFont(popFont);
        option1T.setFill(Color.valueOf("WHITE"));
        option3T.setFill(Color.valueOf("WHITE")); //color of text
        option5T.setFill(Color.valueOf("WHITE"));
        option7T.setFill(Color.valueOf("WHITE"));
        //* end round select boxes */

        //go button
        Text buttonGo = new Text("GO!");
        buttonGo.setFont(subTitle);
        buttonGo.setFill(Paint.valueOf("WHITE"));
        Rectangle startButton = new Rectangle(600, 125);
        startButton.setFill(Paint.valueOf("RED"));
        StackPane start = new StackPane(startButton, buttonGo);
        StackPane.setMargin(buttonGo, new Insets(10,0,0,0));
        layout.getChildren().add(start);
        start.setVisible(false); //hide button until option is pressed

        //on click for buttons, also sets the gamerounds
        option1S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("GREEN"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds=1;
            start.setVisible(true);
        });
        option3S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("GREEN"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds=3;
            start.setVisible(true);
        });
        option5S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("GREEN"));
            option4.setFill(Paint.valueOf("RED"));
            gamerounds=5;
            start.setVisible(true);
        });
        option7S.setOnMouseClicked(e -> {
            option1.setFill(Paint.valueOf("RED"));
            option2.setFill(Paint.valueOf("RED"));
            option3.setFill(Paint.valueOf("RED"));
            option4.setFill(Paint.valueOf("GREEN"));
            gamerounds=7;
            start.setVisible(true);
        });
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(game());
            }
        });

        //adding elements and spacing
        rects.getChildren().addAll(option1S, option3S, option5S, option7S);
        HBox.setMargin(rects, new Insets(0,0,0,54));
        roundSelection.getChildren().add(rects);
        root.getChildren().addAll(selectedColorP1,selectedColorP2,layout);
        root.getChildren().add(2, p1WaveAnimation);
        root.getChildren().add(2, p2WaveAnimation);

        return new Scene(root, 800, 800, Paint.valueOf("BLACK"));
    }

    private static void wave(int player, Group container){ //animation for when colors are selected
        int startY; //vertical start position
        Color tempC; //color of the wave
        int ticks; //where to print the square
        if (player == 1) {
            startY = 250;
            tempC = p1; //this would be the static value, just testing
            ticks = p1Ticks;
        }
        else { //player 2
            startY = 375;
            tempC = p2;
            ticks = p2Ticks;
        }

        Rectangle temp = new Rectangle(ticks * 25, startY, 25, 125); //gradually increasing rectangle wave
        temp.setFill(tempC);
        container.getChildren().add(temp); //adds the wave rectangles to the scene

        if (player == 1)
            p1Ticks++;
        else //p2
            p2Ticks++;
    }

    public Scene results(){ //presents the result of the game with colors
        Group root = new Group();
        VBox display = new VBox(20); //contains everything
        display.setLayoutX(50);
        display.setLayoutY(200);
        root.getChildren().add(display);

        Rectangle colorWinner = new Rectangle(0,0,810,280); //little bigger then the bounds since the
        root.getChildren().add(0,  colorWinner);


        Color winnerC; //color of the top square
        Color loserC; //color of the buttons
        Text winner = new Text(); //who wins
        winner.setFont(subTitle);
        winner.setFill(Paint.valueOf("WHITE"));

        //when no name is chosen
//        if (p1N.equals(""))
//            p1N = "Player 1";
//        if (p2N.equals(""))
//            p2N = "Player 2";

        //determines which color and name to show
        if (p1S > p2S) {
            winner.setText(p1N + " Wins!");
            winnerC = p1;
            loserC = p2;
        }
        else {
            winner.setText(p2N + " Wins!");
            winnerC = p2;
            loserC = p1;
        }
        colorWinner.setFill(winnerC);
        display.getChildren().add(winner);

        HBox score = scoreDisplay(); //displays the scores
        display.getChildren().add(score);

        //play again button
        Text playAgain = new Text("Play again?");
        playAgain.setFont(popFont);
        playAgain.setFill(Paint.valueOf("WHITE"));
        VBox.setMargin(playAgain, new Insets(50,0,0,0));
        display.getChildren().add(playAgain);

        //yes and no buttons
        HBox options = new HBox(30);
        Text yes = new Text("yes");
        yes.setFont(popFont);
        yes.setFill(Paint.valueOf("WHITE"));
        Text no = new Text("no");
        no.setFont(popFont);
        no.setFill(Paint.valueOf("WHITE"));
        Rectangle option1 = new Rectangle(80, 50, loserC); //background for buttons
        Rectangle option2 = new Rectangle(80,50, loserC);
        StackPane op1S = new StackPane(option1, yes); //clickable buttons
        StackPane op2S = new StackPane(option2,no);

        //click handlers for the buttons
        op1S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when yes is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(customizeBike());//go to customize screen
            }
        });
        op2S.setOnMouseClicked(new EventHandler<MouseEvent>() {//when no is clicked
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(credits()); //credits screen
            }
        });

        //adding elements
        options.getChildren().addAll(op1S, op2S);
        display.getChildren().add(options);

        return new Scene(root, 800,800, Paint.valueOf("BLACK"));
    }

    public Scene credits(){ //displays who made the project
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

        Text fontUsed = new Text("Font: League Spartan - The League of Moveable Type");
        fontUsed.setFill(Paint.valueOf("WHITE"));
        fontUsed.setFont(popFont);

        //adding elements
        layout.getChildren().addAll(thanksTitle, theBoys,fontUsed);
        root.getChildren().add(layout);
        return new Scene(root,1200,800,Paint.valueOf("BLACK"));
    }

    public Scene game(){ //heres where the action happens. Contains the game and access to TronPane
        Group root = new Group();//contains tron pane
        TronPane tronPane = new TronPane(popFont,p1N,p2N,p1,p2, gamerounds); //passes customizable elements to tronPane
        root.getChildren().add(tronPane);

        Scene display = new Scene(root, 800, 800, Paint.valueOf("BLACK")); //scene needs to be built before adding keyinputs
        display.setOnKeyReleased(e -> { //controls the direction of the lines and next round
            //directions
            if (!tronPane.isPopUpOnScreen()) { //no popups
                if (e.getCode() == KeyCode.A)  //go left p1
                    tronPane.setDirectionPlayerOne("l");
                else if (e.getCode() == KeyCode.D) //go right p1
                    tronPane.setDirectionPlayerOne("r");
                else if (e.getCode() == KeyCode.LEFT)//go left p2
                    tronPane.setDirectionPlayerTwo("l");
                else if (e.getCode() == KeyCode.RIGHT) //go right p2
                    tronPane.setDirectionPlayerTwo("r");
            }

            else if (e.getCode() == KeyCode.SPACE){
                if (tronPane.isPopUpOnScreen()){
                    if (tronPane.isGameOver()) {
                        root.getChildren().remove(tronPane);
                        p1S = tronPane.getP1S(); //gets the score of the game
                        p2S = tronPane.getP2S();
                        stage.setScene(results()); //ends game
                    }
                    else
                        tronPane.resetGame(); //count down and resets game
                }
                //else, ignore. no reason for space bar you silly goof
            }
        });
        return display;
    }

    public HBox scoreDisplay(){ //displays score
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
