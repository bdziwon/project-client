package net;


import org.apache.maven.settings.Server;
import util.DataPackage;
import util.RuntimeDataHolder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Communication {

    private static Communication communication = null;
    private BlockingQueue<ServerRequest> requests = new ArrayBlockingQueue<ServerRequest>(100);

    private boolean running = false;

    private Communication() {

    }

    public void startThread() {
        if (running) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                communicationMethod();
            }
        });
        t.start();
        running = true;
    }

    private void communicationMethod() {

        Socket socket = RuntimeDataHolder.getInstance().getSocket();
        ObjectInputStream  input   = null;
        ObjectOutputStream output  = null;
        ServerRequest request = null;
        DataPackage   dataPackage = null;

        if (socket == null) {
            throw new RuntimeException("Socket is null");
        }


        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input  = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (running) {
            try {
                request = requests.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                output.writeObject(request.getDataPackage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!request.isWaitingforResponse()) {
                continue;
            }
            try {
                 dataPackage = (DataPackage) input.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            request.setDataPackage(dataPackage);
            request.getSemaphore().release();
        }
    }
    public static Communication getInstance() {
        if (communication == null) {
            communication = new Communication();
        }
        return communication;
    }

    public void addRequest(ServerRequest request) {
        try {
            this.requests.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
