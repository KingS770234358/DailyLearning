package com.wq.algorithm.lesson5bag9p;

public class ZeroOneBag {

    public static int zeroOneBag(int [] weight, int[] value, int V){
        int[] dp = new int[V + 10];
        dp[0] = 0;
        for(int i = 1; i <= weight.length; i++){
            for(int j = V; j >= weight[i-1]; j--){
                dp[j] = Math.max(dp[j], dp[j - weight[i-1]] + value[i-1]);
            }
        }
        return dp[V];
    }

    public static int zeroOneBagByArray(int[] weight, int[] value, int V){
        int[][] dp = new int[weight.length + 10][V + 10];
        dp[0][0] = 0;
        for(int i = 1; i <= weight.length; i++){
            for(int j = 0; j <= V; j++){
                if(j >= weight[i-1]){ // 装的下才考虑装不装
                    //                 不取第i-1件物品  取第i-1件物品
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight[i-1]] + value[i-1]);
                }else{ // 装不下直接不装第i-1件物品 只装前i-2个物品
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[weight.length][V];
    }
}
