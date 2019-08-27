package com.adams.test.feignclient.controller;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Adams
 * @create 2019/8/27 11:23
 */
public class AdamsCountDownLatch {

    private int count;

    private Sync sync;

    public AdamsCountDownLatch(int count) {
        this.count = count;
        sync = new Sync(count);
    }

    public void countdown() {
        sync.releaseShared(1);
    }

    public void await() {
        sync.acquireShared(1);
    }


    class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            if(getState() == 0) {
                return 1;
            }else {
                return -1;
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int state = getState();
                if(compareAndSetState(state, state - arg)) {
                    return state == 0;
                }
            }
        }
    }
}
