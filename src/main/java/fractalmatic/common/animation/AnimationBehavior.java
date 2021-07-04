package fractalmatic.common.animation;

/**
 * This interface can be thought as a part of a strategy pattern, handles the actual animation behavior
 */
public interface AnimationBehavior {

    /**
     * Retrieves the range of the animation
     *
     * @return the range of the animation
     */
    double getRange();

    /**
     * Sets the range of the animation
     *
     * @param range the range of the animation
     */
    void setRange(double range);

    /**
     * Retrieves the speed of the animation
     *
     * @return the speed of the animation
     */
    double getSpeed();

    /**
     * Sets the speed of the animation
     *
     * @param speed the speed of the animation
     */
    void setSpeed(double speed);

    /**
     * Resets the animation
     */
    void resetAnimation();

    /**
     * Retrieves the next value, which is calculated for next frame
     *
     * @return the next value for the next frame
     */
    double getNextValue();

    /**
     * Sets whether the speed needs to be exponential in given scale, to use exponential speed,
     * the exponential range and constant needs to be set
     *
     * @param isExponential whether the speed scale is exponential
     */
    void setExponentialSpeed(boolean isExponential);

    /**
     * The speed range, is to be used for the exponential range calculation
     *
     * @param min the minimum value of the range, inclusive
     * @param max the maximum value of the range inclusive
     */
    void setExponentialRange(double min, double max);

    /**
     * The exponential constant B in AB^x
     *
     * @param constant the exponential constant
     */
    void setExponentialConstant(double constant);
}
