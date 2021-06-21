package com.example.myapplication22.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Model {

    private PrintWriter out;
    public BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();
    boolean status = false;

    public Model() { }

    public boolean connect(String ip, int port) {
        Thread t = new Thread(() -> {
            try {
                Socket fg = new Socket(ip, port);
                out = new PrintWriter(fg.getOutputStream(), true);
                status = true;
            } catch (Exception e) {
                System.out.println("---Could Not Connect To FlightGear---\n");
                System.out.println("Exception: "+e.toString());
                status = false;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            System.out.println("Could not join thread");
            System.out.println("Exception: "+e.toString());
        }
        if (status) {
            run();
        }
        return status;
    }

    public void run() {
        new Thread(() -> {
            while (true) {
                try {
                    dispatchQueue.take().run();
                } catch (InterruptedException ignored) { }
            }
        }).start();
    }

    public void update(String target, float value) {
        try {
            dispatchQueue.put(() -> {
                out.print("set /controls/" + target + " " + value + "\r\n");
                out.flush();
            });
        } catch (InterruptedException e) {
            System.out.println("out is still NULL\n");
        }
    }
}
