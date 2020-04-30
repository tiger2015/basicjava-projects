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
        Client client = new Client("products", RedisCommon.createJedisPool());
       query(client);
       // createIndex(client);

       // addDocument(client);

        // client.deleteDocument("doc1");
    }

    public static void addDocument(Client client){
        /**
        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "hello world");
        fields.put("body", "lorem ipsum");
        fields.put("price", 1337);
        client.addDocument("doc1", fields);
         */
        Map<String,Object> fields = new HashMap<>();
        fields.put("prod_id","BNBG01");
        fields.put("prod_name","Fish bean bag toy");
        fields.put("prod_price",3.49);
        fields.put("prod_dec","Fish bean bag toy, complete with bean bag worms with which to feed it");
        client.addDocument("prod1", fields);


    }


    public static void createIndex(Client client){
//        Schema schema = new Schema()
//                .addTextField("title",5.0)
//                .addTextField("body", 1.0)
//                .addNumericField("price");
//        client.createIndex(schema, Client.IndexOptions.defaultOptions());


        Schema schema = new Schema()
                .addTextField("prod_id", 5.0)
                .addTextField("prod_name", 1.0)
                .addNumericField("prod_price")
                .addTextField("prod_dec", 1.0);
        client.createIndex(schema, Client.IndexOptions.defaultOptions());
    }


    public static void query(Client client){

        // 搜索的名称"hello world"
        // 搜索匹配：
        // 1. AND: 多个单词：foo bar baz
        // 2. OR: hello|hallo|hola
        // 3. NOT:
        // 4. 前缀匹配：a*
        // 5. 指定字段: @field:hellword
        // 6. 数字范围: @field:[{min} {max}]
        // 7. GEO： @field:[{lon} {lat} {radius} {m|km|mi|ft}]
        // 8. 模糊匹配：%hello%
        Query query = new Query("bear");
        SearchResult result = client.search(query);
        result.docs.forEach(e->log.info(e.toString()));
    }


}
