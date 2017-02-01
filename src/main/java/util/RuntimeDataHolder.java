package util;

import controllers.NavigationController;
import javafx.scene.Scene;
import net.Communication;
import util.facades.ProjectFacade;
import util.facades.UserFacade;

import java.net.Socket;

public class RuntimeDataHolder {

    private static RuntimeDataHolder runtimeDataHolder = null;

    private RuntimeDataMementos mementos        = new RuntimeDataMementos();
    private User                loggedUser      = null;
    private Scene               scene           = null;
    private Communication       communication   = null;

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

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public RuntimeDataMementos getMementos() {
        return mementos;
    }

    public void setMementos(RuntimeDataMementos mementos) {
        this.mementos = mementos;
    }

    public void saveToMemento() {
        RuntimeDataHolder holder = new RuntimeDataHolder();
        holder.loggedUser = this.loggedUser;
        holder.scene = this.scene;
        holder.communication = this.communication;
        mementos.addMemento(holder.loggedUser.toString(), holder);

    }

    public void restoreFromMemento(String key) {
        RuntimeDataHolder holder = mementos.getMemento(key);
        this.loggedUser = holder.loggedUser;
        this.scene = holder.scene;
        this.communication = holder.communication;
    }

    public void createNew() {
        RuntimeDataHolder runtimeDataHolder = new RuntimeDataHolder();
        this.saveToMemento();
        this.setCommunication(new Communication());
        this.setLoggedUser(runtimeDataHolder.loggedUser);
        UserFacade.getInstance().connectToServer();
        this.setScene(runtimeDataHolder.getScene());
        this.getCommunication().startThread();
    }
}
