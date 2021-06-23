package circles.util;

import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class mimics a ranged double property, does not allow to set to a value outside its the range
 */
public class SimpleRangedDoubleProperty extends SimpleDoubleProperty {

    /** The minimum limit of the range */
    private double min;
    /** The maximum limit of the range */
    private double max;

    /**
     * Instantiates a new simple ranged double property.
     *
     * @param min   the minimum limit of the range
     * @param max   the maximum limit of the range
     * @param value the value is to be set
     */
    public SimpleRangedDoubleProperty(double min, double max, double value) {
        super();
        this.min = min;
        this.max = max;
        if (checkIfInRange(value)) {
            super.set(value);
        } else {
            set(min);
        }
    }

    /**
     * Instantiates a new simple ranged double property.
     *
     * @param min   the minimum limit of the range
     * @param max   the maximum limit of the range
     */
    public SimpleRangedDoubleProperty(double min, double max) {
        this(min,max,min);
    }

    /**
     * Set minimum limit of the range
     *
     * @param min   the minimum limit of the range
     */
    public void setMin(double min){
        this.min = min;
    }

    /**
     * Set maximum limit of the range
     *
     * @param max   the maximum limit of the range
     */
    public void setMax(double max){
        this.max = max;
    }

    /**
     * Gets minimum value of the range
     *
     * @return  the minimum limit of the range
     */
    public double getMin() {
        return min;
    }

    /**
     * Get max double.
     *
     * @return the maximum limit of the range
     */
    public double getMax(){
        return max;
    }

    /**
     * {@inheritDoc}
     *
     * * does not set the value if the value is not in the range
     */
    @Override
    public void set(double value) {
        if (checkIfInRange(value)) {
            super.set(value);
        }
    }

    /**
     * {@inheritDoc}
     *
     * * does not set the value if the value is not in the range
     */
    @Override
    public void setValue(Number value) {
        if (value != null) {
            if(checkIfInRange(value.doubleValue()))
                super.setValue(value);
        }
    }

    /**
     * Checks if the value in range.
     *
     * @param value the value is to be checked
     * @return the boolean
     */
    private boolean checkIfInRange(double value){
        return Double.compare(min, value) <= 0 && Double.compare(max, value) >= 0;
    }
}
