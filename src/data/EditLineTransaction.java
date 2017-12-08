package data;

import javafx.scene.paint.Color;

public class EditLineTransaction implements Transaction {

    SubwayLine line;
    String oldName, newName;
    Color oldColor, newColor;
    boolean oldCircular, newCircular;

    public EditLineTransaction(SubwayLine line, String oldName, String newName, Color oldColor, Color newColor, boolean oldCircular, boolean newCircular) {
        this.line = line;
        this.oldName = oldName;
        this.newName = newName;
        this.oldColor = oldColor;
        this.newColor = newColor;
        this.oldCircular = oldCircular;
        this.newCircular = newCircular;

    }

    @Override
    public void doAction() {
        line.getEnd().getLabel().setText(newName);
        line.getStart().getLabel().setText(newName);
        line.setColor(newColor);
        line.setCircular(newCircular);
        line.fixPoints();
    }

    @Override
    public void undoAction() {
        line.getEnd().getLabel().setText(oldName);
        line.getStart().getLabel().setText(oldName);
        line.setColor(oldColor);
        line.setCircular(oldCircular);
        line.fixPoints();
    }
}
