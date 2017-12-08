package data;

public class RemoveStationFromLineTransaction implements Transaction {

    Station station;
    SubwayLine line;

    public RemoveStationFromLineTransaction(Station station, SubwayLine line) {
        this.station = station;
        this.line = line;
    }

    @Override
    public void doAction() {
        line.removeStation(station);
        station.removeSubwayLine(line);

    }

    @Override
    public void undoAction() {
        line.addStation(station);
        station.addSubwayLine(line);
    }
}
