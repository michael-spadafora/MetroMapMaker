package gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;

import java.awt.*;

import static css.mmmStyle.*;
import static djf.settings.AppStartupConstants.*;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;

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





    VBox editToolbar;
    FlowPane stationSubBox2a;
    FlowPane stationSubBox2b;
    VBox routeButtonBox;
    HBox decorSubBox3;
    FlowPane decorSubBox4;
    FlowPane fontSubPane3;
    Pane canvas;


    public mmmWorkspace(AppTemplate initApp) {
        app = initApp;
        gui = app.getGUI();
        initLayout();
        initControllers();
    }

    private void initControllers() {
    }

    private void initLayout() {
        initTopToolbars();
        initSidePane();
        initFileToolbarStyle();
    }

    private void initSidePane() {
        ((Button)(gui.getFileToolbar().getChildren().get(0))).setOnAction(e->{
            activateWorkspace(app.getGUI().getAppPane());
        });
        editToolbar = new VBox();
        lineBox = new VBox();
        lineSubBox1 = new FlowPane();
        //lineSubBox1.getChildren().add(metroLinesText);
        linesComboBox = new ComboBox<>();
        lineSubBox1.getChildren().add(linesComboBox);
        linesColorPicker = new ColorPicker();
        lineSubBox1.getChildren().add(linesColorPicker);

        lineSubBox2 = new FlowPane();
        addLineButton = initChildButton(lineSubBox2, "add line",false);
        removeLineButton = initChildButton(lineSubBox2, "remove line", true);
        lineSubBox3 = new FlowPane();
        addStationsToLineButton = initChildButton(lineSubBox3, "addStation.png", "Adds a station to this line", false);
        removeStationsFromLineButton = initChildButton(lineSubBox3, "removeStation.png", "Removes a station from this line", false);


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
        stationColorPicker = new ColorPicker();

       // Text spacer = new Text("\n");
        stationSubBox2 = new FlowPane();
        addStationButton = initChildButton(stationSubBox2, "New Station", false);
        deleteStationButton = initChildButton(stationSubBox2, "Delete Station", true);

        stationSubBox2a = new FlowPane();
        snapToGridButton = initChildButton(stationSubBox2a, "snap to Grid", false);

        stationSubBox2b = new FlowPane();
        moveStationLabel = initChildButton(stationSubBox2b, "Move Label", true);
        rotateStationLabel = initChildButton(stationSubBox2b, "Rotate label", true);



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
        canvas = new Pane();
        workspace = new BorderPane();
        ((BorderPane) workspace).setLeft(editToolbar);
        ((BorderPane) workspace).setCenter(canvas);

        //activateWorkspace(app.getGUI().getAppPane());



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







        //BackgroundFill backgroundFill = new BackgroundFill(Color.rgb( 216, 223, 234), null, null);
       // Background buttonbg = new Background(backgroundFill);
       // aboutButton.setBackground(buttonbg);


        topToolbar.getChildren().add(undoRedoToolbar);


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

    @Override
    public void reloadWorkspace(AppDataComponent appDataComponent) {

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
            }

            lineSubBox2.getStyleClass().add(UNBORDERED_PANE);
            {
                addLineButton.getStyleClass().add(SIDE_BUTTON);
                removeLineButton.getStyleClass().add(SIDE_BUTTON);
                addStationsToLineButton.getStyleClass().add(SIDE_BUTTON);
                removeStationsFromLineButton.getStyleClass().add(SIDE_BUTTON);
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
}

