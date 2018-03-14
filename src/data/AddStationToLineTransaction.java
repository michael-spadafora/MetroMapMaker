package data;

public class AddStationToLineTransaction implements  Transaction {

    SubwayLine line;
    Station station;

    public AddStationToLineTransaction(SubwayLine line, Station station) {
        this.line = line;
        this.station = station;
    }

    @Override
    public void doAction() {
        line.addStation(station);
        station.addSubwayLine(line);

    }

    @Override
    public void undoAction() {
        line.removeStation(station);
        station.removeSubwayLine(line);
    }
}
