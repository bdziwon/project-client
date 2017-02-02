package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.Communication;
import net.ServerRequest;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.facades.UserFacade;

import java.io.IOException;
import java.util.*;


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

        Communication communication = new Communication();
        RuntimeDataHolder.getInstance().setCommunication(communication);
        UserFacade.getInstance().connectToServer();

        communication.startThread();

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
        RuntimeDataHolder.getInstance().setScene(scene);
        stage.setScene(scene);
        if (centered) {
            stage.centerOnScreen();
        }
    }


//    public static void navigateTo(Scene scene) {
//        Stage  stage         = (Stage) scene.getWindow();
//        stage.setScene(scene);
//    }

    public static void navigateUp(Scene scene) {
        Stage   stage = (Stage) scene.getWindow();
        try {
            scene = scenes.pop();
        } catch (NoSuchElementException e) {
            return;
        }
        RuntimeDataHolder.getInstance().setScene(scene);
        stage.setScene(scene);


    }

    public static void navigateUp(Scene scene, boolean centered) {
        Stage   stage = (Stage) scene.getWindow();
        try {
            scene = scenes.pop();
        } catch (NoSuchElementException e) {
            return;
        }
        RuntimeDataHolder.getInstance().setScene(scene);
        stage.setScene(scene);
        if (centered) {
            stage.centerOnScreen();
        }


    }

    public static void exitApp() {

        Communication c = RuntimeDataHolder.getInstance().getCommunication();
        if (c.isRunning()) {
            DataPackage     dataPackage         = new DataPackage("disconnect",null);
            ServerRequest   request             = new ServerRequest(dataPackage, false);
            DataPackage     receivedDataPackage;

            c.addRequest(request);
            c.setRunning(false);

        }

        HashMap<String, RuntimeDataHolder> runtimeDataHolderHashMap
                = RuntimeDataHolder.getInstance().getMementos().getList();

        for (Map.Entry<String, RuntimeDataHolder> entry : runtimeDataHolderHashMap.entrySet()
             ) {
            String          entryKey            = entry.getValue().getLoggedUser().toString();
            DataPackage     dataPackage         = new DataPackage("disconnect",null);
            ServerRequest   request             = new ServerRequest(dataPackage, false);
            DataPackage     receivedDataPackage;

            System.out.println("Entry key: "+entryKey);

            RuntimeDataHolder.getInstance().restoreFromMemento(entryKey);


            c = RuntimeDataHolder.getInstance().getCommunication();

            c.addRequest(request);
            c.setRunning(false);

            receivedDataPackage = request.getDataPackage();

            System.out.println(receivedDataPackage.getDetails());
        }



        Platform.exit();
    }
}