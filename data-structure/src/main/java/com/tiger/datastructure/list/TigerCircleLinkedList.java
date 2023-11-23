package com.tiger.datastructure.list;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/12 17:15
 * @Description:
 * @Version: 1.0
 **/
public class TigerCircleLinkedList<T> implements TigerList<T> {
    private Node<T> head = null;
    private int size = 0;

    @Override
    public T remove(T value) {
        if (head == null) return null;
        Node prev, next;
        prev = head;
        next = head.next();
        while (next != head) {
            if (next.value().equals(value)) {
                prev.setNext(next.next());
                break;
            }
            prev = next;
            next = next.next();
        }
        if (next == head && !head.value().equals(value)) {
            return null;
        }
        if (head.value().equals(value)) {
            head = head.next();
            prev.setNext(head);
        }
        size--;
        return (T) next.value();
    }

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            head.setNext(node);
        } else {
            Node<T> prev = head;
            while (head != prev.next()) {
                prev = prev.next();
            }
            prev.setNext(node);
            node.setNext(head);
        }
        size++;
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
        TigerCircleLinkedList<Integer> list = new TigerCircleLinkedList<>();
        list.add(2);
        list.add(5);
        list.add(3);
        list.add(9);


        System.out.println(list.get(0));
        System.out.println(list.exists(2));
        System.out.println(list.exists(9));
        Node next = list.head();
        do {
            System.out.println(next.value());
            next = next.next();
        } while (next != list.head);

    }
}
