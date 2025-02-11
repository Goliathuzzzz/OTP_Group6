package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;

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
    private final Set<String> selectedInterests = new HashSet<>();
    private final List<RadioButton> allRadioButtons = new ArrayList<>();

    private static final List<String> CATEGORY_ORDER = List.of("animals", "food", "hobbies", "sports", "science");

    private static final Map<String, List<String>> INTERESTS_MAP = Map.of(
            "animals", List.of("koirat", "kissat", "hiiret", "hevoset"),
            "food", List.of("vegaaniruoka", "kasvisruoka", "sekaruokavalio"),
            "hobbies", List.of("tv-sarjat", "videopelit", "tähtien katselu", "aktivismi", "talous", "meditaatio", "teatteri", "esiintyminen"),
            "sports", List.of("lenkkeily", "pyöräily", "uinti", "pallopelit", "kuntosali", "kamppailulajit"),
            "science", List.of("biologia", "matematiikka", "fysiikka", "kemia", "avaruus", "ohjelmointi")
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

    public void setInterests(List<String> interests) {
        optionsContainer.getChildren().clear();
        allRadioButtons.clear();

        for (String interest : interests) {
            optionsContainer.getChildren().add(createOptionPane(interest));
        }
    }

    private Pane createOptionPane(String text) {
        Pane optionPane = new Pane();
        optionPane.setPrefSize(315, 71);
        optionPane.getStyleClass().add("option-btn");

        Rectangle background = new Rectangle(315, 71);
        background.setArcHeight(28);
        background.setArcWidth(28);
        background.getStyleClass().add("option-bg");

        RadioButton radioButton = new RadioButton();
        radioButton.setLayoutX(24);
        radioButton.setLayoutY(21);
        radioButton.getStyleClass().add("radio");

        Label label = new Label(text);
        label.setLayoutX(80);
        label.setLayoutY(20);
        label.getStyleClass().add("option-text");

        radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                selectedInterests.add(text);
            } else {
                selectedInterests.remove(text);
            }
            System.out.println("Current Selection: " + selectedInterests);
        });

        allRadioButtons.add(radioButton);
        optionPane.getChildren().addAll(background, radioButton, label);
        return optionPane;
    }

    public void setCategory(String category) {
        this.currentCategory = category;
        System.out.println("Current category set to: " + category);
    }

    public Set<String> getSelectedInterests() {
        return selectedInterests;
    }

    public void loadInterests(String category) {
        List<String> interests = INTERESTS_MAP.getOrDefault(category, List.of());
        setInterests(interests);
        setCategory(category);
    }
}
