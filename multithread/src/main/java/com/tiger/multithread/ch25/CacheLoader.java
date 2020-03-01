package com.tiger.multithread.ch25;

@FunctionalInterface
public interface CacheLoader<K, V> {
    V load(K k);
}
