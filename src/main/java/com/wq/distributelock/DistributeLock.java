package com.wq.distributelock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper实现分布式锁
 * 临时带序号节点 序号值最小的获取到锁
 */
public class DistributeLock {

    private static final String ZOOKEEPER_SERVER0 = "192.168.200.130:2181";
    private static final String ZOOKEEPER_SERVER1 = "192.168.200.128:2181";
    private static final String ZOOKEEPER_SERVER2 = "192.168.200.129:2181";
    // 连接至整个集群 下列链接字符串中的“逗号”前后不可以有空格，空格会被识别成字符串的一部分
    private static final String CONNECT_STR = ZOOKEEPER_SERVER0 + "," + ZOOKEEPER_SERVER1 + "," + ZOOKEEPER_SERVER2;
    private static final int SESSION_TIMEOUT = 30000; // ms
    private ZooKeeper zkClient = null;
    // 制造阻塞
    private CountDownLatch connectionLatch = new CountDownLatch(1);

    // 当前节点名称
    private String currentNode = null;

    // 前一个节点的路径
    private String waitPath;
    private CountDownLatch lockLatch = new CountDownLatch(1);

    /**
     * 初始化连接，判断锁根节点/locks是否存在，不存在则创建
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public DistributeLock() throws IOException, KeeperException, InterruptedException { // 构造函数中获取连接
        // 获取连接（这里是开启一个线程去建立连接
        zkClient = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            // 这个是连接建立成功之后调用的回调函数
            // 在获取节点getChildren、获取节点信息getData的使用可以设置watcher=true 来调用这个回调函数
            @Override
            public void process(WatchedEvent event) {
                // 与zk连接成功之后 释放锁，程序继续执行
                if(event.getState() == Event.KeeperState.SyncConnected){ // 连接建立成功的状态
                    connectionLatch.countDown(); // 释放连接时加的锁 程序继续执行
                }
                // 监听 前一个节点删除后 lockLatch需要释放
                // 监听到了节点删除事件  且  事件发生的路径等于当前线程等待的前一个节点路径waitPath
                if(event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)){
                    lockLatch.countDown(); // 释放等待前一个节点被删除的锁
                }
            }
        });
        // 等待上面zk正常连接在之后才往下走
        connectionLatch.await();

        // 判断根节点 /locks 是否存在
        Stat stat = zkClient.exists("/locks", false); // 不用监听回调 false
        if(stat == null){ // 不存在则创建
            zkClient.create("/locks",
                    "locks".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT); // 根节点，要是永久的
        }
    }

    /**
     * 对zk加锁
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void zkLock() throws KeeperException, InterruptedException {
        // 创建对应的临时带序号节点 这里返回了创建节点的结果
        currentNode = zkClient.create("/locks/" + "seq-",
                null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL); // 必须得是 临时带序号节点
        // 获得所有子节点的名称
        List<String> children = zkClient.getChildren("/locks", false);
        System.out.println("当前" + Thread.currentThread().getName() + "节点数：" + children.size());
        // 判断创建的节点的序号是否是最小的
        if(children.size()==1){  // 如果children 只有一个值，那就直接获取锁，
            return ;
        }else{
            Collections.sort(children); // 对子节点进行排序
            // 获得当前node的名称 seq-00000000
            String thisNode = currentNode.substring("/locks/".length()); // 截掉 开头的 /locks/
            System.out.println("当前" + Thread.currentThread().getName() + "节点名称：" + thisNode);
            System.out.println("当前" + Thread.currentThread().getName() + "等待队列：" + children);
            int myIndex = children.indexOf(thisNode); // 获取当前节点在排序后数组中的位置
            System.out.println("当前节点值序号：" + myIndex);
            // 如果有多个节点，需要判断，当前节点是不是最小
            // 这里不采用直接跟排序后数组第一个元素对比是否相等的方式，因为存在不确定性
            if(myIndex == -1){ // 下标等于-1说明数据有问题
                System.out.println("数据异常");
            }
            if(myIndex == 0){// 如果是，则获取到锁
                return ;
            }else{ // 如果不是，则需要监听前一个序号比自己小的节点
                waitPath = "/locks/" + children.get(myIndex - 1); // 获得前一个节点的名称
                // true 表示使用创建ZooKeeper时的Watcher
                zkClient.getData(waitPath, true, new Stat()); // watcher就是获取节点状态发生变化时的回调函数
                // 只有当监听结束了才能获得锁
                lockLatch.await(); // 等待节点状态发生改变 调用回调函数中释放锁
                // 获得锁之后处理业务 然后return
                return ;
            }
        }
    }

    /**
     * 对zk解锁
     */
    public void zkUnlock(){
        System.out.println("释放锁...");
        // 删除节点即可
        try {
            zkClient.delete(currentNode, -1); // 第二个参数为版本，-1 1都行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
