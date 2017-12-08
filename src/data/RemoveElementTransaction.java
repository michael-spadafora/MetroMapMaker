package data;

public class RemoveElementTransaction implements Transaction{

    DraggableElement element;
    mmmData data;

    public RemoveElementTransaction( mmmData data,DraggableElement element) {
        this.element = element;
        this.data = data;
    }

    @Override
    public void doAction() {
        data.removeElement(element);
    }

    @Override
    public void undoAction() {
        data.addElement(element);
        if (element instanceof  Station){
            for (SubwayLine line: ((Station) element).getSubwayLines()){
                line.addStation((Station) element);
                line.fixPoints();
            }
        }



    }
}
