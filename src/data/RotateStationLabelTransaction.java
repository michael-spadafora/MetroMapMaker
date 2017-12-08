package data;

public class RotateStationLabelTransaction implements Transaction {
    Station station;

    public RotateStationLabelTransaction(Station station) {
        this.station = station;
    }

    @Override
    public void doAction() {
        station.rotateLabel();

    }

    @Override
    public void undoAction() {
        station.rotateLabel();

    }
}
