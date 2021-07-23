package com.wq.algorithm.lesson3stock6p;

public class BuyKTime {
    public static int buyKTime(int[] prices, int kTimes){
        if(kTimes > prices.length / 2) {
            return BuyInfinite.buyInfinite(prices);
        }
        int[][][] dp = new int[prices.length + 1][kTimes + 1][2];
        for(int i = 0; i < prices.length; i++){
            for(int k = kTimes; k > 0; k--){ // 这里是从后往前更新！！！参考背包问题
                if(i == 0){
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i]; // 第1天的股
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i]);
            }
        }
        return dp[prices.length - 1][kTimes][0];
    }
}
