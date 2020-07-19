package com.tiger.datastructure.queue;

import com.tiger.datastructure.list.Node;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/13 20:49
 * @Description:
 * @Version: 1.0
 **/
public class TigerQueue<T> implements TigerAbstractQueue<T> {

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    @Override
    public void enqueue(T value) {
        Node node = new Node(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (head == null) return null;
        Node node = head;
        head = head.next();
        size--;
        return (T) node.value();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Node head() {
        return head;
    }

    public static void main(String[] args) {
        TigerQueue<Integer> queue = new TigerQueue<>();
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(6);

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());



    }

}
