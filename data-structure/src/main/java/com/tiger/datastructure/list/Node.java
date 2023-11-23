package com.tiger.datastructure.list;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/12 16:15
 * @Description:
 * @Version: 1.0
 **/
public class Node<T> {
    private T value;
    private Node next = null; // 后继节点
    private Node prev = null; // 前驱节点

    public Node(T value) {
        this.value = value;
    }

    public Node next() {
        return next;
    }

    public Node prev() {
        return prev;
    }

    public T value() {
        return value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
