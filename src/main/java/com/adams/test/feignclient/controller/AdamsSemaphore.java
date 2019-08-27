package com.adams.test.feignclient.controller;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Adams
 * @create 2019/8/27 13:07
 */
public class AdamsSemaphore {

    private static int count;

    private Sync sync;

    public AdamsSemaphore(int count) {
        sync = new Sync();
        count = count;
    }

    public void accquire() {
        sync.acquireShared(1);
    }

    public void release() {
        sync.releaseShared(1);
    }

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int state = getState();
                int size = state + 1;
                if (size <= count) {
                    if (compareAndSetState(state, size)) {
                        return 1;
                    }
                } else {
                    return -1;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int state = getState();
                int size = state - 1;
                if (size > 0) {
                    if (compareAndSetState(state, size)) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
    }
}
