package controller;

import context.GUIContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import model.Participant;
import model.Session;
import model.categories.Animal;
import model.categories.Category;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public class InterestSelectionController extends BaseController {

    @FXML
    private ScrollPane scrollContainer;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView goBack, homeIcon, profileIcon, backIcon;

    @FXML
    private VBox optionsContainer; //holds dynamically generated options

    private String currentCategory;
    private final List<RadioButton> allRadioButtons = new ArrayList<>();
    private final GUIContext context = GUIContext.getInstance();
    private Participant getParticipant() {
        if (context.isUser()) {
            return context.getUser();
        }
        return context.getGuest();
    }
    private Session session;
    @FXML
    private void initialize() {
        session = new Session(getParticipant());
    }

    private static final List<String> CATEGORY_ORDER = List.of("animals", "food", "hobbies", "sports", "science");

    private static final Map<String, List<? extends Category>> INTERESTS_MAP = Map.of(
            "animals",Session.getAnimals(),
            "food", Session.getFoods(),
            "hobbies", Session.getHobbies(),
            "sports", Session.getSports(),
            "science", Session.getSciences()
    );

    @FXML
    private void handleHomeClick(MouseEvent event) {
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene("options");
    }

    @FXML
    private void handleBack() {
        switchScene("session");
    }

    @FXML
    private void handleContinue() {
        System.out.println("DEBUG: handleContinue() called.");
        System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));
        loadNextCategory();
    }

    private void loadNextCategory() {
        int currentIndex = CATEGORY_ORDER.indexOf(currentCategory);
        if (currentIndex == -1 || currentIndex + 1 >= CATEGORY_ORDER.size()) {
            switchScene("session");
        } else {
            loadInterests(CATEGORY_ORDER.get(currentIndex + 1));
        }
    }

    public void setInterests(List<? extends Category> interests) {
        optionsContainer.getChildren().clear();
        allRadioButtons.clear();

        for (Category interest : interests) {
            optionsContainer.getChildren().add(createOptionPane(interest));
        }
    }

    private Pane createOptionPane(Category interest) {
        Pane optionPane = new Pane();
        optionPane.setPrefSize(290, 71);
        optionPane.getStyleClass().add("option-btn");

        Pane background = new Pane();
        background.setPrefSize(290, 71);
        background.getStyleClass().add("option-bg");

        RadioButton radioButton = new RadioButton();
        radioButton.setLayoutX(24);
        radioButton.setLayoutY(21);
        radioButton.getStyleClass().add("radio");

        String labelName = interest.toString().replaceAll("_", " ");
        Label label = new Label(labelName.toLowerCase());
        label.setLayoutX(80);
        label.setLayoutY(20);
        label.getStyleClass().add("option-text");

        radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                session.addParticipantInterest(interest);
            } else {
                session.removeParticipantInterest(interest);
            }
            System.out.println("Current Selection: " + session.getParticipantInterests());
        });

        allRadioButtons.add(radioButton);
        optionPane.getChildren().addAll(background, radioButton, label);
        return optionPane;
    }

    public void setCategory(String category) {
        this.currentCategory = category;
        System.out.println("Current category set to: " + category);
    }

    public List<Category> getSelectedInterests() {
        return session.getParticipantInterests();
    }

    public void loadInterests(String category) {
        List<? extends Category> interests = INTERESTS_MAP.getOrDefault(category, List.of());
        setInterests(interests);
        setCategory(category);
    }
}
