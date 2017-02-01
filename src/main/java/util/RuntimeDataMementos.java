package util;

import java.util.HashMap;

/**
 * Created by lorkano on 01.02.17.
 */
public class RuntimeDataMementos {
    private HashMap<String, RuntimeDataHolder> mementos = new HashMap<String, RuntimeDataHolder>();

    public RuntimeDataHolder getMemento(String key) {
        RuntimeDataHolder result = mementos.get(key);
        return result;
    }

    public void addMemento(String key, RuntimeDataHolder memento) {
        mementos.put(key, memento);
    }

    public HashMap<String, RuntimeDataHolder> getList() {
        return mementos;
    }
}
