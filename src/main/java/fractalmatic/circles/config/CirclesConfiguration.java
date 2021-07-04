package fractalmatic.circles.config;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * This is a data class that stores the all configurations of the fractal
 */
public class CirclesConfiguration implements Serializable {
    /** The version number for serialization. */
    private static final long serialVersionUID = 1L;

    /** The name of the configuration. */
    private String name;
    /** The background color. */
    private String backgroundColor;
    /** Whether fractal final color is active. */
    private boolean fractalFinalColorActive;
    /** The fractal color. */
    private String fractalColor;
    /** The fractal final color. */
    private String fractalFinalColor;
    /** The child count. */
    private int childCount;
    /** The number recursions. */
    private int recursions;
    /** The start angle. */
    private double startAngle;
    /** The initial radius. */
    private double initialRadius;
    /** The size ratio. */
    private double sizeRatio;
    /** Whether line width final is active. */
    private boolean lineWidthFinalActive;
    /** The line width. */
    private double lineWidth;
    /** The line width final. */
    private double lineWidthFinal;
    /** Whether opacity final is active. */
    private boolean opacityFinalActive;
    /** The opacity. */
    private double opacity;
    /** The opacity final. */
    private double opacityFinal;

    /** Whether the animation is active. */
    private boolean isAnimationActive;

    //animation settings
    /** Whether start angle animation is active. */
    private boolean startAngleAnimationActive;
    /** The start angle animation amplitude. */
    private double startAngleAnimationAmplitude;
    /** The start angle animation speed. */
    private double startAngleAnimationSpeed;

    /** Whether initial radius animation is active. */
    private boolean initialRadiusAnimationActive;
    /** The initial radius animation amplitude. */
    private double initialRadiusAnimationAmplitude;
    /** The initial radius animation speed. */
    private double initialRadiusAnimationSpeed;

    /** Whether size ratio animation is active. */
    private boolean sizeRatioAnimationActive;
    /** The size ratio animation amplitude. */
    private double sizeRatioAnimationAmplitude;
    /** The size ratio animation speed. */
    private double sizeRatioAnimationSpeed;

    /** Whether line width animation is active. */
    private boolean lineWidthAnimationActive;
    /** The line width animation amplitude. */
    private double lineWidthAnimationAmplitude;
    /** The line width animation speed. */
    private double lineWidthAnimationSpeed;

    /** Whether line width final animation is active. */
    private boolean lineWidthFinalAnimationActive;
    /** The line width final animation amplitude. */
    private double lineWidthFinalAnimationAmplitude;
    /** The line width final animation speed. */
    private double lineWidthFinalAnimationSpeed;

    /** Whether opacity animation is active. */
    private boolean opacityAnimationActive;
    /** The opacity animation amplitude. */
    private double opacityAnimationAmplitude;
    /** The opacity animation speed. */
    private double opacityAnimationSpeed;

    /** Whether opacity final animation is active. */
    private boolean opacityFinalAnimationActive;
    /** The opacity final animation amplitude. */
    private double opacityFinalAnimationAmplitude;
    /** The opacity final animation speed. */
    private double opacityFinalAnimationSpeed;

    //limits
    /** The configuration maximum number of circles limit. -limit without animation */
    private int configurationMaximumCircles;
    /** The animation maximum number of circles limit. -limit with animation */
    private int animationMaximumCircles;

