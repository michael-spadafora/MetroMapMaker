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

    public SubwayLine() {
        stations = new ArrayList<>();
        start = new LineEnd(this, "default", 100, 200);
        end = new LineEnd(this, 110,120);
        addLineEnd(start);
        addLineEnd(end);
        setStrokeWidth(6);
    }

    public SubwayLine(String name) {
        stations = new ArrayList<>();
        start = new LineEnd(this, name, 100, 200);
        end = new LineEnd(this, name, 110,120);
        addLineEnd(start);
        addLineEnd(end);
        setStrokeWidth(6);
    }

    public String getName(){
        return start.getLabel().getText();
    }

    public SubwayLine(ArrayList<Line> lineSegments, ArrayList<Station> stations) {
        //this.lineSegments = lineSegments;
        this.stations = stations;
    }



    public void setName(String s){
        start.getLabel().setText(s);
        end.getLabel().setText(s);
    }

    public LineEnd getStart() {
        return start;
    }

    public LineEnd getEnd() {
        return end;
    }

    public void addStation(Station station){
        if (!stations.contains(station)){
            int index = 0;
            if (stations.size() >= 0){
             index = getClosestStationIndex(station) + 1 ;}
            stations.add(index, station);
            getPoints().addAll(station.getCoordinates());
            fixPoints();
        }


    }

    public void addLineEnd(LineEnd line){
         getPoints().addAll(line.getCoordinates());
    }

    public void fixPoints(){

        getPoints().clear();

        getPoints().addAll(start.getCoordinates());

        for (Station s: stations){
            getPoints().addAll(s.getCoordinates());
        }

        getPoints().addAll(end.getCoordinates());
    }

    public void setColor(Color color){
        setStroke(color);
        start.setStroke(color);
        end.setStroke(color);
    }

    public Color getColor(){
        return (Color) getStroke();
    }

    public void removeStation(Station station) {
        stations.remove(station);
        getPoints().removeAll(station.getCoordinates());
        fixPoints();

    }

    public ArrayList<Station> getStations() {
        return stations;
    }


    public int getClosestStationIndex(Station newS){
        int currIndex = 0;
        double minDistance = 99999;
        int i = 0;

        for (Station s: stations){
            double xDistance = Math.pow(newS.getX() - s.getX(),2);
            double yDistance = Math.pow(newS.getY() - s.getY(),2);
            double totDistance = xDistance+yDistance;
            if (totDistance<=minDistance){
                currIndex = i;
                minDistance = totDistance;
            }
            i++;
        }

        double xDistance = Math.pow(newS.getX() - start.getX(),2);
        double yDistance = Math.pow(newS.getY() - start.getY(),2);
        double totDistance = xDistance+yDistance;
        if (totDistance<=minDistance){
            currIndex = -1;
            minDistance = totDistance;
        }



         xDistance = Math.pow(newS.getX() - end.getX(),2);
         yDistance = Math.pow(newS.getY() - end.getY(),2);
         totDistance = xDistance+yDistance;
        if (totDistance<=minDistance){
            currIndex = stations.size()-1;
            minDistance = totDistance;
        }
        return currIndex;

    }
}