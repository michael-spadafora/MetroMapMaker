/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.DraggableElement;
import data.DraggableImage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author mspadafora
 */
public class DraggableText extends Text implements DraggableElement {

    double startX;
    double startY;
    Text text;
    double size;

    public DraggableText() {
        setX(0.0);
        setY(0.0);
        setOpacity(1.0);
        startX = 0.0;
        startY = 0.0;
    }

    public double getFontSize() {
        return size;
    }

    public DraggableText(String words){
        super(words);
        setX(100);
        setY(100);
        setOpacity(1.0);
        startX = 0.0;
        startY = 0.0;
    }

    public DraggableText(String words, String size, String font, boolean[] boldItalic) {
        super(words);
        // text = new Text();
        // text.setText(words);
        this.size = Double.parseDouble(size);

        FontWeight bold;
        FontPosture italic;

        if (boldItalic[0]) {
            bold = FontWeight.BOLD;
        } else {
            bold = FontWeight.NORMAL;
        }

        if (boldItalic[1]) {
            italic = FontPosture.ITALIC;
        } else {
            italic = FontPosture.REGULAR;
        }
        //Font tempFont = new Font(font);
        Font newFont = Font.font(font, bold, italic, Double.parseDouble(size));

        super.setFont(newFont);

    }


    @Override
    public void start(int x, int y) {
        startX = x;
        startY = y;
        //setX(x);
        //setY(y);
    }

    public void initText(String words, String fontSize, String font, boolean[] boldItalic) {
        text = new Text();
        text.setText(words);

        size = Double.parseDouble(fontSize);

        FontWeight bold;
        FontPosture italic;

        if (boldItalic[0]) {
            bold = FontWeight.BOLD;
        } else {
            bold = FontWeight.NORMAL;
        }

        if (boldItalic[1]) {
            italic = FontPosture.ITALIC;
        } else {
            italic = FontPosture.REGULAR;
        }
        Font newFont = Font.font(font, bold, italic, size);

        super.setFont(newFont);

    }

    @Override
    public void drag(int x, int y) {

        double diffX = x - startX; //getx is the top left corner of the rect
        double diffY = y - startY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;
        xProperty().set(newX);
        yProperty().set(newY);
        startX = x;
        startY = y;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {

    }

    @Override
    public String getElementType() {
        return "text";
    }

    public String cT(double x, double y) {
        return "(x,y): (" + x + "," + y + ")";
    }




}
