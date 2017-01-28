package util;

import java.net.Socket;

public class RuntimeDataHolder {

    private static RuntimeDataHolder runtimeDataHolder = null;

    private Socket  socket      = null;
    private User    loggedUser  = null;

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
}
