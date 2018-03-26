package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

public class TronScreenController extends Application{

    private TronPaneMarch25 tronPane = new TronPaneMarch25();

    @Override
    public void start(Stage primaryStage){

        Scene scene = new Scene(tronPane, 800, 800);
        scene.setOnMousePressed(this::processMousePressed);
        scene.setOnMouseReleased(this::processMouseReleased);
        scene.setOnKeyPressed(this::processKeyPress);

        primaryStage.setTitle("TronDemo");
        primaryStage.setScene(scene);
        primaryStage.show();

        tronPane.requestFocus();
    }

    public void processMousePressed(MouseEvent e){
        tronPane.pause();
    }

    public void processMouseReleased(MouseEvent e){
        tronPane.play();
    }

    public void processKeyPress(KeyEvent e){

        if (e.getCode() == KeyCode.LEFT){
            tronPane.setDirection("l");
        } else if (e.getCode() == KeyCode.RIGHT){
            tronPane.setDirection("r");
        }

    }

    public static void main(String[] args){
        launch(args);
    }



}
