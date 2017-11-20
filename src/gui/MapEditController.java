package gui;

import data.DraggableElement;
import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.AppTemplate;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import static data.mmmState.ADDING_STATION_TO_LINE;


public class MapEditController {
    AppTemplate app;
    mmmData data;
    mmmWorkspace workspace;

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


    }

    public void processRemoveLine() {

    }

    public void processEditLine() {
    }

    public void processAddStationToLine() {
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        data.setState(ADDING_STATION_TO_LINE);


    }

    public void processRemoveStationFromLine() {
    }

    public void processRemoveStation() {

        data.removeSelectedElement();

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
}
