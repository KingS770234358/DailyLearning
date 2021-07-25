#### 系统理解 
一共 n 类物品，背包的容量是 m，每类物品的体积为v, 价值为w，限制个数为s
①传统的dp方程：
dp[i][j] 表示将前 i 种物品放入容量为 j 的背包中所得到的最大价值
dp[i][j] = max(不放入物品 i，放入1个物品 i，放入2个物品 i, ... , 放入k个物品 i)  （这里 k 要满足：k <= s, j - k*v >= 0）
不放物品i = dp[i-1][j]
放1个物品i = dp[i-1][j - v] + w
...
放k个物品 i = dp[i-1][j - k*v] + k*w
综上：
dp[i][j] = max(dp[i-1][j], dp[i-1][j-v] + w, dp[i-1][j-2*v] + 2*w,..., dp[i-1][j-k*v] + k*w)

②重复利用dp数组来保存上一轮的信息：
令 dp[j] 表示容量为j的情况下，获得的最大价值，
则针对每一类物品 i ，都更新一下 dp[m] --> dp[0] 的值，最后 dp[m] 就是一个全局最优值
dp[m] = max(dp[m], dp[m-v] + w, dp[m-2*v] + 2*w, dp[m-3*v] + 3*w, ...)

③接下来，我们把 dp[0] --> dp[m] 写成下面这种形式，其中 0 <= j < weight[i]
dp[0], dp[v],   dp[2*v],   dp[3*v],   ... , dp[k*v]
dp[1], dp[v+1], dp[2*v+1], dp[3*v+1], ... , dp[k*v+1]
dp[2], dp[v+2], dp[2*v+2], dp[3*v+2], ... , dp[k*v+2]
...
###############################################################
# dp[j], dp[v+j], dp[2*v+j], dp[3*v+j], ... , dp[k*v+j]  
###############################################################
因此，可以把 dp 数组分成 j 个类，每一类中的值，都是在同类之间转换得到的
也就是说，dp[k*v+j] 只依赖于 { dp[j], dp[v+j], dp[2*v+j], dp[3*v+j], ... , dp[k*v+j] }
因为我们需要的是{ dp[j], dp[v+j], dp[2*v+j], dp[3*v+j], ... , dp[k*v+j] } 中的最大值，
可以通过维护一个单调队列来得到结果。这样的话，问题就变成了 j 个单调队列的问题
所以，针对上述最后一行可以得到
dp[j]    =     dp[j]
dp[j+v]  = max(dp[j] +  w,  dp[j+v])
dp[j+2v] = max(dp[j] + 2w,  dp[j+v] +  w, dp[j+2v])
dp[j+3v] = max(dp[j] + 3w,  dp[j+v] + 2w, dp[j+2v] + w, dp[j+3v])
...
但是，这个队列中前面的数，每次都会增加一个 w ，所以我们需要做一些转换，使队列中的每个数都变成固定的
dp[j]    =     dp[j]
dp[j+v]  = max(dp[j], dp[j+v] - w) + w
dp[j+2v] = max(dp[j], dp[j+v] - w, dp[j+2v] - 2w) + 2w
dp[j+3v] = max(dp[j], dp[j+v] - w, dp[j+2v] - 2w, dp[j+3v] - 3w) + 3w
dp[j+kv] = max(dp[j], dp[j+v] - w, dp[j+2v] - 2w,..., dp[j+kv] - kw) + kw
...
这样，每次入队的值是 dp[j+k*v] - k*w，队列中的值就会是固定的，不会以 “第几次“ 入队列而发生变化
④单调队列问题，最重要的两点
1）维护队列元素的个数，如果不能继续入队，弹出队头元素
  本题中，队列中元素的个数应该为 s+1 个，即 0 -- s 个物品 i
2）维护队列的单调性，即：尾值 >= dp[j + k*v] - k*w

#### 直观理解 - 男人八题中的背包问题理解难点：
① dp[j] 是由 所有与j mod 当前物品体积weight[i] 同余的 v 状态 dp[v] + k * value[i]转移而来， 即 dp[j] = max(dp[v] + k * value[i]);
② 对于 k 的限制：
   k是 dp[j]的最优解中包含 当前物品i的个数（可通过枚举来找到最优的k）
   对于每个不同的体积j，只要考虑它最多包含k个当前物品即可，即 体积考虑范围为 j ~ j - k * weight[i]
