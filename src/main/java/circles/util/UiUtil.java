package circles.util;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utility class for circles fractal
 */
public class UiUtil {

    /**
     * The singleton instance.
     */
    public static final UiUtil tools = new UiUtil();

    /**
     * Instantiates a new Ui util.
     */
    private UiUtil() {

    }

    /**
     * Shows a yes or no dialog box.
     *
     * @param message the message is to be displayed
     * @return the boolean
     */
    public boolean alertYesOrNo(String message) {
        AtomicBoolean result = new AtomicBoolean(false);

        Alert alert = new Alert(Alert.AlertType.NONE, message);
        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.getButtonTypes().add(ButtonType.YES);
        dialogPane.getButtonTypes().add(ButtonType.NO);
        dialogPane.getStylesheets().add(
                getClass().getResource("/circles/ui/circles.css").toExternalForm());

        dialogPane.setOnMousePressed(pressEvent -> {
            dialogPane.setOnMouseDragged(dragEvent -> {
                alert.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                alert.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        alert.showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    result.set(true);
                });

        return result.get();
    }

    /**
     * Shows quick popup message with given message at the right side of the given source. If message is error,
     * message color is red
     *
     * @param source  the source
     * @param message the message is to be shown
     * @param isError whether the message is error
     */
    public void showQuickPopupMessage(Control source, String message, boolean isError) {

        Label label = new Label(message);
        Rectangle container = new Rectangle();
        container.getStyleClass().add("popup-container");
        container.widthProperty().bind(label.widthProperty());
        container.heightProperty().bind(label.heightProperty());
        Popup popup = new Popup();
        popup.getContent().add(container);
        popup.getContent().add(label);
        Point2D anchorPoint = source.localToScreen(
                source.getWidth() + 2,
                2
        );
        popup.show(source.getScene().getWindow(),
                anchorPoint.getX(),
                anchorPoint.getY()
        );
        popup.setAutoHide(true);
        if (isError) {
            label.setTextFill(Color.RED);
        }
    }
}
