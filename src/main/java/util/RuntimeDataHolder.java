package util;

import controllers.NavigationController;
import util.facades.ProjectFacade;
import util.facades.UserFacade;

import java.net.Socket;

public class RuntimeDataHolder {

    private static RuntimeDataHolder runtimeDataHolder = null;

    private Socket          socket          = null;
    private User            loggedUser      = null;
    private UserFacade      userFacade      = new UserFacade();
    private ProjectFacade   projectFacade   = new ProjectFacade();

    private RuntimeDataHolder() {
    }

    public static RuntimeDataHolder getInstance() {
        if (runtimeDataHolder == null) {
            runtimeDataHolder = new RuntimeDataHolder();
        }
        return runtimeDataHolder;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

}
