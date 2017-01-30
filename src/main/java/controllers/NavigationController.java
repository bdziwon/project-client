package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.Communication;
import util.RuntimeDataHolder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Stack;


public class NavigationController extends Application {

    private static Deque<Scene> scenes = new ArrayDeque<Scene>();

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        GridPane root = new GridPane();

        primaryStage.setTitle("Logowanie");
        primaryStage.setResizable(false);

        try {
            root = (GridPane) FXMLLoader.load(getClass().getClassLoader()
                    .getResource("LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        RuntimeDataHolder.getInstance().getUserFacade().connectToServer();
        Communication.getInstance().startThread();
        this.primaryStage = primaryStage;
    }

    public static void navigateTo(String sceneFXML, ActionEvent event) {
        Stage  stage         = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene  currentScene  = stage.getScene();
        Parent root          = null;

        scenes.push(currentScene);

        try {
            root = (GridPane) FXMLLoader.load(NavigationController.class.getClassLoader()
                    .getResource(sceneFXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public static void navigateUp(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene;
        try {
            scene = scenes.pop();
        } catch (NoSuchElementException e) {
            return;
        }

        stage.setScene(scene);


    }
}