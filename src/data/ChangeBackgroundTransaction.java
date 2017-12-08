package data;

import gui.mmmWorkspace;
import javafx.scene.layout.Background;

public class ChangeBackgroundTransaction implements Transaction {

    Background oldBackground;
    Background newBackground;
    mmmData data;

    public ChangeBackgroundTransaction(mmmData data, Background oldBackground, Background newBackground) {
        this.oldBackground = oldBackground;
        this.newBackground = newBackground;
        this.data = data;
    }

    @Override
    public void doAction() {
        ((mmmWorkspace) data.getApp().getWorkspaceComponent()).getCanvas().setBackground(newBackground);
    }

    @Override
    public void undoAction() {
        ((mmmWorkspace) data.getApp().getWorkspaceComponent()).getCanvas().setBackground(oldBackground);
    }
}
