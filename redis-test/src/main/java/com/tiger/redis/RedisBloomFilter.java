package com.tiger.redis;

import io.rebloom.client.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisBloomFilter {
    public static void main(String[] args) {
        Client bloomClient = new Client(RedisCommon.createJedisPool());
        bloomClient.createFilter("test", 10000, 0.0001);

    }
}
