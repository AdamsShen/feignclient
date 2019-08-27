package com.adams.test.feignclient.controller;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Adams
 * @create 2019/8/26 16:54
 */
public class FutureTask<E> implements Future<E>, Runnable {

    private Callable<E> task;
    private AtomicReference<Boolean> isComplete = new AtomicReference<>(false);
    private LinkedBlockingQueue<Thread> blockingQueue;
    private E e;

    public FutureTask(Callable<E> task) {
        this.task = task;
        this.blockingQueue = new LinkedBlockingQueue<>(100);
    }

    @Override
    public void run() {
        try {
            if(!isComplete.get()) {
                e = task.call();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isComplete.set(true);
        }

        while(true) {
            Thread thread = blockingQueue.poll();
            if(null != thread) {
                LockSupport.unpark(thread);
            }else {
                break;
            }
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public E get() throws InterruptedException, ExecutionException {
        if(!isComplete.get()) {
            blockingQueue.offer(Thread.currentThread());
        }
        while (!isComplete.get()) {
            LockSupport.park();
        }
        return e;
    }

    @Override
    public E get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
