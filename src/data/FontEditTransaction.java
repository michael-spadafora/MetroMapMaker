package data;

import javafx.scene.text.Font;

public class FontEditTransaction implements Transaction {

    DraggableElement element;
    Font oldFont, newFont;

    public FontEditTransaction(DraggableElement element, Font oldFont, Font newFont) {
        this.element = element;
        this.oldFont = oldFont;
        this.newFont = newFont;
    }

    @Override
    public void doAction() {
        if (element instanceof LineEnd){
            ((LineEnd) element).getLabel().setFont(newFont);
        }
        if (element instanceof Station){
            ((Station) element).getLabel().setFont(newFont);
        }
        if (element instanceof DraggableText){
            ((DraggableText) element).setFont(newFont);
        }

    }

    @Override
    public void undoAction() {
        if (element instanceof LineEnd){
            ((LineEnd) element).getLabel().setFont(oldFont);
        }
        if (element instanceof Station){
            ((Station) element).getLabel().setFont(oldFont);
        }
        if (element instanceof DraggableText){
            ((DraggableText) element).setFont(oldFont);
        }

    }
}
