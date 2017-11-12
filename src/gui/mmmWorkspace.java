package gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;

public class mmmWorkspace extends AppWorkspaceComponent {

    AppGUI gui;
    AppTemplate app;

    public mmmWorkspace(AppTemplate initApp) {
        app = initApp;
        gui = app.getGUI();
        initLayout();
        initControllers();
    }

    private void initControllers() {
    }

    private void initLayout() {
    }

    @Override
    public void resetWorkspace() {

    }

    @Override
    public void reloadWorkspace(AppDataComponent appDataComponent) {

    }
}
