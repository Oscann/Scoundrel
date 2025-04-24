# Scoundrel

Scoundrel is a single player rogue-like card game created by Zach Gage and Kurt Bieg.

This reporistory holds a recreation of the game in Java, using the Java Swing toolkit.

If you want to learn more about the game, try reading this [rules manual](http://www.stfj.net/art/2011/Scoundrel.pdf).

# Basic Structure

The game has an `App` class that holds the other main classes together and runs the game loop thread. The `Window` class will be mostly used to hold the `MainPanel` and getting the screen bounds. The `MainPanel` class will be used for rendering and receiving the inputs, which are redirected to the `State` class through the `App`. The `State` class is the one that will handle the most operations in the game; it represents an environment and handles how everything should be rendered or executed.

![App](https://github.com/user-attachments/assets/1818c5fc-c7eb-421d-a648-45fb8ec8f79a)
