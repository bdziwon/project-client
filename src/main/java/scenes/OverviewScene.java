package scenes;

import controllers.NavigationController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.Credentials;
import util.Project;
import util.RuntimeDataHolder;
import util.User;
import util.facades.ProjectFacade;
import util.facades.UserFacade;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class OverviewScene implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Label userInfoLabel;

    @FXML
    private Label leftMenuLabel;

    @FXML
    private ListView<Project> projectList;

    @FXML
    private TextArea projectTitleTextArea;

    @FXML
    private TextArea projectDescriptionTextArea;

    @FXML
    private  Button saveChangesButton;

    @FXML
    private Button removeProjectButton;

    private Project selectedProject = new Project();

    @FXML
    private void logoutMenuItemAction(ActionEvent event) {
        boolean otherUserLogged = UserFacade.getInstance().logout();
        if (otherUserLogged) {
            NavigationController.navigateTo("OverviewScene.fxml", menuBar.getScene(), true);
            return;
        }
        NavigationController.navigateTo("LoginScene.fxml", menuBar.getScene(), true);
    }

    @FXML
    private void saveChangesButtonAction(ActionEvent event) {
        if (selectedProject == null || selectedProject.getId() == -1) {
            System.out.println("error przy zapisywaniu");
            return;
        }
        selectedProject.setTitle(projectTitleTextArea.getText());
        selectedProject.setDescription(projectDescriptionTextArea.getText());
        ProjectFacade.getInstance().updateProject(selectedProject);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                NavigationController.navigateTo("OverviewScene.fxml",menuBar.getScene(),false);
            }
        });
    }

    @FXML
    private void removeProjectButtonAction(ActionEvent event) {
        if (selectedProject == null || selectedProject.getId() == -1) {
            System.out.println("error przy usuwaniu");
            return;
        }
        ProjectFacade.getInstance().removeProject(selectedProject);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                NavigationController.navigateTo("OverviewScene.fxml",menuBar.getScene(),false);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User    loggedUser      = RuntimeDataHolder.getInstance().getLoggedUser();
        String  jobTitle        = loggedUser.getJobTitle();

        initUserInfoLabel();

        if (jobTitle.equals("ADMINISTRATOR")) {
            initAdministrator();
        }
        initChangeUser();
        initProjectList();
    }

    private void initProjectList() {
        ArrayList<Project> projects = ProjectFacade.getInstance().getProjectList();
        projectList.getItems().clear();
        for (Project project : projects
             ) {
            projectList.getItems().add(project);
        }

        projectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observableValue, Project project, Project t1) {
                selectedProject = t1;
                projectTitleTextArea.setText(t1.getTitle());
                projectDescriptionTextArea.setText(t1.getDescription());
            }
        });

    }

    private void initChangeUser() {
        Menu menu = new Menu("Zmień użytkownika");

        MenuItem menuItem = new MenuItem("Dodaj..");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RuntimeDataHolder.getInstance().saveToMemento();
                RuntimeDataHolder.getInstance().createNew();
                NavigationController.navigateTo("LoginScene.fxml",menuBar.getScene(),false);
            }
        });

        menu.getItems().addAll(menuItem);
        HashMap<String, RuntimeDataHolder> mementos = RuntimeDataHolder.getInstance()
                .getMementos().getList();

        for (Map.Entry<String, RuntimeDataHolder> entry : mementos.entrySet()) {
            String title = entry.getValue().getLoggedUser().toString();
            menuItem = new MenuItem(title);

            if (title.equals(RuntimeDataHolder.getInstance()
                    .getLoggedUser().toString())) {
                continue;
            }

            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    RuntimeDataHolder.getInstance().saveToMemento();
                    RuntimeDataHolder.getInstance().restoreFromMemento(((MenuItem)event.getSource()).getText());
                    NavigationController.navigateTo("OverviewScene.fxml",menuBar.getScene(),false);
                }
            });

            menu.getItems().addAll(menuItem);
        }
        menuBar.getMenus().addAll(menu);
    }

    private void initUserInfoLabel() {
        User    loggedUser      = RuntimeDataHolder.getInstance().getLoggedUser();

        userInfoLabel.setText("Zalogowano " +
                loggedUser.getName()+" "+loggedUser.getSurname()+
                "["+loggedUser.getJobTitle() + "]"
        );
    }

    private void initAdministrator() {
        Menu    menu;
        projectTitleTextArea.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        removeProjectButton.setDisable(false);
        leftMenuLabel.setText("Wszystkie projekty");
        menu = new Menu("Administracja");
        final MenuItem createProjectMenuItem = new MenuItem("Stwórz projekt");

        createProjectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createProjectMenuItemAction(event);
            }
        });

        menu.getItems().addAll(createProjectMenuItem);
        menuBar.getMenus().addAll(menu);
    }

    private void createProjectMenuItemAction(ActionEvent event) {
        NavigationController.navigateTo("CreateProjectScene.fxml", menuBar.getScene(), false);
    }

}