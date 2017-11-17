package gui;

import data.Station;
import data.mmmData;
import djf.AppTemplate;

public class MapEditController {
    AppTemplate app;
    mmmData data;

    public MapEditController(AppTemplate app) {
        this.app = app;
        data = (mmmData) app.getDataComponent();
    }

    public void processAddLine() {

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
    }

    public void processAddStation() {
        Station stat = new Station();
        data.addStation(stat);
    }
}
