package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;


public class WelcomeDialogSingleton extends Stage {
    static WelcomeDialogSingleton singleton = null;

    SplitPane splitPane;
    VBox recentPane;
    VBox rightPane;
    final Label recentLabel = new Label( "Recent Work");
    final Label titleLable = new Label("Metro Map Maker");
    final String logoURL = FILE_PROTOCOL + "./src/gui/images/" + "logo.PNG";
    Image image;
    ImageView imageView;
    ArrayList<Button> recentWorks;
    ArrayList<String> recentLabels;
    Scene messageScene;
    Button newButton;



    public static WelcomeDialogSingleton getSingleton() {
        if (singleton == null){
            singleton = new WelcomeDialogSingleton();
        }
        return singleton;
    }

    public WelcomeDialogSingleton(){

    }


    public void init(Stage primaryStage) {
       // System.out.println(System.getProperty("user.dir"));
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        recentLabels = new ArrayList<String>();
        this.setTitle("Welcome to Metro Map Maker");


        for (int i = 0; i<6;i++){
            recentLabels.add("Placeholder");
        }

        recentWorks = new ArrayList<Button>();

        splitPane = new SplitPane();
        recentPane = new VBox();
        rightPane = new VBox();
        imageView = new ImageView(logoURL);
        recentPane.getChildren().add(recentLabel);

        newButton = new Button("Create new map");

        for (int i = 0; i< 6; i++){
            Button b = new Button(recentLabels.get(i));
            recentWorks.add(b);
        }
        for(Button b: recentWorks){
            recentPane.getChildren().add(b);
        }

        rightPane.getChildren().add(imageView);
        rightPane.getChildren().add(newButton);


        splitPane.getItems().add(recentPane);
        splitPane.getItems().add(rightPane);
        messageScene = new Scene(splitPane);
        this.setScene(messageScene);







    }
}
