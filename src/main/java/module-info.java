module com.example.gamenewjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gamenewjava to javafx.fxml;
    exports com.example.gamenewjava;
    exports com.example.gamenewjava.Scenes;
    opens com.example.gamenewjava.Scenes to javafx.fxml;
}