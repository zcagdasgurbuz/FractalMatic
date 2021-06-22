package circles.calculation;

import javafx.animation.Interpolator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class generates and draws the circles fractal
 */
public class FractalGenerator {

    private GraphicsContext graphicsContext;
    private Pane explorer;
    private boolean isRenderingActive;
    private IntegerProperty drawnCircles;

    /**
     * Constructor..
     *
     * @param explorer the pane is to be used for background color
     * @param graphicsContext the graphic context is to be used for drawing
     */
    public FractalGenerator(Pane explorer, GraphicsContext graphicsContext) {

        this.graphicsContext = graphicsContext;
        this.explorer = explorer;
        isRenderingActive = true;
        drawnCircles = new SimpleIntegerProperty(0);
    }

    /**
     * This method draws the fractal with the given parameters.
     *
     * @param backgroundColor  background color to be set
     * @param fractalFinalColorActive  whether the final fractal color is active
     * @param fractalColor  fractal color to be set
     * @param fractalFinalColor the final fractal color to be set
     * @param childCount    the child count to be drawn
     * @param maxRecursions  the maximum recursions tobe drawn
     * @param startAngle    the angle that the first child to be drawn at
     * @param radius    the radius of the first circle
     * @param sizeRatio the size ratio between parent and child
     * @param lineWidthFinalActive  whether the final line width is active
     * @param lineWidth the line width
     * @param lineWidthFinal    the final line width
     * @param opacityFinalActive    whether the final opacity is active
     * @param opacity   the opacity
     * @param opacityFinal  the final opacity
     */
    public void drawFractal(Color backgroundColor,
                            boolean fractalFinalColorActive, Color fractalColor, Color fractalFinalColor,
                            int childCount, int maxRecursions,
                            double startAngle, double radius, double sizeRatio,
                            boolean lineWidthFinalActive, double lineWidth, double lineWidthFinal,
                            boolean opacityFinalActive, double opacity, double opacityFinal) {
        //set background
        explorer.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        //clear canvas
        graphicsContext.clearRect(0, 0,
                graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(fractalColor);
        graphicsContext.setGlobalAlpha(opacity);
        graphicsContext.setLineWidth(lineWidth);
        drawnCircles.set(0);
        HashMap<Integer, Color> colors = new HashMap<>();
        HashMap<Integer, Double> lineWidths = new HashMap<>();
        HashMap<Integer, Double> opacities = new HashMap<>();

        // (endValue - startValue) * stepNumber / lastStepNumber + startValue
        // https://stackoverflow.com/questions/13488957/interpolate-from-one-color-to-another
        if (fractalFinalColorActive) {
            double red, green, blue;
            //(endValue - startValue) * stepNumber / lastStepNumber + startValue;
            for (int idx = 0; idx < maxRecursions; idx++) {
                red = ((((Color) fractalFinalColor).getRed() - ((Color) fractalColor).getRed())) *
                        (idx / (double) (maxRecursions - 1)) + ((Color) fractalColor).getRed();
                green = ((((Color) fractalFinalColor).getGreen() - ((Color) fractalColor).getGreen())) *
                        (idx / (double) (maxRecursions - 1)) + ((Color) fractalColor).getGreen();
                blue = ((((Color) fractalFinalColor).getBlue() - ((Color) fractalColor).getBlue())) *
                        (idx / (double) (maxRecursions - 1)) + ((Color) fractalColor).getBlue();
                colors.put(idx, new Color(red, green, blue, 1.0));
            }
        }
        //prepare the line widths
        if (lineWidthFinalActive) {
            double lineWidthInc = (lineWidthFinal - lineWidth) / (double) (maxRecursions - 1);
            for (int idx = 0; idx < maxRecursions; idx++) {
                lineWidths.put(idx, lineWidth + (lineWidthInc * idx));
                //lineWidths.put(idx, Interpolator.LINEAR.interpolate(lineWidth, lineWidthFinal, idx / ((double) maxRecursions - 1)));
            }
        } else {
            //this is needed for line lengths adjustments
            for (int idx = 0; idx < maxRecursions; idx++) {
                lineWidths.put(idx, lineWidth);
            }
        }
        // prepare the line widths
        if (opacityFinalActive) {
            double opacityInc = (opacityFinal - opacity) / (double) (maxRecursions - 1);
            for (int idx = 0; idx < maxRecursions; idx++) {
                opacities.put(idx, opacity + (opacityInc * idx));
            }
        }
        // if it is ok, start generating with actual drawing method
        if (isRenderingActive) {
            generateFractal(graphicsContext.getCanvas().getWidth() / 2.0,
                    graphicsContext.getCanvas().getHeight() / 2.0,
                    radius,
                    sizeRatio / 100.0,
                    childCount,
                    Math.toRadians(startAngle),
                    fractalFinalColorActive,
                    maxRecursions - 1,
                    colors,
                    lineWidthFinalActive,
                    lineWidths,
                    opacityFinalActive,
                    opacities,
                    graphicsContext);
        }
    }

    /**
     * This helper method performs the actual drawing.
     *
     * @param x x coordinate of the first circle
     * @param y y coordinate of the first circle
     * @param radius    the radius of the first circle
     * @param sizeRatio the size ratio of the parent to its child
     * @param childCount    the child count
     * @param startAngle    the angle that the first child to be drawn at
     * @param finalColorActive   whether the final fractal color is active
     * @param maxRecursions the number of recursions
     * @param colors    colors for every recursion level
     * @param lineWidthFinalActive  whether the final line width is active
     * @param lineWidths    line widths for every recursion level
     * @param opacityFinalActive    whether the final opacity is active
     * @param opacities opacities for every recursion level
     * @param graphicsContext   the graphics context is to be used to draw
     */
    private void generateFractal(double x, double y, double radius, double sizeRatio,
                                 int childCount, double startAngle, boolean finalColorActive,
                                 int maxRecursions, HashMap<Integer, Color> colors,
                                 boolean lineWidthFinalActive, HashMap<Integer, Double> lineWidths,
                                 boolean opacityFinalActive, HashMap<Integer, Double> opacities,
                                 GraphicsContext graphicsContext) {

        int recursionDepth = 0;
        Stack<double[]> stack = new Stack<>();
        stack.push(new double[]{x, y, radius, recursionDepth});
        while (!stack.isEmpty()) {

            double[] current = stack.pop();
            double currentX = current[0];
            double currentY = current[1];
            double currentRadius = current[2];
            int currentDepth = (int) current[3];

            if (finalColorActive) {
                graphicsContext.setStroke(colors.get(currentDepth));
            }
            if (lineWidthFinalActive) {
                graphicsContext.setLineWidth(lineWidths.get(currentDepth));
            }
            if (opacityFinalActive) {
                graphicsContext.setGlobalAlpha(opacities.get(currentDepth));
            }

            if (currentRadius >= 1) {
                graphicsContext.strokeOval(currentX - currentRadius, currentY - currentRadius,
                        currentRadius * 2, currentRadius * 2);

                drawnCircles.set(drawnCircles.get() + 1);
            }

            if (currentDepth < maxRecursions) {
                double newRadius = currentRadius * sizeRatio;
                for (int count = 0; count < childCount; count++) {
                    //current angle sin and cos values
                    double cos = Math.cos(-startAngle - (count * 2 * Math.PI / childCount));
                    double sin = Math.sin(-startAngle - (count * 2 * Math.PI / childCount));
                    //line start
                    double lineStartX = currentX + (currentRadius + lineWidths.get(currentDepth)) * cos;
                    double lineStartY = currentY + (currentRadius + lineWidths.get(currentDepth)) * sin;
                    double lineLengthX = (currentRadius - newRadius - lineWidths.get(currentDepth + 1)) * cos;
                    double lineLengthY = (currentRadius - newRadius - lineWidths.get(currentDepth + 1)) * sin;

                    if (finalColorActive) {
                        graphicsContext.setStroke(colors.get(currentDepth));
                    }
                    if (lineWidthFinalActive) {
                        graphicsContext.setLineWidth(lineWidths.get(currentDepth));
                    }
                    if (opacityFinalActive) {
                        graphicsContext.setGlobalAlpha(opacities.get(currentDepth));
                    }

                    graphicsContext.strokeLine(lineStartX, lineStartY,
                            (lineStartX + lineLengthX), (lineStartY + lineLengthY));

                    stack.push(new double[]{(lineStartX + currentRadius * cos),
                            (lineStartY + currentRadius * sin), newRadius, currentDepth + 1});
                }
            }
        }
    }

    /**
     * This method sets whether the rendering is active or not
     *
     * @param isRenderingActive whether the rendering is active
     */
    public void setRenderingActive(boolean isRenderingActive) {
        this.isRenderingActive = isRenderingActive;
    }

    /**
     * This method retrieves the number of drawn circles property, property is updated by every draw request
     * @return  the number of drawn circles property
     */
    public IntegerProperty drawnCircles() {
        return drawnCircles;
    }

}
