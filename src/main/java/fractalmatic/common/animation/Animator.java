package fractalmatic.common.animation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;

import java.util.Set;

/**
 * This interface can be thought of as a subject interface in the observer pattern.
 * However there are more function than a subject to mimic an animation
 */
public interface Animator {
    /**
     * Attaches animatable to the subscribers list
     *
     * @param animatable the animatable is to be attached
     */
    void attachAnimatable(Animatable animatable);

    /**
     * Attaches all animatables to the subscribers list
     *
     * @param animatables the animatables are to be attached
     */
    void attachAll(Set<Animatable> animatables);

    /**
     * Detaches animatable from the subscriber list
     *
     * @param animatable the animatable is to be detached
     */
    void detachAnimatable(Animatable animatable);

    /**
     * Detaches all animatables from the subscriber list
     *
     * @param animatables the animatables are to be detached
     */
    void detachAll(Set<Animatable> animatables);

    /**
     * Detach all animatables from the subscriber list
     */
    void detachAll();

    /**
     * Notify new frame.
     */
    void notifyNewFrame();

    /**
     * Starts the animation
     */
    void start();

    /**
     * Stops the animation
     */
    void stop();

    /**
     * Gets max frame rate of the animation.
     *
     * @return the max frame rate
     */
    int getMaxFrameRate();

    /**
     * Sets max frame rate of the animation.
     *
     * @param maxFrameRate the max frame rate
     */
    void setMaxFrameRate(int maxFrameRate);

    /**
     * Animation active property -tells whether the animation is active.
     *
     * @return the animation active property - whether the animation is active
     */
    BooleanProperty activeProperty();

    /**
     * Animation frame count property, the number of frames from the animation start
     *
     * @return the number of frames counted from the animation start
     */
    LongProperty frameCountProperty();
}
