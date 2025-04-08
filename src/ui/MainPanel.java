package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import core.App;
import input.MouseInputs;

public class MainPanel extends JPanel {
    MouseInputs mouseInputs;

    public MainPanel() {
        this.setBackground(Color.black);

        mouseInputs = new MouseInputs();
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        App.currState.render(g);
    }
}
