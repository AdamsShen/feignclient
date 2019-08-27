package com.adams.test.feignclient.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Adams
 * @create 2019/8/26 15:12
 */
public class BlockingQueue {
    private ReentrantLock lock;
    private Condition notEmpty;
    private Condition notFull;
    private Object[] items;
    private int count;
    private int putIndex;
    private int takeIndex;

    public BlockingQueue(int size) {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        items = new Object[size];
    }

    public void put(Object object) {
        lock.lock();
        try {
            while(count == items.length) {
                notFull.await();
            }
            items[putIndex] = object;
            if(++putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        try {
            while(count == 0) {
                notEmpty.await();
            }
            Object obj = items[takeIndex];
            items[takeIndex] = null;
            if(++takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notFull.signal();
            return obj;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public Object peek() {
        lock.lock();
        try {
            return items[count];
        } finally {
            lock.unlock();
        }
    }
}
