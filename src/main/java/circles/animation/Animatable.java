package circles.animation;

/**
 * This interface can be thought of as an observer, however there are more of an observer since there are some other
 * functions to be able to perform animation
 */
public interface Animatable {

    /**
     * This method is an update request, performs necessary functions
     */
    void update();

    /**
     * This tells whether the animation is active
     * @return whether the animation is active
     */
    boolean getActiveStatus();

    /**
     * This method sets the animation behavior of the animatable
     *
     * @param animationBehavior the animation behavior is to be used in this animatable
     */
    void setAnimationBehavior(AnimationBehavior animationBehavior);

    /**
     * This method retrieves of the animation behavior
     *
     * @return the animation behavior
     */
    AnimationBehavior getAnimationBehavior();

    /**
     * This method sets the animator, the animator can be thought of as the subject in observer pattern
     *
     * @param animator the animator of the animatable object
     */
    void setAnimator(Animator animator);

    /**
     * This method retrieves the current animator of the animatable, the animator can be thought of as the subject in observer pattern
     *
     * @return
     */
    Animator getAnimator();

    /**
     * This method starts the animation, if animator is set, attaches itself to animatable
     */
    void start();

    /**
     * This method stops the animation, if animator is set, detaches itself from animatable
     */
    void stop();

    /**
     *  This method retrieves the value when the animation starts
     *
     * @return animation base value
     */
    Number getAnimationBaseValue();

    /**
     * This retrieves the id of animatable
     * @return id
     */
    String getId();

}
