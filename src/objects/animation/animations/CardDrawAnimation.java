package objects.animation.animations;

import java.awt.Point;

import core.App;
import objects.Card;

public class CardDrawAnimation extends AbstractAnimation<Card> {
    public CardDrawAnimation(Card target, Point deckCoords, Point destinyCoords) {
        super(target);

        float animationDurationInSeconds = 0.25f;
        int totalTicks = (int) (App.UPS * animationDurationInSeconds);
        int ticksPerStep = 5;
        int nTranslationSteps = totalTicks / ticksPerStep;

        float dx = (float) ((destinyCoords.getX() - deckCoords.getX()) / nTranslationSteps);
        int y = (int) destinyCoords.getY();

        addStep(() -> {
            target.setIsFlipped(true);
            target.setX((int) deckCoords.getX());
            target.setY(y);
        }, ticksPerStep);

        for (int i = 0; i < totalTicks / ticksPerStep; i++) {
            addStep(() -> {
                int prevX = target.getX();

                target.setX((int) (prevX + dx));
            }, ticksPerStep);
        }

        addStep(() -> {
            target.setX((int) destinyCoords.getX());
            target.setY(y);
        }, ticksPerStep * 20);

        addStep(() -> {
            target.setIsFlipped(false);
        }, 0);
    }
}
