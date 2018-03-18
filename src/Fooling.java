import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.animation.ScaleTransition;
import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;

import javafx.util.Duration;
public class Fooling extends Application {
    public void start(Stage primaryStage) throws Exception{
        MoveTo start = new MoveTo(300,200);
        LineTo end = new LineTo(400,200);
        Path path = new Path();
        path.getElements().addAll(start, end);
        path.setStrokeWidth(10);
        path.setStroke(Paint.valueOf("RED"));
        PathTransition move = new PathTransition();
        move.setNode(path);
        move.setPath(path);
        move.setDuration(Duration.seconds(3));
        Group group = new Group (path);
        Scene root = new Scene(group, 600, 600);
        root.setFill(Paint.valueOf("BLACK"));
        primaryStage.setScene(root);
        primaryStage.setTitle("Hello World");
        primaryStage.show();
        LineTo test = new LineTo(350,250);
        path.getElements().add(1,test);
        Bounds limit= path.getLayoutBounds();
        Line test1 = new Line();
        test1.setStartX(370);
        test1.setStartY(350);
        test1.setEndX(370);
        test1.setEndY(100);
        test1.setStrokeWidth(10);
        test1.setStroke(Paint.valueOf("WHITE"));
        group.getChildren().add(test1);
        test1.toBack();
        Bounds limit2 = test1.getLayoutBounds();
        System.out.println(limit.intersects(limit2));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
