import data.mmmData;
import djf.AppTemplate;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import file.mmmFiles;
import gui.EnterNameSingleton;
import gui.RouteDisplayDialogSingleton;
import gui.WelcomeDialogSingleton;
import gui.mmmWorkspace;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;

import java.io.File;
import java.util.Locale;

public class MetroMapMakerApp extends AppTemplate{
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS THE
        // FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
        fileComponent = new mmmFiles();
        dataComponent = new mmmData(this);
        workspaceComponent = new mmmWorkspace(this);
    }

    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method
     * inherited from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // LET'S START BY INITIALIZING OUR DIALOGS
        AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
        messageDialog.init(primaryStage);
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.init(primaryStage);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        WelcomeDialogSingleton welc = WelcomeDialogSingleton.getSingleton();
        welc.init(primaryStage);
        welc.showAndWait();
        RouteDisplayDialogSingleton rdds = RouteDisplayDialogSingleton.getSingleton();
        rdds.init(primaryStage);
        EnterNameSingleton ens = EnterNameSingleton.getSingleton();
        ens.init(primaryStage);
        String name = "";
        if (welc.getWillMakeNew()){
            ens.showAndWait();
            name = ens.getName();
            if (new File(name).exists()){
                 messageDialog.show("file exists already", "file exists already");
            }

        }

        //rdds.show("placeholder", "placeholder", "placeholder", "placeholder");

        try {
            // LOAD APP PROPERTIES, BOTH THE BASIC UI STUFF FOR THE FRAMEWORK
            // AND THE CUSTOM UI STUFF FOR THE WORKSPACE
            boolean success = loadProperties(APP_PROPERTIES_FILE_NAME);
            if (!success){
                throw new Exception();
            }

            if (success) {
                // GET THE TITLE FROM THE XML FILE
                String appTitle = props.getProperty(APP_TITLE) + " " + name;

                // BUILD THE BASIC APP GUI OBJECT FIRST
                gui = new AppGUI(primaryStage, appTitle, this);

                // THIS BUILDS ALL OF THE COMPONENTS, NOTE THAT
                // IT WOULD BE DEFINED IN AN APPLICATION-SPECIFIC
                // CHILD CLASS
                buildAppComponentsHook();

                // NOW OPEN UP THE WINDOW
                primaryStage.show();

                if (welc.getWillMakeNew() || welc.isLoadedFile()){
                    this.getWorkspaceComponent().activateWorkspace(getGUI().getAppPane());
                }
            }



        }catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("error loading", "Error loading");
        }
    }
}
