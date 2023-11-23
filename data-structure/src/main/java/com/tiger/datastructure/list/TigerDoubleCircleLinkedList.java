package com.tiger.datastructure.list;

/**
 * 双向循环链表
 * 第一个节点对的前驱指向最后一个节点
 * 最后一个节点的后继指向第一个节点
 *
 * @Auther: Zeng Hu
 * @Date: 2020/7/12 19:06
 * @Description:
 * @Version: 1.0
 **/
public class TigerDoubleCircleLinkedList<T> implements TigerList<T> {
    private Node<T> head = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            head.setNext(node);
            head.setPrev(node);
        } else {
            Node tail = head.prev();
            tail.setNext(node);
            node.setPrev(tail);
            node.setNext(head);
            head.setPrev(node);
        }
        size++;
    }

    @Override
    public boolean exists(T value) {
        if (head == null) return false;
        Node tempHead = head;
        Node tail = head.prev();
        while (tempHead != tail) {
            if (tempHead.value().equals(value)) {
                return true;
            }
            if (tail.value().equals(value)) {
                return true;
            }
            tempHead = tempHead.next();
            tail = tail.prev();
        }
        return false;
    }

    @Override
    public T remove(T value) {
        if (head == null) return null;

        Node tempHead = head;
        Node tail = head.prev();
        Node removed = null;
        // 双向查找
        while (tempHead != tail) {
            if (tempHead.value().equals(value)) {
                removed = tempHead;
                break;
            }
            if (tail.value().equals(value)) {
                removed = tail;
                break;
            }
            tempHead = tempHead.next();
            tail = tail.prev();
        }
        if (removed == null) {
            return null;
        }

        if (removed == head) {
            head.prev().setNext(removed.next());
            head.next().setPrev(removed.prev());
            head = removed.next();
        } else {
            removed.prev().setNext(removed.next());
            removed.next().setPrev(removed.prev());
        }
        size--;
        return (T) removed.value();
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
        TigerDoubleCircleLinkedList<Integer> list = new TigerDoubleCircleLinkedList<>();
        list.add(2);
        list.add(6);
        list.add(3);
        list.add(9);

        System.out.println(list.exists(2));
        System.out.println(list.exists(9));
        System.out.println(list.get(0));
        list.remove(9);


        System.out.println("=============");
        Node next = list.head();
        while (next.next() != list.head()) {
            System.out.println(next.value());
            next = next.next();
        }
        System.out.println(next.value());
    }

}
