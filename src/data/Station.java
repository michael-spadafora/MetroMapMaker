package data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
public class Station extends Circle implements DraggableElement {
    ArrayList<SubwayLine> subwayLines;
    Text label;
    private double startCenterX;
    private double startCenterY;
    private int currentOrbitState = 0;
    private double horizDisplace = 10;
    private double verticalDisplace = 10;

    public void setSubwayLines(ArrayList<SubwayLine> subwayLines) {
        this.subwayLines = subwayLines;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public double getStartCenterX() {
        return startCenterX;
    }

    public void setStartCenterX(double startCenterX) {
        this.startCenterX = startCenterX;
    }

    public double getStartCenterY() {
        return startCenterY;
    }

    public void setStartCenterY(double startCenterY) {
        this.startCenterY = startCenterY;
    }

    public int getCurrentOrbitState() {
        return currentOrbitState;
    }

    public void setCurrentOrbitState(int currentOrbitState) {
        this.currentOrbitState = currentOrbitState;
    }

    public ArrayList<SubwayLine> getSubwayLines() {
        return subwayLines;
    }

    public Station(){
        label = new Text ("default");
        label.setX(200+this.getRadius()+10);
        label.setY(300+this.getRadius()+10);
        subwayLines = new ArrayList<SubwayLine>();
        this.setCenterX(200);
        this.setCenterY(300);
        this.setRadius(10);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(2);
    }

    public Text getLabel() {
        return label;
    }

    public Station(String name){
        label = new Text (name);
        this.setRadius(10);
        subwayLines = new ArrayList<SubwayLine>();
        this.setCenterX(200);
        this.setCenterY(300);
        verticalDisplace = 10+(label.getLayoutBounds().getHeight()/2);

        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        currentOrbitState = -1;
        orbitLabel();


    }

    public void orbitLabel(){
        currentOrbitState = (currentOrbitState+1) %4;
        double radius = getRadius();

        if (currentOrbitState == 0){
            horizDisplace = radius;
            verticalDisplace = radius+(label.getLayoutBounds().getHeight()/2);
        }

        if (currentOrbitState == 1){
            horizDisplace = -radius-label.getLayoutBounds().getWidth()-1;
            verticalDisplace = radius+(label.getLayoutBounds().getHeight()/2);
           // verticalDisplace = 1209381023;
        }

        if (currentOrbitState == 2){
            horizDisplace = -radius-label.getLayoutBounds().getWidth();
            verticalDisplace = -radius;//-label.getLayoutBounds().getHeight();
        }

        if (currentOrbitState == 3){
            horizDisplace = radius;
            verticalDisplace = -radius;//-label.getLayoutBounds().getHeight();
        }

        label.setX(this.getCenterX() + horizDisplace);
        label.setY(this.getCenterY()+ verticalDisplace);

    }

    public void rotateLabel(){
        double rotation = label.getRotate() + 90;
        if (rotation == 180 || rotation == 0){
            label.setRotate(0);
        }
        else {
            label.setRotate(rotation);
        }
    }

    public void addSubwayLine(SubwayLine subLine){
        subwayLines.add(subLine);
    }

    public void removeSubwayLine(SubwayLine subLine){
        subwayLines.remove(subLine);
    }

    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;
    }

    @Override
    public void drag(int x, int y) {
        double diffX = x - startCenterX; //this is the current cursor position minus the start position
        double diffY = y - startCenterY;

        double newX = getCenterX() + diffX;
        double newY = getCenterY() + diffY;
        setCenterX(newX);
        setCenterY(newY);
        currentOrbitState--;
        orbitLabel();
        //double[] newLocs = getNewLabelLocation(newX, newY);
        //label.setX(newLocs[0]);
       //label.setY(newLocs[1]);
        startCenterX = x;
        startCenterY = y;

        for(SubwayLine s: subwayLines){
            s.fixPoints();
        }
    }

    private double[] getNewLabelLocation(double newX, double newY) {
        double x,  y;
        x = newX+10;
        y = newY+10;
        return new double[]{x,y};

    }

    @Override
    public double getX() {
        return getCenterX() - getRadius();
    }

    @Override
    public double getY() {
        return getCenterY() - getRadius();
    }

