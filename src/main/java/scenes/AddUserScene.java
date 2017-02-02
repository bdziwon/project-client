package scenes;

import controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Project;
import util.RuntimeDataHolder;
import util.User;
import util.builders.ProjectBuilder;
import util.facades.ProjectFacade;
import util.facades.UserFacade;


public class AddUserScene {

    Project project = (Project) RuntimeDataHolder.getInstance().getSharedParam();

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

        ProjectFacade.getInstance().updateProject(project);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dodano użytkownika");
        alert.setHeaderText("Użytkownik dodany pomyślnie");
        alert.showAndWait();
        NavigationController.navigateTo("OverviewScene.fxml",sendButton.getScene(),false);
        return;


    }

    @FXML
    private void addUserButtonAction(ActionEvent event) {
        String          username        = usernameField.getText();

        User user = UserFacade.getInstance().findUserByLogin(username);

        if (user.getId() == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Użytkownik nie istnieje");
            alert.setHeaderText("Użytkownik nie istnieje");
            alert.setContentText("Spróbuj ponownie");
            alert.showAndWait();
            return;
        } else {
            project.addUser(user);
            usersListView.getItems().addAll(user);
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        NavigationController.navigateUp(backButton.getScene(), true);
    }

}