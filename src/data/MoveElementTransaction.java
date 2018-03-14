package data;

public class MoveElementTransaction implements Transaction {
    double oldx, oldy, newx, newy;
    DraggableElement element;

    public MoveElementTransaction(double oldx, double oldy, double newx, double newy, DraggableElement element) {
        this.oldx = oldx;
        this.oldy = oldy;
        this.newx = newx;
        this.newy = newy;
        this.element = element;
    }

    @Override
    public void doAction() {
        if (element instanceof Station){
            ((Station) element).setCenterX(newx);
            ((Station) element).setCenterY(newy);
            ((Station) element).fixAllLines();
        }

        else if (element instanceof LineEnd){
            ((LineEnd) element).setCenterX(newx);
            ((LineEnd) element).setCenterY(newy);
            ((LineEnd) element).getSubwayLine().fixPoints();
        }

        else if (element instanceof DraggableText){
            ((DraggableText) element).setX(newx);
            ((DraggableText) element).setY(newy);
        }
        else if (element instanceof DraggableImage){
            ((DraggableImage) element).setX(newx);
            ((DraggableImage) element).setY(newy);
        }

    }

    @Override
    public void undoAction() {
        if (element instanceof Station){
            ((Station) element).setCenterX(oldx);
            ((Station) element).setCenterY(oldy);
            ((Station) element).fixAllLines();
            ((Station) element).fixLabel();
        }

        else if (element instanceof LineEnd){
            ((LineEnd) element).setCenterX(oldx);
            ((LineEnd) element).setCenterY(oldy);
            ((LineEnd) element).getSubwayLine().fixPoints();
            ((LineEnd)element).fixLabel();
        }

        else if (element instanceof DraggableText){
            ((DraggableText) element).setX(oldx);
            ((DraggableText) element).setY(oldy);
        }
        else if (element instanceof DraggableImage){
            ((DraggableImage) element).setX(oldx);
            ((DraggableImage) element).setY(oldy);
        }


    }
}
