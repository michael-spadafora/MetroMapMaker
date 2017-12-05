package gui;

import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.AppTemplate;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.sound.sampled.Line;
import java.util.ArrayList;

import static data.mmmState.ADDING_STATION_TO_LINE;
import static data.mmmState.REMOVING_STATION_FROM_LINE;
import static djf.ui.AppYesNoCancelDialogSingleton.YES;
import static javafx.scene.paint.Color.BLUE;


public class MapEditController {
    AppTemplate app;
    mmmData data;
    final double ZOOM_CONSTANT = 1.1;
   //mmmWorkspace workspace;

    public MapEditController(AppTemplate app) {
        this.app = app;
        data = (mmmData) app.getDataComponent();
        //workspace = (mmmWorkspace) app.getWorkspaceComponent();

    }

    public void processAddLine() {
        LineSingleton ls = LineSingleton.getSingleton();
        //EnterNameSingleton ens = EnterNameSingleton.getSingleton();
        ls.show(true);

        if (ls.getSelection().equals("Confirm")){

            SubwayLine temp = new SubwayLine(ls.getLineName());
            temp.setColor(ls.getSelectedColor());
            data.addSubwayLine(temp);
            mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
            workspace.updateLineComboBox(data.getElements());
            workspace.getLinesComboBox().getSelectionModel().select(temp.getStart().getLabel().getText());
        }


    }

    public void processRemoveLine() {

        AppYesNoCancelDialogSingleton appYesNoCancelDialogSingleton = AppYesNoCancelDialogSingleton.getSingleton();

        appYesNoCancelDialogSingleton.show("Line remove", "Are you sure you want to remove this line?");
        String selection = appYesNoCancelDialogSingleton.getSelection();
        if (selection.equals(YES)){
        data.removeSelectedLine();
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateLineComboBox(data.getElements());}
        //workspace.

    }

    public void processEditLine() {
        LineSingleton ls = LineSingleton.getSingleton();
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        SubwayLine line = workspace.getSelectedLine();

        ls.show(line.getStart().getLabel().getText(), (Color) line.getStroke());

        if (ls.getSelection().equals("Confirm")){
            line.setName(ls.getLineName());
            line.setColor(ls.getSelectedColor());
            workspace.updateLineComboBox(data.getElements());


        }

    }

    public void processAddStationToLine() {
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        data.setState(ADDING_STATION_TO_LINE);


    }

    public void processRemoveStationFromLine() {
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        data.setState(REMOVING_STATION_FROM_LINE);
    }

    public void processRemoveStation() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        ArrayList<SubwayLine> lines = workspace.getSelectedStation().getSubwayLines();

        for (SubwayLine line: lines){
            line.removeStation(workspace.getSelectedStation());
        }
        data.removeSelectedStation();
        workspace.updateStationComboBox(data.getElements());

    }

    public void processAddStation() {
       EnterNameSingleton ens = EnterNameSingleton.getSingleton();
       ens.show(true);


       if (ens.getSelection().equals("Confirm")){


       String name = ens.getName();
        Station stat = new Station(name);
        data.addStation(stat);


        //data.addText(stat.getLabel());


        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateStationComboBox(data.getElements());
        workspace.getStationComboBox().getSelectionModel().select(stat.getLabel().getText());
       }
    }

    public void processLineComboBoxClick() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        String selected = workspace.getLinesComboBox().getSelectionModel().getSelectedItem();
        if (selected!=null){
            for (Node node:data.getElements()){
                if (node instanceof LineEnd && ((LineEnd) node).getLabel()!=null){
                    if (((LineEnd) node).getLabel().getText().equals(selected)){
                        data.setSelectedElement(node);
                    }

                }
            }

                   
        }
    }

    public void processStationComboBoxClick() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        String selected = workspace.getStationComboBox().getSelectionModel().getSelectedItem();
        if (selected!=null){
            for (Node node:data.getElements()){
                if (node instanceof Station ){
                    if (((Station) node).getLabel().getText().equals(selected)){
                        data.setSelectedElement(node);
                    }

                }
            }


        }
    }

    public void processStationColorChange(Color value) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Station station = workspace.getSelectedStation();
        if (station!=null){
            station.setFill(value);
        }

    }

    public void processLineColorChange(Color value) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        SubwayLine line = workspace.getSelectedLine();
        if (line!= null) {
            line.setColor(value);
        }
    }


    public void zoomIn(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
    canvas.setBackground(new Background(new BackgroundFill(BLUE, null, null)));

        canvas.setScaleX(canvas.getScaleX() * ZOOM_CONSTANT);
        canvas.setScaleY(canvas.getScaleY()* ZOOM_CONSTANT);
}

    public void zoomOut(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        canvas.setScaleX(canvas.getScaleX() * (1/ZOOM_CONSTANT));
        canvas.setScaleY(canvas.getScaleY()* (1/ZOOM_CONSTANT));

        //workspace.resetWorkspace();
    }

    public void changeLineThickness(int i){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.getSelectedLine().setStrokeWidth(i);
    }

    public void changeStationRadius(double d){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.getSelectedStation().changeRadius(d);
    }


    public void setBackgroundColor(Color color) {
        //mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        BackgroundFill bgfill = new BackgroundFill(color, null, null);
        app.getGUI().getAppPane().setBackground(new Background(bgfill));
    }

    public void orbitSelectedStationLabel() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.getSelectedStation().orbitLabel();
    }
}
