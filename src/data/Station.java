package data;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import javafx.scene.text.Text;
public class Station extends Circle implements DraggableElement {
    ArrayList<SubwayLine> subwayLines;
    Text label;
    private double startCenterX;
    private double startCenterY;


    public Station(){
        label = new Text ("default");
        label.setX(200+10);
        label.setY(300+10);
        subwayLines = new ArrayList<SubwayLine>();
        this.setCenterX(200);
        this.setCenterY(300);
        this.setRadius(10);

    }

    public Text getLabel() {
        return label;
    }

    public Station(String name){

        subwayLines = new ArrayList<SubwayLine>();
        this.setCenterX(200);
        this.setCenterY(300);
        this.setRadius(10);

    }

    public void addSubwayLine(SubwayLine subLine){
        subwayLines.add(subLine);
    }

    public void removeSubwayLine(SubwayLine subLine){
        subwayLines.remove(subLine);
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
        label.setX(newX+10);
        label.setY(newY+10);
        startCenterX = x;
        startCenterY = y;
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
        return STATION;
    }
}
