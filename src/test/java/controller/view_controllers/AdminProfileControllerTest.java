package controller.view_controllers;

import context.GUIContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminProfileControllerTest extends ApplicationTest {

    private AdminProfileController adminProfileController;
    private GUIContext guiContextMock;
    private Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        guiContextMock = mock(GUIContext.class);

        when(guiContextMock.isUser()).thenReturn(true);
        when(guiContextMock.getUserName()).thenReturn("Admin User");
        when(guiContextMock.getUserEmail()).thenReturn("admin@example.com");
        when(guiContextMock.getUserPhoneNumber()).thenReturn("123-456-7890");

        when(guiContextMock.getUserNameProperty()).thenReturn(new javafx.beans.property.SimpleStringProperty("Admin User"));
        when(guiContextMock.getEmailProperty()).thenReturn(new javafx.beans.property.SimpleStringProperty("admin@example.com"));
        when(guiContextMock.getPhoneProperty()).thenReturn(new javafx.beans.property.SimpleStringProperty("123-456-7890"));

        var fxmlLocation = getClass().getResource("/fxml/admin_profile.fxml");
        assertNotNull(fxmlLocation, "admin_profile.fxml file not found.");

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> new AdminProfileController(guiContextMock)); // Inject the mock

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
        assertEquals("admin@example.com", emailLabel.getText(), "Email label should match user email");
        assertEquals("123-456-7890", phoneLabel.getText(), "Phone label should match user phone");
    }
}
