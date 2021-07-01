package circles.ui;

import circles.animation.AnimationManager;
import circles.calculation.PropertyManager;
import circles.util.ConfigurationManager;
import circles.util.UiUtil;
import fractalmatic.App;
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
import javafx.stage.Stage;


/**
 * Controller of the main ui of circles fractals.
 */
public class CirclesMainUiController {

    /** The close button svg shape */
    private static final String closeButtonPath = "M90.914,5.296c6.927-7.034,18.188-7.065,25.154-0.068 c6.961,6.995,6.991,18.369," +
            "0.068,25.397L85.743,61.452l30.425,30.855c6.866,6.978,6.773,18.28-0.208,25.247 c-6.983,6.964-18.21," +
            "6.946-25.074-0.031L60.669,86.881L30.395,117.58c-6.927,7.034-18.188,7.065-25.154,0.068 c-6.961-6.995-" +
            "6.992-18.369-0.068-25.397l30.393-30.827L5.142,30.568c-6.867-6.978-6.773-18.28,0.208-25.247 c6.983-6.963," +
            "18.21-6.946,25.074,0.031l30.217,30.643L90.914,5.296L90.914,5.296z";


    /** The main pane that contains everything */
    @FXML
    public Pane mainPane;
    /** The fractal explorer */
    @FXML
    public Pane circlesExplorer;
    /** The sliding menu */
    @FXML
    public StackPane slidingMenu;
    /** The close button */
    @FXML
    public Button closeButton;
    /** The circles explorer controller */
    @FXML
    CirclesExplorerController circlesExplorerController;

    /**
     * Initializes before loading
     */
    @FXML
    void initialize() {

        SVGPath closeButtonSVG = new SVGPath();
        closeButtonSVG.setContent(closeButtonPath);
        // closeButton = new Region();// this is going to be actual close button
        closeButton.setShape(closeButtonSVG); // region is converted to the svg shape
        closeButton.getStyleClass().add("closeButton");
        //closeButton.getStyleClass().add("animationCloseButton");
        closeButton.setOnAction(event -> {

            if (UiUtil.tools.alertYesOrNo("Do you want to turn back to main menu?")) {
                if (ConfigurationManager.INSTANCE.shouldSaveBeforeClosing()) {
                    ConfigurationManager.INSTANCE.handleSavingBeforeClosing();
                }
                AnimationManager.INSTANCE.reset();
                App.setRoot("/fractalmatic/Home.fxml");
            }
        });

        closeButton.translateXProperty().bind(Bindings.subtract(mainPane.widthProperty(), 33));
        closeButton.setTranslateY(8);

        Button btn = new Button();
        btn.setShape(closeButtonSVG);

        circlesExplorer.prefWidthProperty().bind(mainPane.widthProperty());
        circlesExplorer.prefHeightProperty().bind(mainPane.heightProperty());
        //clip explorer since canvas is huge, otherwise JFXDecorator right and bottom border do not show up.

        Rectangle clipPane = new Rectangle();
        circlesExplorer.setClip(clipPane);
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
    public void resetFractalCenter(){
        Platform.runLater(()-> circlesExplorerController.resetCenterPoint());
    }
}
