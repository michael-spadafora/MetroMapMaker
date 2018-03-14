package data;

public class StationRadiusTransaction implements Transaction {

    Station station;
    double oldThicc;
    double newThicc;

    public StationRadiusTransaction(Station station, double oldThicc, double newThicc) {
        this.station = station;
        this.oldThicc = oldThicc;
        this.newThicc = newThicc;
    }

    @Override
    public void doAction() {
        station.setRadius(newThicc);
    }

    @Override
    public void undoAction() {
        station.setRadius(oldThicc);
    }
}
