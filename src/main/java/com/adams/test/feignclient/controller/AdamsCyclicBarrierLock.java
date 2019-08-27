package com.adams.test.feignclient.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Adams
 * @create 2019/8/27 12:51
 */
public class AdamsCyclicBarrierLock {
    private int cur_count = 0;

    private int size;

    private ReentrantLock reentrantLock;

    private Condition condition;

    private volatile Object generation = new Object();

    public AdamsCyclicBarrierLock(int size) {
        this.size = size;
        this.reentrantLock = new ReentrantLock();
        this.condition = this.reentrantLock.newCondition();
    }

    public void await() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            Object obj = this.generation;
            this.cur_count++;
            if(cur_count == size) {
                nextGeneration();
            }else {
                for(;;) {
                    condition.await();
                    if(obj != generation) {
                        break;
                    }
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public void nextGeneration() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            cur_count = 0;
            condition.signalAll();
            this.generation = new Object();
        } finally {
            reentrantLock.unlock();
        }
    }
}
