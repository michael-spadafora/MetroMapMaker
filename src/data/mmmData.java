package data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

public class mmmData implements AppDataComponent {

    Effect highlightedEffect;
    UndoRedoStack undoRedoStack;
    AppTemplate app;
    ObservableList<Node> elements;
    mmmState state;

    public DraggableElement getSelectedElement() {
        return selectedElement;
    }

    DraggableElement selectedElement;

    public mmmData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;
        undoRedoStack = new UndoRedoStack();
        selectedElement = null;
        state = mmmState.SELECTING_SHAPE;



        DropShadow dropShadowEffect = new DropShadow();
        dropShadowEffect.setOffsetX(0.0f);
        dropShadowEffect.setOffsetY(0.0f);
        dropShadowEffect.setSpread(1.0);
        dropShadowEffect.setColor(Color.YELLOW);
        dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
        dropShadowEffect.setRadius(5);
        highlightedEffect = dropShadowEffect;

    }

    public void unhighlightShape(DraggableElement shape) {
        ((Station) (shape)).setEffect(null);
    }

    public void highlightShape(DraggableElement shape) {
        ((Station) (shape)).setEffect(highlightedEffect);
    }

    @Override
    public void resetData() {

    }

    public void setShapes(ObservableList<Node> children) {
        elements = children;
    }

    public void addSubwayLine(SubwayLine swl){
        elements.addAll(swl.getLineSegments());
    }

    public void addStation(Station stat){
        elements.add(stat);
    }

    public DraggableElement selectTopShape(int x, int y) {
        DraggableElement shape = getTopShape(x, y);
        // if (shape == selectedShape) {
        //    return shape;
        // }

        if (selectedElement != null) {
            unhighlightShape(selectedElement);
        }
        if (shape != null) {
            highlightShape(shape);
            mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
            //workspace.loadSelectedShapeSettings(shape);
        }
        selectedElement = shape;
        if (shape != null) {
            ((DraggableElement) shape).start(x, y);
        }
        return shape;
    }

    public DraggableElement getTopShape(int x, int y) {
        for (int i = elements.size() - 1; i >= 0; i--) {
            DraggableElement shape = (DraggableElement) elements.get(i);
            if (shape instanceof Station) {
                    Station stat = (Station) shape;
                    if (stat.contains(x,y)){
                        return shape;
                    }
            }
        }
        return null;
    }

    public boolean isInState(mmmState state) {
        return this.state == state;
    }

    public void setState(mmmState state) {
        this.state = state;
    }
}
