package gui;

import data.*;
import djf.AppTemplate;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;

import static data.mmmState.*;

public class CanvasController {
    AppTemplate app;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMousePress(int x, int y, int clicks) {
        mmmData dataManager = (mmmData) app.getDataComponent();
        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            DraggableElement shape = dataManager.selectTopShape(x, y);

           /* if (shape instanceof DraggableText && clicks>=2) {
                dataManager.setState(golState.EDITING_TEXT);
                dataManager.editText(shape);
            }*/
            Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
//        } //else if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
//            dataManager.startNewRectangle(x, y);
//        }// else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
//           dataManager.startNewEllipse(x, y);
//        } //else if (dataManager.isInState(golState.STARTING_IMAGE)) {
//            dataManager.startNewImage(x, y);
//        }// else if (dataManager.isInState(golState.STARTING_TEXT)) {
//            dataManager.startNewText(x, y);
//        }
            mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
            workspace.reloadWorkspace(dataManager);
        }
        else if (dataManager.isInState(ADDING_STATION_TO_LINE)){
            DraggableElement shape = dataManager.selectTopShape(x, y);

            if (shape instanceof Station)
            {
                mmmWorkspace workspace = (mmmWorkspace) app.getWorkspaceComponent();
                //workspace.reloadWorkspace(dataManager);
                mmmData data = (mmmData) app.getDataComponent();

                String temp =workspace.getLinesComboBox().getSelectionModel().getSelectedItem();
                // why is this not working?
                SubwayLine subwayLine = data.getLineFromString(temp);
                if (subwayLine!=null) {
                    subwayLine.addStation((Station) shape);
                    ((Station) shape).addSubwayLine(subwayLine);
                }

            }

            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            dataManager.setState(SELECTING_SHAPE);




        }
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        mmmData dataManager = (mmmData) app.getDataComponent();
        if (dataManager.isInState(DRAGGING_SHAPE)) {
            DraggableElement selectedDraggableShape = (DraggableElement) dataManager.getSelectedElement();
            selectedDraggableShape.drag(x, y);
            if (selectedDraggableShape instanceof Station){
                for(SubwayLine sl : ((Station) selectedDraggableShape).getSubwayLines()){
                    sl.fixPoints();
                }
            }
            if (selectedDraggableShape instanceof LineEnd){
                ((LineEnd) selectedDraggableShape).getSubwayLine().fixPoints();

            }
            app.getGUI().updateToolbarControls(false);
        }

    }

    /**
     * Respond to mouse button release on the rendering surface, which we call
     * canvas, but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        mmmData dataManager = (mmmData) app.getDataComponent();
       if (dataManager.isInState(mmmState.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(mmmState.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
        }
    }
}
