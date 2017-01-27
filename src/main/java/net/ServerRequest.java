package net;

import util.DataPackage;

import java.util.concurrent.Semaphore;

/**
 * Created by lorkano on 27.01.17.
 */
public class ServerRequest {
    private DataPackage dataPackage;
    private Semaphore   semaphore = new Semaphore(0);

    public DataPackage getDataPackage() {
        return dataPackage;
    }

    public ServerRequest(DataPackage dataPackage) {
        this.dataPackage = dataPackage;
    }

    public void setDataPackage(DataPackage dataPackage) {
        this.dataPackage = dataPackage;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
}
