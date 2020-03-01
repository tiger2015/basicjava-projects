package com.tiger.multithread.listtest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {

        System.out.println(2<<16);
        List<String> list = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        for (int i = 0; i < (2 << 16); i++) {
            list.add(i + "");
            linkedList.add(i + "");
        }
        long start = System.nanoTime();
        for (int i = 0; i < (2 << 12); i++) {
            list.add(i, i + "");
            list.remove(i);
        }
        long end = System.nanoTime();
        System.out.println("total time:" + (end - start) / 1000 + " ms");


        start = System.nanoTime();
        for (int i = 0; i < (2 << 12); i++) {
            linkedList.add(i, i + "");
            linkedList.remove(i);
        }
        end = System.nanoTime();
        System.out.println("total time:" + (end - start) / 1000 + " ms");

    }
}
