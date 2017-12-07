package gui;

import data.Station;
import data.SubwayLine;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DisplayAllStationsOnLineSingleton extends Stage {
    private static DisplayAllStationsOnLineSingleton ourInstance = new DisplayAllStationsOnLineSingleton();

    public static DisplayAllStationsOnLineSingleton getInstance() {
        return ourInstance;
    }

    private DisplayAllStationsOnLineSingleton() {
    }

    VBox overallBox;
    Text label = new Text();
    TextArea displayArea = new TextArea();
    Scene messageScene;

    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        overallBox = new VBox();
        overallBox.getChildren().add(label);
        overallBox.getChildren().add(displayArea);
        displayArea.setEditable(false);



        messageScene = new Scene(overallBox);
        this.setScene(messageScene);
    }

    public void show (SubwayLine subwayLine){
        String s = "";
        label.setText(subwayLine.getName());
        for (Station station:subwayLine.getStations()) {
            s += station.getLabel().getText() + "\n";
        }
        displayArea.setText(s);
        showAndWait();
    }



}
