package view;

import context.GuiContext;
import context.LocaleManager;
import controller.BaseController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.SceneNames;
import java.util.Objects;

public class Gui extends Application {
    private static final String path = "/fxml/" + SceneNames.WELCOME + ".fxml";
    LocaleManager localeManager = LocaleManager.getInstance();
    GuiContext guiContext = GuiContext.getInstance();

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource(path));
        guiContext.setStage(stage);

        localeManager.setLocale(new Locale("en", "US"));
        localeManager.setBundle(ResourceBundle
                .getBundle("Messages", localeManager.getLocale()));
        stage.setTitle(localeManager.getBundle().getString("welcome"));

        stage.setResizable(false);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(Objects.requireNonNull(Gui.class
                .getResourceAsStream("/images/cherries.png"))));
        scene.getRoot().setStyle("-fx-font-family: 'HoeflerText'");
        stage.setScene(scene);
        stage.show();
    }

}
