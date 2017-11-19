package data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

public class SubwayLine extends Polyline{
    ArrayList<Station> stations;
    double startX;
    double startY;
    Color color;


    public SubwayLine() {
        Line firstLine = new Line(300, 300, 200, 200);

        stations = new ArrayList<>();
        color = Color.BLACK;
        firstLine.setFill(color);

    }

    public SubwayLine(ArrayList<Line> lineSegments, ArrayList<Station> stations) {
        //this.lineSegments = lineSegments;
        this.stations = stations;
    }

    public ArrayList<Line> getLineSegments() {
      //  return lineSegments;
        return null;
    }
}