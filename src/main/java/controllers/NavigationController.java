package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.Communication;
import net.ServerRequest;
import util.DataPackage;
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

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                exitApp();
            }
        });

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

    public static void exitApp() {
        DataPackage dataPackage = new DataPackage("disconnect",null);
        DataPackage   receivedDataPackage;

        ServerRequest request     = new ServerRequest(dataPackage);
        Communication c = Communication.getInstance();

        c.addRequest(request);
        c.setRunning(false);

        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        receivedDataPackage = request.getDataPackage();

        System.out.println(receivedDataPackage.getDetails());

        Platform.exit();
    }
}