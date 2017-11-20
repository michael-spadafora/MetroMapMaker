package data;

public interface DraggableElement {
    public static final String STATION = "STATION";
    public static final String LINE = "LINE";
    public static final String LINE_END = "LINE_END";
    //public static final String IMAGE = "IMAGE";
    // public static final String TEXT = "TEXT";
    //public mmmState getStartingState();

    public void start(int x, int y);
    public void drag(int x, int y);
    //public void size(int x, int y);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);
    public String getElementType();
}
