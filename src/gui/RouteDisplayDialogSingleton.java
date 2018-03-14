package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RouteDisplayDialogSingleton extends Stage {
    private static RouteDisplayDialogSingleton ourInstance = null;
    VBox vBox;
    final String route = "Route From ";
    Text text;
    Text spacer = new Text("\n\n");
    TextArea textArea;
    Button okayButton;
    Scene messageScene;

    public static RouteDisplayDialogSingleton getSingleton() {
        if (ourInstance == null){
            ourInstance = new RouteDisplayDialogSingleton();
        }
     return ourInstance;
    }

    private RouteDisplayDialogSingleton() {
    }

    public void init(Stage primaryStage){

        this.setTitle("Route");

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        text = new Text();
        textArea = new TextArea();
        textArea.setEditable(false);
        okayButton = new Button("Ok");
        okayButton.setOnAction( e-> {
            this.close();
        });

        //vBox.getChildren().add(text);
        vBox.getChildren().add(textArea);
        vBox.getChildren().add(spacer);
        vBox.getChildren().add(okayButton);


        messageScene = new Scene(vBox);
        this.setScene(messageScene);
    }

    public void show(String title, String from, String to, String message) {
        // SET THE DIALOG TITLE BAR TITLE
        setTitle(title);

        text = new Text(route + from + " To " + to + "\n \n");
        vBox.getChildren().add(0, text);
        textArea.setText(message);
        showAndWait();
    }


}
