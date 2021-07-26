package com.wq.algorithm.lesson5bag9p;

import java.util.Arrays;

/**
 * 男人八题 - 多重背包
 */
public class MultiBagIII {
    /**
     * 1000 20000 20000
     */
    public static int multiBagIII(int[] weight, int[] value, int[] nLimit, int V){
        int[] dp = new int[V + 1];
        int[] pre;
        int[] deque = new int[V + 1];
        for(int i = 0; i < weight.length; i++){ // 遍历物品 复杂度 N
            pre = Arrays.copyOf(dp, dp.length);
            // 根据 第2层 第3层 的循环复杂度，可知 2 3 两层复杂度 ≈ weight[i] * （V - remainder）/ weight[i] = V
            for(int remainder = 0; remainder < weight[i]; remainder++){ // 复杂度 weight[i]
                // 对于 每件物品 的 每组同余数 的容积 都用单调队列进行维护
                int head = 0; // head == tail 代表有1个元素，head>tail代表队列为空，head<=tail代表队列不为空
                int tail = -1;
                // 遍历相同余数的 容积sameRemainderV，它们的余数都是 remainder
                // 复杂度 （V - remainder）/ weight[i]
                for(int sameRemainderV = remainder; sameRemainderV <= V; sameRemainderV += weight[i]){
                    // sameRemainderV - deque[head] > nLimit[i] * weight[i]
                    // 即 说明，队列中的元素超过了 nLimit[i] + 1个
                    // 队列中元素的个数应该为 s+1 个，即 取 0 个物品i，取 1 个物品i，取 nLimit[i] 个物品i
                    // ==== 0~nLimit[i] 共 nLimit[i] + 1个 状态
                    if(head <= tail && sameRemainderV - deque[head] > nLimit[i] * weight[i]){
                        head++; // 队首元素出列
                    }
                    /**
                     * dp[j]    =     dp[j]
                     * dp[j+weight[i]]  = max(dp[j], dp[j+weight[i]] - value[i]) + value[i]
                     * dp[j+2weight[i]] = max(dp[j], dp[j+weight[i]] - value[i], dp[j+2v] - 2value[i]) + 2value[i]
                     * ...
                     * dp[j+k*weight[i]] = max(dp[j], dp[j+weight[i]] - value[i], dp[j+2weight[i]] - 2value[i],...,
                     *                         dp[j+k*weight[i]] - k*value[i]) + k*value[i]
                     *
                     * 这里如队的是 max括起来的部分，所以是入队的元素是： dp[j+k*weight[i]] - kw
                     * pre[deque[tail]] - (deque[tail] - remainder) / weight[i] * value[i]
                     * pre[sameRemainderV] - (sameRemainderV - remainder) / weight[i] * value[i]
                     * 即 (deque[tail] - remainder) / weight[i] 或者 (sameRemainderV - remainder) / weight[i] 都等于k
                     *    deque[tail] 或者 sameRemainderV 都等于 j+k*weight[i]
                     */
                    while(head <= tail &&
                            pre[deque[tail]] - (deque[tail] - remainder) / weight[i] * value[i] <=
                                    pre[sameRemainderV] - (sameRemainderV - remainder) / weight[i] * value[i]){
                        tail--; // 将要入队的 容积 产生的价值 大于队尾容积产生的价值
                    }
                    deque[++tail] = sameRemainderV; // 这里 tail从-1开始，一定要先++再使用

                    if(head <= tail){                                     // deque[head] 即 历史价值最高时的容积
                        dp[sameRemainderV] = Math.max(dp[sameRemainderV],
                                pre[deque[head]] + (sameRemainderV - deque[head]) / weight[i] * value[i]);
                    }
                }
            }
        }
        return dp[V];
    }
}