③ 这k个数形成 经典单调队列 滑动窗口问题
 当前第i件物品 体积3 价值5
 设dp[j] 最优解包含 k = 7 个当前物品

 dp[ 36 ] = Math.max(dp[36], dp[33] + 5, dp[30] + 2 * 5, dp[27] + 3 * 5, dp[24] + 4 * 5,
                     dp[21] + 5 * 5, dp[18] + 6 * 5, dp[15] + 7 * 5, == dp[12] + 8 * 5,
                     dp[9] + 9 * 5, dp[6] + 10 * 5, dp[3] + 11 * 5); ==
                    36 33 30 27 24 21 18 15 == 12 9  6  3 == 这些数 mod 当前物品体积 3 都是 = 0
 包含的 当前物品 个数   0  1  2  3  4  5  6  7 ==  8 9 10 11 ==
 == == 之间都是不需要考虑的部分
 这里到 dp[15] + 7 * 5终止

 dp[ 37 ] = Math.max( dp[37], dp[ 37 - 3 ] + 5) = Math.max( dp[37], dp[ 34 ] + 5)
          = Math.max( dp[37], Math.max(dp[34], dp[34 - 3] + 5) + 5) = Math.max( dp[37], Math.max(dp[34], dp[31] + 5) + 5)
          ....
 dp[ 37 ] = Math.max(dp[37], dp[34] + 5, dp[31] + 2 * 5, dp[28] + 3 * 5, dp[25] + 4 * 5,
                     dp[22] + 5 * 5, dp[19] + 6 * 5, dp[16] + 7 * 5,  == dp[13] + 8 * 5,
                     dp[10] + 9 * 5, dp[7] + 10 * 5, dp[4] + 11 * 5); ==
                   37 34 31 28 25 22 19 16  == 13 10  7  4 == 这些数 mod 当前物品体积 3 都是 = 1
 包含的 当前物品 个数 0   1  2  3  4  5  6  7  ==  8  9 10 11 ==
 == == 之间都是不需要考虑的部分
 这里到 dp[16] + 7 * 5终止

 dp[ 38 ] = Math.max(dp[38], dp[35] + 5, dp[32] + 2 * 5, dp[29] + 3 * 5, dp[26] + 4 * 5,
                     dp[23] + 5 * 5, dp[20] + 6 * 5, dp[17] + 7 * 5, == dp[14] + 8 * 5,
                     dp[11] + 9 * 5, dp[8] + 10 * 5, dp[5] + 11 * 5); ==
                    38 35 32 29 26 23 20 17 == 14 11  8  5 == 这些数 mod 当前物品体积 3 都是 = 2
 包含的 当前物品 个数  0   1  2  3  4  5  6  7 ==  8  9 10 11 ==
 == == 之间都是不需要考虑的部分
 这里到 dp[17] + 7 * 5终止

 dp[ 39 ] = Math.max(dp[39], dp[36] + 5, dp[33] + 2 * 5, dp[30] + 3 * 5, dp[27] + 4 * 5,
                     dp[24] + 5 * 5, dp[21] + 6 * 5, dp[18] + 7 * 5,  == dp[15] + 8 * 5,
                     dp[12] + 9 * 5, dp[9] + 10 * 5, dp[6] + 11 * 5, dp[3] + 12 * 5); ==
                    39 36 33 30 27 24 21 18 15 12  9  6  3
 包含的 当前物品 个数  0   1  2  3  4  5  6  7 ==  8  9 10 11 ==
 == == 之间都是不需要考虑的部分
 这里到 dp[18] + 7 * 5终止
 对比 dp[39] 和 dp[36] （都只取 7 个）
 dp[ 36 ] = Math.max(dp[36], dp[33] + 5, dp[30] + 2 * 5, dp[27] + 3 * 5, dp[24] + 4 * 5,
                     dp[21] + 5 * 5, dp[18] + 6 * 5, dp[15] + 7 * 5, == dp[12] + 8 * 5,
                     dp[9] + 9 * 5, dp[6] + 10 * 5, dp[3] + 11 * 5); ==
 dp[ 39 ] = Math.max(dp[39], dp[36] + 5, dp[33] + 2 * 5, dp[30] + 3 * 5, dp[27] + 4 * 5,
                     dp[24] + 5 * 5, dp[21] + 6 * 5, dp[18] + 7 * 5,  == dp[15] + 8 * 5,
                     dp[12] + 9 * 5, dp[9] + 10 * 5, dp[6] + 11 * 5, dp[3] + 12 * 5); ==
 dp[36] 到 dp[15] + 7 * 5终止
 dp[39] 到 dp[18] + 7 * 5终止
 除了窗口 进行了一个滑动之外（从dp[15]滑动到了dp[18]）
 窗口内的每个元素都加上了1个value[i], dp[36] -> dp[36] + 5; dp[33] + 5 -> dp[33] + 2 * 5 ...

 即：
 ① dp[j] = Math.max( dp[所有与j同余的体积] + k * value[i] )，状态dp[j]是从所有与j同余的 v 的状态 dp[v] + k * value[i]中取最大者转移而来
 ② 对于 k 的限制：
    k是 dp[j]的最优解中包含 当前物品i的个数（可通过枚举来找到最优的k）
    对于每个不同的体积j，只要考虑它最多包含k个当前物品即可，即 体积考虑范围为 j ~ j - k * weight[i]
