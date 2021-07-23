package com.wq.algorithm.lesson3stock6p;

public class BuyInfiniteCold {

    public static int buyInfiniteCold(int[] prices){
        int dp_i_0_pre = 0;
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        for(int i = 2; i <= prices.length + 1; i++){
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i-2]);
            dp_i_1 = Math.max(dp_i_1, dp_i_0_pre - prices[i-2]);
            dp_i_0_pre = temp;
        }
        return dp_i_0; // 手上不持股 利润最大
    }
    public static int buyInfiniteColdByArray(int[] prices){
        int[][] dp = new int[prices.length + 2][2];
        // 再往前一天初始化       [7, 1, 5, 3, 6, 4]
        dp[0][0] = 0;   // 0  0
                        // -  -
        dp[0][1] = Integer.MIN_VALUE;
        dp[1][0] = 0;
        dp[1][1] = Integer.MIN_VALUE;
        for(int i = 2; i <= prices.length + 1; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i-2]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-2][0] - prices[i-2]);
        }
        return dp[prices.length + 1][0]; // 手上不持股 利润最大
    }
}
