package controller.view_controllers;

import controller.BaseController;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Participant;
import model.Session;
import model.categories.Animal;
import model.categories.Category;
import model.categories.Food;
import model.categories.Hobby;
import model.categories.Science;
import model.categories.Sports;
import util.SceneNames;

public class InterestSelectionController extends BaseController {

    private static final List<String> CATEGORY_ORDER =
            List.of("animals", "food", "hobbies", "sports", "science");
    private static final Map<String, List<? extends Category>> INTERESTS_MAP = Map.of(
            "animals", Session.getAnimals(),
            "food", Session.getFoods(),
            "hobbies", Session.getHobbies(),
            "sports", Session.getSports(),
            "science", Session.getSciences()
    );
    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private VBox optionsContainer; //holds dynamically generated options
    @FXML
    private AnchorPane interestSelectionPane;
    private String currentCategory;
    private Session session;

    private Participant getParticipant() {
        if (guiContext.isUser()) {
            return guiContext.getUser();
        } else if (guiContext.isGuest()) {
            return guiContext.getGuest();
        } else {
            return null;
        }
    }

    @FXML
    private void initialize() {
        session = guiContext.getSession();
        if (session == null || session.getParticipant() == null) {
            logger.log(Level.SEVERE, "ERROR: Session or participant is null "
                    + "in InterestSelectionController initialize()");
        }
        Platform.runLater(() -> {
            Stage stage = (Stage) interestSelectionPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("interest_selection"));
            } else {
                logger.log(Level.SEVERE, "Stage is null in "
                        + "InterestSelectionController initialize()");
            }
        });
    }

    @FXML
    private void handleBack() {
        switchScene(SceneNames.SESSION);
    }

    @FXML
    private void handleContinue() {
        logger.info("DEBUG: handleContinue() called.");
        logger.log(Level.INFO, "DEBUG: stage is {}", (stage == null ? "NULL" : "SET"));
        loadNextCategory();
    }

    private void loadNextCategory() {
        int currentIndex = CATEGORY_ORDER.indexOf(currentCategory);
        if (currentIndex == -1 || currentIndex + 1 >= CATEGORY_ORDER.size()) {
            switchScene(SceneNames.SESSION);
        } else {
            loadInterests(CATEGORY_ORDER.get(currentIndex + 1));
        }
    }

    public void setInterests(List<? extends Category> interests) {
        optionsContainer.getChildren().clear();
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

        RadioButton radioButton = createRadioButtons(interest);
        Label label = createLabel(interest);
        optionPane.getChildren().addAll(background, radioButton, label);
        return optionPane;
    }

    private RadioButton createRadioButtons(Category interest) {
        RadioButton radioButton = new RadioButton();
        radioButton.setLayoutX(24);
        radioButton.setLayoutY(21);
        radioButton.getStyleClass().add("radio");

        radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                session.addParticipantInterest(interest);
                addInterestByCategory(getParticipant(), interest);
            } else {
                session.removeParticipantInterest(interest);
                removeInterestByCategory(getParticipant(), interest);
            }
            logger.info("Current Selection: " + session.getParticipantInterests());
        });

        return radioButton;
    }

    private Label createLabel(Category interest) {
        String labelName;
        if (interest instanceof Enum<?>) {
            String key = interest.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                    + "_"
                    + ((Enum<?>) interest).name().toLowerCase(Locale.ROOT);
            labelName = bundle.getString(key);
        } else {
            labelName = interest.toString();
        }

        logger.log(Level.INFO, "localized name: {}", labelName);

        Label label = new Label(labelName);
        label.setLayoutX(80);
        label.setLayoutY(20);
        label.getStyleClass().add("option-text");
        return label;
    }

    public void setCategory(String category) {
        this.currentCategory = category;
        logger.log(Level.INFO, "Current category set to: {}", category);
    }

    public void loadInterests(String category) {
        List<? extends Category> interests = INTERESTS_MAP.getOrDefault(category, List.of());
        setInterests(interests);
        setCategory(category);
    }

    private void addInterestByCategory(Participant participant, Category interest) {
        if (participant == null) {
            logger.log(Level.SEVERE, "ERROR: Participant is null "
                    + "in InterestSelectionController addInterestByCategory()");
            return;
        }
        if (interest instanceof Animal animal) {
            participant.addAnimalInterest(animal);
        } else if (interest instanceof Food food) {
            participant.addFoodInterest(food);
        } else if (interest instanceof Hobby hobby) {
            participant.addHobbiesInterest(hobby);
        } else if (interest instanceof Science science) {
            participant.addScienceInterest(science);
        } else if (interest instanceof Sports sports) {
            participant.addSportsInterest(sports);
        }
    }

    private void removeInterestByCategory(Participant participant, Category interest) {
        if (participant == null) {
            logger.log(Level.SEVERE, "ERROR: Participant is null "
                    + "in InterestSelectionController removeInterestByCategory()");
            return;
        }
        if (interest instanceof Animal) {
            participant.getAnimalInterests().remove(interest);
        } else if (interest instanceof Food) {
            participant.getFoodInterests().remove(interest);
        } else if (interest instanceof Hobby) {
            participant.getHobbiesInterests().remove(interest);
        } else if (interest instanceof Science) {
            participant.getScienceInterests().remove(interest);
        } else if (interest instanceof Sports) {
            participant.getSportsInterests().remove(interest);
        }
    }

}
