package gui;

import data.*;
import djf.AppTemplate;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

import static data.mmmState.ADDING_STATION_TO_LINE;
import static data.mmmState.REMOVING_STATION_FROM_LINE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppYesNoCancelDialogSingleton.YES;
import static javafx.scene.paint.Color.BLUE;


public class MapEditController {
    AppTemplate app;
    mmmData data;
    final double ZOOM_CONSTANT = 1.1;
    ArrayList<Line> lines;
    UndoRedoStack undoRedoStack;
   //mmmWorkspace workspace;

    public MapEditController(AppTemplate app) {
        this.app = app;
        data = (mmmData) app.getDataComponent();
        undoRedoStack = new UndoRedoStack(data);
        //workspace = (mmmWorkspace) app.getWorkspaceComponent();
    }

    public void processAddLine() {
        LineSingleton ls = LineSingleton.getSingleton();
        //EnterNameSingleton ens = EnterNameSingleton.getSingleton();
        ls.show(true);

        if (ls.getSelection().equals("Confirm")){

            SubwayLine temp = new SubwayLine(ls.getLineName());
            temp.setColor(ls.getSelectedColor());
            data.addSubwayLine(temp);
            mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
            workspace.updateLineComboBox(data.getElements());
            workspace.getLinesComboBox().getSelectionModel().select(temp.getStart().getLabel().getText());
            Transaction transaction = new AddLineTransaction(data, temp);
            undoRedoStack.addTransaction(transaction);
        }


    }

    public void processRemoveLine() {

        AppYesNoCancelDialogSingleton appYesNoCancelDialogSingleton = AppYesNoCancelDialogSingleton.getSingleton();

        appYesNoCancelDialogSingleton.show("Line remove", "Are you sure you want to remove this line?");
        String selection = appYesNoCancelDialogSingleton.getSelection();
        if (selection.equals(YES)){
            SubwayLine line = data.removeSelectedLine();
            mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
            workspace.updateLineComboBox(data.getElements());
            Transaction transaction = new RemoveLineTransaction(data, line);
            undoRedoStack.addTransaction(transaction);
        }


        //workspace.

    }

    public void processEditLine() {
        LineSingleton ls = LineSingleton.getSingleton();
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        SubwayLine line = workspace.getSelectedLine();

        ls.show(line.getStart().getLabel().getText(), (Color) line.getStroke(), line.isCircular());

        if (ls.getSelection().equals("Confirm")){
            line.setName(ls.getLineName());
            line.setColor(ls.getSelectedColor());
            line.setCircular(ls.getCircularCheckbox().isSelected());
            workspace.updateLineComboBox(data.getElements());
        }

    }

    public void processAddStationToLine() {
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        data.setState(ADDING_STATION_TO_LINE);


    }

    public void processRemoveStationFromLine() {
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        data.setState(REMOVING_STATION_FROM_LINE);
    }

    public void processRemoveStation() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        ArrayList<SubwayLine> lines = workspace.getSelectedStation().getSubwayLines();

