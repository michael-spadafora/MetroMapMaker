package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.Line;

public class LineSingleton extends Stage {
     static LineSingleton singleton;

    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button cancelButton;
    String selection;
    ColorPicker colorPicker;
    TextField textField;

    // CONSTANT CHOICES

    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";

    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     *
     * //@param primaryStage The owner of this modal dialog.
     */
    private LineSingleton() {}

    /**
     * The static accessor method for this singleton.
     *
     * @return The singleton object for this type.
     */
    public static LineSingleton getSingleton() {
        if (singleton == null)
            singleton = new LineSingleton();
        return singleton;
    }

    /**
     * This method initializes the singleton for use.
     *
     * @param primaryStage The window above which this
     * dialog will be centered.
     */
    public void init(Stage primaryStage) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label();
        colorPicker = new ColorPicker();
        textField = new TextField();

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button("Confirm");
        cancelButton = new Button(CANCEL);

        // MAKE THE EVENT HANDLER FOR THESE BUTTONS
        EventHandler<ActionEvent> yesNoCancelHandler = (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            LineSingleton.this.selection = sourceButton.getText();
            LineSingleton.this.hide();
        };

        // AND THEN REGISTER THEM TO RESPOND TO INTERACTIONS
        yesButton.setOnAction(yesNoCancelHandler);
        //noButton.setOnAction(yesNoCancelHandler);
        cancelButton.setOnAction(yesNoCancelHandler);

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        //buttonBox.getChildren().add(noButton);
        buttonBox.getChildren().add(cancelButton);



        // WE'LL PUT EVERYTHING HERE
        HBox infoBox = new HBox();
        infoBox.getChildren().add(textField);
        infoBox.getChildren().add(colorPicker);

        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(infoBox);
        messagePane.getChildren().add(buttonBox);

        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }

    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }

    public String getLineName(){
        return textField.getText();
    }

    public Color getSelectedColor(){
        return colorPicker.getValue();
    }

    /**
     * This method loads a custom message into the label
     * then pops open the dialog.
     *
     //* @param title The title to appear in the dialog window bar.
     *
    // * @param message Message to appear inside the dialog.
     */
    public void show(String currentName, Color currentColor) {
        selection = "null";
        setTitle("Edit Station");
        // SET THE DIALOG TITLE BAR TITLE
        //setTitle(title);
        textField.setText(currentName);
        colorPicker.setValue(currentColor);
        messageLabel.setText("Edit station");
        showAndWait();
    }

    public void show(boolean whatever){
        selection = "null";
        setTitle("Add New Station");
        colorPicker.setValue(Color.BLACK);
        messageLabel.setText("Enter name and choose a color");
        textField.setText("");
        showAndWait();
    }

}
