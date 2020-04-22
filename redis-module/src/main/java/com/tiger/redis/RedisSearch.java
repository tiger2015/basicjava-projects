package com.tiger.redis;

import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RedisSearch {

    public static void main(String[] args) {
        Client client = new io.redisearch.client.Client("testung", RedisCommon.createJedisPool());
        Schema schema = new Schema()
                .addTextField("title",5.0)
                .addTextField("body", 1.0)
                .addNumericField("price");
        //client.createIndex(schema, io.redisearch.client.Client.IndexOptions.defaultOptions());

        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "hello world");
        fields.put("body", "lorem ipsum");
        fields.put("price", 1337);
        // client.addDocument("doc1", fields);

        Query query = new Query("hello world")
                .addFilter(new Query.NumericFilter("price",0, 10000))
                .limit(0,5);
        SearchResult result = client.search(query);
        result.docs.forEach(e->log.info(e.toString()));
    }

}
