package scenes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import util.Credentials;

import java.io.IOException;

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
        primaryStage.setTitle("Logowanie");
        primaryStage.setResizable(false);

        GridPane root = new GridPane();
        try {
            root = (GridPane) FXMLLoader.load(getClass().getClassLoader().getResource("LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

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

    private boolean credentialsHaveServerSideError(Credentials credentials) {
        //TODO: wysyłanie na serwer i odbiór
        return false;
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        //TODO: register
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
        Platform.exit();
    }

}