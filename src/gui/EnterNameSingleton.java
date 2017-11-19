package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class EnterNameSingleton extends Stage{
    private static EnterNameSingleton ourInstance = new EnterNameSingleton();
    Scene messageScene;
    Button okButton;
    TextField textfield;
    VBox vbox;
    Text topText = new Text("Enter a name");

    public static EnterNameSingleton getSingleton() {
        return ourInstance;
    }

    private EnterNameSingleton() {
    }

    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        vbox = new VBox();
        vbox.getChildren().add(topText);
        okButton = new Button("ok");
        textfield = new TextField();
        okButton.setOnAction( e-> {
            String returnedName = textfield.getText();
            this.close();
        });
        vbox.getChildren().add(okButton);

        vbox.getChildren().add(textfield);

        messageScene = new Scene(vbox);
        this.setScene(messageScene);




    }

    public String getName() {
        return textfield.getText();
    }
}
