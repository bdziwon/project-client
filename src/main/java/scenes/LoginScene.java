package scenes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import net.Communication;
import net.ServerRequest;
import org.apache.maven.plugin.logging.Log;
import util.Credentials;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.User;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class LoginScene extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

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

        connectToServer();
        Communication.getInstance().startThread();
    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        String login    = loginField.getText();
        String password = passwordField.getText();
        Credentials credentials = new Credentials(login,password);
        boolean error;

        error = credentialsHaveLocalError(credentials);

        if (error) {
            return;
        }

        error = credentialsHaveServerSideError(credentials);

        if (error) {
            return;
        }

    }

    private boolean connectToServer() {
        while (true) {
            Socket socket = RuntimeDataHolder.getInstance().getSocket();
            if (socket != null) {
                return true;
            }
            try {
                socket = new Socket("127.0.0.1", 4000);
                RuntimeDataHolder.getInstance().setSocket(socket);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Połączenie nie udane");
                alert.setHeaderText("Połączenie z serwerem nieudane");
                alert.setContentText("Czy chcesz spróbować ponownie?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    continue;
                } else {
                    Platform.exit();
                }
                return false;
            }
            return true;
        }

    }

    private boolean credentialsHaveLocalError(Credentials credentials) {

        String message = "";

        if (!credentials.loginHaveAllowedLength()) {
            message += "Minimalna długość nazwy użytkownika: 4 znaki"+System.getProperty("line.separator");
        }

        if (!credentials.passwordHaveAllowedLength()) {
            message += "Minimalna długość hasła: 4 znaki"+System.getProperty("line.separator");
        }

        if (credentials.loginContainsUnallowedChars()) {
            message += "Niedozwolone znaki w nazwie użytkownika"+System.getProperty("line.separator");
        }
        if (credentials.passwordContainsUnallowedChars()) {
            message += "Niedozwolone znaki w haśle"+System.getProperty("line.separator");
        }

        if (message.length() > 0) {
            message += System.getProperty("line.separator") + "Popraw błędy i spróbuj ponownie";
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Nieprawidłowy format danych");
            alert.setHeaderText("Błądy logowania:");
            alert.setContentText(message);
            alert.showAndWait();

            return true;
        }

        return false;
    }

    private boolean credentialsHaveServerSideError(Credentials credentials)  {
        //TODO: wysyłanie na serwer i odbiór
        String details = "login";
        DataPackage dataPackage = new DataPackage(details, credentials);
        ServerRequest request   = new ServerRequest(dataPackage);
        Communication.getInstance().addRequest(request);
        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        //TODO: register
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
        DataPackage   dataPackage = new DataPackage("disconnect",null);
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

    public void przyklad() {
        //TODO: remove

        //Umieszczanie usera na serwerze
        //Tworzenie odpowiedniego zapytania
        User user = new User();
        user.setName("Heniek");
        user.setSurname("Tester");
        DataPackage   dataPackage = new DataPackage("insert",user);
        ServerRequest request     = new ServerRequest(dataPackage);

        //Wyświetlenie przed
        System.out.println("id przed dodaniem: "+user.getId());

        //dodawanie do wysłania
        Communication.getInstance().addRequest(request);

        //Opcjonalnie - oczekiwanie na odpowiedź jeśli trzeba:
        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Wyświetlenie wyniku
        user = (User) request.getDataPackage().getObject();
        System.out.println("id po dodaniu (z bazy): "+user.getId());

    }

}