package com.adams.test.feignclient.controller;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Adams
 * @create 2019/8/27 14:16
 */
public class AdamsCountDownLatchV2 {

    private int count;

    private Sync sync;

    public AdamsCountDownLatchV2(int count) {
        this.count = count;
        this.sync = new Sync(count);
    }

    public void countDown() {
        sync.releaseShared(1);
    }

    public void await() {
        sync.acquireShared(1);
    }

    static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;) {
                int state = getState();
                if(state == 0) {
                    return false;
                }

                int next = state - 1;
                if(compareAndSetState(state, next)) {
                    return next == 0;
                }
            }
        }
    }
}
