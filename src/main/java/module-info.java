module org.example.doan {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql.rowset;
    requires java.desktop;
    opens org.example.doan to javafx.fxml;
    exports org.example.doan;
    exports org.example.doan.Controller;
    opens org.example.doan.Controller to javafx.fxml;
    opens model;


}