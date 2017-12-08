package gui;

import data.Station;
import data.SubwayLine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FindRouteController {
     public void findRoute(Station start, Station destination, ArrayList<Station> path){
         ArrayList<Station> newPath = new ArrayList<>();
         for (Station s: path){
             newPath.add(s);
         }
         newPath.add(start);
         if (start.getPath().size() == 0 || start.getPath().size() > newPath.size()) {
             start.setPath(path);
         }
         else {
             return;
         }



         if (start.equals(destination)){
             return;
         }

         ArrayList<Station> adjacentStations = new ArrayList<>();
         int startIndex;
         for (SubwayLine line:start.getSubwayLines()){
             startIndex = line.getStations().indexOf(start);
             if (startIndex > 0){
                 adjacentStations.add(line.getStations().get(startIndex-1));
             }
             if (startIndex<line.getStations().size()-2){
                 adjacentStations.add(line.getStations().get(startIndex+1));
             }
         }

         for (Station stat: adjacentStations){
             findRoute(stat, destination, newPath);
         }

         return;


     }
}
