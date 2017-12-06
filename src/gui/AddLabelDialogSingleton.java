package gui;

public class AddLabelDialogSingleton {
    private static AddLabelDialogSingleton ourInstance = new AddLabelDialogSingleton();

    public static AddLabelDialogSingleton getInstance() {
        return ourInstance;
    }

    private AddLabelDialogSingleton() {
    }



}
