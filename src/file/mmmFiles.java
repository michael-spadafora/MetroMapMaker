package file;

import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.sound.sampled.Line;
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

        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

        if (filePath.contains(".")){
            String[] parts = filePath.split(".");
            fileName = parts[0];

        }


        JsonObject finalProduct = Json.createObjectBuilder()
                .add(JSON_NAME, fileName)
                .add(JSON_LINES,lineArray)
                .add(JSON_STATIONS, stationArray)
                .build();

        filePath+=".json";


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
        double x = station.getCenterX();
        double y = station.getCenterY();


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
                //.add(JSON_TYPE, type)
                .add(JSON_NAME, name)
                .add(JSON_X, x)
                .add(JSON_Y, y)
                //.add(JSON_RAD, radius)
                //.add(JSON_COLOR, statColor)
                //.add(JSON_LINES, linesJson)
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
            arrayBuilder.add(stat.getLabel().getText());
        }

        JsonArray stationsJson = arrayBuilder.build();

        JsonObject colorJson = makeJsonColorObject(subwayLine.getColor());


        JsonObject shapeJson = Json.createObjectBuilder()
                //.add(JSON_TYPE, type)
                .add(JSON_NAME, name)
                .add(JSON_CIRCULAR, circular)
                .add(JSON_X_START, xStart)
                .add(JSON_Y_START, yStart)
                .add(JSON_X_END, xEnd)
                .add(JSON_Y_END, yEnd)
                .add(JSON_COLOR, colorJson)
                //.add(JSON_LINE_WIDTH, strokeWidth)
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
        JsonArray jsonLineArray = json.getJsonArray(JSON_LINES);
        JsonArray jsonStationArray = json.getJsonArray(JSON_STATIONS);
        ArrayList<Station> stations = new ArrayList<>();
        ArrayList<SubwayLine> subwayLines = new ArrayList<>();
        for (int i = 0; i < jsonStationArray.size(); i++) {
            JsonObject jsonNode = jsonStationArray.getJsonObject(i);
            Node node = loadNode(jsonNode);
            Station station = (Station) node;
            stations.add(station);
        }

        for (int i = 0; i < jsonLineArray.size(); i++) {
            JsonObject jsonNode = jsonLineArray.getJsonObject(i);
            Node node = loadLine(jsonNode, stations);
            SubwayLine subwayLine = (SubwayLine) node;
            subwayLines.add(subwayLine);

        }

        for (SubwayLine swl: subwayLines ){
            ((mmmData) data).addSubwayLine(swl);
        }

        for (Station stat: stations){
            ((mmmData) data).addStation(stat);
        }

        mmmWorkspace workspace = (mmmWorkspace) dataManager.getApp().getWorkspaceComponent();
        workspace.updateLineComboBox(dataManager.getElements());

    }

    private Node loadNode(JsonObject jsonNode) {
        //String type = jsonNode.getString(JSON_TYPE);
        Node returnedNode = null;

        returnedNode = loadStation(jsonNode);
//            case JSON_IS_LINE:
//                returnedNode = loadLine(jsonNode);
//                break;


        return returnedNode;
    }

    private Node loadLine(JsonObject jsonNode, ArrayList<Station> stations) {
        SubwayLine line = new SubwayLine(jsonNode.getString(JSON_NAME));
        JsonArray jsonStations = jsonNode.getJsonArray(JSON_STATIONS_ON_LINE);
        ArrayList<String> currStations = new ArrayList<>();

        for (int i = 0; i < jsonStations.size(); i++){
            //JsonObject temp = jsonStations.getJsonObject(i);
            currStations.add(jsonStations.getString(i));
        }

        for (Station stat: stations){
            for (String name:currStations){
                String labelText = stat.getLabel().getText();
                if (name.equals(labelText)){
                    line.addStation(stat);
                    stat.addSubwayLine(line);
                }
            }
        }

        double startx, starty, endx, endy;

        try{
            startx= getDataAsDouble(jsonNode, JSON_X_START);
            starty = getDataAsDouble(jsonNode, JSON_Y_START);
            endx = getDataAsDouble(jsonNode, JSON_X_END);
            endy = getDataAsDouble(jsonNode, JSON_Y_END);
        }
        catch (Exception ex){
            startx = line.getStations().get(0).getX()-10;
            starty = line.getStations().get(0).getY()-10;

            endx = line.getStations().get(line.getStations().size()-1).getX()+10;
            endy = line.getStations().get(line.getStations().size()-1).getY()+10;
        }

        line.getStart().setCenterX(startx);
        line.getStart().setCenterY(starty);
        line.getStart().getLabel().setX(startx+10);
        line.getStart().getLabel().setY(starty+10);
        line.getEnd().setCenterX(endx);
        line.getEnd().setCenterY(endy);
        line.getEnd().getLabel().setX(endx+10);
        line.getEnd().getLabel().setY(endy+10);

        line.setCircular(jsonNode.getBoolean(JSON_CIRCULAR));


        line.fixPoints();

        line.setColor(loadColor(jsonNode, JSON_COLOR));
        return line;


    }

    private Node loadStation(JsonObject jsonNode) {
        Station stat  = new Station();
        stat.getLabel().setText(jsonNode.getString(JSON_NAME));
        stat.setCenterX(getDataAsDouble(jsonNode, JSON_X));
        stat.setCenterY(getDataAsDouble(jsonNode, JSON_Y));

        return stat;
    }

    @Override
    public void exportData(AppDataComponent appDataComponent, String filePath) throws IOException {
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

        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

        if (filePath.contains(".")){
            String[] parts = filePath.split(".");
            fileName = parts[0];

        }


        JsonObject finalProduct = Json.createObjectBuilder()
                .add(JSON_NAME, fileName)
                .add(JSON_LINES,lineArray)
                .add(JSON_STATIONS, stationArray)
                .build();

        String imagePath = filePath;
        filePath+=".json";



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



        mmmWorkspace workspace = (mmmWorkspace)dataManager.getApp().getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File file = new File(imagePath+".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
    }

    @Override
    public void importData(AppDataComponent appDataComponent, String s) throws IOException {
        loadData(appDataComponent, s);
    }

    private JsonObject exportSubwayLineJson(Node node) {
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
            arrayBuilder.add(stat.getLabel().getText());
        }

        JsonArray stationsJson = arrayBuilder.build();

        JsonObject colorJson = makeJsonColorObject(subwayLine.getColor());


        JsonObject shapeJson = Json.createObjectBuilder()
                .add(JSON_TYPE, type)
                .add(JSON_NAME, name)
                .add(JSON_CIRCULAR, circular)
//                .add(JSON_X_START, xStart)
//                .add(JSON_Y_START, yStart)
//                .add(JSON_X_END, xEnd)
//                .add(JSON_Y_END, yEnd)
                .add(JSON_COLOR, colorJson)
                .add(JSON_LINE_WIDTH, strokeWidth)
                .add(JSON_STATIONS_ON_LINE, stationsJson)
                .build();

        return shapeJson;
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