package util;

public class RuntimeDataHolder {

    private static RuntimeDataHolder runtimeDataHolder = null;

    private User loggedUser;

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
}
