module org.example.doan {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires java.naming;
    opens org.example.doan to javafx.fxml;
    opens model to org.hibernate.orm.core;

    exports org.example.doan;
}
