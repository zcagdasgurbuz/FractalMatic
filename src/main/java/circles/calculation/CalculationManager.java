package circles.calculation;

import circles.animation.AnimationManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This singleton class handles the connection between other managers and explorer via fractal generator.
 */
public enum CalculationManager {
    INSTANCE; //singleton manager

    private FractalGenerator generator;
    private Pane explorer;
    private GraphicsContext graphicsContext;

    /**
     * Sets the explorer to be used in this manager class
     *
     * @param explorer the fractal explorer
     */
    public void setExplorer(Pane explorer) {
        this.explorer = explorer;
    }

    /**
     * Sets the graphics context to be used to draw fractal
     *
     * @param graphicsContext the graphic context to be used to draw
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Initializes the fractal generator to draw fractals on explorer
     */
    public void initializeGenerator() {
        generator = new FractalGenerator(explorer, graphicsContext);
    }

    /**
     * Returns the fractal generator
     *
     * @return the fractal generator
     */
    public FractalGenerator getGenerator() {
        return generator;
    }

    /**
     * Computes the  maximum number of possible recursions
     *
     * @param initialRadius  initial radius of the fractal
     * @param recursionDepth the requested recursion depth
     * @param sizeRatio      the size ratio of the child of its parent
     * @return the maximum number of possible recursions
     */
    public int calculateExpectedRecursionDepth(double initialRadius, double recursionDepth, double sizeRatio) {
        int maxRecursion = 1;
        double r = initialRadius;
        while (r > 1 && maxRecursion < recursionDepth) {
            r = r * sizeRatio / 100.0;
            maxRecursion++;
        }
        if (r < 1) {
            maxRecursion--;
        }
        return maxRecursion;
    }

    /**
     * Computes the number of circles to be drawn
     *
     * @param maxRecursion the maximum recursion depth
     * @param childCount   the child count
     * @return the number of circles
     */
    public long calculateExpectedCirclesCount(int maxRecursion, int childCount) {

        long calculatedCircleCount;

        if (childCount == 1) {
            calculatedCircleCount = maxRecursion;
        } else {
            calculatedCircleCount = (long) (1 + (((Math.pow(childCount, maxRecursion - 1) - 1) / (childCount - 1))
                    + (Math.pow(childCount, maxRecursion - 1) - 1)));
        }
        return calculatedCircleCount;
    }

    /**
     * Request drawing, if calculated number of circles is in acceptable range performs drawing.
     * Does not check the number of circles if the animation is active
     *
     * @param backgroundColor         background color
     * @param fractalFinalColorActive whether the final color is active
     * @param fractalColor            the fractal color
     * @param fractalFinalColor       the final fractal color, the leaf circles
     * @param childCount              the child count
     * @param maxRecursions           the maximum number of recursions
     * @param startAngle              the start angle,the angle of the first child
     * @param radius                  the initial radius
     * @param sizeRatio               the size ratio of the child to its parent
     * @param lineWidthFinalActive    whether the final line width is active
     * @param lineWidth               the line width of the fractal
     * @param lineWidthFinal          the final line width of the fractal, the leaf circles
     * @param opacityFinalActive      whether the final opacity is active
     * @param opacity                 the opacity of the fractal
     * @param opacityFinal            the final opacity, the leaf circles
     */
    public void drawRequest(Color backgroundColor,
                            boolean fractalFinalColorActive, Color fractalColor, Color fractalFinalColor,
                            int childCount, int maxRecursions,
                            double startAngle, double radius, double sizeRatio,
                            boolean lineWidthFinalActive, double lineWidth, double lineWidthFinal,
                            boolean opacityFinalActive, double opacity, double opacityFinal) {

        if (AnimationManager.INSTANCE.getAnimator().activeProperty().get() || PropertyManager.INSTANCE.getOkToDraw()) {
            generator.drawFractal(backgroundColor, fractalFinalColorActive, fractalColor, fractalFinalColor, childCount, maxRecursions,
                    startAngle, radius, sizeRatio, lineWidthFinalActive, lineWidth, lineWidthFinal, opacityFinalActive, opacity, opacityFinal);
        }
    }

}
