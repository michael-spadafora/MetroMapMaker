package data;

import gui.LineEnd;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

public class SubwayLine extends Polyline{
    ArrayList<Station> stations;
    LineEnd start;
    LineEnd end;

    double startX;
    double startY;
    Color color;


    public SubwayLine() {

        stations = new ArrayList<>();
        //setLayoutX(100);
        //setLayoutY(100);
        start = new LineEnd(this, "default", 100, 200);
        end = new LineEnd(this, 110,120);
//        getPoints().addAll(new Double[]{
//                0.0, 0.0,
//                20.0, 10.0,
//                10.0, 20.0 });

        addLineEnd(start);
        addLineEnd(end);


    }

    public SubwayLine(ArrayList<Line> lineSegments, ArrayList<Station> stations) {
        //this.lineSegments = lineSegments;
        this.stations = stations;
    }

    public ArrayList<Line> getLineSegments() {
      //  return lineSegments;
        return null;
    }

    public LineEnd getStart() {
        return start;
    }

    public LineEnd getEnd() {
        return end;
    }

    public void addStation(Station station){
        getPoints().addAll(station.getCoordinates());


    }

    public void addLineEnd(LineEnd line){
         getPoints().addAll(line.getCoordinates());
    }

    public void fixPoints(){

        int totItems = stations.size();
        totItems+=2;

        getPoints().remove(0,totItems*2);

        for (Station s: stations){
            getPoints().addAll(s.getCoordinates());
        }
        getPoints().addAll(start.getCoordinates());
        getPoints().addAll(end.getCoordinates());

    }
}