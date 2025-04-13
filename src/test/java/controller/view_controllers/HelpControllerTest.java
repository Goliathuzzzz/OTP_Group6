package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class HelpControllerTest {

    private HelpController helpController;
    private Stage stage;

    @Start
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        var fxmlLocation = getClass().getResource("/fxml/help.fxml");
        assertNotNull(fxmlLocation, "help.fxml file not found.");

        ResourceBundle bundle =
                ResourceBundle.getBundle("Messages", Locale.US); // use Messages.properties
        FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);

        Parent root = loader.load();
        helpController = loader.getController();
        assertNotNull(helpController, "HelpController should be initialized.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Test
    void testInitialize() throws Exception {
        VBox vBox = getPrivateField(helpController, "vBox", VBox.class);

        assertNotNull(vBox, "VBox should not be null");
        assertEquals(Pos.CENTER, vBox.getAlignment(), "VBox alignment should be CENTER");
        assertEquals(new Insets(0, 0, 50, 0), vBox.getPadding(),
                "VBox padding should be (0,0,50,0)");
    }

    private <T> T getPrivateField(Object instance, String fieldName, Class<T> type)
            throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return type.cast(field.get(instance));
    }
}
