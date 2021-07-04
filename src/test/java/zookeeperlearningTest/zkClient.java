package zookeeperlearningTest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

// 不需要容器的测试，注释掉，速度会更快
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = {ZooKeeperApplication.class})
public class zkClient {

    // 下列链接字符串中的“逗号”前后不可以有空格，空格会被识别成字符串的一部分
    private static final String ZOOKEEPER_SERVER0 = "192.168.200.130:2181";
    private static final String ZOOKEEPER_SERVER1 = "192.168.200.128:2181";
    private static final String ZOOKEEPER_SERVER2 = "192.168.200.129:2181";
    // 连接至整个集群
    private static final String CONNECT_STR = ZOOKEEPER_SERVER0 + "," + ZOOKEEPER_SERVER1 + "," + ZOOKEEPER_SERVER2;
    private static final int SESSION_TIMEOUT = 20000; // ms
    private ZooKeeper zkClient = null;
    // 制造阻塞
    static CountDownLatch latch = new CountDownLatch(1);

    /**
     * 初始化ZooKeeper客户端
     * @throws IOException
     */
    @Before
    public void initZkCli() throws IOException { // 创建ZooKeeper客户端的时候可能抛出IOException
        zkClient = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            // 收到事件后的回调函数（用户的业务逻辑）
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());
                List<String> childrenList = new ArrayList<>();
                // 再次启动监听，这样可以达到一直监听的效果
                try {
                    childrenList = zkClient.getChildren("/", true);
                } catch (KeeperException e) {
                    // SESSION_TIMEOUT 连接超时时间可以设置的长一点，防止连接不上抛出异常
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String children : childrenList){
                    System.out.println(children);
                }
            }
        });
    }

    /**
     * 创建节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        /**
         * 参数一：路径地址
         * 参数二：想要保存的数据，需要转换成字节数组
         * 参数三：ACL访问控制列表（Access control list）,
         *      参数类型为ArrayList<ACL>，Ids接口提供了一些默认的值可以调用。
         *      OPEN_ACL_UNSAFE     This is a completely open ACL
         *                          这是一个完全开放的ACL，不安全
         *      CREATOR_ALL_ACL     This ACL gives the
         *                           creators authentication id's all permissions.
         *                          这个ACL赋予那些授权了的用户具备权限
         *      READ_ACL_UNSAFE     This ACL gives the world the ability to read.
         *                          这个ACL赋予用户读的权限，也就是获取数据之类的权限。
         * 参数四：创建的节点类型。枚举值CreateMode
         *      PERSISTENT (0, false, false)
         *      PERSISTENT_SEQUENTIAL (2, false, true)
         *          这两个类型创建的都是持久型类型节点，会话结束之后不会自动删除。
         *          区别在于，第二个类型所创建的节点名后会有一个单调递增的数值
         *      EPHEMERAL (1, true, false)
         *      EPHEMERAL_SEQUENTIAL (3, true, true)
         *          这两个类型所创建的是临时型类型节点，在会话结束之后，自动删除。
         *          区别在于，第二个类型所创建的临时型节点名后面会有一个单调递增的数值。
         * 返回值：create()方法的返回值是创建的节点的实际路径
         */
        String nodeCreated = zkClient.create("/atguigu",
                "shuaige".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    /**
     * 获取子节点，并监听节点的变化
     */
    @Test
    public void getChildreAndWatch() throws KeeperException, InterruptedException {
        // 这里应该是开启一个线程去监听ZooKeeper服务器的变化
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断接口是否存在
     */
    @Test
    public void nodeIsExist() throws KeeperException, InterruptedException {

            Stat stat = zkClient.exists("/atguigu", false);
            System.out.println(stat == null ? "===not exist===" : "===exist===");
            latch.await();
    }
}
