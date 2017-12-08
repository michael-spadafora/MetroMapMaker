package data;

public class AddLineTransaction implements Transaction {
    SubwayLine line;
    mmmData data;

    public AddLineTransaction(mmmData data, SubwayLine line) {

        this.line = line;
        this.data = data;
    }

    @Override
    public void doAction() {
        data.addSubwayLine(line);
        line.fixPoints();

    }

    @Override
    public void undoAction() {
        data.removeLine(line);
    }
}
