package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.util.ArrayList;

public class TronPaneMarch25 extends Pane{

    //attributes, also makes rectangle and animation timeline object
    private double sizeX = 5, sizeY = 5;
    private double recLocX = 50, recLocY = 50;
    private double dx = 0, dy = 1;
    private Rectangle rect = new Rectangle(recLocX, recLocY, sizeX, sizeY);

    private ArrayList<Rectangle> rectPath = new ArrayList<Rectangle>();
    private Rectangle currentTip;
    private Rectangle currentTail;

    private Timeline animation;


    // private MoveTo move = new MoveTo();
//    private LineTo line = new LineTo();
//    private Path path = new Path();

    //constructor sets up animation and creates rectangle
    public TronPaneMarch25(){
        rect.setFill(Color.BLUE);
        getChildren().add(rect);
        currentTip = rect;

        animation = new Timeline(
                new KeyFrame(Duration.millis(10), e -> movePlayer())
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void play(){
        animation.play();
    }

    public void pause(){
        animation.pause();
    }

    public void addAPixelToPath(double x, double y){

        Rectangle r = new Rectangle(x, y, 5, 5);
        r.setFill(Color.BLUE);

        if (!rectPath.contains(r))
            rectPath.add(r);
    }

    public Rectangle addAPixelToPathAndReturnRect(double x, double y){

        Rectangle r = new Rectangle(x, y, 5, 5);
        r.setFill(Color.BLUE);

        if (!rectPath.contains(r))
            rectPath.add(r);

        return r;
    }

    public void refreshPathAsChild(){
        for (int i = 0; i < rectPath.size(); i++){
            if (!getChildren().contains(rectPath.get(i))){
                getChildren().add(rectPath.get(i));
            }
        }
    }

    public void directionChange(Rectangle pos){

        currentTail = addAPixelToPathAndReturnRect(pos.getX(), pos.getY());
    }

    public void setDirection(String d){
        //going left relative
        if (d.equals("l")){

            //going right rn
            if (dx == 1 && dy == 0){
                dx = 0;
                dy = -1;
                //relative left = go up

                //going left rn
            } else if (dx == -1 && dy == 0){
                dx = 0;
                dy = 1;
                //relative left = go down

                //going down rn
            } else if (dx == 0 && dy == 1){
                dx = 1;
                dy = 0;
                //relative left = go right

                //going up rn
            } else if (dx == 0 && dy == -1){
                dx = -1;
                dy = 0;
                //relative left = go left
            }
        } else if (d.equals("r")){

            //going right rn
            if (dx == 1 && dy == 0){
                dx = 0;
                dy = 1;
                //relative right = go down
                //going left rn
            } else if (dx == -1 && dy == 0){
                dx = 0;
                dy = -1;
                //relative right = go up

                //going down rn
            } else if (dx == 0 && dy == 1){
                dx = -1;
                dy = 0;
                //relative right = go left

                //going up rn
            } else if (dx == 0 && dy == -1){
                dx = 1;
                dy = 0;
                //relative right = go right
            }
        }
    }

    public DoubleProperty rateProperty(){
        return animation.rateProperty();
    }

    protected void movePlayer(){

        if (dx == 1 || dx == -1){
            sizeX = 5;
            sizeY = 5;
        } else if (dy == 1 || dy == -1){
            sizeX = 5;
            sizeY = 5;
        }

        recLocX += dx;
        recLocY += dy;

        rect.setX(recLocX);
        rect.setY(recLocY);

        rect.setWidth(sizeX);
        rect.setHeight(sizeY);

        addAPixelToPath(recLocX, recLocY);
        refreshPathAsChild();

        //line.setY(rect.getY());
        //path.getElements().add(line);
        //getChildren().add(path);
    }
}