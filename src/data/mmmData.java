package data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static data.mmmState.SELECTING_SHAPE;


public class mmmData implements AppDataComponent {

    Effect highlightedEffect;
    AppTemplate app;
    ObservableList<Node> elements;
    mmmState state;

    ArrayList<Line> gridLines;
    ArrayList<Node> elementsHidden;



    public DraggableElement getSelectedElement() {
        return selectedElement;
    }

    DraggableElement selectedElement;


    public mmmData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;
        //undoRedoStack = new UndoRedoStack(this);
        selectedElement = null;
        state = SELECTING_SHAPE;
        gridLines = new ArrayList<>();
        elementsHidden = new ArrayList<>();



        DropShadow dropShadowEffect = new DropShadow();
        dropShadowEffect.setOffsetX(0.0f);
        dropShadowEffect.setOffsetY(0.0f);
        dropShadowEffect.setSpread(1.0);
        dropShadowEffect.setColor(Color.YELLOW);
        dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
        dropShadowEffect.setRadius(5);
        highlightedEffect = dropShadowEffect;

    }

    public void hideElements(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();

        //for (int i = elements.si)

    }

    public AppTemplate getApp() {
        return app;
    }

    public void unhighlightShape(DraggableElement shape) {
        ((Node) (shape)).setEffect(null);
    }

    public void highlightShape(DraggableElement shape) {
        try{
        ((Node) (shape)).setEffect(highlightedEffect);
        }
        catch (Exception ex){

        }
    }

    @Override
    public void resetData() {
        setState(SELECTING_SHAPE);
        //newShape = null;
        selectedElement = null;

        // INIT THE COLORS
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.setSelectedLineColor(Color.BLACK);
        workspace.setSelectedStationColor(Color.BLACK);

        elements.clear();
        ((mmmWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().clear();


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
        fixStationPriority();

    }

    public void addStation(Station stat){
        elements.add(stat);
        elements.add(stat.getLabel());
        //stat.getLabel().setX(stat.getCenterX()+10);
        //stat.getLabel().setY(stat.getCenterY()+10);
    }

    public void addText(Text text){
        elements.add(text);
    }

    public DraggableElement selectTopShape(int x, int y) {
        DraggableElement shape = getTopShape(x, y);

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

        setSelectedElement((Node) selectedElement);
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
            workspace.setSelectedLineWidth(((SubwayLine) selectedElement).getStrokeWidth());
            workspace.setSelectedTextColor(((SubwayLine) selectedElement).getStart().getLabel().getFill());

        }

        else if (selectedElement instanceof LineEnd) {
            workspace.setSelectedLineColor((Color) ((LineEnd) selectedElement).getStroke());
            workspace.setSelectedLineWidth(((LineEnd) selectedElement).getSubwayLine().getStrokeWidth());
            workspace.setSelectedTextColor(((LineEnd) selectedElement).getLabel().getFill());
            workspace.setSelectedTextSize((int)((LineEnd) selectedElement).getLabel().getFont().getSize());
            workspace.setSelectedTextFamily(((LineEnd) selectedElement).getLabel().getFont().getFamily());
        }

        else if (selectedElement instanceof Station){
            workspace.setSelectedStationColor((Color) ((Station) selectedElement).getFill());
            workspace.setSelectedStationWidth(((Station) selectedElement).getRadius());
            workspace.setSelectedTextColor(((Station) selectedElement).getLabel().getFill());
            workspace.setSelectedTextSize((int) ((Station) selectedElement).getLabel().getFont().getSize());
            workspace.setSelectedTextFamily(((Station) selectedElement).getLabel().getFont().getFamily());
        }

        else if (selectedElement instanceof DraggableText){
            workspace.setSelectedTextColor(((DraggableText) selectedElement).getFill());
            workspace.setSelectedTextSize((int) ((DraggableText) selectedElement).getFont().getSize());
            workspace.setSelectedTextFamily(((DraggableText) selectedElement).getFont().getFamily());
        }
        highlightShape((DraggableElement) element);
    }

    public DraggableElement getTopShape(int x, int y) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.emptyComboBoxes();

        for (int i = elements.size() - 1; i >= 0; i--) {
            if (!(elements.get(i) instanceof Text && !(elements.get(i) instanceof DraggableText)) && !(elements.get(i) instanceof SubwayLine) && !(elements.get(i) instanceof Line)){
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

    public DraggableElement removeSelectedElement() {
        DraggableElement element = selectedElement;
        if (selectedElement instanceof  Station){
            elements.remove(((Station) selectedElement).getLabel());
            elements.remove(selectedElement);
        }

        if (selectedElement instanceof LineEnd){
            removeSelectedLine();
        }

        if (selectedElement instanceof DraggableImage || selectedElement instanceof DraggableText){
            elements.remove(selectedElement);
        }

        return selectedElement;

    }

    public void addLineEnd(LineEnd end){
        SubwayLine line = end.getSubwayLine();
        addSubwayLine(line);

    }

    public SubwayLine removeSelectedLine(){
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

        return line;
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

    public void addElement(DraggableElement node){

        if (node instanceof SubwayLine){
            addSubwayLine((SubwayLine) node);

        }

        else if (node instanceof  LineEnd){
            addLineEnd((LineEnd) node);
        }

        else if (node instanceof Station){
            addStation((Station) node);
        }

        else if (node instanceof DraggableImage){
            addImage((DraggableImage) node);
        }

        else if (node instanceof DraggableText){
            addText((DraggableText) node);
        }


        fixStationPriority();
    }

    private void addImage(DraggableImage img) {
        elements.add(img);
    }

    public void fixStationPriority(){

        try{
        ArrayList<Node> nodes = new ArrayList<>();

        for (Node swl: elements){
            if (swl instanceof Station){
                elements.remove(swl);
                elements.add(swl);
            }

        }
        }
        catch (Exception ex){

        }
    }

    public void showGrid(ArrayList<Line> gridlines){
        if (this.gridLines.isEmpty()){
            this.gridLines = gridlines;
            elements.addAll(0, gridlines);
        }
        else {
            elements.removeAll(gridLines);
            gridLines.clear();
        }
    }

    public void removeElement(DraggableElement element) {

        if (selectedElement instanceof  Station){
            elements.remove(((Station) selectedElement).getLabel());
            elements.remove(selectedElement);
        }

        if (selectedElement instanceof LineEnd){
            removeSelectedLine();
        }

        if (selectedElement instanceof DraggableImage || selectedElement instanceof DraggableText){
            elements.remove(selectedElement);
        }

        elements.remove(element);

    }


    public void removeLine(SubwayLine line) {
        elements.remove(line.getStart().getLabel());
        elements.remove(line.getEnd().getLabel());
        elements.remove(line.getStart());
        elements.remove(line.getConnectorLine());
        elements.remove(line.getEnd());
        elements.remove(line);
    }



}
