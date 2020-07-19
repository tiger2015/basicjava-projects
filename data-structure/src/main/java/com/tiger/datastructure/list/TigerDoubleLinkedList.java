package com.tiger.datastructure.list;

/**
 * 双向链表，在删除节点时只需要借助前驱节点
 *
 * @Auther: Zeng Hu
 * @Date: 2020/7/12 16:56
 * @Description:
 * @Version: 1.0
 **/
public class TigerDoubleLinkedList<T> implements TigerList<T> {
    private Node<T> head = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            head.setNext(null);
            head.setPrev(null);
        } else {
            Node next = head;
            while (next.next() != null) next = next.next();
            node.setPrev(next);
            next.setNext(node);
            node.setNext(null);
        }
        size++;
    }


    @Override
    public T remove(T value) {
        if (head == null) return null;
        Node prev = head;
        while (prev != null) {
            if (prev.value().equals(value)) { // 找到该节点
                if (prev.prev() != null)
                    prev.prev().setNext(prev.next());
                if (prev.next() != null)
                    prev.next().setPrev(prev.prev());
                size--;
                break;
            }
            prev = prev.next();
        }
        if (prev == null) {
            return null;
        }
        // 如果是头节点被删除
        if (prev == head) {
            head = prev.next();
            head.setPrev(null);
        }
        return (T) prev.value();
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public Node<T> head() {
        return head;
    }

    public static void main(String[] args) {
        TigerDoubleLinkedList<Integer> list = new TigerDoubleLinkedList<>();
        list.add(2);
        list.add(4);
        list.add(-1);
        list.add(6);

       // System.out.println(list.get(4));
        System.out.println(list.exists(-9));
        System.out.println(list.exists(6));

        list.remove(2);

        Node<Integer> next = list.head();
        while (next != null) {
            System.out.println(next.value());
            next = next.next();
        }
    }

}
