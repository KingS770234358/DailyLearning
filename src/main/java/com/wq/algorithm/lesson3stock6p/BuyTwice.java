package com.wq.algorithm.lesson3stock6p;

public class BuyTwice {
    public static int buyTwice(int[] prices){
        int dp_i_00 = 0;
        int dp_i_10 = 0;
        int dp_i_20 = 0;
        int dp_i_01 = Integer.MIN_VALUE;
        int dp_i_11 = Integer.MIN_VALUE;
        int dp_i_21 = Integer.MIN_VALUE;
        for(int i = 1; i <= prices.length; i++){
            dp_i_20 = Math.max(dp_i_20, dp_i_21 + prices[i-1]);
            dp_i_21 = Math.max(dp_i_21, dp_i_10 - prices[i-1]);
            dp_i_10 = Math.max(dp_i_10, dp_i_11 + prices[i-1]);
            dp_i_11 = Math.max(dp_i_11, dp_i_00 - prices[i-1]);
        }
        return dp_i_20;
    }
    public static int buyTwiceByArray(int[] prices){
        int[][][] dp = new int[prices.length + 1][3][2]; // 第1个2代表2次交易 第2个2代表 持股和不持股两种状态
        dp[0][0][0] = 0;
        dp[0][0][1] = Integer.MIN_VALUE;
        dp[0][1][0] = 0;
        dp[0][1][1] = Integer.MIN_VALUE;
        dp[0][2][0] = 0;
        dp[0][2][1] = Integer.MIN_VALUE;
        for(int i = 1; i <= prices.length; i++){
            for(int k = 2; k > 0; k--){ // dp[i][0][x] 购买0次 dp[i][1][x] 购买1次 dp[i][2][x] 购买2次
                dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i-1]);
                dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i-1]);
            }
        }
        return dp[prices.length][2][0];
    }
}
