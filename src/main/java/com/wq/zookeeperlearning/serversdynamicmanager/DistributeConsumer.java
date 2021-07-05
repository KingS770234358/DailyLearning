package com.wq.zookeeperlearning.serversdynamicmanager;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeConsumer {

    private static final String ZOOKEEPER_SERVER0 = "192.168.200.130:2181";
    private static final String ZOOKEEPER_SERVER1 = "192.168.200.128:2181";
    private static final String ZOOKEEPER_SERVER2 = "192.168.200.129:2181";
    // 连接至整个集群 下列链接字符串中的“逗号”前后不可以有空格，空格会被识别成字符串的一部分
    private static final String CONNECT_STR = ZOOKEEPER_SERVER0 + "," + ZOOKEEPER_SERVER1 + "," + ZOOKEEPER_SERVER2;
    private static final int SESSION_TIMEOUT = 30000; // ms
    private ZooKeeper zkClient = null;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        // 1.获取zk连接
        DistributeConsumer consumer = new DistributeConsumer();
        consumer.getConnection();

        // 2.监听 /servers 下面子节点的增加和删除
        consumer.getServerList();

        // 3.业务逻辑
        consumer.business();
    }

    private void business() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getServerList() throws KeeperException, InterruptedException {
        /**
         * 这里第二个参数如果是true，则会走 getConnection方法中创建ZooKeeper客户端时定义的Watcher
         * 如果是new Watcher, 那么监听到节点下的变化，就会走这边new出来的这个Watcher
         */
        List<String> children = zkClient.getChildren("/servers", true);
        List<String> servers = new ArrayList<>();
        for (String child : children){
            // false ==> 不监听
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }
        // 打印服务器列表
        System.out.println(servers);
    }

    private void getConnection() throws IOException {
        zkClient = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            // 收到事件后的回调函数（用户的业务逻辑）
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("消费者监听到ZooKeeper上的节点变化");
                try {
                    getServerList(); // 保证递归监控服务器的状态
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
