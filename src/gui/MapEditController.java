package gui;

import data.DraggableElement;
import data.Station;
import data.SubwayLine;
import data.mmmData;
import djf.AppTemplate;
import javafx.scene.text.Text;


public class MapEditController {
    AppTemplate app;
    mmmData data;

    public MapEditController(AppTemplate app) {
        this.app = app;
        data = (mmmData) app.getDataComponent();
    }

    public void processAddLine() {
//        EnterNameSingleton ens = EnterNameSingleton.getSingleton();
//        ens.showAndWait();
//        String name = ens.getName();

        SubwayLine temp = new SubwayLine();
        data.addSubwayLine(temp);


    }

    public void processRemoveLine() {
    }

    public void processEditLine() {
    }

    public void processAddStationToLine() {
    }

    public void processRemoveStationFromLine() {
    }

    public void processRemoveStation() {
        data.removeSelectedElement();
    }

    public void processAddStation() {
       EnterNameSingleton ens = EnterNameSingleton.getSingleton();
       ens.showAndWait();
       String name = ens.getName();

        Station stat = new Station(name);
        data.addStation(stat);
        data.addText(stat.getLabel());
    }
}
