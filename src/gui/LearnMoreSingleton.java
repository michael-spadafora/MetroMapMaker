package gui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LearnMoreSingleton extends Stage{
    private static LearnMoreSingleton ourInstance = new LearnMoreSingleton();

    public static LearnMoreSingleton getInstance() {
        return ourInstance;
    }

    private LearnMoreSingleton() {
    }

    VBox overallBox;
    String applicationName = "MetroMapMaker";
    String debuggingEnterpriseNames = "Debugging Enterprise Assosciates: Michael Spadafora, " +
            "Richard McKenna, Ritwik Banerjee, Eugene Stark";
    String frameworksUsed = "DesktopJavaFramework was used in the making of this application";
    String developedYear = "Year Developed: 2017";

    public void init (Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        overallBox = new VBox();
        String[] strings = new String[]{applicationName, debuggingEnterpriseNames,frameworksUsed,developedYear};
        Text[] texts = new Text[4];

        for (int i = 0; i<4; i++){
            texts[i] = new Text(strings[i]);
            overallBox.getChildren().add(texts[i]);
        }
        Scene scene = new Scene(overallBox);
        this.setScene(scene);

    }


}
