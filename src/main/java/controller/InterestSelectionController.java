package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import java.util.List;

public class InterestSelectionController extends BaseController {

    @FXML
    private ScrollPane scrollContainer;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView goBack, homeIcon, profileIcon, backIcon;

    private int currentCategoryIndex = 0;

    private final String[] categories = {"animals", "food", "hobbies", "science", "sports"};

    @FXML
    private void handleHomeClick(MouseEvent event) {
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene("profile");
    }

    // kirjaudu ulös
    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene("options");
    }

    //edelliselle sivulle
    @FXML
    private void handleBack() {
        switchScene("session");
    }

    @FXML
    private void handleContinue() {
        System.out.println("DEBUG: handleContinue() called.");
        System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));
        currentCategoryIndex++;

        if (currentCategoryIndex < categories.length) {
            loadNextCategory();
        } else {
            System.out.println("All categories selected! Navigating to the next step...");
            switchScene("session");
        }
    }

    private void loadNextCategory() {
        String nextCategory = categories[currentCategoryIndex]; //get the next category

        switch (nextCategory) {
            case "animals":
                loadAnimalInterests();
                break;
            case "food":
                loadFoodInterests();
                break;
            case "hobbies":
                loadHobbyInterests();
                break;
            case "science":
                loadScienceInterests();
                break;
            case "sports":
                loadSportInterests();
                break;
        }
    }

    @FXML
    private VBox optionsContainer; //this will hold dynamically generated options

    public void setInterests(List<String> interests) {
        optionsContainer.getChildren().clear();

        for (String interest : interests) {
            Pane optionPane = createOptionPane(interest);
            optionsContainer.getChildren().add(optionPane);
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

        optionPane.getChildren().addAll(background, radioButton, label);
        return optionPane;
    }

    public void loadAnimalInterests() {
        List<String> animalInterests = List.of("koirat", "kissat", "hiiret", "hevoset");
        setInterests(animalInterests);
    }

    public void loadFoodInterests() {
        List<String> foodInterests = List.of("vegaaniruoka", "kasvisruoka", "sekaruokavalio");
        setInterests(foodInterests);
    }

    public void loadHobbyInterests() {
        List<String> hobbyInterests = List.of("tv-sarjat", "videopelit", "tähtien katselu", "aktivismi", "talous", "meditaatio", "teatteri", "esiintyminen");
        setInterests(hobbyInterests);
    }

    public void loadScienceInterests() {
        List<String> scienceInterests = List.of("biologia", "matematiikka", "fysiikka", "kemia", "avaruus", "ohjelmointi");
        setInterests(scienceInterests);
    }

    public void loadSportInterests() {
        List<String> sportInterests = List.of("lenkkeily", "pyöräily", "uinti", "pallopelit", "kuntosali", "kamppailulajit");
        setInterests(sportInterests);
    }
}