    @Override
    public double getWidth() {
        return getRadius() * 2;
    }

    @Override
    public double getHeight() {
        return getRadius() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        setCenterX(initX + (initWidth/2));
        setCenterY(initY + (initHeight/2));
        setRadius(initWidth/2);

    }

    @Override
    public String getElementType() {
        return STATION;
    }

    public Double[] getCoordinates() {
        return new Double[]{getCenterX(), getCenterY()};
    }


    public void changeRadius(double r){
        this.setRadius(r);
        currentOrbitState--;
        orbitLabel();
        //label.setX(this.getCenterX() + this.getRadius());
        //label.setY(this.getCenterY() + this.getRadius());

    }

    public void fixAllLines(){
        for(SubwayLine s: subwayLines){
            s.fixPoints();
        }
    }

    public void fixLabel(){
        currentOrbitState--;
        orbitLabel();
    }

    public void italisize() {

        Font font = label.getFont();
        FontPosture posture;
        FontWeight weight;
        if (font.getStyle().equals("Regular") || font.getStyle().equals("Bold")){
            posture = FontPosture.ITALIC;
        }
        else {
            posture = FontPosture.REGULAR;
        }

        if (font.getStyle().equals("Regular") || font.getStyle().equals("Italic")){
            weight = FontWeight.NORMAL;
        }
        else {
            weight = FontWeight.BOLD;
        }

        label.setFont(Font.font(label.getFont().getFamily(), weight, posture, label.getFont().getSize()));


    }

    public void bolden() {

        Font font = label.getFont();
        FontWeight weight;
        FontPosture posture;
        if (font.getStyle().equals("Regular") || font.getStyle().equals("Italic")){
            weight = FontWeight.BOLD;
        }
        else {
            weight = FontWeight.NORMAL;
        }

        if (font.getStyle().equals("Regular") || font.getStyle().equals("Bold")){
            posture = FontPosture.REGULAR;
        }
        else {
            posture = FontPosture.ITALIC;
        }

        label.setFont(Font.font(label.getFont().getFamily(), weight, posture, label.getFont().getSize()));


    }

    public void changeFontSize(double newSize) {
        Font font = label.getFont();
        FontWeight weight;
        FontPosture posture;
        if (font.getStyle().equals("Regular") || font.getStyle().equals("Italic")){
            weight = FontWeight.NORMAL;
        }
        else {
            weight = FontWeight.BOLD;
        }

        if (font.getStyle().equals("Regular") || font.getStyle().equals("Bold")){
            posture = FontPosture.REGULAR;
        }
        else {
            posture = FontPosture.ITALIC;
        }

        label.setFont(Font.font(font.getFamily(), weight, posture, newSize));

    }

    public void changeFontFamily(String selectedItem) {
        Font font = label.getFont();
        FontWeight weight;
        FontPosture posture;
        if (font.getStyle().equals("Regular") || font.getStyle().equals("Italic")){
            weight = FontWeight.NORMAL;
        }
        else {
            weight = FontWeight.BOLD;
        }

        if (font.getStyle().equals("Regular") || font.getStyle().equals("Bold")){
            posture = FontPosture.REGULAR;
        }
        else {
            posture = FontPosture.ITALIC;
        }

        label.setFont(Font.font(selectedItem, weight, posture, font.getSize()));
    }

    @Override
    public Station clone(){

        Station clonedStation = new Station(this.getLabel().getText());
        /*ArrayList<SubwayLine> subwayLines;
        Text label;
        private double startCenterX;
        private double startCenterY;
        private int currentOrbitState = 0;
        private double horizDisplace = 10;
        private double verticalDisplace = 10;*/

        clonedStation.setCurrentOrbitState(this.currentOrbitState);
        clonedStation.setCenterX(this.getCenterX());
        clonedStation.setCenterY(this.getCenterY());
        clonedStation.setRadius(this.getRadius());
        Color c = (Color) this.getFill();
        clonedStation.setFill(Color.color(c.getRed(), c.getGreen(), c.getBlue()));
        clonedStation.getLabel().setFont(this.getLabel().getFont());
        //clonedStation.setSubwayLines();

        return clonedStation;

    }
}
