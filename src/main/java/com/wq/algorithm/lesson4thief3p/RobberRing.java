package com.wq.algorithm.lesson4thief3p;

import java.util.Arrays;

public class RobberRing {
    public static int robberRing(int[] houses){
        // Arrays.copyOfRange( arr, start, end + 1); 从下标start处复制到下标end处
        return Math.max(Robber.robber(Arrays.copyOfRange(houses, 0, houses.length-1)),
                Robber.robber(Arrays.copyOfRange(houses, 1, houses.length)));
    }
}
