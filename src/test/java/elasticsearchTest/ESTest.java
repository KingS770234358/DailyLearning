package elasticsearchTest;

import com.alibaba.fastjson.JSON;
import com.wq.elasticsearchlearning.ESApplication;
import com.wq.elasticsearchlearning.pojo.User;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse; // 注意
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.Highlighter;
import org.json.JSONString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试es 7.13.3 高级客户端API
 */
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
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("javaelasticsearch");
        // 2.客户端执行 创建索引 的请求，得到1个请求的响应 CreateIndexResponse，对应PUT请求
        System.out.println(restHighLevelClient);
        System.out.println(restHighLevelClient.indices());
        // RequestOptions.DEFAULT 默认的请求参数
        // 执行请求后 获得请求的响应
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
    // 2.获取索引 - 判断索引是否存在
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("javaelasticsearch2");
        // 返回是否存在
        Boolean exist = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    // 3.删除索引
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("index02"); // 为空串会报错
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged()); // acknowledgedResponse.isAcknowledged() ===> true
    }

    // 4.添加文档 - 先创建pojo
    @Test
    public void testAddDocument() throws IOException {
        // 4.1 创建对象 new User
        User user = new User("狂神说", 3);
        IndexRequest indexRequest = new IndexRequest("javaelasticsearch");
        indexRequest.id("1"); // 这里id要是String
        indexRequest.timeout(TimeValue.timeValueSeconds(1)); // 1s的超时时间 默认就是1s
        // 4.2 将数据user转成JSON 放入请求
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON); // 指定数据类型为JSON - XContentType.JSON
        // 4.3 客户端发送请求 获取响应的结果                  这里使用的是index方法（不是indices方法）
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        // 4.4 打印响应信息
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status()); // ==> CREATED 对应命令返回的状态
    }

    // 5.获取文档
    @Test
    public void testExistDocument() throws IOException {
        GetRequest getRequest = new GetRequest("javaelasticsearch", "1"); // 按照 id 从指定index中取doc
        getRequest.fetchSourceContext(new FetchSourceContext(false)); // false表示不获取 _source上下文 效率更高
        getRequest.storedFields("_none_"); // 设置排序规则 默认为_none_

        Boolean exist = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exist);

    }
    // 6.获取文档的详细信息
    @Test
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("javaelasticsearch", "1"); // 按照 id 从指定index中取doc
        // getRequest.storedFields("_none_"); // 设置排序规则 默认为_none_
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String getRes = getResponse.getSourceAsString(); // 可以以String形式打印Source 显示文档内容
        // 也可以使用Map形式 key value键值对的形式返回
        System.out.println(getRes);
        System.out.println(getResponse);
    }
    // 7.更新文档记录
    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("javaelasticsearch", "1"); // 按照 id 从指定index中取doc
        updateRequest.timeout("1s"); // 1s的超时时间
        User newUser = new User("狂神说Java", 18);
        updateRequest.doc(JSON.toJSONString(newUser), XContentType.JSON); // 指定以什么格式存入文档
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }
    // 8.删除文档记录
    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("javaelasticsearch", "1"); // NOT_FOUND / OK
        deleteRequest.timeout("1s"); // 1s的超时时间
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }
    // 9.批量插入
    @Test
    public void testBulkDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        List<User> userList = new ArrayList<>();
        userList.add(new User("kuangshen1", 3));
        userList.add(new User("kuangshen2", 3));
        userList.add(new User("kuangshen3", 3));
        userList.add(new User("qinjiang1", 3));
        userList.add(new User("qinjiang2", 3));
        userList.add(new User("qinjiang3", 3));

        // 构造bulkRequest 批处理请求
        for (int i=0; i<userList.size(); i++){
            bulkRequest.add(
                    new IndexRequest("javaelasticsearch")
                    .id("" + i) // 如果不设置id的话 随机字符串作为id
                    .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkItemResponses.hasFailures()); // 是否失败？ false 没有失败 就是成功
    }
    // 10. 查询
    /**
     * SearchSourceBuilder 构建查询
     * HighlighterBuilder 构建高亮
     * TermQueryBuilder 构建精确查询
     * MatchAllQueryBuilder 匹配查询
     * xxxxBuilder xxxxQueryBuilder 对应 kibana 中使用的所有命令
     * @throws IOException
     */
    public void testSearch() throws IOException {
        // 10.1 查询请求
        SearchRequest searchRequest = new SearchRequest("javaelasticsearch");// 指定要搜索的索引

        // 10.2 查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 10.3 查询条件 精确查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "qingjiang1");
        // MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery(); // 匹配所有
        // 10.4 查询条件设置到查询构造器中 - 可以点进 SearchRequest 源码查看
        searchSourceBuilder.query(termQueryBuilder);
//        searchSourceBuilder.from(); // 查询的分页 起始位置
//        searchSourceBuilder.size(); // 查询的分页 页大小
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // HighlighterBuilder 构建高亮
        // searchSourceBuilder.highlighter();

        // 10.5 将查询构造器 sourcebuilder 设置到请求中
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits())); // 获得命中的数据
        System.out.println("=============================================");
        for (SearchHit searchHit : searchResponse.getHits().getHits()){ // 这里有两个.getHits().getHits()
            System.out.println(searchHit.getSourceAsMap()); // 以Map形式返回单个文档
        }
    }
}
