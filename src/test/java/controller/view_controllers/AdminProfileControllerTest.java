package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import guiContext.GuiContext;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AdminProfileControllerTest extends ApplicationTest {

    private AdminProfileController adminProfileController;
    private GuiContext guiContextMock;
    private Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        guiContextMock = mock(GuiContext.class);

        when(guiContextMock.isUser()).thenReturn(true);
        when(guiContextMock.getUserName()).thenReturn("Admin User");
        when(guiContextMock.getUserEmail()).thenReturn("admin@example.com");
        when(guiContextMock.getUserPhoneNumber()).thenReturn("123-456-7890");

        when(guiContextMock.getUserNameProperty()).thenReturn(
                new SimpleStringProperty("Admin User"));
        when(guiContextMock.getEmailProperty()).thenReturn(
                new SimpleStringProperty("admin@example.com"));
        when(guiContextMock.getPhoneProperty()).thenReturn(
                new SimpleStringProperty("123-456-7890"));

        var fxmlLocation = getClass().getResource("/fxml/admin_profile.fxml");
        assertNotNull(fxmlLocation, "admin_profile.fxml file not found.");

        ResourceBundle testBundle = ResourceBundle.getBundle("Messages", Locale.US);
        FXMLLoader loader = new FXMLLoader(fxmlLocation, testBundle);
        loader.setControllerFactory(param -> new AdminProfileController(guiContextMock));

        root = loader.load();
        adminProfileController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @BeforeEach
    void setUp() {
        assertNotNull(adminProfileController, "Controller should be initialized");
    }

    @Test
    void testControllerInitialization() {
        Label nameLabel = lookup("#nameLabel").query();
        Label emailLabel = lookup("#emailLabel").query();
        Label phoneLabel = lookup("#phoneLabel").query();

        assertNotNull(nameLabel, "Name label should exist");
        assertNotNull(emailLabel, "Email label should exist");
        assertNotNull(phoneLabel, "Phone label should exist");

        assertEquals("Admin User", nameLabel.getText(), "Name label should match user name");
        assertEquals("admin@example.com", emailLabel.getText(),
                "Email label should match user email");
        assertEquals("123-456-7890", phoneLabel.getText(), "Phone label should match user phone");
    }
}
