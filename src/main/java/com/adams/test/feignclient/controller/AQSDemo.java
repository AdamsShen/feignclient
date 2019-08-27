package com.adams.test.feignclient.controller;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Adams
 * @create 2019/8/26 17:15
 */
public class AQSDemo {

    private LinkedBlockingQueue<Thread> linkedBlockingQueue = new LinkedBlockingQueue<Thread>(100);

    private AtomicInteger state = new AtomicInteger(0);

    private AtomicReference<Thread> thread = new AtomicReference<>();

    private AtomicInteger readCount = new AtomicInteger(0);

    public boolean tryAccquire(int accquire) {
        if(state.get() == 0) {
            if(state.compareAndSet(0, state.get() + accquire)) {
                thread.set(Thread.currentThread());
                return true;
            }
        } else if(Thread.currentThread() == thread.get()) {
            state.set(state.get() + accquire);
            return true;
        }
        return false;
    }

    public void accquire(int accquire) {
        while(!tryAccquire(accquire)){
            linkedBlockingQueue.offer(Thread.currentThread());

            for(;;) {
                Thread thread = linkedBlockingQueue.peek();
                if(null != thread && Thread.currentThread() == thread) {
                    if(tryAccquire(accquire)) {
                        linkedBlockingQueue.poll();
                        return;
                    } else {
                        LockSupport.park();
                    }
                } else {
                    LockSupport.park();
                }
            }
        }
    }

    public boolean tryRelease(int accquire) {
        if(Thread.currentThread() != thread.get()) {
            throw new IllegalMonitorStateException("不合法的监听器状态");
        }

        state.set(state.get() - accquire);

        if(state.get() == 0) {
            thread.set(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean release(int accquire) {
        if(tryRelease(accquire)) {
            Thread thread = linkedBlockingQueue.peek();
            if(null != thread) {
                LockSupport.unpark(thread);
            }
            return true;
        }

        return false;
    }

    public boolean tryAccquireShared(int accquire) {
        for(;;) {
            if(state.get() != 0 && thread.get() != Thread.currentThread()) {
                return false;
            }

            if(readCount.compareAndSet(0, readCount.get() + accquire)) {
                return true;
            }
        }
    }

    public void accquireShared(int accquire) {
        while(!tryAccquireShared(accquire)) {
            linkedBlockingQueue.offer(Thread.currentThread());

            for(;;) {
                Thread thread = linkedBlockingQueue.peek();
                if(null != thread && thread == Thread.currentThread()) {
                    if(tryAccquireShared(accquire)) {
                        linkedBlockingQueue.poll();

                        Thread peek = linkedBlockingQueue.peek();
                        if(null != peek) {
                            LockSupport.unpark(peek);
                        }
                        return;
                    } else {
                        LockSupport.park();
                    }
                } else {
                    LockSupport.park();
                }
            }
        }
    }

    public boolean tryRelaseShared(int accquire) {
        for(;;) {
            if(readCount.compareAndSet(readCount.get(), readCount.get() - accquire)) {
                if(readCount.get() == 0) {
                    thread.set(null);
                    return true;
                }
                return false;
            }
        }
    }

    public boolean releaseShared(int accquire) {
        if(tryRelaseShared(accquire)) {
            Thread thread = linkedBlockingQueue.peek();
            if(null != thread) {
                LockSupport.unpark(thread);
            }
            return true;
        }

        return false;
    }
}
