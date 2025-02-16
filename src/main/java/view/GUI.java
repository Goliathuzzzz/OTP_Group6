package view;

import controller.BaseController;
import controller.WelcomeController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
        Parent root = fxmlLoader.load();

        Object controller = fxmlLoader.getController();
        if (controller instanceof BaseController baseController) {
            baseController.setStage(stage);
        }

        stage.setTitle("~ tatskatytöt speed dating ~"); // placeholder title
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/cherries.png")));
        scene.getRoot().setStyle("-fx-font-family: 'HoeflerText'");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}