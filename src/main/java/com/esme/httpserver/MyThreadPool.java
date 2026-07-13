package com.esme.httpserver;

import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
    private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final Thread[] workers;
    public MyThreadPool(int numThreads) {
        workers = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = taskQueue.take();
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
    public void submit(Runnable task) {
        taskQueue.offer(task);
    }
}