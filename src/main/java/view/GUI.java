package view;

import context.LocaleManager;
import controller.BaseController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import util.SceneNames;

import java.util.Locale;
import java.util.ResourceBundle;

public class GUI extends Application {
    private static final String path = "/fxml/" + SceneNames.WELCOME + ".fxml";
    // placeholder title
    private static final String TITLE = " \uD835\uDCC9\uD835\uDCB6\uD835\uDCC9\uD835\uDCC8\uD835\uDCC0\uD835\uDCB6\uD835\uDCC9\uD835\uDCCE\uD835\uDCC9ö\uD835\uDCC9 "
            + "\uD835\uDCC8\uD835\uDCC5\uD835\uDC52\uD835\uDC52\uD835\uDCB9 \uD835\uDCB9\uD835\uDCB6\uD835\uDCC9\uD835\uDCBE\uD835\uDCC3\uD835\uDC54";
    LocaleManager localeManager = LocaleManager.getInstance();

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        Parent root = fxmlLoader.load();

        Object controller = fxmlLoader.getController();
        if (controller instanceof BaseController baseController) {
            baseController.setStage(stage);
        }

        localeManager.setLocale(new Locale("en", "US"));
        localeManager.setBundle(ResourceBundle.getBundle("Messages", localeManager.getLocale()));
        stage.setTitle(localeManager.getBundle().getString("welcome"));

        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/cherries.png")));
        scene.getRoot().setStyle("-fx-font-family: 'HoeflerText'");
        stage.setScene(scene);
        stage.show();
    }

}
