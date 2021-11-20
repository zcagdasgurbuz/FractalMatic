package fractalmatic.common.ui;

import fractalmatic.circles.calculation.CalculationManager;
import fractalmatic.common.util.SimpleRangedDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


/**
 * Fractal Explorer controller, handles the dragging and zooming
 */
public class BasicExplorerController {


    /** The x coordinate prop of canvas */
    private final DoubleProperty canvasX;
    /** The y coordinate prop of canvas */
    private final DoubleProperty canvasY;
    /** The zoom prop of canvas */
    private final DoubleProperty zoom;
    /** The explorer -background/container */
    public Pane explorer;
    /** The canvas where drawing happens. */
    public Canvas canvas;
    /** The calculation manager */
    CalculationManager calculationManager = CalculationManager.INSTANCE;
    /** The captured x coordinate when mouse pressed. */
    private Double capturedX;
    /** The captured y coordinate when mouse pressed */
    private Double capturedY;

    /**
     * Instantiates a new Circles explorer controller.
     */
    public BasicExplorerController() {
        canvasX = new SimpleDoubleProperty();
        canvasY = new SimpleDoubleProperty();
        capturedX = 0.0;
        capturedY = 0.0;
        zoom = new SimpleRangedDoubleProperty(1.0D, 3.0D, 1.0D);
    }

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        canvas.translateXProperty().bind(canvasX);
        canvas.translateYProperty().bind(canvasY);
        canvas.scaleXProperty().bind(zoom);
        canvas.scaleYProperty().bind(zoom);
        activateMouseDragging();
        activateZoomByScrolling();
        //
        calculationManager.setExplorer(explorer);
        calculationManager.setGraphicsContext(canvas.getGraphicsContext2D());
        calculationManager.initializeGenerator();
    }

    /**
     * Resets the canvas position, -centers and resets zoom
     */
    public void resetCenterPoint() {
        zoom.set(1.0D);
        canvasX.set(-(this.canvas.getWidth() - explorer.getWidth()) / 2.0D);
        canvasY.set(-(this.canvas.getHeight() - explorer.getHeight()) / 2.0D);
    }

    /**
     * Assigns the necessary mouse listeners for mouse dragging
     */
    private void activateMouseDragging() {
        explorer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            capturedX = event.getX() / zoom.get() - canvasX.get();
            capturedY = event.getY() / zoom.get() - canvasY.get();

        });
        explorer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            canvasX.set(event.getX() / zoom.get() - capturedX);
            canvasY.set(event.getY() / zoom.get() - capturedY);
        });
        explorer.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                resetCenterPoint();
            }
        });
    }

    /**
     * Assigns the necessary mouse listeners for zooming
     */
    private void activateZoomByScrolling() {
        explorer.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            double zoomValue = zoom.get();
            if (scrollEvent.getDeltaY() < 0) {
                zoom.set(zoomValue + 0.05);
            } else {
                zoom.set(zoomValue - 0.05);
            }
        });
    }


    /**
     * Retrieves the graphics context of the canvas
     *
     * @return the graphics context of the canvas
     */
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    /**
     * Retrieves the pane behind canvas, which is designed to act as a background
     *
     * @return the explorer pane, -background
     */
    public Pane getExplorer() {
        return explorer;
    }
}
