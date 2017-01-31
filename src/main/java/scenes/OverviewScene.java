package scenes;

import controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.Credentials;
import util.RuntimeDataHolder;
import util.User;
import util.facades.UserFacade;

import java.net.URL;
import java.util.ResourceBundle;


public class OverviewScene implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private void logoutMenuItemAction(ActionEvent event) {
        UserFacade.getInstance().logout();
        NavigationController.navigateTo("LoginScene.fxml", menuBar.getScene(), true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User    loggedUser      = RuntimeDataHolder.getInstance().getLoggedUser();
        String  jobTitle        = loggedUser.getJobTitle();
        Menu    menu            = null;

        if (jobTitle.equals("ADMINISTRATOR")) {
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
            return;
        }

    }

    private void createProjectMenuItemAction(ActionEvent event) {
        NavigationController.navigateTo("CreateProjectScene.fxml", menuBar.getScene(), false);
    }

}