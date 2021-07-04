package fractalmatic.circles.animation;

import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is an animatable double property.
 */
public class AnimatableRangedDoubleProperty extends SimpleDoubleProperty implements Animatable{

    /** The minimum value of the range. */
    private double min;
    /** The maximum value of the range. */
    private double max;
    /** The animation start value. */
    private double animationStartValue;
    /** The animation behavior. */
    private AnimationBehavior animationBehavior;
    /** The animation active status. */
    private boolean animationActiveStatus;
    /** The animator. */
    private Animator animator;
    /** The id of the animatable. */
    private String id;

    /**
     * Full constructor..
     *
     * @param min   the minimum limit value of the property
     * @param max   the maximum limit value of the property
     * @param value the value
     * @param id    the id
     */
    public AnimatableRangedDoubleProperty(double min, double max, double value, String id) {
        super();
        animator = null;
        animationBehavior = null;
        animationActiveStatus = false;
        this.min = min;
        this.max = max;
        animationStartValue = value;
        if (checkIfInRange(value)) {
            super.set(value);
        } else {
            set(min);
        }
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    /**
     * Partial constructor..
     *
     * @param min the minimum limit value of the property
     * @param max the maximum limit value of the property
     * @param id  the id
     */
    public AnimatableRangedDoubleProperty(double min, double max, String id) {
        this(min, max, min, id);
    }

    /**
     * Sets the minimum limit
     *
     * @param min the minimum limit
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Sets the maximum limit
     *
     * @param max the maximum limit
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Retrieves the minimum limit
     *
     * @return the minimum limit
     */
    public double getMin() {
        return min;
    }

    /**
     * Retrieves the maximum limit
     *
     * @return the maximum limit
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Retrieves id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * String representation, same as id
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * The natural order, the id is used for natural ordering
     *
     * @param other the other AnimatableRangedDoubleProperty is to be compared to current
     * @return the natural order value, <0, =0, >0
     */
    @Override
    public int compareTo(Animatable other) {

        return this.toString().compareTo(other.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(double value) {
        if (checkIfInRange(value)) {
            super.set(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Number value) {
        if (value != null) {
            if (checkIfInRange(value.doubleValue()))
                super.setValue(value);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        set(animationStartValue + animationBehavior.getNextValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getActiveStatus() {
        return animationActiveStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAnimationBehavior(AnimationBehavior animationBehavior) {
        if (animationBehavior != null) {
            this.animationBehavior = animationBehavior;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationBehavior getAnimationBehavior() {
        return animationBehavior;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Animator getAnimator() {
        return animator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (animator != null && !animationActiveStatus) {
            animator.attachAnimatable(this);
            animationActiveStatus = true;
            animationStartValue = get();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (animator != null && animationActiveStatus) {
            animator.detachAnimatable(this);
            animationActiveStatus = false;
            animationBehavior.resetAnimation();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Number getAnimationBaseValue() {
        return animationStartValue;
    }

    /**
     * Helper method to check if the value is between the min and max limits
     *
     * @param value the value is to be checked
     * @return whether the value is in range
     */
    private boolean checkIfInRange(double value) {
        return Double.compare(min, value) <= 0 && Double.compare(max, value) >= 0;
    }
}
