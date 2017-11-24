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
    String selection;

    public static EnterNameSingleton getSingleton() {
        return ourInstance;
    }

    private EnterNameSingleton() {
    }

    public String getSelection() {
        return selection;
    }

    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        vbox = new VBox();
        vbox.getChildren().add(topText);
        okButton = new Button("ok");
        textfield = new TextField();
        okButton.setOnAction( e-> {
            selection = "Confirm";
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

    public void show(boolean whatever){
        selection = "null";
        textfield.setText("");
        showAndWait();

    }
}
