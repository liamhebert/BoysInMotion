
package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      primaryStage.setTitle("ur mum gay");
      Circle circle = new Circle();
      circle.setCenterX(250);
      circle.setCenterY(250);
      circle.setRadius(100);
      circle.setFill(Paint.valueOf("RED"));
      Group root = new Group(circle);
      Scene scene = new Scene (root, 500,500);
      primaryStage.setScene(scene);
      primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
