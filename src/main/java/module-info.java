module fractalmatic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    opens fractalmatic to javafx.fxml;
    opens circles.ui to javafx.fxml;
    exports fractalmatic;
    exports circles.animation;
    exports circles.calculation;
    exports circles.ui;
    exports circles.util;
}