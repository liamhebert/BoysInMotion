
package sample;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.awt.*;
import javafx.scene.shape.Rectangle;
//WE ARE GONNA USE THESE THREE BIG TIME
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.util.Duration;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      primaryStage.setTitle("gaymom");
      Circle circ = new Circle();
      circ.setCenterY(250);
      circ.setCenterX(450);
      circ.setRadius(100);
      circ.setFill(Paint.valueOf("RED"));
      TranslateTransition translateTransition = new TranslateTransition();
      translateTransition.setNode(circ);
      translateTransition.setToY(300);
      translateTransition.setToX(200);
      translateTransition.setDuration(Duration.millis(100));
      translateTransition.setCycleCount(10);
      translateTransition.setAutoReverse(true);
      translateTransition.play();
      Group root = new Group(circ);
      Scene scene = new Scene (root, 900,500);
      scene.setFill(Paint.valueOf("WHITE"));
      primaryStage.setScene(scene);
      primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
