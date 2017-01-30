package scenes;

import com.sun.istack.internal.NotNull;
import controllers.NavigationController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.Communication;
import util.Credentials;
import util.RuntimeDataHolder;
import util.User;
import util.facades.UserFacade;

import java.io.IOException;


public class RegisterScene {

    @FXML
    private Button sendButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private ComboBox jobTitleComboBox;

    @FXML
    private void sendButtonAction(ActionEvent event) {
        String      result;
        String      login       = loginField.getText();
        String      password    = passwordField.getText();
        Credentials credentials = new Credentials(login, password);
        String      name        = nameField.getText();
        String      surname     = surnameField.getText();
        String      jobTitle    = (String) jobTitleComboBox.getValue();
        User        user        = new User();

        user.setName(name);
        user.setSurname(surname);
        UserFacade userFacade = RuntimeDataHolder.getInstance().getUserFacade();

        result = userFacade.register(credentials, user);
        if (!result.equals("registered")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Rejestracja nieudana!");
            alert.setHeaderText("Rejestracja nieudana!");
            alert.setContentText(result);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rejestracja pomyślna!");
        alert.setHeaderText("Rejestracja pomyślna!");
        alert.setContentText("Zaloguj się!");

        NavigationController.navigateUp(event);
        alert.showAndWait();

        return;
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        NavigationController.navigateUp(event);
    }


}