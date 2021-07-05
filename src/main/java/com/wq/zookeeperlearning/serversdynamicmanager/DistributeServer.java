package com.wq.zookeeperlearning.serversdynamicmanager;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式服务器 动态上下线管理
 */
public class DistributeServer {

    private static final String ZOOKEEPER_SERVER0 = "192.168.200.130:2181";
    private static final String ZOOKEEPER_SERVER1 = "192.168.200.128:2181";
    private static final String ZOOKEEPER_SERVER2 = "192.168.200.129:2181";
    // 连接至整个集群 下列链接字符串中的“逗号”前后不可以有空格，空格会被识别成字符串的一部分
    private static final String CONNECT_STR = ZOOKEEPER_SERVER0 + "," + ZOOKEEPER_SERVER1 + "," + ZOOKEEPER_SERVER2;
    private static final int SESSION_TIMEOUT = 30000; // ms
    private ZooKeeper zkClient = null;
    // 制造阻塞
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 1.获取zk连接
        DistributeServer distributeServer = new DistributeServer();
        distributeServer.getConnection();

        // 2.注册服务器到zk集群
        distributeServer.register(); // 将当前服务器注册到ZooKeeper集群中

        // 3.执行相关的业务逻辑（这里就是让main程序睡一会儿，防止程序直接结束
        distributeServer.business();

    }

    private void business() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void register() throws UnknownHostException, KeeperException, InterruptedException {
        String serverIp = Inet4Address.getLocalHost().getHostAddress();
        // 所谓的服务器上线/注册就是在ZooKeeper节点下创建该服务器对应的节点信息
        // 这里的第一个参数path 是和后面第二个参数拼接起来执行的命令 create [xxx] path+内容
        String createRes = zkClient.create("/servers/" + serverIp,
                serverIp.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                // 创建的是临时的节点，服务器如果下线，对应的节点就从ZooKeeper中消失
                // 同时是带序号的节点，可以有多台同样的服务器
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(serverIp + "is onLine.");
    }

    private void getConnection() throws IOException {
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
}
