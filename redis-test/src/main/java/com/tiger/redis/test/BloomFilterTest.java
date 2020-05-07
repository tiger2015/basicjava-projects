package com.tiger.redis.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.*;

public class BloomFilterTest {
    private static final long bitLen = 1000000;
    private static BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), bitLen, 0.02);
    private static Set<String> set = new HashSet<>();
    private static List<String> list = new ArrayList<>();

    private static void init() {
        for (int i = 0; i < bitLen; i++) {
            String id = UUID.randomUUID().toString();
            set.add(id);
            filter.put(id);
            list.add(id);
        }
    }


    public static void main(String[] args) {
        init();
        int right = 0;
        int wrong = 0;
        for (int i = 0; i < 1000; i++) {
            String data = i % 100 == 0 ? list.get(i / 100) : UUID.randomUUID().toString();
            if (filter.mightContain(data)) {
                if (set.contains(data)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }
        System.out.println("total count:" + set.size());
        System.out.println("right:" + right);
        System.out.println("wrong:" + wrong);
    }


}
