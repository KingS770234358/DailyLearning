package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 统计 产生价值最大 的方案数 （0-1背包情形）
 * dp[v] 体积恰好为v时， 产生的最大价值
 * 注意： 与之前不同 之前dp[v] 为 体积最多为v是，产生的最大价值
 *       初始化不一样，这里的dp[0] = 0; dp[1~V] = -INF
 * schema[v] 体积恰好为v时，产生最大价值的方案数
 *
 */
public class ZeroOneBagSchemaNum {

    private static int MOD = 1000000007;
    public static int zeroOneBagSchemaNum(int[] volumes, int[] values, int V){
        int[] dp = new int[V + 1];
        for(int i=1;i<=V;i++) // ① 除了dp[0]，其他dp[i]初始化为 -INF，恰好 vs 最多 的区别
            dp[i] = Integer.MIN_VALUE;
        int[] schema = new int[V + 1];
        schema[0] = 1; // ② 体积为0时，产生的最优解的方案有1种，其他体积都是初始化为0
        // 一、统计每个体积下产生最优解的方案数
        for(int i = 0; i < volumes.length; i++){
            for(int j = V; j >= volumes[i]; j--){ // ③ 0-1背包 倒序更新
                int temp = Math.max(dp[j], dp[j - volumes[i]] + values[i]);
                int s = 0;
                // ④ 最优方案来自dp[j]，则统计 schema[j]
                if(temp == dp[j]){
                    s += schema[j];
                }
                // ④ 最优方案来自dp[j - volumes[i]] + values[i]，则统计 schema[j - volumes[i]]
                if(temp == dp[j - volumes[i]] + values[i]){
                    s += schema[j - volumes[i]];
                }
                if(s >= MOD) s-=MOD;
                dp[j] = temp;
                schema[j] = s;
            }
        }
        // 二、确定最优解（价值最大是多少）
        int maxValue = 0;
        for (int i = 0; i <=V; i++) {
            maxValue = Math.max(maxValue, dp[i]);
        }
        // 三、搜集所有产生最优解的体积所能产生的方案数
        int res = 0;
        for(int i = 0; i <= V; i++){
            if(dp[i] == maxValue){
                res += schema[i];
                if(res >= MOD) res -= MOD;
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        int nItem = Integer.valueOf(line.split(" ")[0]);
        int V = Integer.valueOf(line.split(" ")[1]);
        int[] volumes = new int[nItem]; // ⑤ 这里不能有 nItem + 10， 多出来的10会被遍历到
        int[] values = new int[nItem];
        for(int i = 0; i < nItem; i++){
            line = bufferedReader.readLine();
            volumes[i] = Integer.valueOf(line.split(" ")[0]);
            values[i] = Integer.valueOf(line.split(" ")[1]);
        }
        System.out.println(zeroOneBagSchemaNum(volumes, values, V));
    }
}
