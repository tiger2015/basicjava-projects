package com.tiger.datastructure.queue;

import com.tiger.datastructure.list.Node;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/13 21:40
 * @Description:
 * @Version: 1.0
 **/
public interface TigerAbstractQueue<T> {
    void enqueue(T value);

    T dequeue();

    int size();

    boolean isEmpty();

    Node head();
}
