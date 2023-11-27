package com.tiger.elsaticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/9/13 11:09
 * @Description:
 * @Version: 1.0
 **/
public class ElasticSearchApplication {


    public static void main(String[] args) {


        HttpHost httpHost = new HttpHost("192.168.100.101", 9200);
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient highLevelClient = new RestHighLevelClient(builder);


        // index API

        IndexRequest indexRequest = new IndexRequest("megacorp");
        indexRequest.id("4");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("first_name", "John");
        jsonMap.put("last_name", "Mike");
        jsonMap.put("interest", "coding");
        jsonMap.put("age", "26");
        jsonMap.put("about", "I like to hiking ");
        indexRequest.source(jsonMap);
        // 可选配置
        indexRequest.timeout(TimeValue.timeValueMillis(3000));
        //indexRequest.routing("routing");
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        //indexRequest.version(3L);
        //indexRequest.versionType(VersionType.INTERNAL);
        // create不支持设置version
        indexRequest.opType(DocWriteRequest.OpType.CREATE);
        //
        //indexRequest.setPipeline("pipeline");

        // get API

        GetRequest getRequest = new GetRequest();
        getRequest.index("megacorp");
        getRequest.id("1");
        // 不返回_source字段
       // getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        String[] includes = {}; //需要返回的字段
        String[] excludes = {"about", "last_name"}; // 不需要返回的字段
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);

        // get source  API
        // 只返回source部分
        GetSourceRequest getSourceRequest = new GetSourceRequest("megacorp", "2");
        getSourceRequest.fetchSourceContext(fetchSourceContext);

        // exists API
        GetRequest existsRequest = new GetRequest();
        existsRequest.index("megacorp");
        existsRequest.id("6");
        existsRequest.fetchSourceContext(new FetchSourceContext(false));
        existsRequest.storedFields("_none_");

        // delete API
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("megacorp");
        deleteRequest.id("6");

        // update API



        try {
            // IndexResponse response = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
           // GetResponse response = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
            // GetSourceResponse response = highLevelClient.getSource(getSourceRequest, RequestOptions.DEFAULT);
           // boolean exists = highLevelClient.exists(existsRequest, RequestOptions.DEFAULT);
            DeleteResponse response = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(response);



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                highLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
