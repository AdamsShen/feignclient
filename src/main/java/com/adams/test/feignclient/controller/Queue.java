package com.adams.test.feignclient.controller;

import java.util.ArrayList;

/**
 * @author Adams
 * @create 2019/8/26 14:54
 */
public class Queue<T> {
    private ArrayList<T> r = new ArrayList<>(10);

    public void offer(T t) {
        r.add(t);
    }

    public T poll() {
      return r.remove(0);
    }
}

class Stack<T> {
    private ArrayList<T> r;

    public void offer(T t) {
        r.add(t);
    }

    public T poll() {
        return r.remove(r.size() - 1);
    }

    public T peek() {
        return r.get(r.size() - 1);
    }
}
