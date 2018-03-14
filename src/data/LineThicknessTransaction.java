package data;

public class LineThicknessTransaction implements Transaction {
    SubwayLine line;
    double oldThickness;
    double newThickness;

    public LineThicknessTransaction(SubwayLine line, double oldThickness, double newThickness) {
        this.line = line;
        this.oldThickness = oldThickness;
        this.newThickness = newThickness;
    }

    @Override
    public void doAction() {
        line.setStrokeWidth(newThickness);

    }

    @Override
    public void undoAction() {
        line.setStrokeWidth(oldThickness);
    }
}
