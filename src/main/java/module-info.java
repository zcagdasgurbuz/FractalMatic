module fractalmatic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    opens fractalmatic to javafx.fxml;
    opens fractalmatic.circles.ui to javafx.fxml;
    exports fractalmatic;
    exports fractalmatic.circles.animation;
    exports fractalmatic.circles.calculation;
    exports fractalmatic.circles.ui;
    exports fractalmatic.circles.config;
    exports fractalmatic.common.ui;
    opens fractalmatic.common.ui to javafx.fxml;
    exports fractalmatic.common.animation;
    exports fractalmatic.common.util;
    exports fractalmatic.common.ui.util;
}