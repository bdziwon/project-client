package scenes;

import controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.Communication;
import net.ServerRequest;
import util.Credentials;
import util.DataPackage;
import util.Project;
import util.User;
import util.builders.ProjectBuilder;
import util.facades.UserFacade;


public class CreateProjectScene {

    ProjectBuilder builder = new ProjectBuilder();

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button sendButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button backButton;

    @FXML
    private ListView usersListView;

    @FXML
    private void sendButtonAction(ActionEvent event) {

        if (titleField.getText().length() < 1 || descriptionField.getText().length() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wypełnij wszystkie pola");
            alert.setHeaderText("Znaleziono puste pola");
            alert.setContentText("Wypełnij je");
            alert.showAndWait();
            return;
        }

        Project project = builder
                .setTitle(titleField.getText())
                .setDescription(descriptionField.getText())
                .build();

        DataPackage     dataPackage     = new DataPackage("insert",project);
        ServerRequest   request         = new ServerRequest(dataPackage);

        Communication.getInstance().addRequest(request);

        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dataPackage = request.getDataPackage();
        project     = (Project) dataPackage.getObject();

        if (project.getId() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd tworzenia projektu");
            alert.setHeaderText("Wystąpił nieoczekiwany błąd");
            alert.setContentText("Spróbuj ponownie");
            alert.showAndWait();
            return;
        }

        dataPackage.setDetails("update");

        request = new ServerRequest(dataPackage);

        Communication.getInstance().addRequest(request);

        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stworzono nowy projekt");
        alert.setHeaderText("Projekt stworzony pomyślnie");
        alert.showAndWait();
        NavigationController.navigateUp(sendButton.getScene());
        return;


    }

    @FXML
    private void addUserButtonAction(ActionEvent event) {
        String          username        = usernameField.getText();
        User            user            = new User();
        String          details         = "select username";
        DataPackage     dataPackage     = new DataPackage(details,username);
        ServerRequest   serverRequest   = new ServerRequest(dataPackage);

        Communication.getInstance().addRequest(serverRequest);
        try {
            serverRequest.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = (User) serverRequest.getDataPackage().getObject();
        if (user.getId() == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Użytkownik nie istnieje");
            alert.setHeaderText("Użytkownik nie istnieje");
            alert.setContentText("Spróbuj ponownie");
            alert.showAndWait();
            return;
        } else {
            builder.addUser(user);
            usersListView.getItems().addAll(user);
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        NavigationController.navigateUp(backButton.getScene());
    }

}