package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import guiContext.GuiContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class ProfileControllerTest extends ApplicationTest {

    private ProfileController profileController;
    private GuiContext guiContextMock;
    private Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        guiContextMock = mock(GuiContext.class);

        when(guiContextMock.isUser()).thenReturn(true);
        when(guiContextMock.getUserName()).thenReturn("Test User");
        when(guiContextMock.getUserEmail()).thenReturn("test@example.com");
        when(guiContextMock.getUserPhoneNumber()).thenReturn("987-654-3210");

        when(guiContextMock.getUserNameProperty()).thenReturn(
                new SimpleStringProperty("Test User"));
        when(guiContextMock.getEmailProperty()).thenReturn(
                new SimpleStringProperty("test@example.com"));
        when(guiContextMock.getPhoneProperty()).thenReturn(
                new SimpleStringProperty("987-654-3210"));

        var fxmlLocation = getClass().getResource("/fxml/profile.fxml");
        assertNotNull(fxmlLocation, "profile.fxml file not found.");

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> new ProfileController(guiContextMock));

        root = loader.load();
        profileController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(profileController, "Controller should be initialized");
    }

    @Test
    void testControllerInitialization() {
        Label nameLabel = lookup("#nameLabel").query();
        Label emailLabel = lookup("#emailLabel").query();
        Label phoneLabel = lookup("#phoneLabel").query();

        assertNotNull(nameLabel, "Name label should exist");
        assertNotNull(emailLabel, "Email label should exist");
        assertNotNull(phoneLabel, "Phone label should exist");

        assertEquals("Test User", nameLabel.getText(), "Name label should match user name");
        assertEquals("test@example.com", emailLabel.getText(),
                "Email label should match user email");
        assertEquals("987-654-3210", phoneLabel.getText(), "Phone label should match user phone");
    }
}
