package com.adams.test.feignclient.controller;

/**
 * @author Adams
 * @create 2019/8/27 11:52
 */
public class AdamsCyclicBarrier {

    private int count;

    private int size;
    private Object generation = new Object();

    public AdamsCyclicBarrier(int count) {
        this.count = count;
        this.size = count;
    }

    public void await() {
        synchronized (AdamsCyclicBarrier.class) {
            this.count++;
            Object gen = generation;

            if(this.count == size) {
                nextGenaretion();
            } else {
                for(;;) {
                    try {
                        AdamsCyclicBarrier.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(gen != generation) {
                        break;
                    }
                }
            }
        }
    }

    private void nextGenaretion() {
        synchronized (AdamsCyclicBarrier.class) {
            AdamsCyclicBarrier.class.notifyAll();
            count = 0;
            generation = new Object();
        }
    }


    /*public void accquire(int args) throws InterruptedException {
        synchronized (AdamsCyclicBarrier.class) {
            count = count - args;
            while(count != 0) {
                AdamsCyclicBarrier.class.wait();
            }

        }
    }

    public void release(int args) {
        synchronized (AdamsCyclicBarrier.class) {
            count = count + args;
            if(count == size) {
                count = 0;
                AdamsCyclicBarrier.class.notifyAll();
            }
        }
    }*/
}
