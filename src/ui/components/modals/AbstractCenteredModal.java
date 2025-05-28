package ui.components.modals;

import ui.Window;

public abstract class AbstractCenteredModal extends AbstractModal {

    public AbstractCenteredModal(int width, int height) {
        super(0, 0, width, height);
        this.setX((Window.screenWidth - width) / 2);
        this.setY((Window.screenHeight - height) / 2);
    }

}
