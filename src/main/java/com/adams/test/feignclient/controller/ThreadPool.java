package com.adams.test.feignclient.controller;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Adams
 * @create 2019/8/26 16:33
 */
public class ThreadPool {
    private ArrayList<WorkThread> works;
    private int coreSize;
    private int maxSize;
    private LinkedBlockingQueue<Runnable> coreBlockingQueue;
    private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue(100);

    public ThreadPool(int coreSize, int maxSize) {
        this.coreBlockingQueue = new LinkedBlockingQueue<>(coreSize);
        this.coreSize = coreSize;
        this.maxSize = coreSize;
        this.works = new ArrayList<>(coreSize);
        for (int i = 0; i < coreSize; i++) {
            this.works.add(new WorkThread());
        }
    }

    public void execute(Runnable runnable) {
        if(coreBlockingQueue.size() < coreSize) {
            coreBlockingQueue.offer(runnable);
            works.get(coreBlockingQueue.size()).start();
        }else {
            linkedBlockingQueue.offer(runnable);
        }
    }


    class WorkThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    Runnable task = linkedBlockingQueue.take();

                    if(null != task) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
