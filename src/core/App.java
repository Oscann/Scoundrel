package core;

import core.gamestate.Game;
import core.gamestate.State;
import ui.MainPanel;
import ui.Window;

public class App implements Runnable {
    public static Window window;
    public static MainPanel root;
    public static State currState;
    public static final int FPS = 60, UPS = 200;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        root = new MainPanel();
        window = new Window(root);
        currState = new Game();

        Thread gameThread = new Thread(new App());
        gameThread.start();
    }

    @Override
    public void run() {
        double timeBetweenUpdates = (double) (Math.pow(10, 9) / UPS);
        double timeBetweenRender = (double) (Math.pow(10, 9) / FPS);
        long now;
        long lastIteration = System.nanoTime();

        double deltaU = 0;
        double deltaR = 0;

        while (true) {
            now = System.nanoTime();

            deltaU += (now - lastIteration) / timeBetweenUpdates;
            deltaR += (now - lastIteration) / timeBetweenRender;

            // System.out.println(timeBetweenRender);
            // System.out.println(deltaR);
            // System.out.println(now - lastIteration);
            // System.out.println((double) (now - lastIteration) / timeBetweenRender);

            if (deltaR >= 1) {
                root.repaint();
                deltaR--;
            }

            if (deltaU >= 1) {
                currState.update();
                deltaU--;
            }

            lastIteration = now;
        }
    }
}