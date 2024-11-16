module org.example.multimedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.example.multimedia to javafx.fxml;
    exports org.example.multimedia;
}