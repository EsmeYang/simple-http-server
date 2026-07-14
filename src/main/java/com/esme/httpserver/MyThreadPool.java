package com.esme.httpserver;

import java.util.ArrayList;
import java.util.List;

public class MyThreadPool {

    private final List<Runnable> taskList = new ArrayList<>();
    private final Thread[] workers;

    public MyThreadPool(int numThreads) {
        workers = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            workers[i].start();
        }
    }

    public synchronized void put(Runnable task) {
        taskList.add(task);
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (taskList.isEmpty()) {
            wait();
        }
        return taskList.remove(0);
    }

    public void submit(Runnable task) {
        put(task);  // submit 现在调用 put()
    }
}