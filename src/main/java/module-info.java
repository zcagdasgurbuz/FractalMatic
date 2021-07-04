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
    exports fractalmatic.circles.util;
}