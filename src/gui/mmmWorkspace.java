package gui;

import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import data.*;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import properties_manager.PropertiesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static css.mmmStyle.*;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import static javafx.scene.paint.Color.BLUE;

public class mmmWorkspace extends AppWorkspaceComponent {

    AppGUI gui;
    AppTemplate app;

    protected Button undoButton;
    protected Button redoButton;
   // protected Button pasteButton;
    protected FlowPane undoRedoToolbar;
    protected  Button aboutButton;

    VBox lineBox;
    FlowPane lineSubBox1;
    FlowPane lineSubBox2;
    FlowPane lineSubBox3;
    FlowPane lineSubBox4;
    Text metroLinesText = new Text( "Metro Line");
    ComboBox<String> linesComboBox;
    ColorPicker linesColorPicker;
    Button addLineButton;
    Button removeLineButton;
    Button addStationsToLineButton;
    Button removeStationsFromLineButton;
    Slider lineThicknessSlider;

    VBox stationBox;
    ComboBox<String> stationComboBox;
    ColorPicker stationColorPicker;
    FlowPane stationSubBox1;
    FlowPane stationSubBox2;
    FlowPane stationSubBox3;
    Button addStationButton;
    Button deleteStationButton;
    Button snapToGridButton;
    Button moveStationLabel;
    Button rotateStationLabel;
    Slider stationRadiusSlider;

    VBox fontBox;

    FlowPane fontSubPane1;
    ColorPicker fontColorPicker;
    Text fontText = new Text("Font:\t\t\t\t            ");
    FlowPane fontSubPane2;
    Button boldButton;
    Button italicButton;
    ComboBox fontClassComboBox;
    ComboBox fontSizeComboBox;



    HBox routeBox;
    ComboBox<String> fromComboBox, toComboBox;
    Button findRouteButton;
    VBox toFromBox;

    VBox decorBox;
    HBox decorSubBox1;
    Text decorLabel = new Text("Background Color:\t\t\t      ");
    ColorPicker backgroundColorPicker;
    HBox decorSubBox2;
    Button setImageBackgroundButton, addImageButton, addLabelButton, removeElementButton;

    VBox navigationBox;
    FlowPane navigationSubBox1;
    Text navigationText = new Text("Navigation");
    CheckBox showGrid;
    FlowPane navigationSubBox2;

    Button zoomInButton;
    Button zoomOutButton;
    Button biggerMapButton;
    Button smallerMapButton;


    MapEditController mapEditController;


    VBox editToolbar;
    FlowPane stationSubBox2a;
    FlowPane stationSubBox2b;
    VBox routeButtonBox;
    HBox decorSubBox3;
    FlowPane decorSubBox4;
    FlowPane fontSubPane3;
    Pane canvasWrapper;
    Pane canvas;
    Button editLineButton;
    Button stationListButton;
    CanvasController canvasController;

    Button exportButton;
    Button importButton;


    ArrayList<Line> gridLines;

    double oldThickness;
    double newThickness;



