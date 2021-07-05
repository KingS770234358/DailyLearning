package com.wq.zookeeperlearning.distributelock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class CuratorDistributeLock {

    private static final String ZOOKEEPER_SERVER0 = "192.168.200.130:2181";
    private static final String ZOOKEEPER_SERVER1 = "192.168.200.128:2181";
    private static final String ZOOKEEPER_SERVER2 = "192.168.200.129:2181";
    // 连接至整个集群 下列链接字符串中的“逗号”前后不可以有空格，空格会被识别成字符串的一部分
    private static final String CONNECT_STR = ZOOKEEPER_SERVER0 + "," + ZOOKEEPER_SERVER1 + "," + ZOOKEEPER_SERVER2;
    // connection 超时时间
    private static final int CONNECT_TIMEOUT = 3000; // ms
    private static final int SESSION_TIMEOUT = 30000; // ms
    private static final int RETRY_INTERVAL = 3000; // ms
    private static final int RETRY_TIMES_MAX = 3; // ms
    private static final String LOCK_ROOT = "/locks";

    private ZooKeeper zkClient = null;
    // 制造阻塞
    private CountDownLatch connectionLatch = new CountDownLatch(1);

    // 当前节点名称
    private String currentNode = null;

    // 前一个节点的路径
    private String waitPath;
    private CountDownLatch lockLatch = new CountDownLatch(1);


    public static void main(String[] args) {

        // 创建分布式锁
        // new InterProcessMutex(CuratorFramework, LOCK_ROOT);
        // 第一个参数是包含 客户端与ZooKeeper的连接信息的 CuratorFramework对象
        // 第二个参数是 用于实现分布式锁的根目录

        /**
         *
         * 线程2 获取锁
         * 线程2 重入锁
         * 线程2 释放重入锁
         * 线程1 获取锁
         * 线程1 重入锁
         * 线程2 再次释放锁  // 这里线程2的打印位置 与预期不同，与CPU的调度有关
         * 线程1 释放重入锁
         * 线程1 再次释放锁
         */
        InterProcessLock lock1 = new InterProcessMutex(getCuratorFramework(), LOCK_ROOT);
        InterProcessLock lock2 = new InterProcessMutex(getCuratorFramework(), LOCK_ROOT);
        new Thread(new Runnable() {
            @Override public void run() {
                // 获取锁对象
                try {
                    lock1.acquire();
                    System.out.println("线程1 获取锁");
                    // 测试锁重入
                    lock1.acquire();
                    System.out.println("线程1 重入锁");
                    Thread.sleep(5 * 1000);
                    lock1.release();
                    System.out.println("线程1 释放重入锁");
                    lock1.release();
                    System.out.println("线程1 再次释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取锁对象
                try {
                    lock2.acquire();
                    System.out.println("线程2 获取锁");
                    // 测试锁重入
                    lock2.acquire();
                    System.out.println("线程2 重入锁");
                    Thread.sleep(5 * 1000);
                    lock2.release();
                    System.out.println("线程2 释放重入锁");
                    lock2.release();
                    System.out.println("线程2 再次释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    // 分布式锁初始化
    public static CuratorFramework getCuratorFramework (){
        //重试策略，初试时间 3 秒，重试3 次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(RETRY_INTERVAL, RETRY_TIMES_MAX);
        //通过工厂创建 Curator 获得与ZooKeeper的客户端连接
        CuratorFramework client = CuratorFrameworkFactory.builder() // builder开始配置
                                    .connectString(CONNECT_STR) // 连接集群的URLs
                                    .connectionTimeoutMs(CONNECT_TIMEOUT) // 连接超时时间
                                    .sessionTimeoutMs(SESSION_TIMEOUT)// 会话超时时间
                                    .retryPolicy(retryPolicy) // 重试策略
                                    .build(); // 正式的创建
        client.start(); //开启连接
        System.out.println("zookeeper 获取连接成功...");
        return client;
    }
}
