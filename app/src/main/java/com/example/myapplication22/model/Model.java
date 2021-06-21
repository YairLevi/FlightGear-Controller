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


    public Model() { }

    public void connect(String ip, int port) {
        Thread t = new Thread(() -> {
            try {
                Socket fg = new Socket(ip, port);
                out = new PrintWriter(fg.getOutputStream(), true);
            } catch (Exception e) {
                System.out.println("---Could Not Connect To FlightGear---\n");
                System.out.println(e.toString());
            }
        });
        t.start();
        run();
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
