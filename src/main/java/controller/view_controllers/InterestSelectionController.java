package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Participant;
import model.Session;
import model.categories.*;
import util.SceneNames;

import java.util.*;

public class InterestSelectionController extends BaseController {

    @FXML
    private ScrollPane scrollContainer;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView goBack, homeIcon, profileIcon, backIcon, helpIcon;

    @FXML
    private VBox optionsContainer; //holds dynamically generated options

    @FXML
    private AnchorPane interestSelectionPane;

    private String currentCategory;
    private final List<RadioButton> allRadioButtons = new ArrayList<>();
    private final GUIContext context = GUIContext.getInstance();
    private Session session;
    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

    private Participant getParticipant() {
        if (context.isUser()) {
            return context.getUser();
        } else if (context.isGuest()) {
            return context.getGuest();
        } else {
            return null;
        }
    }

    @FXML
    private void initialize() {
        session = context.getSession();
        if (session == null || session.getParticipant() == null) {
            System.err.println("ERROR: Session or participant is null in InterestSelectionController initialize()");
        }
        Platform.runLater(() -> {
            Stage stage = (Stage) interestSelectionPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("interest_selection"));
            } else {
                System.err.println("Stage is null in InterestSelectionController initialize()");
            }
        });
    }

    private static final List<String> CATEGORY_ORDER = List.of("animals", "food", "hobbies", "sports", "science");

    private static final Map<String, List<? extends Category>> INTERESTS_MAP = Map.of(
            "animals", Session.getAnimals(),
            "food", Session.getFoods(),
            "hobbies", Session.getHobbies(),
            "sports", Session.getSports(),
            "science", Session.getSciences()
    );

    @FXML
    private void handleBack() {
        switchScene(SceneNames.SESSION);
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
            switchScene(SceneNames.SESSION);
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

        String labelName;
        if (interest instanceof Enum<?>) {
            String key = interest.getClass().getSimpleName().toLowerCase() + "_" + ((Enum<?>) interest).name().toLowerCase();
            labelName = bundle.getString(key);
        } else {
            labelName = interest.toString();
        }


        System.out.println("localized name: " + labelName);

        Label label = new Label(labelName);
        label.setLayoutX(80);
        label.setLayoutY(20);
        label.getStyleClass().add("option-text");

        radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                session.addParticipantInterest(interest);
                addInterestByCategory(getParticipant(), interest);
            } else {
                session.removeParticipantInterest(interest);
                removeInterestByCategory(getParticipant(), interest);
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

    private void addInterestByCategory(Participant participant, Category interest) {
        if (participant == null) {
            System.err.println("ERROR: Participant is null in InterestSelectionController addInterestByCategory()");
            return;
        }
        if (interest instanceof Animal) {
            participant.addAnimalInterest((Animal) interest);
        } else if (interest instanceof Food) {
            participant.addFoodInterest((Food) interest);
        } else if (interest instanceof Hobby) {
            participant.addHobbiesInterest((Hobby) interest);
        } else if (interest instanceof Science) {
            participant.addScienceInterest((Science) interest);
        } else if (interest instanceof Sports) {
            participant.addSportsInterest((Sports) interest);
        }
    }

    private void removeInterestByCategory(Participant participant, Category interest) {
        if (participant == null) {
            System.err.println("ERROR: Participant is null in InterestSelectionController removeInterestByCategory()");
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
