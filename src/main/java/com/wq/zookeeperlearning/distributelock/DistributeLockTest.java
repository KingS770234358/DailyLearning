package com.wq.zookeeperlearning.distributelock;

import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class DistributeLockTest {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        /**
         * 深入思考这里为什么是 2个 DistributeLock
         * ZooKeeper实现分布式锁，锁资源 从始至终 只有1个，就是ZooKeeper节点上存储 序号最小的 节点
         * 这里2个 DistributeLock 分别存储了2个ZooKeeper的节点，哪个节点的值小 哪个就抢到锁
         * 所以就是 有多少个线程参与竞争，就需要 多少个 DistributeLock 封装 在ZooKeeper上创建的节点
         * 要与 ReentrantLock等锁 区分开来
         */
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                DistributeLock distributeLock1 = new DistributeLock();
                distributeLock1.zkLock(); // 线程1 获得锁
                System.out.println("线程1 启动，获取到锁");
                Thread.sleep(5 * 1000); // 获取到锁后睡眠 模拟业务逻辑的处理
                distributeLock1.zkUnlock(); // 线程1执行完业务逻辑 释放锁
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                DistributeLock distributeLock2 = new DistributeLock();
                distributeLock2.zkLock(); // 线程1 获得锁
                System.out.println("线程2 启动，获取到锁");
                Thread.sleep(5 * 1000); // 获取到锁后睡眠 模拟业务逻辑的处理
                distributeLock2.zkUnlock(); // 线程1执行完业务逻辑 释放锁
            }
        }).start();

    }
}
