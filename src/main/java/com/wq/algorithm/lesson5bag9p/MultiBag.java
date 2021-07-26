package com.wq.algorithm.lesson5bag9p;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiBag {

    /**
     * 1000 2000 2000
     */
    // Item对象， weight，value，无参构造函数 全参构造函数 getter setter
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
    public static int multiBagII(int[] weight, int[] value, int[] nLimitSrc, int V){
        // 这里尽量不要导致 nLimit数组变化，影响后面的测试
        int[] nLimit = Arrays.copyOf(nLimitSrc, nLimitSrc.length);
        List<Item> items = new ArrayList<>();
        // 将nLimit[i]拆解成多个物品
        for (int i = 0; i < weight.length; i++){
            for(int j = 1; j <= nLimit[i]; j*=2){
                nLimit[i] -= j;
                items.add(new Item(j * weight[i], j * value[i])); // 1 2 4 8 16 ... 组成 1~nLimit[i];
            }
            if(nLimit[i] > 0){ // 剩余 不足 2^u 的个数
                items.add(new Item(nLimit[i] * weight[i], nLimit[i] * value[i]));
            }
        }
        int[] dp = new int[V + 1]; // 这里是 V + 10， 下标是体积！！！
        for(int i = 0; i < items.size(); i++){
            for(int j = V; j >= items.get(i).getWeight(); j--){ // 转换成0-1背包问题
                dp[j] = Math.max(dp[j], dp[j - items.get(i).getWeight()] + items.get(i).getValue());
            }
        }
        return dp[V];
    }

    /**
     * 100 * 100 * 100
     */
    public static int multiBag(int[] weight, int[] value, int[] nLimit, int V){
        int[] dp = new int[V + 1]; // 这里是 V + 10 （下标是体积）
        for(int i = 0; i < weight.length; i++){
            for(int j = V; j >= weight[i]; j--){
                for(int k = 1; k <= nLimit[i] && k * weight[i] <= j; k++){ // 遍历取的个数
                    dp[j] = Math.max(dp[j], dp[j - k * weight[i]] + k * value[i]); // 这里要 + k * value[i]
                }
            }
        }
        return dp[V];
    }

}
