package com.wq.algorithm.lesson5bag9p;

public class CompleteBag {
    public static int completeBag(int[] weight, int[] value, int V){
        int[] dp = new int[V + 10];
        for(int i = 1; i <= weight.length; i++){
            for(int j = weight[i-1]; j <= V; j++){
                dp[j] = Math.max(dp[j], dp[j - weight[i-1]] + value[i - 1]);
            }
        }
        return dp[V];
    }
//    public static int completeBagByArray(int[] weight, int[] value, int V){
//        int[][] dp = new int[weight.length + 10][V + 10];
//        for(int i = 1; i <= weight.length; i++){
//            for(int j = 0; j <= )
//        }
//    }
}
