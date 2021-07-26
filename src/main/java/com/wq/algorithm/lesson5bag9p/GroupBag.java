package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 分组背包问题是 0-1背包问题 的一种特殊形式
 * 同时，多重背包问题 是 分组背包问题 的一种特殊性是
 * 多重背包问题 就是 组内物品都是相同的物品的 分组背包问题
 * 中心思想：
 * 第1层循环 枚举所有组
 * 第2层循环 枚举所有容积（0-1背包 从后往前更新）
 * 第3层循环 枚举所有决策，选择组内的某个物品（组内可以1个物品都不选取
 * 100 100 100
 */
public class GroupBag {
    private static class Item{
        private int weight;
        private int value;
        public Item(){
        }
        public Item(int weight, int value){
            this.weight = weight;
            this.value = value;
        }
        public int getWeight() {
            return weight;
        }
        public void setWeight(int weight) {
            this.weight = weight;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
    }
    public static int groupBag(List<List<Item>> groups, int V){
        int[] dp = new int[V + 1];
        for(int g = 0; g < groups.size(); g++){ // 遍历所有的组
            for(int v = V; v >= 0; v--){ // 组内有多个物品，不确定体积v下限
                for(int i = 0; i < groups.get(g).size(); i++){ // 遍历组内所有物品 取出组内决策最优的物品
                    if(v - groups.get(g).get(i).getWeight()>=0)
                        dp[v] = Math.max(dp[v], dp[v - groups.get(g).get(i).getWeight()] + groups.get(g).get(i).getValue());
                }
            }
        }
        return dp[V];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        int nGroup = Integer.valueOf(line.split(" ")[0]);
        int V = Integer.valueOf(line.split(" ")[1]);
        List<List<Item>> groups = new ArrayList<>();
        for(int g = 0; g < nGroup; g++){
            line = bufferedReader.readLine();
            int nItem = Integer.valueOf(line.split(" ")[0]);
            List<Item> items = new ArrayList<>();
            for(int i = 0; i < nItem; i++){
                line = bufferedReader.readLine();
                int itemWeight = Integer.valueOf(line.split(" ")[0]);
                int itemValue = Integer.valueOf(line.split(" ")[1]); // 这里是1不是0
                items.add(new Item(itemWeight, itemValue));
            }
            groups.add(items);
        }
        System.out.println(GroupBag.groupBag(groups, V));
    }
}
