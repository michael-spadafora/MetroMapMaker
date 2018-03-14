package data;


import gui.mmmWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.Stack;

/**
 *
 * @author mike spad
 */
public class UndoRedoStack {

    Stack<Transaction> undoStack;
    Stack<Transaction> redoStack;
    mmmData data;

    public UndoRedoStack(mmmData data) {
        undoStack = new Stack<Transaction>();
        redoStack = new Stack<Transaction>();
        this.data = data;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void addTransaction(Transaction transaction) {
        undoStack.push(transaction);
        redoStack = new Stack<Transaction>();
        ((mmmWorkspace)data.getApp().getWorkspaceComponent()).getMapEditController().fixUndoRedoButtons();
        //  transaction.doAction();
    }

    public void undoTransaction() {
        Transaction transaction = undoStack.pop();
        transaction.undoAction();
        redoStack.push(transaction);
        ((mmmWorkspace)data.getApp().getWorkspaceComponent()).getMapEditController().fixUndoRedoButtons();

    }

    public void redoTransaction() {
        Transaction transaction = redoStack.pop();
        transaction.doAction();
        undoStack.push(transaction);
        ((mmmWorkspace)data.getApp().getWorkspaceComponent()).getMapEditController().fixUndoRedoButtons();
    }

    public boolean undoIsEmpty(){
        return undoStack.isEmpty();
    }
    public boolean redoIsEmpty(){
        return redoStack.isEmpty();
    }

}
