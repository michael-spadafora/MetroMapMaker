package data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import gui.LineEnd;
import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;



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
        ((Node) (shape)).setEffect(null);
    }

    public void highlightShape(DraggableElement shape) {
        ((Node) (shape)).setEffect(highlightedEffect);
    }

    @Override
    public void resetData() {

    }

    public void setShapes(ObservableList<Node> children) {
        elements = children;
    }

    public void addSubwayLine(SubwayLine swl){
        //elements.addAll(swl.getLineSegments());
        elements.add(swl);
        elements.add(swl.getEnd());
        elements.add(swl.getEnd().getLabel());
        elements.add(swl.getStart());
        elements.add(swl.getStart().getLabel());
        elements.add(swl.getConnectorLine());

    }

    public void addStation(Station stat){
        elements.add(stat);
    }

    public void addText(Text text){
        elements.add(text);
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

    public void setSelectedElement(Node element){
        if (selectedElement != null) {
            unhighlightShape(selectedElement);
        }

        selectedElement = (DraggableElement) element;
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();

        if (selectedElement instanceof SubwayLine)
        {
            workspace.setSelectedLineColor((Color) ((SubwayLine) selectedElement).getFill());

        }

        else if (selectedElement instanceof LineEnd){
            workspace.setSelectedLineColor((Color) ((LineEnd) selectedElement).getStroke());
        }

        else if (selectedElement instanceof Station){
            workspace.setSelectedStationColor((Color) ((Station) selectedElement).getFill());
        }
        highlightShape((DraggableElement) element);
    }

    public DraggableElement getTopShape(int x, int y) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.emptyComboBoxes();

        for (int i = elements.size() - 1; i >= 0; i--) {
            if (!(elements.get(i) instanceof Text)&&!(elements.get(i) instanceof SubwayLine) && !(elements.get(i) instanceof Line)){
            DraggableElement shape = (DraggableElement) elements.get(i);
            Node element = (Node) shape;
                if (element.contains(x,y)){
                    if (element instanceof LineEnd){
                        String selection = ((LineEnd) element).getSubwayLine().getStart().getLabel().getText();
                        workspace.getLinesComboBox().getSelectionModel().select(selection);
                    }
                    else if (element instanceof  Station){
                        String selection = ((Station) element).getLabel().getText();
                        workspace.getStationComboBox().getSelectionModel().select(selection);
                    }
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

    public void removeSelectedElement() {
        if (selectedElement instanceof  Station){
             elements.remove(((Station) selectedElement).getLabel());
        }
        elements.remove(selectedElement);
    }

    public void removeSelectedLine(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        SubwayLine line = workspace.getSelectedLine();
        LineEnd start = line.getStart();
        LineEnd end = line.getEnd();
        Line connector = line.getConnectorLine();

        elements.remove(workspace.getSelectedLine());
        elements.remove(start.getLabel());
        elements.remove(start);
        elements.remove(end.getLabel());
        elements.remove(end);
        elements.remove(connector);
    }

    public ObservableList<Node> getElements() {
        return elements;
    }

    public SubwayLine getLineFromString(String s){
        for (Node node: elements){
            if (node instanceof LineEnd && ((LineEnd) node).getLabel()!=null){
                if (((LineEnd) node).getLabel().getText().equals(s)){
                    return ((LineEnd) node).getSubwayLine();
                }
            }
        }
        return null;
    }


    public Station getStationFromString(String s) {
        for (Node node: elements){
            if (node instanceof Station){
                if (((Station) node).getLabel().getText().equals(s)){
                    return ((Station) node);
                }
            }
        }
        return null;
    }

    public void removeSelectedStation() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        elements.remove(workspace.getSelectedStation().getLabel());
        elements.remove(workspace.getSelectedStation());

    }

    public void addElement(Node node){
        if (node instanceof SubwayLine){
            addSubwayLine((SubwayLine) node);

        }

        else if (node instanceof Station){
            addStation((Station) node);
        }

        else if (node instanceof ImageView){
            addImage((ImageView) node);
        }
    }

    private void addImage(ImageView img) {
    }
}
