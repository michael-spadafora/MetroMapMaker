package file;

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
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mmmFiles implements AppFileComponent {
    static final String JSON_NAME = "name";


    static final String JSON_LINES = "lines";
    static final String JSON_TYPE = "type";
   // static final String JSON_NAME = "name"
    static final String JSON_CIRCULAR = "circular";
    static final String JSON_COLOR = "color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_STATIONS_ON_LINE = "station_names";

    static final String JSON_STATIONS = "stations";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";

    // static final String JSON_NAME = "name"

    //static final String JSON_TYPE = "type";




    static final String JSON_X_START = "x_start";
    static final String JSON_Y_START = "y_start";
    static final String JSON_X_END = "x_end";
    static final String JSON_Y_END = "y_end";

    //static final String JSON_RAD = "radius";

    //static final String JSON_FILL_COLOR = "fill_color";
    static final String JSON_IS_STATION = "station";
    static final String JSON_IS_LINE = "subway_line";

    static final String JSON_LINE_WIDTH = "line_width";


    static final String JSON_ELEMENTS = "elements";


    @Override
    public void saveData(AppDataComponent appDataComponent, String filePath) throws IOException {
        mmmData dataManager = (mmmData) appDataComponent;

        // FIRST THE BACKGROUND COLOR
        //Color bgColor = dataManager.getBackgroundColor();
        //JsonObject bgColorJson = makeJsonColorObject(bgColor);
        JsonArrayBuilder lineArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder stationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> elements = dataManager.getElements();

        for (Node node : elements) {
            if (node instanceof SubwayLine) {
                JsonObject subwayJson = createSubwayLineJson(node);
                lineArrayBuilder.add(subwayJson);
            } else if (node instanceof Station) {
                JsonObject stationJson = createStationJson(node);
                stationArrayBuilder.add(stationJson);
            }

//            else if (node instanceof DraggableImage){
//        }
        }



        JsonArray lineArray = lineArrayBuilder.build();
        JsonArray stationArray = stationArrayBuilder.build();

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
                    .add(JSON_NAME, line.getName())
                    .build();
            arrayBuilder.add(lineJson);
        }

        JsonArray linesJson = arrayBuilder.build();

        JsonObject stationJson = Json.createObjectBuilder()
                .add(JSON_TYPE, type)
                .add(JSON_NAME, name)
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
        boolean circular = subwayLine.isCircular();
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
                    .add(JSON_NAME, stat.getLabel().getText())
                    .build();
            arrayBuilder.add(stationJson);
        }

        JsonArray stationsJson = arrayBuilder.build();

        JsonObject colorJson = makeJsonColorObject(subwayLine.getColor());


        JsonObject shapeJson = Json.createObjectBuilder()
                .add(JSON_TYPE, type)
                .add(JSON_IS_STATION, circular)
                .add(JSON_X_START, xStart)
                .add(JSON_Y_START, yStart)
                .add(JSON_X_END, xEnd)
                .add(JSON_Y_END, yEnd)
                .add(JSON_COLOR, colorJson)
                .add(JSON_LINE_WIDTH, strokeWidth)
                .add(JSON_STATIONS_ON_LINE, stationsJson)
                .build();

        return shapeJson;

    }

    private JsonObject makeJsonColorObject(Color color) {
        JsonObject colorJson = Json.createObjectBuilder()
                .add(JSON_RED, color.getRed())
                .add(JSON_GREEN, color.getGreen())
                .add(JSON_BLUE, color.getBlue())
                .add(JSON_ALPHA, color.getOpacity())
                .build();
        return colorJson;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {

        mmmData dataManager = (mmmData) data;
        dataManager.resetData();

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE BACKGROUND COLOR
        //Color bgColor = loadColor(json, JSON_BG_COLOR);
        //dataManager.setBackgroundColor(bgColor);

        // AND NOW LOAD ALL THE SHAPES
        JsonArray jsonShapeArray = json.getJsonArray(JSON_ELEMENTS);
        for (int i = 0; i < jsonShapeArray.size(); i++) {
            JsonObject jsonNode = jsonShapeArray.getJsonObject(i);
            Node node = loadNode(jsonNode);
            //Shape shape = loadShape(jsonShape);
            //dataManager.addElement(shape);
        }

    }

    private Node loadNode(JsonObject jsonNode) {
        String type = jsonNode.getString(JSON_TYPE);
        Node returnedNode = null;

        switch (type){
            case JSON_IS_STATION:
                returnedNode = loadStation(jsonNode);
                break;
            case JSON_IS_LINE:
                returnedNode = loadLine(jsonNode);
                break;

        }

        return returnedNode;
    }

    private Node loadLine(JsonObject jsonNode) {
        SubwayLine line = new SubwayLine(jsonNode.getString(JSON_NAME));

        line.getStart().setCenterX(getDataAsDouble(jsonNode, JSON_X_START));
        line.getStart().setCenterY(getDataAsDouble(jsonNode, JSON_Y_START));
        line.getEnd().setCenterX(getDataAsDouble(jsonNode, JSON_X_END));
        line.getEnd().setCenterY(getDataAsDouble(jsonNode, JSON_Y_END));


    }

    private Node loadStation(JsonObject jsonNode) {
    }

    @Override
    public void exportData(AppDataComponent appDataComponent, String s) throws IOException {

    }

    @Override
    public void importData(AppDataComponent appDataComponent, String s) throws IOException {

    }

    private double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    private Color loadColor(JsonObject json, String colorToGet) {
        JsonObject jsonColor = json.getJsonObject(colorToGet);
        double red = getDataAsDouble(jsonColor, JSON_RED);
        double green = getDataAsDouble(jsonColor, JSON_GREEN);
        double blue = getDataAsDouble(jsonColor, JSON_BLUE);
        double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
        Color loadedColor = new Color(red, green, blue, alpha);
        return loadedColor;
    }
}
