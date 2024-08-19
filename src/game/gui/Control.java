package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Control {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Battle battle;
    public boolean isEasyMode;
    private ArrayList<Lane> lanes = new ArrayList<>();
    private Map<Titan, Label> titans = new HashMap<>();

    @FXML
    private ToggleButton startGameButton;
    @FXML
    private Button easyButton;
    @FXML
    private Button hardButton;
    @FXML
    private Label currentScoreLabel;
    @FXML
    private Label currentTurnLabel;
    @FXML
    private Label currentResourcesLabel;
    @FXML
    private Label currentPhaseLabel;
    @FXML
    private VBox dynamicGridPane;
    @FXML
    private ListView<String> weaponComboBox; // ComboBox for weapon selection
    @FXML
    private ListView<String> laneNumberField; // TextField for lane number input

    @FXML
    void initialize() {
        // Populate weaponComboBox with available weapons
        if (battle != null) {
            weaponComboBox.setMaxHeight(100);
            weaponComboBox.setMaxWidth(100);

            laneNumberField.setMaxHeight(100);
            laneNumberField.setMaxWidth(100);

            for (WeaponRegistry w : battle.getWeaponFactory().getWeaponShop().values()) {
                weaponComboBox.getItems().add(w.getCode() + " " + w.getName());
            }
            int k = 1;
            for (Lane i : lanes) {
                laneNumberField.getItems().add("lane " + k);
                k += 1;
            }
        }
    }

    @FXML
    void switchToGame(ActionEvent event) throws IOException {
        String fxmlFile = isEasyMode ? "GameEasy.fxml" : "GameHard.fxml";
        Parent gameRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(gameRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onStartGameButtonClicked(ActionEvent event) {
        boolean selected = startGameButton.isSelected();
        easyButton.setVisible(selected);
        hardButton.setVisible(selected);
    }

    @FXML
    private void handleButtonAction() {
        if (battle == null) {
            battle = setGameMode(isEasyMode);
            lanes.addAll(battle.getLanes());
            initialize();
        } else {
            battle.passTurn();
        }
        updateLabels();
    }

    private Battle setGameMode(boolean isEasyMode) {
        int initialNumLanes = isEasyMode ? 3 : 5;
        int initialResourcesPerLane = isEasyMode ? 250 : 125;
        int titanSpawnDistance = 900;

        try {
            battle = new Battle(0, 0, titanSpawnDistance, initialNumLanes, initialResourcesPerLane);
        } catch (IOException e) {
            showErrorAlert("Initialization Error", "Failed to initialize the game.");
            e.printStackTrace();
        }

        return battle;
    }

    private void updateLabels() {
        if (battle != null) {
            currentScoreLabel.setText(String.valueOf(battle.getScore()));
            currentTurnLabel.setText(String.valueOf(battle.getNumberOfTurns()));
            currentResourcesLabel.setText(String.valueOf(battle.getResourcesGathered()));
            currentPhaseLabel.setText(String.valueOf(battle.getBattlePhase()));
            populateGridPane(battle.getLanes().size(), battle.getTitanSpawnDistance() + 2);
        }
    }

    private void populateGridPane(int rows, int cols) {
        dynamicGridPane.getChildren().clear();

        for (Lane lane : lanes) {
            HBox laneBox = new HBox();
            Label wlLabel = new Label("w");
            laneBox.getChildren().add(wlLabel);
            wlLabel.setOnMouseClicked(event -> showlanedets(lane)); // Add mouse click event handler

            laneBox.setSpacing(5); // Set spacing between labels
            for (Titan titan : lane.getTitans()) {
                Label titanLabel = new Label(titan.getClass().getSimpleName().charAt(0) + "");
                titanLabel.setTranslateX(titan.getDistance());
                titanLabel.setOnMouseClicked(event -> showTitanDetails(titan)); // Add mouse click event handler
                laneBox.getChildren().add(titanLabel);
            }
            adjustLabelSizes(laneBox);
            laneBox.setPrefSize(100, 100);
            dynamicGridPane.getChildren().add(laneBox);
        }
    }

    private void adjustLabelSizes(HBox laneBox) {
        int numChildren = laneBox.getChildren().size();
        if (numChildren == 0)
            return;

        double totalWidth = dynamicGridPane.getWidth();
        double maxWidthPerLabel = totalWidth / numChildren;

        for (Node node : laneBox.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                label.setMaxWidth(maxWidthPerLabel);
                label.setWrapText(true); // Enable text wrapping if needed
            }
        }
    }

    private void showTitanDetails(Titan titan) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Titan Details");
        alert.setHeaderText(null);
        alert.setContentText("Name: " + titan.getClass().getSimpleName() + "\nDistance: " + titan.getDistance()
                + "\nHealth: " + titan.getCurrentHealth() + "\nheaight:" + titan.getHeightInMeters());
        alert.showAndWait();
    }

    private void showlanedets(Lane titan) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Wall Details");
        alert.setHeight(1000);
        alert.setHeaderText(null);
        alert.setContentText("Name: " + titan.getClass().getSimpleName() + "\nweapons: " + titan.getWeapons()
                + "\ndanger: " + titan.getDangerLevel()
                + "\nhealth: " + titan.getLaneWall().getCurrentHealth());
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void shopinfo() {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Information Alert");
        infoAlert.setHeaderText(null);
        String t = "";
        for (int r : battle.getWeaponFactory().getWeaponShop().keySet()) {
            t += "\n" + battle.getWeaponFactory().getWeaponShop().get(new Integer(r + "")).getName()
                    + "Damage:" + battle.getWeaponFactory().getWeaponShop().get(new Integer(r + "")).getDamage() +
                    "Min range:" + battle.getWeaponFactory().getWeaponShop().get(new Integer(r + "")).getMinRange() +

                    "max range" + battle.getWeaponFactory().getWeaponShop().get(new Integer(r + "")).getMaxRange() +

                    "price:" + battle.getWeaponFactory().getWeaponShop().get(new Integer(r + "")).getPrice();
        }

        infoAlert.setContentText(t);

        infoAlert.showAndWait();

    }

    @FXML
    void handleBuyWeapon(ActionEvent event) {
        int selectedWeapon = Integer.parseInt(weaponComboBox.getSelectionModel().getSelectedItem().split(" ")[0]);
        int laneNumberText = Integer.parseInt(laneNumberField.getSelectionModel().getSelectedItem().split(" ")[1]);

        try {
            battle.purchaseWeapon(selectedWeapon, lanes.get(laneNumberText - 1));
            updateLabels();
        } catch (NumberFormatException e) {
            showErrorAlert("Input Error", "Invalid lane number. Please enter a valid integer.");
        } catch (InvalidLaneException | InsufficientResourcesException e) {
            showErrorAlert("Purchase Error", e.getMessage());
        }
    }
}
