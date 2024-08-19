package game.gui;

import java.io.File;
import java.io.IOException;

import game.engine.dataloader.DataLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.*;;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataLoader.readTitanRegistry();
        DataLoader.readWeaponRegistry();
        try {

            Parent StartingPage = FXMLLoader.load(new File("StartingPage.FXML").toURI().toURL());
            FXMLLoader L = new FXMLLoader(new File("GameEasy.FXML").toURI().toURL());

            Parent Easy = L.load();
            Control c = L.getController();
            c.isEasyMode = true;
            Parent Hard = FXMLLoader.load(new File("GameHard.FXML").toURI().toURL());

            Scene scene = new Scene(StartingPage, Color.BLACK);

            ToggleButton startGameButton = (ToggleButton) StartingPage.lookup("#StartGame");
            Button easyButton = (Button) StartingPage.lookup("#EasyButton");
            Button hardButton = (Button) StartingPage.lookup("#HardButton");

            startGameButton.setOnMouseClicked(event -> {
                easyButton.setVisible(true);
                hardButton.setVisible(true);
                startGameButton.setVisible(false);
            });

            easyButton.setOnAction(event -> {
                Scene gameScene1 = new Scene(Easy, Color.WHITE);
                primaryStage.setScene(gameScene1);
                primaryStage.show();
            });

            hardButton.setOnAction(event -> {
                Scene gameScene2 = new Scene(Hard, Color.WHITE);
                primaryStage.setScene(gameScene2);
                primaryStage.show();
            });
            Image icon = new Image("attack-on-titan-brave-order.png");
            primaryStage.getIcons().add(icon);
            Image backgroundImage = new Image("black-and-white-attack-on-titan-logo-qib40rskdp9p813l.jpg");
            ImageView backgroundView = new ImageView(backgroundImage);
            backgroundView.setFitWidth(scene.getWidth());
            backgroundView.setFitHeight(scene.getHeight());

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(backgroundView, StartingPage);
            scene.setRoot(stackPane);

            primaryStage.setTitle("Attack On Titan");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
