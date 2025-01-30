package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private Button helloButton;

    @FXML
    private void sayHello() {
        helloButton.setText("Hello, World!");
    }
}
