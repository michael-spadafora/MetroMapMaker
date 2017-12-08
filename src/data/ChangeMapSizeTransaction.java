package data;

import javafx.scene.layout.Pane;

public class ChangeMapSizeTransaction implements Transaction {

    Pane canvas;
    double oldWidth,oldHeight, newWidth, newHeight;

    public ChangeMapSizeTransaction(Pane canvas, double oldWidth, double oldHeight, double newWidth, double newHeight) {
        this.canvas = canvas;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public void doAction() {
        canvas.setMinWidth(newWidth);
        canvas.setMaxWidth(newWidth);

        canvas.setMinHeight(newHeight);
        canvas.setMaxHeight(newHeight);
    }

    @Override
    public void undoAction() {
        canvas.setMinWidth(oldWidth);
        canvas.setMaxWidth(oldWidth);

        canvas.setMinHeight(oldHeight);
        canvas.setMaxHeight(oldHeight);
    }
}
