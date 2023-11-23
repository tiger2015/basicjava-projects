package com.tiger.datastructure.stack;

import com.tiger.datastructure.list.Node;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/13 22:27
 * @Description:
 * @Version: 1.0
 **/
public class TigerStack<T> {
    private Node head = null;
    private int size = 0;


    public void push(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            node.setNext(head);
            head = node;
        }
        size++;
    }

    public T pop() {
        if (head == null) return null;
        Node node = head;
        head = head.next();
        size--;
        return (T) node.value();
    }

    public static void main(String[] args) {
        TigerStack<Integer> stack = new TigerStack<>();
        stack.push(3);
        stack.push(4);
        stack.push(-1);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());


    }


}
