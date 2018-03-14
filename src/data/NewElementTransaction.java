package data;

import javafx.scene.Node;

public class NewElementTransaction implements Transaction {

    mmmData data;
    DraggableElement element;

    public NewElementTransaction(mmmData data, DraggableElement element) {
        this.data = data;
        this.element = element;
    }

    @Override
    public void doAction() {
        data.addElement(element);
        if (element instanceof Station){
            ((Station) element).fixAllLines();
        }

    }

    @Override
    public void undoAction() {
        data.removeElement(element);
    }
}
