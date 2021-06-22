package circles.ui;

import circles.calculation.CalculationManager;
import circles.calculation.PropertyManager;
import circles.util.SimpleRangedDoubleProperty;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


/**
 * Fractal Explorer controller, handles the dragging and zooming
 */
public class CirclesExplorerController {


    private final DoubleProperty canvasX;
    private final DoubleProperty canvasY;
    private final DoubleProperty zoom;
    public Pane explorer;
    public Canvas canvas;
    private Double capturedX;
    private Double capturedY;
    private CalculationManager calculationManager = CalculationManager.INSTANCE;
    
    public CirclesExplorerController(){
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
     *  Assigns the necessary mouse listeners for mouse dragging
     */
    private void activateMouseDragging() {
        explorer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                capturedX = event.getX() / zoom.get() - canvasX.get();
                capturedY = event.getY() / zoom.get() - canvasY.get();

            }
        });
        explorer.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                canvasX.set(event.getX() / zoom.get() - capturedX);
                canvasY.set(event.getY() / zoom.get() - capturedY);
            }
        });
        explorer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    resetCenterPoint();
                }

            }
        });
    }

    /**
     *  Assigns the necessary mouse listeners for zooming
     */
    private void activateZoomByScrolling() {
        explorer.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double zoomValue = zoom.get();
                if (scrollEvent.getDeltaY() < 0) {
                    zoom.set(zoomValue + 0.05);
                } else {
                    zoom.set(zoomValue - 0.05);
                }
            }
        });
    }


    /**
     *  Retrieves the graphics context of the canvas
     *
     * @return the graphics context of the canvas
     */
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    /**
     *  Retrieves the pane behind canvas, which is designed to act as a background
     *
     * @return the explorer pane, -background
     */
    public Pane getExplorer() {
        return explorer;
    }
}
