module fractalmatic {

    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.graphics;
    requires com.jfoenix;
    opens fractalmatic to javafx.fxml;
    //opens circles.animation to javafx.fxml;
    opens circles.calculation to javafx.fxml;
    opens circles.ui to javafx.fxml;
    /*opens circles.util to javafx.fxml;*/
    exports fractalmatic;
    exports circles.animation;
    exports circles.calculation;
    exports circles.ui;
    exports circles.util;


}