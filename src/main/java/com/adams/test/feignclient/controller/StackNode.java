package com.adams.test.feignclient.controller;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Adams
 * @create 2019/8/27 11:00
 */
public class StackNode {
    private AtomicStampedReference<Node> top = new AtomicStampedReference<>(null, 0);

    public void push(Node node) {
        Node oldNode = null;
        int stamp;
        do {
            oldNode = top.getReference();
            stamp = top.getStamp();
            node.setNext(oldNode);
        }while (!top.compareAndSet(oldNode, node, stamp, stamp + 1));
    }

    public Node pop() {
        Node oldNode = null;
        Node newNode = null;
        int stamp;
        do {
            stamp = top.getStamp();
            oldNode = top.getReference();
            if(null == oldNode) {
                return null;
            }
            newNode = oldNode.getNext();
        }while (!top.compareAndSet(oldNode, newNode, stamp, stamp + 1));

        return oldNode;
    }
}
