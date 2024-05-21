module org.example.doan {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires java.naming;
    requires java.desktop; // Thêm yêu cầu cho thư viện java.awt
    opens org.example.doan to javafx.fxml;
    opens model to org.hibernate.orm.core;
    opens org.example.doan.Controller to javafx.fxml;
    exports org.example.doan.Controller;
    exports org.example.doan;
}
