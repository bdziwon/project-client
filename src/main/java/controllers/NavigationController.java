package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.Communication;
import net.ServerRequest;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.facades.UserFacade;

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

        primaryStage.setTitle("Projekt");
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
        primaryStage.centerOnScreen();

        UserFacade.getInstance().connectToServer();
        Communication.getInstance().startThread();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                exitApp();
            }
        });

        this.primaryStage = primaryStage;
    }

    public static void navigateTo(String sceneFXML, Scene scene, boolean centered) {
        Stage  stage         = (Stage) scene.getWindow();
        Scene  currentScene  = stage.getScene();
        Parent root          = null;
        scenes.push(currentScene);

        try {
            root = (GridPane) FXMLLoader.load(NavigationController.class.getClassLoader()
                    .getResource(sceneFXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        if (centered) {
            stage.centerOnScreen();
        }
    }


    public static void navigateUp(Scene scene) {
        Stage   stage = (Stage) scene.getWindow();
        try {
            scene = scenes.pop();
        } catch (NoSuchElementException e) {
            return;
        }

        stage.setScene(scene);


    }

    public static void exitApp() {
        DataPackage dataPackage = new DataPackage("disconnect",null);
        DataPackage   receivedDataPackage;

        ServerRequest request     = new ServerRequest(dataPackage, false);
        Communication c = Communication.getInstance();

        c.addRequest(request);
        c.setRunning(false);

        receivedDataPackage = request.getDataPackage();

        System.out.println(receivedDataPackage.getDetails());

        Platform.exit();
    }
}