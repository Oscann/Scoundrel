package objects.animation;

import objects.GameObject;
import objects.animation.animations.AbstractAnimation;

public class AnimationHandler<T extends GameObject> {

    private AbstractAnimation<T> currAnimation = null;

    public void update() {
        if (currAnimation != null && currAnimation.hasNext())
            currAnimation.next();
        else
            currAnimation = null;
    }

    public void setCurrAnimation(AbstractAnimation<T> animation) {
        this.currAnimation = animation;
    }
}