package scenes;

import controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.Credentials;
import util.User;
import util.facades.UserFacade;


public class OverviewScene {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private void logoutMenuItemAction(ActionEvent event) {
        UserFacade.getInstance().logout();
        NavigationController.navigateTo("LoginScene.fxml", menuBar.getScene(), true);
    }
 }