package data;


import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.Stack;

/**
 *
 * @author mike spad
 */
public class UndoRedoStack {

    Stack<ObservableList<Node>> undoStack;
    Stack<ObservableList<Node>> redoStack;
    mmmData data;

    public UndoRedoStack(mmmData data) {
        undoStack = new Stack<ObservableList<Node>>();
        redoStack = new Stack<ObservableList<Node>>();
        this.data = data;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void addTransaction(ObservableList<Node> transaction) {
        undoStack.push(transaction);
        redoStack = new Stack<ObservableList<Node>>();
        //  transaction.doAction();
    }

    public void undoTransaction() {
        ObservableList<Node> transaction = undoStack.pop();
        //transaction.undoAction()
        data.setShapes(transaction);
        redoStack.push(transaction);

    }

    public void redoTransaction() {
        ObservableList<Node> transaction = redoStack.pop();
        data.setShapes(transaction);
        undoStack.push(transaction);
    }

}
