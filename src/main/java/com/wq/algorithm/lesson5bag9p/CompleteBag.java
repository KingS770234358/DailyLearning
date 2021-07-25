package com.wq.algorithm.lesson5bag9p;

/**
 *
 * dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
 * 数学归纳法
 * 1. 假设考虑i-1个物品之后，所以的dp[j] 都是正确的
 * 2. 证明：考虑第i个物品后，所有的dp[j] 也都是正确的
 * 对于某个j而言，最优解包含 k 个 weight[i]
 * dp[j - k * weight[i]] 包含0个weight[i]
 *
 * 更大的容积
 * dp[j - (k - 1) * weight[i]] = Math.max(dp[j - (k - 1) * weight[i]], dp[j - (k - 1) * weight[i] - weight[i]] + value[i]);
 *                             = dp[j - (k - 1) * weight[i] - weight[i]] + value[i]
 *                             = dp[j - k * weight[i]] + value[i] // 更小的容积 包含1个weight[i]
 * ...
 * dp[j] = dp[j - weight[i]] + value[i] // 包含k个weight[i]
 * ****************************************************************************************************
 * 完全背包两层循环，外层循环遍历物品，如当 i = 1遍历到第1个物品（设weight=3， value=4）的时候（此时dp[0~V] = 0）
 * 此时内层循环j遍历所有装的下物品i=1的体积（3~V），j=3是装了1件第1个物品（剩余空间0），j=4是装了1件第1个物品（剩余空间1），
 * j=5是装了1件第1个物品（剩余空间2），直到j=6时，变成装了2件第1个物品（剩余空间0）.....，
 * 以此类推，逢3的倍数的时候，装入的第1个物品的数量就/才 +1。
 * 相当于：外层循环i=1，内层循环就是一直在往所有容积（j：3~V）的背包里填充第i=1个物品，尽可能把容积使用完。
 * 外层循环遍历到后续的物品的时候，就会尽可能的把之前空着的容积都填满，或者用总价值更高的物品替换掉原来占用容积的物品。
 * 这也就是 d[j] = max(d[j], d[j - weight[i]] + value[i])做的事情
 * ****************************************************************************************************
 * 0-1背包 和 完全背包之间的联系：
 * 不管是0-1背包还是完全背包，状态转移方程 d[j] = max(d[j], d[j - weight[i]] + value[i]) 都是
 * 用 小的体积的状态 更新 大的体积的状态。
 * 0-1背包，内层是到这遍历体积（体积从大到小），所以对于同一个物品，添加该物品产生的效果是不会累积到 大的体积的状态 中的，即对于从大到小遍历
 * 到的所有体积，该物品（产生的添加效果）只有1个。
 * 完全背包，内层正着遍历体积（体积从小到大），所以对于同一个物品，添加该物品产生的效果是会累积到 大的体积的状态 中的，即对于从小到大遍历
 * 到的所有提及，该物品（产生的添加效果）是不断累积增加的。
 *
 */
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
    // 背包最朴素写法
    public static int completeBagByArray(int[] weight, int[] value, int V){
        int[] dp = new int[V + 10];
        for(int i = 1; i <= weight.length; i++){
            for(int j = 0; j <= V; j++){
                for(int k = 0; k * weight[i] <= j ; k++){
                    dp[j] = Math.max(dp[j], dp[j - k * weight[i-1]] + k * value[i-1]);
                }
            }
        }
        return 0;
    }
}
