package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;


public class WelcomeDialogSingleton extends Stage {
    static WelcomeDialogSingleton singleton = null;

    HBox splitPane;
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
    private boolean willMakeNew = false;
    private boolean loadedFile = false;

    public boolean isLoadedFile() {
        return loadedFile;
    }

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

        splitPane = new HBox();
        recentPane = new VBox();
        rightPane = new VBox();
        rightPane.setAlignment(Pos.CENTER);
        imageView = new ImageView(logoURL);
        recentPane.getChildren().add(recentLabel);
        newButton = new Button("Create new map");

        for (int i = 0; i< 6; i++){
            Button b = new Button(recentLabels.get(i));
            b.setOnAction( e->{
                this.close();
                loadedFile = true;
            });
            recentWorks.add(b);
        }
        for(Button b: recentWorks){
            recentPane.getChildren().add(b);
        }

        rightPane.getChildren().add(imageView);
        newButton.setOnAction( e->{

            this.close();
            willMakeNew = true;


        });
        rightPane.getChildren().add(newButton);


        splitPane.getChildren().add(recentPane);
        splitPane.getChildren().add(rightPane);
        messageScene = new Scene(splitPane);
        this.setScene(messageScene);

    }

  public boolean getWillMakeNew(){
        return willMakeNew;

  }


}