    /**
     * Full constructor
     *
     * @param name                             name of the configuration
     * @param backgroundColor                  background color
     * @param fractalFinalColorActive          whether final fractal color is active
     * @param fractalColor                     fractal color
     * @param fractalFinalColor                fractal final color
     * @param childCount                       child count
     * @param recursions                       recursions
     * @param startAngle                       start angle at which the first child is to be drawn
     * @param initialRadius                    the initial radius
     * @param sizeRatio                        size ratio of a parent to its child
     * @param lineWidthFinalActive             whether final line width is active
     * @param lineWidth                        line width
     * @param lineWidthFinal                   final line width
     * @param opacityFinalActive               whether the final opacity is active
     * @param opacity                          opacity
     * @param opacityFinal                     final opacity
     * @param isAnimationActive                whether the animation is active
     * @param startAngleAnimationActive        whether the start angle animation is active
     * @param startAngleAnimationAmplitude     start angle animation amplitude value
     * @param startAngleAnimationSpeed         start angle animation speed value
     * @param initialRadiusAnimationActive     whether the initial radius animation is active
     * @param initialRadiusAnimationAmplitude  initial radius amplitude value
     * @param initialRadiusAnimationSpeed      initial radius animation speed
     * @param sizeRatioAnimationActive         whether size ratio animation is active
     * @param sizeRatioAnimationAmplitude      size ratio animation amplitude
     * @param sizeRatioAnimationSpeed          size ratio animation speed
     * @param lineWidthAnimationActive         whether line width animation is active
     * @param lineWidthAnimationAmplitude      line width animation amplitude
     * @param lineWidthAnimationSpeed          line width animation speed
     * @param lineWidthFinalAnimationActive    whether the final line width is active
     * @param lineWidthFinalAnimationAmplitude final line width animation amplitude
     * @param lineWidthFinalAnimationSpeed     final line width animation speed
     * @param opacityAnimationActive           whether the opacity animation is active
     * @param opacityAnimationAmplitude        opacity animation amplitude value
     * @param opacityAnimationSpeed            opacity animation speed value
     * @param opacityFinalAnimationActive      whether the final opacity animation is active
     * @param opacityFinalAnimationAmplitude   final opacity animation amplitude value
     * @param opacityFinalAnimationSpeed       final opacity animation speed
     * @param configurationMaximumCircles      the maximum circles limit - configuration
     * @param animationMaximumCircles          the maximum circles limit - animation
     */
    public CirclesConfiguration(String name, Color backgroundColor,
                                boolean fractalFinalColorActive, Color fractalColor, Color fractalFinalColor,
                                int childCount, int recursions, double startAngle, double initialRadius, double sizeRatio,
                                boolean lineWidthFinalActive, double lineWidth, double lineWidthFinal,
                                boolean opacityFinalActive, double opacity, double opacityFinal, boolean isAnimationActive,
                                boolean startAngleAnimationActive, double startAngleAnimationAmplitude, double startAngleAnimationSpeed,
                                boolean initialRadiusAnimationActive, double initialRadiusAnimationAmplitude, double initialRadiusAnimationSpeed,
                                boolean sizeRatioAnimationActive, double sizeRatioAnimationAmplitude, double sizeRatioAnimationSpeed,
                                boolean lineWidthAnimationActive, double lineWidthAnimationAmplitude, double lineWidthAnimationSpeed,
                                boolean lineWidthFinalAnimationActive, double lineWidthFinalAnimationAmplitude, double lineWidthFinalAnimationSpeed,
                                boolean opacityAnimationActive, double opacityAnimationAmplitude, double opacityAnimationSpeed,
                                boolean opacityFinalAnimationActive, double opacityFinalAnimationAmplitude, double opacityFinalAnimationSpeed,
                                int configurationMaximumCircles, int animationMaximumCircles) {
        this.name = name;
        this.backgroundColor = backgroundColor.toString();
        this.fractalFinalColorActive = fractalFinalColorActive;
        this.fractalColor = fractalColor.toString();
        this.fractalFinalColor = fractalFinalColor.toString();
        this.childCount = childCount;
        this.recursions = recursions;
        this.startAngle = startAngle;
        this.initialRadius = initialRadius;
        this.sizeRatio = sizeRatio;
        this.lineWidthFinalActive = lineWidthFinalActive;
        this.lineWidth = lineWidth;
        this.lineWidthFinal = lineWidthFinal;
        this.opacityFinalActive = opacityFinalActive;
        this.opacity = opacity;
        this.opacityFinal = opacityFinal;

        this.isAnimationActive = isAnimationActive;

        this.startAngleAnimationActive = startAngleAnimationActive;
        this.startAngleAnimationAmplitude = startAngleAnimationAmplitude;
        this.startAngleAnimationSpeed = startAngleAnimationSpeed;
        this.initialRadiusAnimationActive = initialRadiusAnimationActive;
        this.initialRadiusAnimationAmplitude = initialRadiusAnimationAmplitude;
        this.initialRadiusAnimationSpeed = initialRadiusAnimationSpeed;
        this.sizeRatioAnimationActive = sizeRatioAnimationActive;
        this.sizeRatioAnimationAmplitude = sizeRatioAnimationAmplitude;
        this.sizeRatioAnimationSpeed = sizeRatioAnimationSpeed;
        this.lineWidthAnimationActive = lineWidthAnimationActive;
        this.lineWidthAnimationAmplitude = lineWidthAnimationAmplitude;
        this.lineWidthAnimationSpeed = lineWidthAnimationSpeed;
        this.lineWidthFinalAnimationActive = lineWidthFinalAnimationActive;
        this.lineWidthFinalAnimationAmplitude = lineWidthFinalAnimationAmplitude;
        this.lineWidthFinalAnimationSpeed = lineWidthFinalAnimationSpeed;
        this.opacityAnimationActive = opacityAnimationActive;
        this.opacityAnimationAmplitude = opacityAnimationAmplitude;
        this.opacityAnimationSpeed = opacityAnimationSpeed;
        this.opacityFinalAnimationActive = opacityFinalAnimationActive;
        this.opacityFinalAnimationAmplitude = opacityFinalAnimationAmplitude;
        this.opacityFinalAnimationSpeed = opacityFinalAnimationSpeed;

        this.configurationMaximumCircles = configurationMaximumCircles;
        this.animationMaximumCircles = animationMaximumCircles;
    }

