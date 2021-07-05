package fractalmatic.circles.ui;

import fractalmatic.App;
import fractalmatic.circles.animation.AnimationManager;
import fractalmatic.circles.calculation.PropertyManager;
import fractalmatic.circles.config.ConfigurationManager;
import fractalmatic.common.ui.BasicExplorerController;
import fractalmatic.common.ui.util.UiUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;


/**
 * Controller of the main ui of circles fractals.
 */
public class CirclesMainUiController {

    /** The close button svg shape */
    private static final String homeButtonPath = "M10 0 20 8 17 8 17 20 12 20 12 11 8 11 8 20 3 20 3 8 0 8 10 0";


    /** The main pane that contains everything */
    @FXML
    public Pane mainPane;
    /** The fractal explorer */
    @FXML
    public Pane basicExplorer;
    /** The sliding menu */
    @FXML
    public StackPane slidingMenu;
    /** The close button */
    @FXML
    public Button homeButton;
    /** The circles explorer controller */
    @FXML
    BasicExplorerController basicExplorerController;

    /**
     * Initializes before loading
     */
    @FXML
    void initialize() {

        SVGPath homeButtonSVG = new SVGPath();
        homeButtonSVG.setContent(homeButtonPath);
        // closeButton = new Region();// this is going to be actual close button
        homeButton.setShape(homeButtonSVG); // region is converted to the svg shape
        homeButton.getStyleClass().add("closeButton");
        //closeButton.getStyleClass().add("animationCloseButton");
        homeButton.setOnAction(event -> {

            if (UiUtil.tools.alertYesOrNo("Do you want to turn back to main menu?")) {
                if (ConfigurationManager.INSTANCE.shouldSaveBeforeClosing()) {
                    ConfigurationManager.INSTANCE.handleSavingBeforeClosing();
                }
                AnimationManager.INSTANCE.reset();
                App.setRoot("/fractalmatic/Home.fxml");
            }
        });

        homeButton.translateXProperty().bind(Bindings.subtract(mainPane.widthProperty(), 33));
        homeButton.setTranslateY(11);

        Button btn = new Button();
        btn.setShape(homeButtonSVG);

        basicExplorer.prefWidthProperty().bind(mainPane.widthProperty());
        basicExplorer.prefHeightProperty().bind(mainPane.heightProperty());
        //clip explorer since canvas is huge, otherwise JFXDecorator right and bottom border do not show up.

        Rectangle clipPane = new Rectangle();
        basicExplorer.setClip(clipPane);
        mainPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });

        KeyCombination cntrlR = new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN);
        mainPane.setOnKeyPressed(event -> {
            if (cntrlR.match(event)) {
                ConfigurationManager.INSTANCE.randomizeAndSet();
            }
        });

        ConfigurationManager.INSTANCE.initializeConfigs();
        PropertyManager.INSTANCE.drawRequest();
        Platform.runLater(() -> mainPane.requestFocus());
        resetFractalCenter();
    }

    /**
     * Resets fractal center.
     */
    public void resetFractalCenter() {
        Platform.runLater(() -> basicExplorerController.resetCenterPoint());
    }
}
