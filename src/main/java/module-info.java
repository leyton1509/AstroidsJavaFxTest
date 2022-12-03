module com.example.gamenewjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gamenewjava to javafx.fxml;
    exports com.example.gamenewjava;
    exports com.example.gamenewjava.Scenes;
    opens com.example.gamenewjava.Scenes to javafx.fxml;
    exports com.example.gamenewjava.AssetControllers;
    opens com.example.gamenewjava.AssetControllers to javafx.fxml;
    exports com.example.gamenewjava.GUI;
    opens com.example.gamenewjava.GUI to javafx.fxml;
}