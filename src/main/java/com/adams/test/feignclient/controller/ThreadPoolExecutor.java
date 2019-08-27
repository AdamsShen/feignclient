package com.adams.test.feignclient.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Adams
 * @create 2019/8/27 10:20
 */
public class ThreadPoolExecutor {
    private BlockingQueue<Runnable> blockingQueue;

    private List<Thread> workers;

    private int queueSize;

    private int coreSize;

    private volatile boolean isShutdown;

    static class WorkerThread extends Thread {
        private ThreadPoolExecutor executor;

        public WorkerThread(ThreadPoolExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void run() {
            while(!executor.isShutdown || executor.blockingQueue.size() > 0) {
                try {
                    if(!executor.isShutdown) {
                        Runnable task = executor.blockingQueue.take();
                        if(null != task) {
                            task.run();
                        }
                    }else  {
                        Runnable task = executor.blockingQueue.poll();
                        if(null != task) {
                            task.run();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public ThreadPoolExecutor(int coreSize, int queueSize) {
        this.blockingQueue = new LinkedBlockingQueue<>(queueSize);
        this.workers = Collections.synchronizedList(new ArrayList<>());
        this.coreSize = coreSize;
        this.queueSize = queueSize;
        for (int i = 0; i < coreSize; i++) {
            WorkerThread workerThread = new WorkerThread(this);
            this.workers.add(workerThread);
            workerThread.start();
        }
    }

    public void submit(Runnable task) {
        if(!isShutdown) {
            blockingQueue.offer(task);
        }
    }

    public void execute(Runnable task) {
        try {
            if(!isShutdown) {
                blockingQueue.put(task);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        isShutdown = true;

        for (Thread worker : workers) {
            if(worker.getState() == Thread.State.BLOCKED ||
                    worker.getState() == Thread.State.WAITING ||
                    worker.getState() == Thread.State.TIMED_WAITING) {
                worker.interrupt();
            }
        }
    }
}
