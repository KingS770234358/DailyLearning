package com.wq.algorithm.lesson3stock6p;

public class BuyInfinite {
    public static int buyInfinite(int[] prices){
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        for (int i = 1; i <= prices.length; i++){
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i-1]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i-1]);
        }
        return dp_i_0; // 一定是手上不持股票利润最大
    }
    public static int buyInfiniteByArray(int[] prices){
        int[][] dp = new int[prices.length + 1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        for(int i = 1; i <= prices.length; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i-1]);
        }
        return dp[prices.length][0]; // 一定是手上不持股票利润最大
    }
}
