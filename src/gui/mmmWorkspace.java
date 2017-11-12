package gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

import static djf.settings.AppPropertyType.*;
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
        initFileToolbarStyle();



    }

    private void initTopToolbars() {
        FlowPane topToolbar = gui.getTopToolbarPane();
        //topToolbar.setAlignment(Pos.CENTER);
        gui.getFileToolbar().setHgap(5);
        gui.getFileToolbar().setAlignment(Pos.CENTER);
        gui.getFileToolbar().setPrefWrapLength(200);
        //HBox temp = (Hbox) topToolbar.getChildren().get(0);


        undoRedoToolbar = new FlowPane();
        undoRedoToolbar.setPrefWrapLength(150);
        //undoRedoToolbar.setPadding(0);
        undoRedoToolbar.setAlignment(Pos.CENTER);
        undoRedoToolbar.setHgap(5);
        //editToolbar.setColumnHalignment(HPos.CENTER);
        undoButton = initChildButton(undoRedoToolbar, "Undo.png", "removes current item, and copies it to clipboard", true );
        redoButton = initChildButton(undoRedoToolbar, "Redo.png", "copies currently selected item to clipboard", true );
        aboutButton = initChildButton(undoRedoToolbar, "About.png", "pastes clipboard", false );

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
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);

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
        //topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        undoRedoToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        redoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        aboutButton.getStyleClass().add(CLASS_FILE_BUTTON);
        //pasteButton.getStyleClass().add(CLASS_FILE_BUTTON);
       // loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
       // saveButton.getStyleClass().add(CLASS_FILE_BUTTON);
       // exitButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
}
