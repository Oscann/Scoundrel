package objects.interfaces;

import java.awt.event.MouseEvent;

public interface Interactable {
    public void onHover(MouseEvent e);

    public void onClick(MouseEvent e);
}
