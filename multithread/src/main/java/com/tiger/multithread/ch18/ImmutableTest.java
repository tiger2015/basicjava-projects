package com.tiger.multithread.ch18;

/**
 * @Author Zenghu
 * @Date 2022年06月26日 16:46
 * @Description
 * @Version: 1.0
 **/
public class ImmutableTest {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "hello";

        // true
        System.out.println(s1 == s2);

        s2 = s2 + "";
        // false
        System.out.println(s1 == s2);

        // true
        System.out.println(s1.intern() == s2.intern());

        String s3 = new String("hello");
        // false
        System.out.println(s1 == s3);
        // true
        System.out.println(s1.intern() == s3.intern());

    }
}
