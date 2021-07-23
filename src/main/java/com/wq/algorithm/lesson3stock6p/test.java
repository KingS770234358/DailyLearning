package com.wq.algorithm.lesson3stock6p;

public class test {
    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("==== 买卖股票的最佳时机 只购买1次 ===="); // 5
        System.out.println(BuyOnce.buyOnceByArray(prices));
        System.out.println(BuyOnce.buyOnce(prices));
        System.out.println("==== 买卖股票的最佳时机 不限购买次数 ===="); // 7
        System.out.println(BuyInfinite.buyInfiniteByArray(prices));
        System.out.println(BuyInfinite.buyInfinite(prices));
        System.out.println("==== 买卖股票的最佳时机 不限购买次数 + 冻结时间 ===="); // 5
        System.out.println(BuyInfiniteCold.buyInfiniteColdByArray(prices));
        System.out.println(BuyInfiniteCold.buyInfiniteCold(prices));
        System.out.println("==== 买卖股票的最佳时机 不限购买次数 + 手续费 2  ===="); // 3
        System.out.println(BuyInfiniteFee.buyInfiniteFeeByArray(prices, 2));
        System.out.println(BuyInfiniteFee.buyInfiniteFee(prices, 2));
        System.out.println("==== 买卖股票的最佳时机 限制购买2次  ===="); // 7
        System.out.println(BuyTwice.buyTwiceByArray(prices));
        System.out.println(BuyTwice.buyTwice(prices));
        System.out.println("==== 买卖股票的最佳时机 限制购买kTime次  ===="); // 3次 7
        System.out.println(BuyKTime.buyKTime(prices, 3));
    }
}
