module org.example.doan {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.doan to javafx.fxml;
    exports org.example.doan;
}