package data;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.File;

public class DraggableImage  extends DraggableRectangle {
    double startX;
    double startY;
    String path;
    File file;

    public DraggableImage() {
        super();
    }

    public  DraggableImage(String newPath) {

        try {

            //path = "file:" + newPath;
            path =  newPath;



            Image image = new Image(path);//new Image(path);
            // ImageView iv = new ImageView();
            //iv.setImage(image);
            heightProperty().set(image.getHeight());
            widthProperty().set(image.getWidth());

            //DraggableRectangle rect = new DraggableRectangle();
            ImagePattern pattern = new ImagePattern(image);
            this.setFill(pattern);

        } catch (Exception ex) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING, "no image chosen");
            alert.showAndWait();
        }
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public DraggableImage(File selectedFile) {
        file = selectedFile;
        try {

            path = "file:" + selectedFile.getAbsolutePath();
            //path =  selectedFile.getAbsolutePath();
            File file = new File(path);
            boolean exists = file.exists();
            Image image = new Image(path);//new Image(path);
            // ImageView iv = new ImageView();
            //iv.setImage(image);
            heightProperty().set(image.getHeight());
            widthProperty().set(image.getWidth());

            ImagePattern pattern = new ImagePattern(image);
            this.setFill(pattern);

        } catch (Exception ex) {
            System.out.print("");

        }

    }

    public String getShapeType() {
        return "image";
    }
}
