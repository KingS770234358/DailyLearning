package com.wq.algorithm.lesson5bag9p;

import java.util.Arrays;

/**
 * 邻接表的实现
 * 以0为起点的边：0->1 〇
 * 以1为起点的边：1->4 ②, 1->2 ①
 * 以2为起点的边：2->4 ③
 * 以4为起点的边：4->0 ⑤, 4->3 ④
 * 总共有 6 条边 〇~⑤
 * 邻接表（链表实现 - 其中每个数字都是节点
 *  0  -> 1 -> null
 *  1  -> 4 -> 2 -> null
 *  2  -> 4 -> null
 *  3  -> null
 *  4  -> 0 -> 3 -> null
 * 邻接表（数组实现 - 把链表的连接关系 用edge[] 、 next[] 和 head[] 表达）
 * head
 *  0  ： 0〇1， -1                 edge[〇] = 1; next[〇] = -1 ; head[0] = 〇
 *  1  ： 1②4， 1①2， -1           edge[②] = 4; next[②] = ① | edge[①] = 2; next[①] = -1; head[1] = ②
 *  2  :  2③4  -1                 edge[③] = 4; next[③] = -1; head[2] = ③
 *  3  ： -1                       head[3] = -1
 *  4  ： 4⑤0， 4④3， -1          edge[⑤] = 0；next[⑤] = ④ | edge[④] = 4; next[④] = -1; head[4] = ⑤
 */
public class NeighborTable {
    private static int N = 1000;
    private static int nEdge = 0;
    private static int[] head = new int[N + 10]; // head[a] 记录 以 a 为起始点的 最后一条边的编号
    /**
     * next[e] 记录 编号为e的边 的下一条边的编号
     * 在邻接表中，起点相同的边 会以链表的形式串起来
     */
    private static int[] next = new int[N + 10];
    private static int[] edge = new int[N + 10]; // edge[e] 记录 编号为e的边 的目的节点编号

    // 添加边
    public static void add(int a, int b){
        edge[nEdge] = b;
        next[nEdge] = head[a];
        head[a] = nEdge++;
    }

    // 访问从src出发的所有边
    public static void print(int src){
        System.out.println(src + ":");
        for(int i = head[src]; i!=-1; i = next[i]){ // 遍历从src出发的所有边
            System.out.println("(" + src + "," + edge[i] + ")");
        }
    }

    public static void main(String[] args) {
        Arrays.fill(head, -1); // 一定要 初始化，否则遍历时 死循环
        add(0,1);
        add(1,2);
        add(1,4);
        add(2,4);
        add(4,0);
        add(4,3);
        for(int i = 0; i < 5; i++){
            print(i);
        }
    }
}
