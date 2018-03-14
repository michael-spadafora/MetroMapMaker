package data;

public class OrbitStationLabelTransaction implements Transaction {
    Station station;

    public OrbitStationLabelTransaction(Station station) {
        this.station = station;
    }


    @Override
    public void doAction() {
        station.orbitLabel();
    }

    @Override
    public void undoAction() {
        station.orbitLabel();
        station.orbitLabel();
        station.orbitLabel();
    }
}
