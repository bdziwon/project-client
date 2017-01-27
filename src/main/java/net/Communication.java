package net;


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
    private BlockingQueue<DataPackage> requests = new ArrayBlockingQueue<DataPackage>(100);
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
        DataPackage        request = null;

        if (socket == null) {
            throw new RuntimeException("Socket is null");
        }
        try {
            input  = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Wątek communication działa");
            try {
                request = requests.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (request) {
              /*  try {
                    output.writeObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    request = (DataPackage) input.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } */
                System.out.println("notify");
                notify();
            }
        }
    }

    public static Communication getInstance() {
        if (communication == null) {
            communication = new Communication();
        }
        return communication;
    }

    public void addRequest(DataPackage dataPackage) {
        this.requests.add(dataPackage);
    }
}
