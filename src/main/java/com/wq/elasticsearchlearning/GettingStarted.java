package com.wq.elasticsearchlearning;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class GettingStarted {
    public static void main(String[] args) {
        // 1. 初始化ES RestFul高级客户端
        RestHighLevelClient client = new RestHighLevelClient(
                // RestFul风格客户端构造器
                RestClient.builder(
                        // 传入ES的IP:PORT地址作为构造参数
                        new HttpHost("localhost", 9200, "http")
                        // 如果是 ES 集群的话 可以配置多个
                        // , new HttpHost("localhost", 9201, "http")
                )
        );

        // 
    }
}