        for (SubwayLine line: lines){
            line.removeStation(workspace.getSelectedStation());
        }
        data.removeSelectedStation();
        workspace.updateStationComboBox(data.getElements());

    }

    public void processAddStation() {
       EnterNameSingleton ens = EnterNameSingleton.getSingleton();
       ens.show(true);


       if (ens.getSelection().equals("Confirm")){


        String name = ens.getName();
        Station stat = new Station(name);
        data.addStation(stat);
        Transaction transaction = new NewElementTransaction(data, stat);
        undoRedoStack.addTransaction(transaction);


        //data.addText(stat.getLabel());


        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.updateStationComboBox(data.getElements());
        workspace.getStationComboBox().getSelectionModel().select(stat.getLabel().getText());
       }
    }

    public void processLineComboBoxClick() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        String selected = workspace.getLinesComboBox().getSelectionModel().getSelectedItem();
        if (selected!=null){
            for (Node node:data.getElements()){
                if (node instanceof LineEnd && ((LineEnd) node).getLabel()!=null){
                    if (((LineEnd) node).getLabel().getText().equals(selected)){
                        data.setSelectedElement(node);
                    }

                }
            }

                   
        }
    }

    public void processStationComboBoxClick() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        String selected = workspace.getStationComboBox().getSelectionModel().getSelectedItem();
        if (selected!=null){
            for (Node node:data.getElements()){
                if (node instanceof Station ){
                    if (((Station) node).getLabel().getText().equals(selected)){
                        data.setSelectedElement(node);
                    }

                }
            }


        }
    }

    public void processStationColorChange(Color value) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Station station = workspace.getSelectedStation();
        if (station!=null){
            station.setFill(value);
        }

    }

    public void processLineColorChange(Color value) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        SubwayLine line = workspace.getSelectedLine();
        if (line!= null) {
            line.setColor(value);
        }
    }

    public void zoomIn(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        //canvas.setBackground(new Background(new BackgroundFill(BLUE, null, null)));

        canvas.setScaleX(canvas.getScaleX() * ZOOM_CONSTANT);
        canvas.setScaleY(canvas.getScaleY()* ZOOM_CONSTANT);

        canvas.setTranslateY(0);
        canvas.setTranslateX(0);
}

    public void zoomOut(){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();

        double width = canvas.getWidth() * canvas.getScaleX() * 1/ZOOM_CONSTANT;
        double height = canvas.getHeight() * canvas.getScaleY()* (1/ZOOM_CONSTANT);

        if (width>=200 && height>=200){
            canvas.setScaleX(canvas.getScaleX() * (1/ZOOM_CONSTANT));
            canvas.setScaleY(canvas.getScaleY()* (1/ZOOM_CONSTANT));
        }



        canvas.setTranslateY(0);
        canvas.setTranslateX(0);
        //workspace.resetWorkspace();
    }

    public void changeLineThickness(double i){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.getSelectedLine().setStrokeWidth(i);
    }

    public void changeStationRadius(double d){
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.getSelectedStation().changeRadius(d);
    }

    public void setBackgroundColor(Color color) {

        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Background oldBackground = workspace.getCanvas().getBackground();
        BackgroundFill bgfill = new BackgroundFill(color, null, null);
        Background newBackground = new Background(bgfill);
        workspace.getCanvas().setBackground(newBackground);

        ChangeBackgroundTransaction transaction = new ChangeBackgroundTransaction(data, oldBackground, newBackground);
        undoRedoStack.addTransaction(transaction);

    }

    public void orbitSelectedStationLabel() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        try {
            workspace.getSelectedStation().orbitLabel();
            Transaction t = new OrbitStationLabelTransaction(workspace.getSelectedStation());
            undoRedoStack.addTransaction(t);
        }
        catch (NullPointerException ex){

        }
    }

    public void rotateSelectedStationLabel() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        try {
            workspace.getSelectedStation().rotateLabel();
            Transaction t = new RotateStationLabelTransaction(workspace.getSelectedStation());
            undoRedoStack.addTransaction(t);
        }
        catch (NullPointerException ex){

        }

    }

    public void showGrid() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        double height = workspace.getCanvas().getMaxHeight();
        double width = workspace.getCanvas().getMaxWidth();

        if (height == -1){
            height = workspace.getCanvas().getHeight();
        }
        if (width == -1){
            width = workspace.getCanvas().getWidth();
        }

        double top = workspace.getCanvas().getTranslateX() + workspace.getCanvas().getLayoutX();
        double left = workspace.getCanvas().getLayoutY() ;//+ workspace.getCanvas().getTranslateY();

        ArrayList<Line> linez = new ArrayList<>();

        //does the horizontal linez
        for (int i = 0; i < height; i+=20){
            Line gridline = new Line();
            gridline.setStartX(0);
            gridline.setStartY(i);

            gridline.setEndX(width);
            gridline.setEndY(i);

            linez.add(gridline);
        }

        for (int i = 0; i < width; i +=20) {
            Line gridline = new Line();

            gridline.setStartX(i);
            gridline.setStartY(0);

            gridline.setEndX(i);
            gridline.setEndY(0+height);

            linez.add(gridline);

        }

        this.lines = linez;

        data.showGrid(this.lines);



    }

    public void snapToGrid() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();

        if (element instanceof LineEnd || element instanceof Station){
            double xloc = ((Circle)element).getCenterX();
            double yloc = ((Circle)element).getCenterY();

            double xRemainder = xloc % 20;
            double yRemainder = yloc % 20;

            double newXLoc;
            double newYLoc;



            if (xRemainder < 10){
                newXLoc = xloc -xRemainder;
                //newYLoc = yloc - yRemainder;
            }
            else {
                newXLoc = xloc + (20-xRemainder);
            }

            if (yRemainder < 10){
                newYLoc = yloc -yRemainder;
                //newYLoc = yloc - yRemainder;
            }
            else {
                newYLoc = yloc + (20-yRemainder);
            }

            ((Circle)element).setCenterX(newXLoc);
            ((Circle)element).setCenterY(newYLoc);

            if (element instanceof Station){
                ((Station) element).fixAllLines();
            }

            if (element instanceof LineEnd){
                ((LineEnd) element).getSubwayLine().fixPoints();
            }

            Transaction t = new MoveElementTransaction(xloc, yloc, newXLoc, newYLoc, element);
            undoRedoStack.addTransaction(t);

        }


    }

    public void enlargeMap() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        //canvas.setBackground(new Background(new BackgroundFill(BLUE, null, null)));

        //double minehgith = canvas.getLayoutBounds().getHeight();


        canvas.setMinHeight(canvas.getLayoutBounds().getHeight() * ZOOM_CONSTANT);
        canvas.setMinWidth(canvas.getLayoutBounds().getWidth()* ZOOM_CONSTANT);



        canvas.setMaxHeight(canvas.getLayoutBounds().getHeight() * ZOOM_CONSTANT);
        canvas.setMaxWidth(canvas.getLayoutBounds().getWidth()* ZOOM_CONSTANT);



        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        showGrid();//calling it twice redraws it
        showGrid();


    }

    public void shrinkMap() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        //canvas.setBackground(new Background(new BackgroundFill(BLUE, null, null)));

        double height = canvas.getLayoutBounds().getHeight() * (1/ZOOM_CONSTANT);
        double width = canvas.getLayoutBounds().getWidth()* (1/ZOOM_CONSTANT);


        if (height<200){
            height = 200;
        }
        if (width<200){
            width = 200;
        }
        canvas.setMinHeight(height);
        canvas.setMinWidth(width);

        canvas.setMaxHeight(height);
        canvas.setMaxWidth(width);

        Rectangle clipRectangle = new Rectangle();
        clipRectangle.widthProperty().bind(canvas.widthProperty().multiply(canvas.scaleXProperty()));
        clipRectangle.heightProperty().bind(canvas.heightProperty().multiply(canvas.scaleYProperty()));
        clipRectangle.xProperty().bind(canvas.layoutXProperty().add(canvas.translateXProperty()));
        clipRectangle.yProperty().bind(canvas.layoutYProperty().add(canvas.translateYProperty()));

        workspace.getCanvasWrapper().setClip(clipRectangle);
       //lipRectangle.xProperty().bind(canvas.getLayoutX() + canvas.getTranslateX());
       //lipRectangle.setY(100);
       //lipRectangle.setHeight(300);
       //lipRectangle.setWidth(400);


        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        showGrid();
        showGrid();
    }

    public void addImage() {

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        fc.setTitle("");

        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
        DraggableImage newImage = new DraggableImage(selectedFile);

        //newImage.setX(100);
       // newImage.setY(100);

        data.addElement(newImage);

        Transaction t = new NewElementTransaction(data,newImage);

    }

    public void addLabel() {
        EnterNameSingleton ens = EnterNameSingleton.getSingleton();
        ens.show("blahblah");
        if (ens.getSelection() == "Confirm"){
            String s = ens.getEntered();
            DraggableText text = new DraggableText(s);
            data.addElement(text);
            Transaction t = new NewElementTransaction(data, text);
            undoRedoStack.addTransaction(t);
        }


    }

    public void removeSelectedElement() {
        DraggableElement element = data.removeSelectedElement();
        Transaction transaction = new RemoveElementTransaction(data, element );
        undoRedoStack.addTransaction(transaction);
    }

    public void setBackgroundImage() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        fc.setTitle("");

        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        Background oldBackground = workspace.getCanvas().getBackground();
        Image bgImage = new Image("file:" + selectedFile.getAbsolutePath());
        BackgroundImage backgroundImage =  new BackgroundImage(bgImage, null,null,null,null);
        workspace.getCanvas().setBackground(new Background(backgroundImage));

        ChangeBackgroundTransaction transaction = new ChangeBackgroundTransaction(data, oldBackground, new Background(backgroundImage));
        undoRedoStack.addTransaction(transaction);

    }

    public void moveLeft() {

        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        double newLoc = workspace.getCanvas().getTranslateX() + 100;
        double workspaceWidth = workspace.getWorkspace().getWidth()-500;
        if (newLoc<workspaceWidth/2) {
            workspace.getCanvas().setTranslateX(newLoc);
        }


       // data.addElement(new Station());
       //fix this stuff
    }

    public void moveUp() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        double newLoc = workspace.getCanvas().getTranslateY() + 100;
        double workspaceHeight = workspace.getWorkspace().getHeight();
        if (newLoc<workspaceHeight/2) {
            workspace.getCanvas().setTranslateY(newLoc);
        }

    }

    public void moveDown() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        double newLoc = workspace.getCanvas().getTranslateY() - 100;
        double workspaceHeight = workspace.getWorkspace().getHeight();
        if (newLoc>-workspaceHeight/2) {
            workspace.getCanvas().setTranslateY(newLoc);
        }



        //if (workspace.getCanvas().getTranslateY() < 0){
            //workspace.getCanvas().setTranslateY((canvasHeight)/3 +100);
        //}//


       // fix this stuff
    }

    public void moveRight() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        double newLoc = workspace.getCanvas().getTranslateX() - 100;
        double workspaceWidth = workspace.getWorkspace().getWidth()+50;
        if (newLoc>-workspaceWidth/2) {
            workspace.getCanvas().setTranslateX(newLoc);
        }
    }

    public void listAllStations() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DisplayAllStationsOnLineSingleton.getInstance().show(workspace.getSelectedLine());
    }

    public void changeCurrentItemFontColor() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();
        Color newColor = workspace.getFontColorPicker().getValue();

        if (element instanceof Station){
            ((Station) element).getLabel().setFill(newColor);
        }

        if (element instanceof LineEnd){ ;
            ((LineEnd) element).getSubwayLine().getStart().getLabel().setFill(newColor);
            ((LineEnd) element).getSubwayLine().getEnd().getLabel().setFill(newColor);
        }

        if (element instanceof  DraggableText){
            ((DraggableText) element).setFill(newColor);
        }


    }

    public void boldText() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();

        if (element instanceof LineEnd){
            ((LineEnd) element).bolden();
        }
        if (element instanceof  Station){
            ((Station) element).bolden();
        }
        if (element instanceof  DraggableText){
            ((DraggableText) element).bolden();
        }

    }

    public void italicText() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();

        if (element instanceof LineEnd){
            ((LineEnd) element).italisize();
        }
        if (element instanceof  Station){
            ((Station) element).italisize();
        }
        if (element instanceof DraggableText){
            ((DraggableText) element).italisize();
        }

    }


    public void changeFontSize(double newSize) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();

        if (element instanceof LineEnd){
            ((LineEnd) element).changeFontSize(newSize);
        }

        if (element instanceof Station){
            ((Station)element).changeFontSize(newSize);
        }

        if (element instanceof DraggableText){
            ((DraggableText) element).changeFontSize(newSize);
        }

    }

    public void changeFontFamily(String selectedItem) {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        DraggableElement element = data.getSelectedElement();

        if (element instanceof Station){
            ((Station) element).changeFontFamily(selectedItem);
        }
        if (element instanceof  LineEnd){
            ((LineEnd) element).changeFontFamily(selectedItem);
        }
        if (element instanceof DraggableText){
            ((DraggableText) element).changeFontFamily(selectedItem);
        }

    }

    public void fixUndoRedoButtons() {
        mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
        workspace.activateUndoButton(undoRedoStack.undoIsEmpty());
        workspace.activateRedoButton(undoRedoStack.redoIsEmpty());
    }

    public UndoRedoStack getUndoRedoStack() {
        return undoRedoStack;
    }
}
