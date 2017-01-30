package scenes;

import controllers.NavigationController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import net.Communication;
import net.ServerRequest;
import org.apache.maven.plugin.logging.Log;
import util.Credentials;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.User;
import util.facades.UserFacade;

import java.io.IOException;

public class LoginScene  {

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

    public Stage primaryStage;

    @FXML
    private void loginButtonAction(ActionEvent event) {

        UserFacade userFacade = RuntimeDataHolder.getInstance().getUserFacade();

        String      login       = loginField.getText();
        String      password    = passwordField.getText();
        Credentials credentials = new Credentials(login,password);
        String      error;

        error = userFacade.login(credentials);

        if (error != null) {
            //błąd logowania
            String title;
            String header;
            if (error.equals("already logged")) {
                title  = "Użytkownik jest już zalogowany";
                header = title;
                error  = "Serwer zwrócił '"+error+"'";
            } else {
                if (error.equals("user not found")) {
                    title = "Nieprawidłowy login lub hasło";
                    header = title;
                    error = "Serwer zwrócił '"+error+"'";
                } else {
                    title   = "Nieprawidłowy format danych";
                    header  = "Błędy logowania";
                }
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(error);
            alert.showAndWait();
            return;
        }

        //TODO: Zmiana sceny na widok projektów
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        NavigationController.navigateTo("RegisterScene.fxml",event);

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

//    private void goToRegisterScene(ActionEvent event) throws IOException {
//        Stage stage = new Stage();
//        stage.setTitle("Shop Management");
//        Pane myPane = null;
//        myPane = FXMLLoader.load(getClass().getResource("createCategory.fxml"));
//        Scene scene = new Scene(myPane);
//        stage.setScene(scene);
//        stage.show();
//    }

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