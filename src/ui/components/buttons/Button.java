package ui.components.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import core.gamestate.constants.GameRenderingConstants;
import objects.GameObject;
import ui.Window;
import ui.events.MouseClickEventHandler;

public class Button extends GameObject {

    private boolean isHovering;
    private MouseClickEventHandler clickEvent;
    private String text;

    public Button(String text) {
        this.text = text;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255, 255, 255, 70));
        g.setFont(new Font(GameRenderingConstants.DEFAULT_FONT, Font.PLAIN, Window.screenSizeUnit));

        if (isHovering)
            g.fillRect(x, y, width, height);

        g.setColor(Color.white);
        g.drawRect(x, y, width, height);

        FontMetrics fontMetrics = g.getFontMetrics();

        int textX = x + (width - fontMetrics.stringWidth(text)) / 2;
        int textY = y + (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        g.drawString(text, textX, textY);
    }

    @Override
    public void update() {
    }

    public void handleClick(MouseEvent e) {
        if (clickEvent != null)
            clickEvent.handleClick(e);
    }

    public void setClickEvent(MouseClickEventHandler clickEvent) {
        this.clickEvent = clickEvent;
    }

    public void setIsHovering(boolean isHovering) {
        this.isHovering = isHovering;
    }
}
