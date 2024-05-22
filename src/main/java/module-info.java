module com.example.examen_libro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.examen_libro to javafx.fxml;
    exports com.example.examen_libro;
}