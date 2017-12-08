package data;

import javafx.scene.paint.Color;

public class EditLineTransaction implements Transaction {

    SubwayLine line;
    String oldName, newName;
    Color oldColor, newColor;
    boolean oldCircular, newCircular;

    @Override
    public void doAction() {
        line.getEnd().getLabel().setText(newName);
        line.getStart().getLabel().setText(newName);
        line.setColor(newColor);
        line.setCircular(newCircular);
    }

    @Override
    public void undoAction() {
        line.getEnd().getLabel().setText(oldName);
        line.getStart().getLabel().setText(oldName);
        line.setColor(oldColor);
        line.setCircular(oldCircular);
    }
}
