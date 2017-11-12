package data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.scene.paint.Color;

public class mmmData implements AppDataComponent {

    UndoRedoStack undoRedoStack;
    AppTemplate app;

    public mmmData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;
        undoRedoStack = new UndoRedoStack();

        // INIT THE COLORS
        //currentFillColor = Color.web(WHITE_HEX);
        //currentOutlineColor = Color.web(BLACK_HEX);
        //currentBorderWidth = 1;
    }

    @Override
    public void resetData() {

    }
}
