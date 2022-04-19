module com.melissadata.property {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.google.gson;
    requires jdk.crypto.ec;
    requires java.xml;

    opens com.melissadata.property to javafx.fxml;
    opens com.melissadata.property.model to javafx.fxml;
    opens com.melissadata.property.view to javafx.fxml;
    exports com.melissadata.property;
}
