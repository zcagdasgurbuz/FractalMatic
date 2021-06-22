package circles.animation;

/**
 * The type Oscillation behavior.
 */
public class OscillationBehavior implements AnimationBehavior {

    /** TWO_PI */
    private static final double TWO_PI = 2 * Math.PI;
    /** The constant INC_MIN */
    private static final double INC_MIN = TWO_PI / 10000;
    /** The constant INC_MAX */
    private static final double INC_MAX = TWO_PI / 50;
    /** The constant INC_RANGE */
    private static final double INC_RANGE = INC_MAX - INC_MIN;
    /** The constant R_MAX */
    private static final double R_MAX = 10.0;
    /** The constant R_MIN */
    private static final double R_MIN = 0.01;
    /** The constant R_RANGE */
    private static final double R_RANGE = R_MAX - R_MIN;

    /** The x value of the oscillation. */
    private double xValue;
    /** The range of the oscillation - amplitude * 2 */
    private double range;
    /** The amplitude of the oscillation  - range / 2 */
    private double amplitude;
    /** The speed of the animation */
    private double speed;

    /** The minimum value of exponential range */
    private double expMin;
    /** The maximum value of the exponential range */
    private double expMax;
    /** Whether exponential speed is active */
    private boolean isExponential;
    /** The exponential constant B in AB^x */
    private double exponentialConstant;

    /**
     * Partial constructor, instantiates a new oscillation animation behavior - range 1 and speed 1.
     */
    public OscillationBehavior() {
        this(1.0, 1.0);
    }

    /**
     * Instantiates a new oscillation animation behavior
     *
     * @param range the range of the oscillation
     * @param speed the speed of the oscillation
     */
    public OscillationBehavior(double range, double speed) {
        xValue = 0;
        setRange(range);
        setSpeed(speed);
        isExponential = false;
        expMin = expMax = exponentialConstant = 1.0;
    }

    /** {@inheritDoc} */
    @Override
    public void setRange(double range) {
        this.range = Math.abs(range);
        this.amplitude = range / 2.0;
    }

    /** {@inheritDoc} */
    @Override
    public double getRange() {
        return range;
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeed(double speed) {
        if (isExponential) {
            this.speed = getExponentialSpeed(Math.abs(speed));
        } else {
            this.speed = Math.abs(speed);
        }
    }

    /** {@inheritDoc} */
    @Override
    public double getSpeed() {
        return speed;
    }

    /** {@inheritDoc} */
    @Override
    public void resetAnimation() {
        xValue = 0.0;
    }

    /** {@inheritDoc} */
    @Override
    public double getNextValue() {
        if (xValue > TWO_PI) {
            xValue = 0.0;
        } else {
            xValue += ((speed - R_MIN) / R_RANGE) * INC_RANGE + INC_MIN;
        }
        return Math.sin(xValue) * amplitude;
    }

    /** {@inheritDoc} */
    @Override
    public void setExponentialSpeed(boolean isExponentialSpeed) {
        isExponential = isExponentialSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public void setExponentialRange(double min, double max) {
        expMin = min;
        expMax = max;
    }

    /** {@inheritDoc} */
    @Override
    public void setExponentialConstant(double constant) {
        exponentialConstant = constant;
    }

    /**
     * Retrieves corresponding exponential speed value.
     *
     * @param linearSpeed the linear speed
     * @return the exponential speed
     */
    //more info https://stackoverflow.com/questions/49184033/converting-a-range-of-integers-exponentially-to-another-range
    private double getExponentialSpeed(double linearSpeed) {
        double expRange = expMax - expMin;

        double numerator = expRange * Math.pow(exponentialConstant, linearSpeed - expMin) + expMin * Math.pow(exponentialConstant, expRange) - expMax;
        double denominator = Math.pow(exponentialConstant, expRange) - 1;

        return numerator / denominator;

    }
}
