package gui;

import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeDialogSingleton extends Stage {
    static WelcomeDialogSingleton singleton = null;

    SplitPane splitPane = new SplitPane();
    VBox recentPane;
    final Label recentLabel = new Label( "Recent Work");
    final Label titleLable = new Label("Metro Map Maker");
    final String logoURL = "logo.png";

    public static WelcomeDialogSingleton getSingleton() {
        if (singleton == null){
            singleton = new WelcomeDialogSingleton();
        }
        return singleton;
    }

    public WelcomeDialogSingleton(){
    }


    public void init() {
    }
}
