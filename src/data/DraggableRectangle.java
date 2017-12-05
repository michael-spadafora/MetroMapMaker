package data;

import data.DraggableElement;
import javafx.scene.shape.Rectangle;

/**
 * This is a draggable rectangle for our goLogoLo application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DraggableRectangle extends Rectangle implements DraggableElement {

    double startX;
    double startY;

    public DraggableRectangle() {
        setX(0.0);
        setY(0.0);
        setWidth(0.0);
        setHeight(0.0);
        setOpacity(1.0);
        startX = 0.0;
        startY = 0.0;
    }

    public DraggableRectangle(double width, double height) {
        super(width, height);
        setOpacity(1.0);
    }


    @Override
    public void start(int x, int y) {
        startX = x;//getWidth()+x;
        startY = y;

        // LOOK FOR THE STATE HERE
        if (getWidth() == 0) {
            setX(x);
            setY(y);
        }

    }

    @Override
    public void drag(int x, int y) { // x is the position of the mouse
        double diffX = x - startX;
        double diffY = y - startY; //calculates the total difference
        //double diffX =  x - (getX() + (getWidth() / 2)); //double width = x - startCenterX;
        //double diffY = y - (getY() + (getHeight() / 2)); //double diffY = y - startCenterY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;
        xProperty().set(newX);
        yProperty().set(newY);

        startX = x;
        startY = y;
    }

    public String cT(double x, double y) {
        return "(x,y): (" + x + "," + y + ")";
    }

    public void size(int x, int y) {
        double width = x - getX();
        widthProperty().set(width);
        double height = y - getY();
        heightProperty().set(height);
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        xProperty().set(initX);
        yProperty().set(initY);
        widthProperty().set(initWidth);
        heightProperty().set(initHeight);
    }

    @Override
    public String getElementType() {
        return "rectangle";
    }
}
