package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 0-1背包 具体取物品的方案，
 * 物品方案满足：
 *    最优解中所选物品的编号序列，且该编号序列的字典序最小。
 */
public class ZeroOneBagSchema {
    public static void zeroOneBagSchema(int[] volumes, int[] values, int V){
        // ① 这里要追溯最优状态是从以前的哪种状态转移来的 所以需要保留以前的状态记录 需要二维 不能压缩
        int[][] dp = new int[volumes.length + 10][V + 10];

        /**
         * ② 方案的输出必须满足最小字典序，因此 在最后遍历的时候，最好是从头开始遍历
         *   但是 只能从 最优的状态 也就是最后一个物品开始追溯 最优状态的来源
         *   因此这里 把物品的顺序进行颠倒，后面的物品的状态先计算，这样追溯的时候从最后1个物品（实际上是第1个物品）开始追溯
         *   dp[i][j]的含义也发生一些变化，表示从第i件及其之后的物品 在 容积为 j的条件下 的最大价值
         *
         *   ⑥ 从后往前取，只是从后往前遍历而已，原物品的顺序并没有打乱！！！
         */
        for(int i = volumes.length - 1; i >= 0; i--){
            for(int v = 0; v <= V; v++){ // ③ 这里是最最一般的 0-1 背包写法，不是压缩空间的写法 就是正向的
                dp[i][v] = dp[i+1][v]; // ④ 靠后的物品是先算的 所以这里是 dp[i+1][v] ==== 不取第i件物品
                if(v >= volumes[i]){
                    dp[i][v] = Math.max(dp[i][v], dp[i+1][v - volumes[i]] + values[i]); // 取第i件物品
                }
            }
        }
        // ⑥ 这样dp[0][V]变成最终的答案
        int volume = V;
        // ⑥ 从前往后追溯 原物品的顺序并没有打乱！！！
        for(int i = 0; i < volumes.length; i++){ // ⑤ 这里遍历的是物品的序号
            // ⑥ 结合第22行代码  和  判断语句，是从最后1个物品（实际上是第1个物品）开始追溯
            //                这里是>=，等于也是可以装的下的！！！
            if(volume - volumes[i] >= 0 && dp[i][volume] == dp[i+1][volume-volumes[i]] + values[i]){
                // ⑦ 能取一定要取，保证字典序最小
                System.out.print(i + 1 + " ");
                volume -= volumes[i];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        int nItem = Integer.valueOf(line.split(" ")[0]);
        int V = Integer.valueOf(line.split(" ")[1]);
        int[] volumes = new int[nItem];
        int[] values = new int[nItem];
        for(int i = 0; i < nItem; i++){
            line = bufferedReader.readLine();
            volumes[i] = Integer.valueOf(line.split(" ")[0]);
            values[i] = Integer.valueOf(line.split(" ")[1]);
        }
        zeroOneBagSchema(volumes, values, V);
    }
}
