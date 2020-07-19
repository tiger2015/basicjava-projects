package com.tiger.datastructure.list;

/**
 * 单向链表
 *
 * @Auther: Zeng Hu
 * @Date: 2020/7/12 16:19
 * @Description:
 * @Version: 1.0
 **/
public class TigerLinkedList<T> implements TigerList<T> {
    private Node head = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            Node next = head;
            while (next.next() != null) next = next.next();
            next.setNext(node);
        }
        size++;
    }

    @Override
    public T remove(T value) {
        Node prev, next;
        prev = next = head;
        while (next != null) {
            if (next.value().equals(value)) {
                prev.setNext(next.next());
                size--;
                break;
            }
            prev = next;
            next = next.next();
        }
        // 如果是头节点
        if (next == head && head != null) {
            head = head.next();
        }
        return next == null ? null : (T) next.value();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Node head() {
        return head;
    }


    public static void main(String[] args) {
        TigerLinkedList<Integer> list = new TigerLinkedList<>();
        list.add(2);
        list.add(5);
        list.add(3);
        list.add(7);

        //list.remove(2);
        // list.remove(4);
        System.out.println(list.exists(5));

        Node next = list.head();
        while (next != null) {
            System.out.println(next.value() + " ");
            next = next.next();
        }
    }


}
