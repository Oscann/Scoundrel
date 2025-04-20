package objects.animation.animations;

import java.util.ArrayList;

import objects.GameObject;

public abstract class AbstractAnimation<T extends GameObject> {

    private ArrayList<AnimationStep> animationSteps;
    private int currStepIndex = 0, tickIndex = 0;

    protected T target;

    public AbstractAnimation(T target) {
        this.target = target;
        this.animationSteps = new ArrayList<>();
    }

    public void next() {
        AnimationStep currStep = animationSteps.get(currStepIndex);

        if (tickIndex == 0) {
            currStep.action.act();
        }

        tickIndex++;

        if (tickIndex >= currStep.durationInTicks) {
            currStepIndex++;
            tickIndex = 0;
        }
    }

    public boolean hasNext() {
        return currStepIndex < animationSteps.size();
    }

    public void addStep(AnimationStepAction action, int duration) {
        animationSteps.add(new AnimationStep(action, duration));
    }

    private class AnimationStep {
        public int durationInTicks;
        public AnimationStepAction action;

        public AnimationStep(AnimationStepAction action, int durationInTicks) {
            this.action = action;
            this.durationInTicks = durationInTicks;
        }
    }

    public interface AnimationStepAction {
        public void act();
    }
}
