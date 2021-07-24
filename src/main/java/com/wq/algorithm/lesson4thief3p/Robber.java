package com.wq.algorithm.lesson4thief3p;

public class Robber {
    public static int robber(int[] houses){
        int pre = 0;
        int cur = 0;
        int temp;
        for(int i = 0; i< houses.length; i++){
            // int temp = cur; // 把这个挪出去成一个全局变量 省内存
            temp = cur;
            cur = Math.max(cur, pre + houses[i]);
            pre = temp;
        }
        return cur;
    }
    public static int robberByArray(int[] houses){
        int[] dp = new int[houses.length + 2];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i < houses.length + 2; i++){
            //              不偷当前家, 偷当前家
            dp[i] = Math.max(dp[i-1], dp[i-2] + houses[i-2]);
        }
        return dp[houses.length + 1];
    }
}
