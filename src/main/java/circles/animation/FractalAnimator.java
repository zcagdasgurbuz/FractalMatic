package circles.animation;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Fractal animator.
 */
public class FractalAnimator implements Animator {

    /** The dependents/subscribers list */
    private final HashSet<Animatable> dependents;
    /** The active property. */
    private final BooleanProperty active;
    /** The frame count property */
    private final LongProperty frameCount;
    /** The animation timer */
    private final AnimationTimer animation;
    /** The pulse interval -time between every frame */
    private long pulseInterval;
    /** The previous frame time */
    private long oldL;
    /** The Max frame rate */
    private int maxFrameRate;


    /**
     * Instantiates a new fractal animator.
     */
    public FractalAnimator() {
        maxFrameRate = 60;
        active = new SimpleBooleanProperty();
        frameCount = new SimpleLongProperty();
        dependents = new HashSet<>();
        pulseInterval = 1000000 / maxFrameRate;
        oldL = 0;

        animation = new AnimationTimer() {

            @Override
            public void handle(long l) {
                if (l - oldL > pulseInterval) {
                    oldL = l;
                    notifyNewFrame();
                    frameCount.set(frameCount.get() + 1);
                }
            }
        };

    }

    /**
     * Handles start and stop of the animation.
     */
    private void handleStartStop() {
        active.set(dependents.size() > 0);
        if (active.get()) {
            animation.start();
        } else {
            animation.stop();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void attachAnimatable(Animatable animatable) {
        if (animatable != null) {
            dependents.add(animatable);
        }
        handleStartStop();
    }

    /** {@inheritDoc} */
    @Override
    public void attachAll(Set<Animatable> animatables) {
        for (Animatable animatable : animatables) {
            if (animatable != null) {
                dependents.add(animatable);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void detachAnimatable(Animatable animatable) {
        if (animatable != null) {
            dependents.remove(animatable);
        }
        handleStartStop();
    }

    /** {@inheritDoc} */
    @Override
    public void detachAll(Set<Animatable> animatables) {
        for (Animatable animatable : animatables) {
            if (animatable != null) {
                dependents.remove(animatable);
            }
        }
        handleStartStop();
    }

    /** {@inheritDoc} */
    @Override
    public void detachAll() {
        dependents.clear();
        handleStartStop();
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        if (dependents.size() > 0) {
            animation.start();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        animation.stop();
    }

    /** {@inheritDoc} */
    @Override
    public void notifyNewFrame() {
        for (Animatable current : dependents) {
            current.update();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxFrameRate(int maxFrameRate) {
        if (maxFrameRate != 0) {
            this.maxFrameRate = Math.abs(maxFrameRate);
            pulseInterval = 1000000 / maxFrameRate;
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxFrameRate() {
        return maxFrameRate;
    }

    /** {@inheritDoc} */
    @Override
    public BooleanProperty activeProperty() {
        return active;
    }

    /** {@inheritDoc} */
    @Override
    public LongProperty frameCountProperty() {
        return frameCount;
    }
}
