package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import core.App;

public class MainPanel extends JPanel {
    public MainPanel() {
        this.setBackground(Color.black);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        App.currState.render(g);
    }
}
