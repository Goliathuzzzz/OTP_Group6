package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Field;

@ExtendWith(ApplicationExtension.class)
class HelpControllerTest {

    private HelpController helpController;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help.fxml"));
        Parent root = loader.load();
        helpController = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }


    @Test
    void testInitialize() throws Exception {
        VBox vBox = getPrivateField(helpController, "vBox", VBox.class);

        assertNotNull(vBox, "VBox should not be null");
        assertEquals(Pos.CENTER, vBox.getAlignment(), "VBox alignment should be CENTER");
        assertEquals(new Insets(0, 0, 50, 0), vBox.getPadding(), "VBox padding should be (0,0,50,0)");
    }

    private <T> T getPrivateField(Object instance, String fieldName, Class<T> type) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return type.cast(field.get(instance));
    }
}