    public mmmWorkspace(AppTemplate initApp) {
        canvas = new Pane();
        canvasWrapper = new Pane();

        app = initApp;
        gui = app.getGUI();
        initLayout();
        initControllers();
        Platform.setImplicitExit(false);
        app.getGUI().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                AppYesNoCancelDialogSingleton appYesNoCancelDialogSingleton = AppYesNoCancelDialogSingleton.getSingleton();
                appYesNoCancelDialogSingleton.show("Save Work","Do you want to save your work?");
                String selection = appYesNoCancelDialogSingleton.getSelection();
                if(selection ==AppYesNoCancelDialogSingleton.YES)
                {
                    PropertiesManager props = PropertiesManager.getPropertiesManager();
                    try {
                        //String filepath =
                        //app.getFileComponent().saveData(app.getDataComponent(), filepath);
                        //app.getGUI().getFileController().handleSaveRequest();
                        String pathname = app.getGUI().getWindow().getTitle();
                        pathname = pathname.substring(pathname.indexOf("r")+11);
                        //String newPathname = pathname.substring(pathname.indexOf("r"));
                        pathname = PATH_WORK+ pathname;//+ " Metro.json";
                        app.getFileComponent().saveData(app.getDataComponent(), pathname);
                        System.exit(1);
                    } catch (Exception ex) {
                        System.out.print("ioexception");
                    }
                }
                if(selection ==AppYesNoCancelDialogSingleton.NO)
                {
                    System.exit(2);
                }
            }
        });
    }

    public Slider getLineThicknessSlider() {
        return lineThicknessSlider;
    }

    public MapEditController getMapEditController() {
        return mapEditController;
    }

    private void initControllers() {




        fontClassComboBox.getItems().addAll(Font.getFamilies());

        mapEditController = new MapEditController(app);

        findRouteButton.setOnAction(e->{
            mmmData data = (mmmData) app.getDataComponent();
            Station start = data.getStationFromString(fromComboBox.getSelectionModel().getSelectedItem());
            Station end =  data.getStationFromString(toComboBox.getSelectionModel().getSelectedItem());

            if (start != null && end != null) {
                start.setPath(new ArrayList<>());
                end.setPath(new ArrayList<>());

                for (Node node : data.getElements()) {
                    if (node instanceof Station) {
                        ((Station) node).setPath(new ArrayList<>());
                    }

                }

                FindRouteController findRouteController = new FindRouteController();
                findRouteController.findRoute(start, end, new ArrayList<Station>());

                if (end.getPath().size() != 0) {
                    end.getPath().add(end);
                }

                DisplayAllStationsOnLineSingleton displayAllStationsOnLineSingleton = DisplayAllStationsOnLineSingleton.getInstance();
                displayAllStationsOnLineSingleton.show(start, end);

            }





        });

        undoButton.setOnAction( e-> {
            mapEditController.getUndoRedoStack().undoTransaction();
            mapEditController.fixUndoRedoButtons();
        });

        redoButton.setOnAction( e-> {
            mapEditController.getUndoRedoStack().redoTransaction();
            mapEditController.fixUndoRedoButtons();
        });

        fontClassComboBox.setOnAction( e-> {
            mapEditController.changeFontFamily((String)fontClassComboBox.getSelectionModel().getSelectedItem());
        });

        ArrayList<Integer> fontSizes = new ArrayList<>();

        for (int i = 1; i<=24; i+=1){
            fontSizes.add(2*i);
        }

        fontSizeComboBox.setItems(FXCollections.observableArrayList(fontSizes));
        fontSizeComboBox.getSelectionModel().select(4);

        fontSizeComboBox.setOnAction( e-> {



            mapEditController.changeFontSize((int) fontSizeComboBox.getSelectionModel().getSelectedItem());
        });

        italicButton.setOnAction(e->{
            mapEditController.italicText();
        });
        boldButton.setOnAction(e-> {{
                mapEditController.boldText();
        }});

        fontColorPicker.setOnAction(e->{
            mapEditController.changeCurrentItemFontColor();
        });
        
        stationListButton.setOnAction(e->{
            mapEditController.listAllStations();
        });

        app.getGUI().getAppPane().setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code){
                case A: mapEditController.moveLeft();
                    break;
                case W: mapEditController.moveUp();
                    break;
                case S: mapEditController.moveDown();
                    break;
                case D: mapEditController.moveRight();
                    break;
            }
        });
        setImageBackgroundButton.setOnAction(e->{
            mapEditController.setBackgroundImage();
        });
        removeElementButton.setOnAction( e->{
            mapEditController.removeSelectedElement();

        });
        aboutButton.setOnAction( e-> {
            LearnMoreSingleton.getInstance().show();
        });
        addLabelButton.setOnAction( e->{
            mapEditController.addLabel();
        });


        addImageButton.setOnAction( e-> {
            mapEditController.addImage();
        });
        biggerMapButton.setOnAction( e-> {
            mapEditController.enlargeMap();

        });
        smallerMapButton.setOnAction(e-> {
            mapEditController.shrinkMap();
        });

        snapToGridButton.setOnAction( e->{
            mapEditController.snapToGrid();
        });

        showGrid.setOnAction( e-> {
            mapEditController.showGrid();
        });
        rotateStationLabel.setOnAction( e->{
            mapEditController.rotateSelectedStationLabel();
        });
        moveStationLabel.setOnAction( e-> {
            mapEditController.orbitSelectedStationLabel();
        });

        backgroundColorPicker.setOnAction( e-> {
            Color color = backgroundColorPicker.getValue();
            mapEditController.setBackgroundColor(color);
        });

        zoomInButton.setOnAction( e-> {
            mapEditController.zoomIn();
        });

        zoomOutButton.setOnAction( e-> {
            mapEditController.zoomOut();
        });

        stationRadiusSlider.setMajorTickUnit(2);
        stationRadiusSlider.setValue(10);
        stationRadiusSlider.setMin(2);
        stationRadiusSlider.setMax(30);

        stationRadiusSlider.setOnMouseDragged( e-> {
            double radBro = 0;
            try{
                radBro =  stationRadiusSlider.getValue();
                mapEditController.changeStationRadius(radBro);
               // getSelectedStation().changeRadius(radBro);
            }
            catch (Exception ex){

            }
        });

        stationRadiusSlider.setOnMousePressed( e->{
            oldThickness = getSelectedStation().getRadius();
        });

        stationRadiusSlider.setOnMouseReleased( e->{
            newThickness = getSelectedStation().getRadius();
            Transaction t = new StationRadiusTransaction(getSelectedStation(), oldThickness, newThickness);
            mapEditController.getUndoRedoStack().addTransaction(t);

        });

        lineThicknessSlider.setMajorTickUnit(1);
        lineThicknessSlider.setValue(6);
        lineThicknessSlider.setMin(1);
        lineThicknessSlider.setMax(10);


        lineThicknessSlider.setOnMouseDragged( e-> {
            double thicc = 0;
            try{
                thicc = lineThicknessSlider.getValue();
                mapEditController.changeLineThickness(thicc);
                //getSelectedLine().setThickness(thicc);
            }
            catch (Exception ex){

            }
        });

        lineThicknessSlider.setOnMousePressed( e->{
            oldThickness = getSelectedLine().getStrokeWidth();
        });

        lineThicknessSlider.setOnMouseReleased( e->{
            newThickness = getSelectedLine().getStrokeWidth();
            Transaction t = new LineThicknessTransaction(getSelectedLine(), oldThickness, newThickness);
            mapEditController.getUndoRedoStack().addTransaction(t);
        });


        AppFileController fileController = gui.getFileController();

        exportButton.setOnAction( e -> {
            handleExportRequest();
        });

        stationColorPicker.setOnAction( e-> {
            mapEditController.processStationColorChange(stationColorPicker.getValue());
        });

        linesColorPicker.setOnAction( e-> {
            mapEditController.processLineColorChange(linesColorPicker.getValue());
        });


        addLineButton.setOnAction(e-> {
            mapEditController.processAddLine();
        });

        removeLineButton.setOnAction(e-> {
            mapEditController.processRemoveLine();
        });

        editLineButton.setOnAction(e-> {
            mapEditController.processEditLine();
        });


        addStationsToLineButton.setOnAction(e-> {
            mapEditController.processAddStationToLine();
        });

        removeStationsFromLineButton.setOnAction(e-> {
            mapEditController.processRemoveStationFromLine();
        });

        addStationButton.setOnAction(e-> {
            mapEditController.processAddStation();
        });

        deleteStationButton.setOnAction(e-> {
            mapEditController.processRemoveStation();
        });

        linesComboBox.setOnAction(e-> {
            mapEditController.processLineComboBoxClick();
        });

        stationComboBox.setOnAction(e-> {
            mapEditController.processStationComboBoxClick();
        });

        // MAKE THE CANVAS CONTROLLER
        canvasController = new CanvasController(app);
        canvas.setOnMousePressed(e -> {
            canvasController.processCanvasMousePress((int) e.getX(), (int) e.getY(), e.getClickCount());
            mmmData dataManager = (mmmData) app.getDataComponent();

            // e.getClickCount()
        });

        canvas.setOnMouseReleased(e -> {
            canvasController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());
        });
        canvas.setOnMouseDragged(e -> {
//            golData data = (golData) app.getDataComponent();
//            boolean isSizing = true;
//            if (data.getState().equals(golState.SIZING_SHAPE))
//            {
//                isSizing = true;
//
//            }
            canvasController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());


        });
       /* Button removeLineButton;
        Button addStationsToLineButton;
        Button removeStationsFromLineBu tton;
        Slider lineThicknessSlider;*/

    }

    private void initLayout(){
        //canvas = new Pane();
        initTopToolbars();
        initSidePane();
        initFileToolbarStyle();
    }

    /*public void showGridLines(ArrayList<Line> lines){
        gridLines = lines;
        mmmData data = (mmmData) app.getDataComponent();
        for (Line line: gridLines){


        }

    }*/

    private void initSidePane() {
        AppFileController fileController = app.getGUI().getFileController();
        ((Button)(gui.getFileToolbar().getChildren().get(0))).setOnAction(e->{
            activateSidebar();
            fileController.handleNewRequest();
        });

        ((Button)(gui.getFileToolbar().getChildren().get(1))).setOnAction(e->{
            activateSidebar();
            fileController.handleLoadRequest();
        });
        editToolbar = new VBox();
        lineBox = new VBox();
        lineSubBox1 = new FlowPane();
        //lineSubBox1.getChildren().add(metroLinesText);
        linesComboBox = new ComboBox<>();
        lineSubBox1.getChildren().add(linesComboBox);
        editLineButton = new Button("edit");
        editLineButton.setDisable(false);
        lineSubBox1.getChildren().add(editLineButton);
        linesColorPicker = new ColorPicker();
        lineSubBox1.getChildren().add(linesColorPicker);

        lineSubBox2 = new FlowPane();
        addLineButton = initChildButton(lineSubBox2, "add line",false);
        removeLineButton = initChildButton(lineSubBox2, "remove line", false);
        lineSubBox3 = new FlowPane();
        addStationsToLineButton = initChildButton(lineSubBox3, "addStation.png", "Adds a station to this line", false);
        removeStationsFromLineButton = initChildButton(lineSubBox3, "removeStation.png", "Removes a station from this line", false);
        stationListButton = initChildButton(lineSubBox3, "list all stations", false);


        linesColorPicker.setPrefWidth(30);

        lineSubBox4 = new FlowPane();
        lineThicknessSlider = new Slider();
        lineSubBox4.getChildren().add(lineThicknessSlider);






        //lineBox.getChildren().add(metroLinesText);
        lineBox.getChildren().add(lineSubBox1);
        lineBox.getChildren().add(lineSubBox2);
        lineBox.getChildren().add(lineSubBox3);
        lineBox.getChildren().add(lineSubBox4);
        editToolbar.getChildren().add(lineBox);


        //////////Station toolbar


        stationBox = new VBox();

        stationSubBox1 = new FlowPane();

        stationComboBox = new ComboBox<>();
        stationComboBox.setMaxWidth(150);
        stationColorPicker = new ColorPicker();
        stationColorPicker.setMaxWidth(50);
        stationSubBox1.getChildren().add(stationComboBox);
        stationSubBox1.getChildren().add(stationColorPicker);

       // Text spacer = new Text("\n");
        stationSubBox2 = new FlowPane();
        addStationButton = initChildButton(stationSubBox2, "New Station", false);
        deleteStationButton = initChildButton(stationSubBox2, "Delete Station", false);

        stationSubBox2a = new FlowPane();
        snapToGridButton = initChildButton(stationSubBox2a, "snap to Grid", false);

        stationSubBox2b = new FlowPane();
        moveStationLabel = initChildButton(stationSubBox2b, "Move Label", false);
        rotateStationLabel = initChildButton(stationSubBox2b, "Rotate label", false);



        stationSubBox3 = new FlowPane();
        stationRadiusSlider = new Slider();
        stationSubBox3.getChildren().add(stationRadiusSlider);

        stationBox.getChildren().add(stationSubBox1);
        stationBox.getChildren().add(stationSubBox2);
        stationBox.getChildren().add(stationSubBox2b);
        stationBox.getChildren().add(stationSubBox2a);
        stationBox.getChildren().add(stationSubBox3);

        editToolbar.getChildren().add(stationBox);

        /////////////end station toolbar


        routeBox = new HBox();
        fromComboBox = new ComboBox<>();
        toComboBox = new ComboBox<>();



        toFromBox = new VBox();
        toFromBox.getChildren().add(fromComboBox);
        toFromBox.getChildren().add(toComboBox);

        routeButtonBox = new VBox();

        fromComboBox.setPrefWidth(100);
        toComboBox.setPrefWidth(100);


        ////////////////////////////////////////find route
        findRouteButton = initChildButton(routeButtonBox, "findRoute.png", "find route", false);

        routeBox.getChildren().add(toFromBox);
        routeBox.getChildren().add(routeButtonBox);
        routeButtonBox.setAlignment(Pos.CENTER);
        editToolbar.getChildren().add(routeBox);

        /////find route

        decorBox = new VBox();
        decorSubBox1 = new HBox();
       // Text decorLabel = new Text("backgroundColor");
        backgroundColorPicker = new ColorPicker();
        decorSubBox1.getChildren().add(decorLabel);
        decorSubBox1.getChildren().add(backgroundColorPicker);

        decorSubBox2 = new HBox();
       // Button setImageBackgroundButton, addImageButton, addLabelButton, removeElementButton;
        setImageBackgroundButton = initChildButton(decorSubBox2, "Set image background", false);
        decorSubBox3 = new HBox();
        removeElementButton = initChildButton(decorSubBox3, "Remove Element", false);

        decorSubBox4 = new FlowPane();
        addImageButton = initChildButton(decorSubBox4, "Add image", false);
        addLabelButton = initChildButton(decorSubBox4, "Add label", false);

        decorBox.getChildren().add(decorSubBox1);
        decorBox.getChildren().add(decorSubBox2);
        decorBox.getChildren().add(decorSubBox3);
        decorBox.getChildren().add(decorSubBox4);
        editToolbar.getChildren().add(decorBox);


        /////Font toolbar
        fontBox = new VBox();

        fontSubPane1 = new FlowPane();
        fontColorPicker = new ColorPicker();
       // Text fontText = new Text("Font:\t\t\t                      ");
        fontSubPane1.getChildren().add(fontText);
        fontSubPane1.getChildren().add(fontColorPicker);
        fontSubPane2 = new FlowPane();
        boldButton = initChildButton(fontSubPane2, "Bold", false);
        boldButton.setPrefWidth(50);
        italicButton= initChildButton(fontSubPane2, "Italic", false);

        fontSubPane3 = new FlowPane();
        fontClassComboBox = new ComboBox();
        fontSizeComboBox = new ComboBox();
        fontSubPane3.getChildren().add(fontClassComboBox);
        fontSubPane3.getChildren().add(fontSizeComboBox);

        fontBox.getChildren().add(fontSubPane1);
        fontBox.getChildren().add(fontSubPane2);
        fontBox.getChildren().add(fontSubPane3);

        editToolbar.getChildren().add(fontBox);


//////////////////////////

         navigationBox = new VBox();
         navigationSubBox1 = new FlowPane();
         navigationText = new Text("Navigation\t\t");
         showGrid = new CheckBox("Show Grid");
         navigationSubBox1.getChildren().add(navigationText);
         navigationSubBox1.getChildren().add(showGrid);

         navigationSubBox2 = new FlowPane();
        zoomInButton = initChildButton(navigationSubBox2, "ZoomIn.png", "Zoom in on the map", false );
        zoomOutButton = initChildButton(navigationSubBox2, "ZoomOut.png", "Zoom out of the map", false );
        biggerMapButton = initChildButton(navigationSubBox2, "enlargeMap.png", "Enlarges the map", false);
        smallerMapButton = initChildButton(navigationSubBox2, "shrinkMap.png", "shrinks the map", false);


        navigationBox.getChildren().add(navigationSubBox1);
        navigationBox.getChildren().add(navigationSubBox2);
        editToolbar.getChildren().add(navigationBox);
        /////////////

        workspace = new BorderPane();

        mmmData data = (mmmData) app.getDataComponent();
        data.setShapes(canvas.getChildren());

        //canvas.toBack();
        ((BorderPane) workspace).setCenter(canvas);
        ((BorderPane) workspace).setCenter(canvasWrapper);
        canvasWrapper.getChildren().add(canvas);
        ((BorderPane) workspace).setTop(app.getGUI().getTopToolbarPane());
        ((BorderPane) workspace).setLeft(editToolbar);

        //canvasWrapper.setBorder(new Border(new BorderStroke(Color.BLACK,
              //  BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));

        canvas.setMinWidth(1137);
        canvas.setMinHeight(683);

        editToolbar.setDisable(true);
        editToolbar.setOpacity(0);

        //canvas.setBackground(new Background(new BackgroundFill(BLUE, null, null)));

        //activateWorkspace(app.getGUI().getAppPane());



    }

    public Pane getCanvasWrapper(){
        return canvasWrapper;
    }

    private void initTopToolbars() {
        FlowPane topToolbar = gui.getTopToolbarPane();
        gui.getFileToolbar().setHgap(5);
        gui.getFileToolbar().setAlignment(Pos.CENTER);
        gui.getFileToolbar().setPrefWrapLength(200);
        //HBox temp = (Hbox) topToolbar.getChildren().get(0);

        undoRedoToolbar = new FlowPane();
        undoRedoToolbar.setPrefWrapLength(150);
        undoRedoToolbar.setAlignment(Pos.CENTER);
        undoRedoToolbar.setHgap(5);



        undoButton = initChildButton(undoRedoToolbar, "Undo.png", "removes current item, and copies it to clipboard", true);
        redoButton = initChildButton(undoRedoToolbar, "Redo.png", "copies currently selected item to clipboard", true);
        aboutButton = initChildButton(undoRedoToolbar, "About.png", "about us", false);

        exportButton = initChildButton(gui.getFileToolbar(), "Export.png", "exports current map", false);







        //BackgroundFill backgroundFill = new BackgroundFill(Color.rgb( 216, 223, 234), null, null);
       // Background buttonbg = new Background(backgroundFill);
       // aboutButton.setBackground(buttonbg);


        topToolbar.getChildren().add(undoRedoToolbar);

        //((BorderPane) workspace).setTop(app.getGUI().getTopToolbarPane());

        aboutButton.setAlignment(Pos.CENTER_RIGHT);

    }

    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + icon;
        Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(tooltip);
        button.setTooltip(buttonTooltip);

        // PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);

        // AND RETURN THE COMPLETED BUTTON
        return button;
    }

    public Button initChildButton(Pane toolbar, String text, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
       // String imagePath = FILE_PROTOCOL + PATH_IMAGES + text;
       // Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        //button.setGraphic(new ImageView(buttonImage));
        button.setText(text);
        //Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
       // button.setTooltip(buttonTooltip);

        // PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);

        // AND RETURN THE COMPLETED BUTTON
        return button;
    }

    @Override
    public void resetWorkspace() {

    }

    public void updateLineComboBox(ObservableList<Node> nodes){
        ArrayList<String> lines = new ArrayList<>();
        for (Node node: nodes){
            if (node instanceof SubwayLine){
                lines.add(((SubwayLine) node).getStart().getLabel().getText());
            }
        }

        linesComboBox.getItems().clear();
        linesComboBox.getItems().addAll(lines);
        linesComboBox.getSelectionModel().select(0);
    }

    public void updateStationComboBox(ObservableList<Node> nodes){
        ArrayList<String> lines = new ArrayList<>();
        for (Node node: nodes){
            if (node instanceof Station){
                lines.add(((Station) node).getLabel().getText());
            }
        }

        stationComboBox.getItems().clear();
        stationComboBox.getItems().addAll(lines);
        toComboBox.getItems().addAll(lines);
        fromComboBox.getItems().addAll(lines);
        stationComboBox.getSelectionModel().select(0);
    }

    public void emptyComboBoxes(){
      //  linesComboBox.getSelectionModel().select("");
     //  stationComboBox.getSelectionModel().select("");
    }

    @Override
    public void reloadWorkspace(AppDataComponent appDataComponent) {
        mmmData  data = (mmmData) appDataComponent;
       // app.getDataComponent().resetData();


    }

    private void initFileToolbarStyle() {
        undoRedoToolbar.getStyleClass().add(CLASS_BORDERED_PANE);{
            undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
            redoButton.getStyleClass().add(CLASS_FILE_BUTTON);
            aboutButton.getStyleClass().add(CLASS_FILE_BUTTON);
        }
        lineBox.getStyleClass().add(SIDE_PANE);{

            lineSubBox1.getStyleClass().add(UNBORDERED_PANE);{
                linesComboBox.getStyleClass().add(COMBO_BOX);
                linesComboBox.setMaxWidth(130);
            }

            lineSubBox2.getStyleClass().add(UNBORDERED_PANE);
            {
                addLineButton.getStyleClass().add(SIDE_BUTTON);
                removeLineButton.getStyleClass().add(SIDE_BUTTON);
                addStationsToLineButton.getStyleClass().add(SIDE_BUTTON);
                removeStationsFromLineButton.getStyleClass().add(SIDE_BUTTON);
                stationListButton.getStyleClass().add(FULL_WIDTH_SIDE_BUTTON);
            }

            lineSubBox3.getStyleClass().add(UNBORDERED_PANE);
            lineSubBox4.getStyleClass().add(UNBORDERED_PANE);{
                lineThicknessSlider.getStyleClass().add(SLIDER);
            }
        }


        stationBox.getStyleClass().add(SIDE_PANE);{
            stationSubBox1.getStyleClass().add(UNBORDERED_PANE);{
                stationComboBox.getStyleClass().add(COMBO_BOX) ;
                stationColorPicker.getStyleClass().add(COLOR_PICKER);

            }
            stationSubBox2.getStyleClass().add(UNBORDERED_PANE);{
                addStationButton.getStyleClass().add(SIDE_BUTTON);
                deleteStationButton.getStyleClass().add(SIDE_BUTTON);
                moveStationLabel.getStyleClass().add(SIDE_BUTTON);
                rotateStationLabel.getStyleClass().add(SIDE_BUTTON);
                snapToGridButton.getStyleClass().add(FULL_WIDTH_SIDE_BUTTON);
            }
            stationSubBox2a.getStyleClass().add(UNBORDERED_PANE);{

            }
            stationSubBox2b.getStyleClass().add(UNBORDERED_PANE);{

            }
            stationSubBox3.getStyleClass().add(UNBORDERED_PANE);{
                stationRadiusSlider.getStyleClass().add(SLIDER);
            }



        }

        routeBox.getStyleClass().add(SIDE_PANE);{
            toFromBox.getStyleClass().add(VERTICAL_SIDE_PANE);{
               toComboBox.getStyleClass().add(COMBO_BOX);
               fromComboBox.getStyleClass().add(COMBO_BOX);
            }
            routeButtonBox.getStyleClass().add(VERTICAL_SIDE_PANE);{
            findRouteButton.getStyleClass().add(SIDE_BUTTON);}

        }

        decorBox.getStyleClass().add(SIDE_PANE);{
            decorSubBox1.getStyleClass().add(UNBORDERED_PANE);{
                backgroundColorPicker.getStyleClass().add(COLOR_PICKER);
                backgroundColorPicker.setMaxWidth(30);

            }
            decorSubBox2.getStyleClass().add(UNBORDERED_PANE);{
                addImageButton.getStyleClass().add(SIDE_BUTTON);
                setImageBackgroundButton.getStyleClass().add(FULL_WIDTH_SIDE_BUTTON);
                addLabelButton.getStyleClass().add(SIDE_BUTTON);
                removeElementButton.getStyleClass().add(FULL_WIDTH_SIDE_BUTTON);

            }
            decorSubBox3.getStyleClass().add(UNBORDERED_PANE);
            decorSubBox4.getStyleClass().add(UNBORDERED_PANE);

        }

        fontBox.getStyleClass().add(SIDE_PANE);{
            fontSubPane1.getStyleClass().add(UNBORDERED_PANE);{
                fontColorPicker.getStyleClass().add(COLOR_PICKER);
                fontColorPicker.setMaxWidth(30);
        }
            fontSubPane2.getStyleClass().add(UNBORDERED_PANE);{
                boldButton.getStyleClass().add(SIDE_BUTTON);
                italicButton.getStyleClass().add(SIDE_BUTTON);
            }

            fontSubPane3.getStyleClass().add(UNBORDERED_PANE);{
                fontClassComboBox.getStyleClass().add(COMBO_BOX);
                fontSizeComboBox.getStyleClass().add(COMBO_BOX);
                fontClassComboBox.setMaxWidth(100);
                fontSizeComboBox.setMaxWidth(100);
            }
        }

        navigationBox.getStyleClass().add(SIDE_PANE);{
            navigationSubBox1.getStyleClass().add(UNBORDERED_PANE);{

            }
            navigationSubBox2.getStyleClass().add(UNBORDERED_PANE);{
                zoomInButton.getStyleClass().add(SIDE_BUTTON);
                zoomOutButton.getStyleClass().add(SIDE_BUTTON);

                biggerMapButton.getStyleClass().add(SIDE_BUTTON);
                smallerMapButton.getStyleClass().add(SIDE_BUTTON);
            }
        }



    }

    public ComboBox<String> getLinesComboBox() {
        return linesComboBox;
    }

    public String getSelectedLineName(){
        return linesComboBox.getSelectionModel().getSelectedItem();
    }

    public SubwayLine getSelectedLine(){
        mmmData data = (mmmData) app.getDataComponent();
        return data.getLineFromString(getSelectedLineName());
    }

    public String getSelectedStationName(){ return stationComboBox.getSelectionModel().getSelectedItem();}

    public ComboBox<String> getStationComboBox() {
        return stationComboBox;
    }

    public Station getSelectedStation() {
        mmmData data = (mmmData) app.getDataComponent();
        return data.getStationFromString(getSelectedStationName());
    }

    public void setSelectedStationColor(Color c){
        stationColorPicker.setValue(c);
    }

    public void setSelectedLineColor(Color c){
        linesColorPicker.setValue(c);

    }

    public Pane getCanvas() {
        return canvas;
    }

    public void handleExportRequest() {
        // WE'LL NEED THIS TO GET CUSTOM STUFF
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            // MAYBE WE ALREADY KNOW THE FILE
            // OTHERWISE WE NEED TO PROMPT THE USER

                // PROMPT THE USER FOR A FILE NAME
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
                fc.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

                File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                if (selectedFile != null) {
                    exportWork(selectedFile);
                }

        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }


    private void exportWork(File selectedFile) throws IOException {
        // SAVE IT TO A FILE
        app.getFileComponent().exportData(app.getDataComponent(), selectedFile.getPath());

        // MARK IT AS SAVED
        //currentWorkFile = selectedFile;
       // saved = true;

        // TELL THE USER THE FILE HAS BEEN SAVED
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show("success","successfully exported");

        // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
        // THE APPROPRIATE CONTROLS
        //app.getGUI().updateToolbarControls(saved);
    }


    public void setSelectedLineWidth(double selectedLineWidth) {
        lineThicknessSlider.setValue(selectedLineWidth);
    }

    public void setSelectedStationWidth(double selectedStationWidth) {
        stationRadiusSlider.setValue(selectedStationWidth);
    }

    public void activateSidebar() {
        editToolbar.setOpacity(1);
        editToolbar.setDisable(false);
    }

    public void setSelectedTextColor(Paint selectedTextColor) {
        fontColorPicker.setValue((Color) selectedTextColor);
    }

    public ColorPicker getFontColorPicker() {
        return fontColorPicker;
    }

    public void setSelectedTextSize(int selectedTextSize) {
        fontSizeComboBox.getSelectionModel().select(selectedTextSize/2 - 1);
    }

    public void setSelectedTextFamily(String selectedTextFamily) {
        fontClassComboBox.getSelectionModel().select(selectedTextFamily);
    }



    public void activateUndoButton(boolean b) {
        undoButton.setDisable(b);
    }

    public void activateRedoButton(boolean b) {
        redoButton.setDisable(b);
    }
}

