package gui;

import data.DraggableElement;
import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.AppTemplate;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.sound.sampled.Line;
import java.util.ArrayList;

import static data.mmmState.ADDING_STATION_TO_LINE;
import static data.mmmState.REMOVING_STATION_FROM_LINE;


public class MapEditController {
    AppTemplate app;
    mmmData data;
   //mmmWorkspace workspace;

    public MapEditController(AppTemplate app) {
        this.app = app;
        data = (mmmData) app.getDataComponent();
        //workspace = (mmmWorkspace) app.getWorkspaceComponent();

    }

    public void processAddLine() {
        EnterNameSingleton ens = EnterNameSingleton.getSingleton();
        ens.show(true);
        String name = ens.getName();

        SubwayLine temp = new SubwayLine(name);
        data.addSubwayLine(temp);
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateLineComboBox(data.getElements());
        workspace.getLinesComboBox().getSelectionModel().select(temp.getStart().getLabel().getText());


    }

    public void processRemoveLine() {
        data.removeSelectedLine();
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateLineComboBox(data.getElements());
        //workspace.

    }

    public void processEditLine() {
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
       String name = ens.getName();



        Station stat = new Station(name);
        data.addStation(stat);
        data.addText(stat.getLabel());

        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateStationComboBox(data.getElements());
        workspace.getStationComboBox().getSelectionModel().select(stat.getLabel().getText());
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
}
