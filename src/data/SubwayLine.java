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
    boolean circular = false;
    Line circularConnector;

    double startX;
    double startY;


    public SubwayLine(String name) {
        stations = new ArrayList<>();
        start = new LineEnd(this, name, 100, 200);
        end = new LineEnd(this, name, 110,120);
        addLineEnd(start);
        addLineEnd(end);
        setStrokeWidth(6);
        circular = false;
        circularConnector = new Line();
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

        else if (!circular && stations.indexOf(station)==0 || stations.indexOf(station)==stations.size()-1){
//                Station startStat = stations.get(0);
//                Station endStat = stations.get(stations.size()-1);
//                //circularConnector = new Line();
//                circularConnector.startXProperty().bind(startStat.centerXProperty());
//                circularConnector.startYProperty().bind(startStat.centerYProperty());
//                circularConnector.endXProperty().bind(endStat.centerXProperty());
//                circularConnector.endYProperty().bind(endStat.centerYProperty());
//                circularConnector.setStrokeWidth(6);
                circular = true;
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

        if (circular){
            Station startStat = stations.get(0);
            Station endStat = stations.get(stations.size()-1);
            circularConnector.startXProperty().bind(startStat.centerXProperty());
            circularConnector.startYProperty().bind(startStat.centerYProperty());
            circularConnector.endXProperty().bind(endStat.centerXProperty());
            circularConnector.endYProperty().bind(endStat.centerYProperty());
            circularConnector.setStrokeWidth(6);
        }

        getPoints().addAll(end.getCoordinates());
    }

    public void setColor(Color color){
        setStroke(color);
        start.setStroke(color);
        end.setStroke(color);
        circularConnector.setStroke(color);
    }

    public Color getColor(){
        return (Color) getStroke();
    }

    public void removeStation(Station station) {

        if (!circular){
            stations.remove(station);
            //getPoints().removeAll(station.getCoordinates());
            fixPoints();

        }
        if (stations.size()>0 && circular && (station.equals(stations.get(0)) || station.equals(stations.get(stations.size()-1)))){
            circular = false;
            circularConnector.setStrokeWidth(0);
            fixPoints();
        }




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

    public Line getConnectorLine() {
        return circularConnector;
    }
}