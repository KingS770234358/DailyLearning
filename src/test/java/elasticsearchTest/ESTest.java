package elasticsearchTest;

import com.wq.elasticsearchlearning.ESApplication;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class) // 这里一定要使用RunWith指定 运行类，否则注入为空
@SpringBootTest(classes = {ESApplication.class})
public class ESTest {

    @Autowired // 这里变量名要跟beanid 一直才能没有红色波浪线
                // 否则需要用@Qualify注解指定注入哪个Bean
    private RestHighLevelClient restHighLevelClient;

    @Before
    public void before(){
    }
    @After
    public void after(){
    }
    // 1.创建索引请求
    @Test
    public void createIndex() throws IOException {
        // 1.创建 创建索引 的请求 - 这里的索引名一定要都小写
        CreateIndexRequest javaElasticSearch = new CreateIndexRequest("javaelasticsearch");
        // 2.客户端执行 创建索引 的请求，得到1个请求的响应 CreateIndexResponse
        System.out.println(restHighLevelClient);
        System.out.println(restHighLevelClient.indices());
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(javaElasticSearch, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

}
