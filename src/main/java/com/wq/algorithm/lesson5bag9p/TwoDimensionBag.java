package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 二维费用的背包问题： 每件物品只能用一次
 * 1000 100 100 1000
 * ①两个限制条件，限制背包的 最大容量V 和 最大承重W
 * ②每个物品只能取 0 或 1 次
 */
public class TwoDimensionBag {
    public static int twoDimensionBag(int[] volumes, int[] weigths, int[] values, int V, int W){
        int[][] dp = new int[V + 1][W + 1];
        for(int i = 0; i < volumes.length; i++){ // 遍历所有物品
            for(int v = V; v >= volumes[i]; v--){ // 0-1 背包 倒序更新状态 - 体积维度
                for(int w = W; w >= weigths[i]; w--){ // 0-1 背包 倒序更新状态 - 重量维度
                    dp[v][w] = Math.max(dp[v][w], dp[v - volumes[i]][w - weigths[i]] + values[i]);
                }
            }
        }
        return dp[V][W];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        int n = Integer.valueOf(line.split(" ")[0]); // 4 5 6
        int V = Integer.valueOf(line.split(" ")[1]);
        int W = Integer.valueOf(line.split(" ")[2]);

        int[] volumes = new int[n];
        int[] weights = new int[n];
        int[] values = new int[n];

        for (int i = 0; i < n; i++){
            line = input.readLine();
            volumes[i] = Integer.valueOf(line.split(" ")[0]);
            weights[i] = Integer.valueOf(line.split(" ")[1]);
            values[i] = Integer.valueOf(line.split(" ")[2]);
        }

        System.out.println(twoDimensionBag(volumes, weights, values, V, W));
    }
}
