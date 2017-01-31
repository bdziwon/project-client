package scenes;

import controllers.NavigationController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.Communication;
import net.ServerRequest;
import util.Credentials;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.User;
import util.facades.UserFacade;

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

        UserFacade userFacade = UserFacade.getInstance();

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

        NavigationController.navigateTo("OverviewScene.fxml",event, true);

        //TODO: Zmiana sceny na widok projektów
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        NavigationController.navigateTo("RegisterScene.fxml",event, false);

    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
        NavigationController.exitApp();
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