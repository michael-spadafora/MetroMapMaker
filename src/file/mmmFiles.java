package file;

import data.DraggableElement;
import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.sound.sampled.Line;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mmmFiles implements AppFileComponent {
    //static final String JSON_TYPE = "type";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_TYPE = "type";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_X_START = "x_start";
    static final String JSON_Y_START = "y_start";
    static final String JSON_X_END = "x_end";
    static final String JSON_Y_END = "y_end";
    static final String JSON_RAD = "radius";
    static final String JSON_STATION_NAME = "station_name";
    static final String JSON_LINE_NAME = "line_name";
    static final String JSON_FILL_COLOR = "fill_color";
    static final String JSON_IS_STATION = "station";
    static final String JSON_IS_LINE = "subway_line";
    static final String JSON_COLOR = "color";
    static final String JSON_LINE_WIDTH = "line_width";
    static final String JSON_LINES = "lines";
    static final String JSON_STATIONS = "stations";
    static final String JSON_ELEMENTS = "elements";


    @Override
    public void saveData(AppDataComponent appDataComponent, String filePath) throws IOException {
        mmmData dataManager = (mmmData) appDataComponent;

        // FIRST THE BACKGROUND COLOR
        //Color bgColor = dataManager.getBackgroundColor();
        //JsonObject bgColorJson = makeJsonColorObject(bgColor);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> elements = dataManager.getElements();

        for (Node node : elements) {
            if (node instanceof SubwayLine) {
                JsonObject subwayJson = createSubwayLineJson(node);
                arrayBuilder.add(subwayJson);
            } else if (node instanceof Station) {
                JsonObject stationJson = createStationJson(node);
                arrayBuilder.add(stationJson);
            }

//            else if (node instanceof DraggableImage){
//        }
        }



        JsonArray array = arrayBuilder.build();

        JsonObject finalProduct = Json.createObjectBuilder()
                .add(JSON_ELEMENTS,array)
                .build();


        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(finalProduct);
        jsonWriter.close();


        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(finalProduct);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();


    }



    private JsonObject createStationJson(Node node) {


        Station station = (Station) node;
        String type = JSON_IS_STATION;
        double x = station.getX();
        double y = station.getY();
        double radius = station.getRadius();
        String name = station.getLabel().getText();
        ArrayList<SubwayLine> lines = station.getSubwayLines();

        JsonObject statColor = makeJsonColorObject((Color) station.getFill());

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (SubwayLine line: lines){
            JsonObject lineJson = Json.createObjectBuilder()
                    .add(JSON_LINE_NAME, line.getName())
                    .build();
            arrayBuilder.add(lineJson);
        }

        JsonArray linesJson = arrayBuilder.build();

        JsonObject stationJson = Json.createObjectBuilder()
                .add(JSON_TYPE, type)
                .add(JSON_STATION_NAME, name)
                .add(JSON_X, x)
                .add(JSON_Y, y)
                .add(JSON_RAD, radius)
                .add(JSON_COLOR, statColor)
                .add(JSON_LINES, linesJson)
                .build();

        return stationJson;
    }

    private JsonObject createSubwayLineJson(Node node) {
        SubwayLine subwayLine = (SubwayLine) node;

        String type = JSON_IS_LINE;

        String name = subwayLine.getName();
        double xStart = subwayLine.getStart().getX();
        double yStart = subwayLine.getStart().getY();

        double xEnd = subwayLine.getEnd().getX();
        double yEnd = subwayLine.getEnd().getY();

        Color color = subwayLine.getColor();
        double strokeWidth = subwayLine.getStrokeWidth();


        ArrayList<Station> stations = subwayLine.getStations();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();


        for (Station stat: stations){
            JsonObject stationJson = Json.createObjectBuilder()
                    .add(JSON_STATION_NAME, stat.getLabel().getText())
                    .build();
            arrayBuilder.add(stationJson);
        }

        JsonArray stationsJson = arrayBuilder.build();

        JsonObject colorJson = makeJsonColorObject(subwayLine.getColor());


        JsonObject shapeJson = Json.createObjectBuilder()
                .add(JSON_TYPE, type)
                .add(JSON_X_START, xStart)
                .add(JSON_Y_START, yStart)
                .add(JSON_X_END, xEnd)
                .add(JSON_Y_END, yEnd)
                .add(JSON_COLOR, colorJson)
                .add(JSON_LINE_WIDTH, strokeWidth)
                .add(JSON_STATIONS, stationsJson)
                .build();

        return shapeJson;

    }

    private JsonObject makeJsonColorObject(Color color) {
        JsonObject colorJson = Json.createObjectBuilder()
                .add(JSON_RED, color.getRed())
                .add(JSON_GREEN, color.getGreen())
                .add(JSON_BLUE, color.getBlue())
                .add(JSON_ALPHA, color.getOpacity()).build();
        return colorJson;
    }

    @Override
    public void loadData(AppDataComponent appDataComponent, String s) throws IOException {

    }

    @Override
    public void exportData(AppDataComponent appDataComponent, String s) throws IOException {

    }

    @Override
    public void importData(AppDataComponent appDataComponent, String s) throws IOException {

    }
}
