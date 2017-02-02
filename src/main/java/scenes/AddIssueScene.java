package scenes;

import controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Issue;
import util.Project;
import util.RuntimeDataHolder;
import util.User;
import util.builders.ProjectBuilder;
import util.facades.ProjectFacade;
import util.facades.UserFacade;


public class AddIssueScene {

    Project project = (Project) RuntimeDataHolder.getInstance().getSharedParam();

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button sendButton;


    @FXML
    private Button backButton;


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

        Issue issue = new Issue();
        issue.setTitle(titleField.getText());
        issue.setDescription(descriptionField.getText());
        issue.setProjectId(project.getId());

        project.addIssue(issue);

        ProjectFacade.getInstance().updateProject(project);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dodano błąd");
        alert.setHeaderText("Błąd dodany pomyślnie");
        alert.showAndWait();
        NavigationController.navigateTo("OverviewScene.fxml",sendButton.getScene(),false);
        return;


    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        NavigationController.navigateUp(backButton.getScene());
    }

}