    /**
     * Partial constructor, assigns white colors, false, and zero to everything
     *
     * @param name name of the configuration
     */
    public CirclesConfiguration(String name) {
        this(name, Color.WHITE, false, Color.WHITE,
                Color.WHITE, 0, 0, 0,
                0, 0, false, 0,
                0, false, 0, 0, false,
                false, 0, 0, false,
                0, 0, false, 0,
                0, false, 0, 0,
                false, 0, 0, false,
                0, 0, false,
                0, 0, 1000000, 15000);
    }

    /**
     * Partial constructor, assigns empty name, white colors, false and zero to everything
     */
    public CirclesConfiguration() {
        //empty config
        this("");
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets background color.
     *
     * @return the background color
     */
    public Color getBackgroundColor() {
        return Color.valueOf(backgroundColor);
    }

    /**
     * Sets background color.
     *
     * @param backgroundColor the background color
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor.toString();
    }

    /**
     * Is fractal final color active boolean.
     *
     * @return the boolean
     */
    public boolean isFractalFinalColorActive() {
        return fractalFinalColorActive;
    }

    /**
     * Sets fractal final color active.
     *
     * @param fractalFinalColorActive the fractal final color active
     */
    public void setFractalFinalColorActive(boolean fractalFinalColorActive) {
        this.fractalFinalColorActive = fractalFinalColorActive;
    }

    /**
     * Gets fractal color.
     *
     * @return the fractal color
     */
    public Color getFractalColor() {
        return Color.valueOf(fractalColor);
    }

    /**
     * Sets fractal color.
     *
     * @param fractalColor the fractal color
     */
    public void setFractalColor(Color fractalColor) {
        this.fractalColor = fractalColor.toString();
    }

    /**
     * Gets fractal final color.
     *
     * @return the fractal final color
     */
    public Color getFractalFinalColor() {
        return Color.valueOf(fractalFinalColor);
    }

    /**
     * Sets fractal final color.
     *
     * @param fractalFinalColor the fractal final color
     */
    public void setFractalFinalColor(Color fractalFinalColor) {
        this.fractalFinalColor = fractalFinalColor.toString();
    }

    /**
     * Gets child count.
     *
     * @return the child count
     */
    public int getChildCount() {
        return childCount;
    }

    /**
     * Sets child count.
     *
     * @param childCount the child count
     */
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    /**
     * Gets recursions.
     *
     * @return the recursions
     */
    public int getRecursions() {
        return recursions;
    }

    /**
     * Sets recursions.
     *
     * @param recursions the recursions
     */
    public void setRecursions(int recursions) {
        this.recursions = recursions;
    }

    /**
     * Gets start angle.
     *
     * @return the start angle
     */
    public double getStartAngle() {
        return startAngle;
    }

    /**
     * Sets start angle.
     *
     * @param startAngle the start angle
     */
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    /**
     * Gets initial radius.
     *
     * @return the initial radius
     */
    public double getInitialRadius() {
        return initialRadius;
    }

    /**
     * Sets initial radius.
     *
     * @param initialRadius the initial radius
     */
    public void setInitialRadius(double initialRadius) {
        this.initialRadius = initialRadius;
    }

    /**
     * Gets size ratio.
     *
     * @return the size ratio
     */
    public double getSizeRatio() {
        return sizeRatio;
    }

    /**
     * Sets size ratio.
     *
     * @param sizeRatio the size ratio
     */
    public void setSizeRatio(double sizeRatio) {
        this.sizeRatio = sizeRatio;
    }

    /**
     * Is line width final active boolean.
     *
     * @return the boolean
     */
    public boolean isLineWidthFinalActive() {
        return lineWidthFinalActive;
    }

    /**
     * Sets line width final active.
     *
     * @param lineWidthFinalActive the line width final active
     */
    public void setLineWidthFinalActive(boolean lineWidthFinalActive) {
        this.lineWidthFinalActive = lineWidthFinalActive;
    }

    /**
     * Gets line width.
     *
     * @return the line width
     */
    public double getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets line width.
     *
     * @param lineWidth the line width
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * Gets line width final.
     *
     * @return the line width final
     */
    public double getLineWidthFinal() {
        return lineWidthFinal;
    }

    /**
     * Sets line width final.
     *
     * @param lineWidthFinal the line width final
     */
    public void setLineWidthFinal(double lineWidthFinal) {
        this.lineWidthFinal = lineWidthFinal;
    }

    /**
     * Is opacity final active boolean.
     *
     * @return the boolean
     */
    public boolean isOpacityFinalActive() {
        return opacityFinalActive;
    }

    /**
     * Sets opacity final active.
     *
     * @param opacityFinalActive the opacity final active
     */
    public void setOpacityFinalActive(boolean opacityFinalActive) {
        this.opacityFinalActive = opacityFinalActive;
    }

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    public double getOpacity() {
        return opacity;
    }

    /**
     * Sets opacity.
     *
     * @param opacity the opacity
     */
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    /**
     * Gets opacity final.
     *
     * @return the opacity final
     */
    public double getOpacityFinal() {
        return opacityFinal;
    }

    /**
     * Sets opacity final.
     *
     * @param opacityFinal the opacity final
     */
    public void setOpacityFinal(double opacityFinal) {
        this.opacityFinal = opacityFinal;
    }

    /**
     * Is animation active boolean.
     *
     * @return the boolean
     */
//animations
    public boolean isAnimationActive() {
        return isAnimationActive;
    }

    /**
     * Sets animation active.
     *
     * @param animationActive the animation active
     */
    public void setAnimationActive(boolean animationActive) {
        isAnimationActive = animationActive;
    }

    /**
     * Is start angle animation active boolean.
     *
     * @return the boolean
     */
    public boolean isStartAngleAnimationActive() {
        return startAngleAnimationActive;
    }

    /**
     * Sets start angle animation active.
     *
     * @param startAngleAnimationActive the start angle animation active
     */
    public void setStartAngleAnimationActive(boolean startAngleAnimationActive) {
        this.startAngleAnimationActive = startAngleAnimationActive;
    }

    /**
     * Gets start angle animation amplitude.
     *
     * @return the start angle animation amplitude
     */
    public double getStartAngleAnimationAmplitude() {
        return startAngleAnimationAmplitude;
    }

    /**
     * Sets start angle animation amplitude.
     *
     * @param startAngleAnimationAmplitude the start angle animation amplitude
     */
    public void setStartAngleAnimationAmplitude(double startAngleAnimationAmplitude) {
        this.startAngleAnimationAmplitude = startAngleAnimationAmplitude;
    }

    /**
     * Gets start angle animation speed.
     *
     * @return the start angle animation speed
     */
    public double getStartAngleAnimationSpeed() {
        return startAngleAnimationSpeed;
    }

    /**
     * Sets start angle animation speed.
     *
     * @param startAngleAnimationSpeed the start angle animation speed
     */
    public void setStartAngleAnimationSpeed(double startAngleAnimationSpeed) {
        this.startAngleAnimationSpeed = startAngleAnimationSpeed;
    }

    /**
     * Is initial radius animation active boolean.
     *
     * @return the boolean
     */
    public boolean isInitialRadiusAnimationActive() {
        return initialRadiusAnimationActive;
    }

    /**
     * Sets initial radius animation active.
     *
     * @param initialRadiusAnimationActive the initial radius animation active
     */
    public void setInitialRadiusAnimationActive(boolean initialRadiusAnimationActive) {
        this.initialRadiusAnimationActive = initialRadiusAnimationActive;
    }

    /**
     * Gets initial radius animation amplitude.
     *
     * @return the initial radius animation amplitude
     */
    public double getInitialRadiusAnimationAmplitude() {
        return initialRadiusAnimationAmplitude;
    }

    /**
     * Sets initial radius animation amplitude.
     *
     * @param initialRadiusAnimationAmplitude the initial radius animation amplitude
     */
    public void setInitialRadiusAnimationAmplitude(double initialRadiusAnimationAmplitude) {
        this.initialRadiusAnimationAmplitude = initialRadiusAnimationAmplitude;
    }

    /**
     * Gets initial radius animation speed.
     *
     * @return the initial radius animation speed
     */
    public double getInitialRadiusAnimationSpeed() {
        return initialRadiusAnimationSpeed;
    }

    /**
     * Sets initial radius animation speed.
     *
     * @param initialRadiusAnimationSpeed the initial radius animation speed
     */
    public void setInitialRadiusAnimationSpeed(double initialRadiusAnimationSpeed) {
        this.initialRadiusAnimationSpeed = initialRadiusAnimationSpeed;
    }

    /**
     * Is size ratio animation active boolean.
     *
     * @return the boolean
     */
    public boolean isSizeRatioAnimationActive() {
        return sizeRatioAnimationActive;
    }

    /**
     * Sets size ratio animation active.
     *
     * @param sizeRatioAnimationActive the size ratio animation active
     */
    public void setSizeRatioAnimationActive(boolean sizeRatioAnimationActive) {
        this.sizeRatioAnimationActive = sizeRatioAnimationActive;
    }

    /**
     * Gets size ratio animation amplitude.
     *
     * @return the size ratio animation amplitude
     */
    public double getSizeRatioAnimationAmplitude() {
        return sizeRatioAnimationAmplitude;
    }

    /**
     * Sets size ratio animation amplitude.
     *
     * @param sizeRatioAnimationAmplitude the size ratio animation amplitude
     */
    public void setSizeRatioAnimationAmplitude(double sizeRatioAnimationAmplitude) {
        this.sizeRatioAnimationAmplitude = sizeRatioAnimationAmplitude;
    }

    /**
     * Gets size ratio animation speed.
     *
     * @return the size ratio animation speed
     */
    public double getSizeRatioAnimationSpeed() {
        return sizeRatioAnimationSpeed;
    }

    /**
     * Sets size ratio animation speed.
     *
     * @param sizeRatioAnimationSpeed the size ratio animation speed
     */
    public void setSizeRatioAnimationSpeed(double sizeRatioAnimationSpeed) {
        this.sizeRatioAnimationSpeed = sizeRatioAnimationSpeed;
    }

    /**
     * Is line width animation active boolean.
     *
     * @return the boolean
     */
    public boolean isLineWidthAnimationActive() {
        return lineWidthAnimationActive;
    }

    /**
     * Sets line width animation active.
     *
     * @param lineWidthAnimationActive the line width animation active
     */
    public void setLineWidthAnimationActive(boolean lineWidthAnimationActive) {
        this.lineWidthAnimationActive = lineWidthAnimationActive;
    }

    /**
     * Gets line width animation amplitude.
     *
     * @return the line width animation amplitude
     */
    public double getLineWidthAnimationAmplitude() {
        return lineWidthAnimationAmplitude;
    }

    /**
     * Sets line width animation amplitude.
     *
     * @param lineWidthAnimationAmplitude the line width animation amplitude
     */
    public void setLineWidthAnimationAmplitude(double lineWidthAnimationAmplitude) {
        this.lineWidthAnimationAmplitude = lineWidthAnimationAmplitude;
    }

    /**
     * Gets line width animation speed.
     *
     * @return the line width animation speed
     */
    public double getLineWidthAnimationSpeed() {
        return lineWidthAnimationSpeed;
    }

    /**
     * Sets line width animation speed.
     *
     * @param lineWidthAnimationSpeed the line width animation speed
     */
    public void setLineWidthAnimationSpeed(double lineWidthAnimationSpeed) {
        this.lineWidthAnimationSpeed = lineWidthAnimationSpeed;
    }

    /**
     * Is line width final animation active boolean.
     *
     * @return the boolean
     */
    public boolean isLineWidthFinalAnimationActive() {
        return lineWidthFinalAnimationActive;
    }

    /**
     * Sets line width final animation active.
     *
     * @param lineWidthFinalAnimationActive the line width final animation active
     */
    public void setLineWidthFinalAnimationActive(boolean lineWidthFinalAnimationActive) {
        this.lineWidthFinalAnimationActive = lineWidthFinalAnimationActive;
    }

    /**
     * Gets line width final animation amplitude.
     *
     * @return the line width final animation amplitude
     */
    public double getLineWidthFinalAnimationAmplitude() {
        return lineWidthFinalAnimationAmplitude;
    }

    /**
     * Sets line width final animation amplitude.
     *
     * @param lineWidthFinalAnimationAmplitude the line width final animation amplitude
     */
    public void setLineWidthFinalAnimationAmplitude(double lineWidthFinalAnimationAmplitude) {
        this.lineWidthFinalAnimationAmplitude = lineWidthFinalAnimationAmplitude;
    }

    /**
     * Gets line width final animation speed.
     *
     * @return the line width final animation speed
     */
    public double getLineWidthFinalAnimationSpeed() {
        return lineWidthFinalAnimationSpeed;
    }

    /**
     * Sets line width final animation speed.
     *
     * @param lineWidthFinalAnimationSpeed the line width final animation speed
     */
    public void setLineWidthFinalAnimationSpeed(double lineWidthFinalAnimationSpeed) {
        this.lineWidthFinalAnimationSpeed = lineWidthFinalAnimationSpeed;
    }

    /**
     * Is opacity animation active boolean.
     *
     * @return the boolean
     */
    public boolean isOpacityAnimationActive() {
        return opacityAnimationActive;
    }

    /**
     * Sets opacity animation active.
     *
     * @param opacityAnimationActive the opacity animation active
     */
    public void setOpacityAnimationActive(boolean opacityAnimationActive) {
        this.opacityAnimationActive = opacityAnimationActive;
    }

    /**
     * Gets opacity animation amplitude.
     *
     * @return the opacity animation amplitude
     */
    public double getOpacityAnimationAmplitude() {
        return opacityAnimationAmplitude;
    }

    /**
     * Sets opacity animation amplitude.
     *
     * @param opacityAnimationAmplitude the opacity animation amplitude
     */
    public void setOpacityAnimationAmplitude(double opacityAnimationAmplitude) {
        this.opacityAnimationAmplitude = opacityAnimationAmplitude;
    }

    /**
     * Gets opacity animation speed.
     *
     * @return the opacity animation speed
     */
    public double getOpacityAnimationSpeed() {
        return opacityAnimationSpeed;
    }

    /**
     * Sets opacity animation speed.
     *
     * @param opacityAnimationSpeed the opacity animation speed
     */
    public void setOpacityAnimationSpeed(double opacityAnimationSpeed) {
        this.opacityAnimationSpeed = opacityAnimationSpeed;
    }

    /**
     * Is opacity final animation active boolean.
     *
     * @return the boolean
     */
    public boolean isOpacityFinalAnimationActive() {
        return opacityFinalAnimationActive;
    }

    /**
     * Sets opacity final animation active.
     *
     * @param opacityFinalAnimationActive the opacity final animation active
     */
    public void setOpacityFinalAnimationActive(boolean opacityFinalAnimationActive) {
        this.opacityFinalAnimationActive = opacityFinalAnimationActive;
    }

    /**
     * Gets opacity final animation amplitude.
     *
     * @return the opacity final animation amplitude
     */
    public double getOpacityFinalAnimationAmplitude() {
        return opacityFinalAnimationAmplitude;
    }

    /**
     * Sets opacity final animation amplitude.
     *
     * @param opacityFinalAnimationAmplitude the opacity final animation amplitude
     */
    public void setOpacityFinalAnimationAmplitude(double opacityFinalAnimationAmplitude) {
        this.opacityFinalAnimationAmplitude = opacityFinalAnimationAmplitude;
    }

    /**
     * Gets opacity final animation speed.
     *
     * @return the opacity final animation speed
     */
    public double getOpacityFinalAnimationSpeed() {
        return opacityFinalAnimationSpeed;
    }

    /**
     * Sets opacity final animation speed.
     *
     * @param opacityFinalAnimationSpeed the opacity final animation speed
     */
    public void setOpacityFinalAnimationSpeed(double opacityFinalAnimationSpeed) {
        this.opacityFinalAnimationSpeed = opacityFinalAnimationSpeed;
    }

    /**
     * Get configuration maximum circles int.
     *
     * @return the int
     */
    public int getConfigurationMaximumCircles() {
        return configurationMaximumCircles;
    }

    /**
     * Set configuration maximum circles.
     *
     * @param configurationMaximumCircles the configuration maximum circles
     */
    public void setConfigurationMaximumCircles(int configurationMaximumCircles) {
        this.configurationMaximumCircles = configurationMaximumCircles;
    }

    /**
     * Get animation maximum circles int.
     *
     * @return the int
     */
    public int getAnimationMaximumCircles() {
        return animationMaximumCircles;
    }

    /**
     * Set animation maximum circles.
     *
     * @param animationMaximumCircles the animation maximum circles
     */
    public void setAnimationMaximumCircles(int animationMaximumCircles) {
        this.animationMaximumCircles = animationMaximumCircles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return (this == other) || (other != null && other.getClass().getName().equals(this.getClass().getName()) &&
                ((CirclesConfiguration) other).getName().equals(this.name));
    }
}
