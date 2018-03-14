package data;

public class RemoveLineTransaction implements Transaction {
    mmmData data;
    SubwayLine line;

    public RemoveLineTransaction(mmmData data, SubwayLine line) {
        this.data = data;
        this.line = line;
    }


    @Override
    public void doAction() {
        data.removeLine(line);
    }

    @Override
    public void undoAction() {
        data.addSubwayLine(line);
        line.fixPoints();

    }
}
