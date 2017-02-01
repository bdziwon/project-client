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

    private BlockingQueue<ServerRequest> requests = new ArrayBlockingQueue<ServerRequest>(100);
    private Socket socket = null;

    private boolean running = false;

    public Communication() {
        super();
    }

    public void startThread() {
        if (running) {
            return;
        }
        System.out.println("Rozpoczęto nowy wątek communication");
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

        Socket socket = RuntimeDataHolder.getInstance().getCommunication().getSocket();
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

        System.out.println("Koniec połączenia klienta");
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
