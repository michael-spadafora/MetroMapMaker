package gui;

import data.DraggableElement;
import data.SubwayLine;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class LineEnd extends Circle implements DraggableElement{

    Text label;
    private double startCenterX;
    private double startCenterY;
    SubwayLine sl;


    public LineEnd(){
        label = new Text ("default");
        label.setX(200+10);
        label.setY(300+10);
        this.setCenterX(200);
        this.setCenterY(300);
        this.setRadius(4);
    }

    public Double[] getCoordinates() {
        return new Double[]{this.getCenterX(), this.getCenterY()};
    }

    public Text getLabel() {
        return label;
    }

    public LineEnd(String name){
        label = new Text (name);
        label.setX(200+10);
        label.setY(300+10);
        this.setCenterX(200);
        this.setCenterY(300);
        this.setRadius(6);

    }

    public LineEnd(SubwayLine sl, String name, double x, double y){
        this.sl = sl;
        label = new Text (name);
        label.setX(x+10);
        label.setY(y+10);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(6);
    }

    public LineEnd(SubwayLine sl, double x, double y){
        this.sl = sl;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(6);
    }


    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;
    }

    @Override
    public void drag(int x, int y) {
        double diffX = x - startCenterX; //this is the current cursor position minus the start position
        double diffY = y - startCenterY;
        double newX = getCenterX() + diffX;
        double newY = getCenterY() + diffY;
        setCenterX(newX);
        setCenterY(newY);
        double[] newLocs = getNewLabelLocation(newX, newY);
        if (label!= null){
        label.setX(newLocs[0]);
        label.setY(newLocs[1]);}
        startCenterX = x;
        startCenterY = y;
        getSubwayLine().fixPoints();
    }

    private double[] getNewLabelLocation(double newX, double newY) {
        double x,  y;
        x = newX+10;
        y = newY+10;
        return new double[]{x,y};

    }

    @Override
    public double getX() {
        return getCenterX() - getRadius();
    }

    @Override
    public double getY() {
        return getCenterY() - getRadius();
    }

    @Override
    public double getWidth() {
        return getRadius() * 2;
    }

    @Override
    public double getHeight() {
        return getRadius() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        setCenterX(initX + (initWidth/2));
        setCenterY(initY + (initHeight/2));
        setRadius(initWidth/2);

    }

    @Override
    public String getElementType() {
        return LINE_END;
    }

    public SubwayLine getSubwayLine() {
        return sl;
    }
}

