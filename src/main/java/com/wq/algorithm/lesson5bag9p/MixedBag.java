package com.wq.algorithm.lesson5bag9p;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 混合背包问题 1000 1000 1000
 * 物品列表中有三种物品
 * 一种是0-1背包物品，即该物品只能 取 0 或 1 次
 * 一种是完全背包物品，即该物品可以 取 无限多 次
 * 一种是多重背包物品，即该物品可以 取 指定K 次
 * 中心思想：
 * 首先拆解 多重背包问题物品 为 0-1 背包物品
 * 根据当前的 物品类型使用不同的状态转移方程
 *
 */
public class MixedBag {
    private static class Item{
        private int type;
        private int weight, value;
        public Item(){
        }
        public Item(int type, int weight, int value){
            this.type = type;
            this.weight = weight;
            this.value = value;
        }
        public int getKind() {
            return type;
        }
        public void setKind(int kind) {
            this.type = kind;
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
    public static int mixedBag(int[] weight, int[]value, int[] typeSrc, int V){
        int[] type = Arrays.copyOf(typeSrc, typeSrc.length);
        int[] dp = new int[V + 1];
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < weight.length; i++){
            if(type[i] == -1){
                items.add(new Item(-1, weight[i], value[i]));
            }else if(type[i] != 0){
                for(int j = 1; j <= type[i]; j*=2){
                    items.add(new Item(-1, j * weight[i], j * value[i]));
                    type[i] -= j;
                }
                if(type[i]>0){
                    items.add(new Item(-1, type[i] * weight[i], type[i] * value[i]));
                }
            }else if(type[i] == 0){
                items.add(new Item(type[i], weight[i], value[i]));
            }
        }
        for(Item item : items){
            if(item.type == -1){
                for(int j = V; j >= item.weight; j--){
                    dp[j] = Math.max(dp[j], dp[j - item.getWeight()] + item.getValue());
                }
            }else{
                for(int j = item.weight; j <= V ; j++){
                    dp[j] = Math.max(dp[j], dp[j - item.getWeight()] + item.getValue());
                }
            }
        }
        return dp[V];
    }
}
