package data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class mmmData implements AppDataComponent {

    UndoRedoStack undoRedoStack;
    AppTemplate app;
    ObservableList<Node> elements;
    mmmState state;
    private DraggableElement selectedElement;

    public mmmData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;
        undoRedoStack = new UndoRedoStack();
        selectedElement = null;
        state = mmmState.DRAGGING_NOTHING;

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

        /*if (selectedShape != null) {
            unhighlightShape(selectedShape);
        }*/
        if (shape != null) {
           // highlightShape(shape);
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
