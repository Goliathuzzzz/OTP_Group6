package controller;

import javafx.fxml.FXML;

public class WelcomeController extends BaseController {

    @FXML
    public void initialize() {
        System.out.println("tervetuloa!");
    }

    @FXML
    private void handleClick() {
        switchScene("options");
    }
}
