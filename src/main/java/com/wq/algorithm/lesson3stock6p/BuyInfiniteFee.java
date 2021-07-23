package com.wq.algorithm.lesson3stock6p;

public class BuyInfiniteFee {
    public static int buyInfiniteFee(int[] prices, int fee){
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        for (int i = 1; i <= prices.length; i++){
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i-1]);
            dp_i_1 = Math.max(dp_i_1, dp_i_0 - prices[i-1] - fee);
        }
        return dp_i_0;
    }
    public static int buyInfiniteFeeByArray(int[] prices, int fee){
        int[][]dp = new int[prices.length + 1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        for(int i = 1; i <= prices.length; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i-1] - fee); // 买入需要缴纳手续费
        }
        return dp[prices.length][0]; // 手上不持股 利润最大
    }
}
