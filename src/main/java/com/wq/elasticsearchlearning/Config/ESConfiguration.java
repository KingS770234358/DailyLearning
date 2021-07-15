package com.wq.elasticsearchlearning.Config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 在 spring-boot-autoconfiguration 中找
// elasticsearch中的ElasticsearchRestClientConfigurations类 和 data.elasticsearch
// xxxxAutoConfiguration xxxConfigurationProperties

@Configuration
public class ESConfiguration {

    // 注册到容器中
    @Bean // bean id 为方法名 class为返回值
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                // RestFul风格客户端构造器
                RestClient.builder(
                        // 传入ES的IP:PORT地址作为构造参数
                        new HttpHost("localhost", 9200, "http")
                        // 如果是 ES 集群的话 可以配置多个
                        // , new HttpHost("localhost", 9201, "http")
                )
        );
        return client;
    }

}
