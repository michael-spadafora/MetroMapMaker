package data;


import java.util.Stack;

/**
 *
 * @author mike spad
 */
public class UndoRedoStack {

    Stack<Transaction> undoStack;
    Stack<Transaction> redoStack;

    public UndoRedoStack() {
        undoStack = new Stack<Transaction>();
        redoStack = new Stack<Transaction>();
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
        //  transaction.doAction();
    }

    public void undoTransaction() {
        Transaction transaction = undoStack.pop();
        transaction.undoAction();
        redoStack.push(transaction);

    }

    public void redoTransaction() {
        Transaction transaction = redoStack.pop();
        transaction.doAction();
        undoStack.push(transaction);
    }

}
