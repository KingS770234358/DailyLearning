package com.wq.algorithm.lesson5bag9p;

import net.sf.saxon.expr.Component;
import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BagTest {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        int n = Integer.valueOf(line.split(" ")[0]); // 4 5
        int[] weight = new int[n + 10];
        int[] value = new int[n + 10];
        int[] nLimit = new int[n + 10];
        int V = Integer.valueOf(line.split(" ")[1]);
        // 1 2 | 2 4 | 3 4 | 4 5
        for (int i = 0; i < n; i++){
            line = input.readLine();
            weight[i] = Integer.valueOf(line.split(" ")[0]);
            value[i] = Integer.valueOf(line.split(" ")[1]);
        }
        System.out.println("==== 0-1背包问题 - 2D数组 ====");
        System.out.println(ZeroOneBag.zeroOneBagByArray(weight, value, V));
        System.out.println("==== 0-1背包问题 - 1D数组 ====");
        System.out.println(ZeroOneBag.zeroOneBag(weight, value, V));
        System.out.println("==== 完全背包问题 ====");
        System.out.println(CompleteBag.completeBag(weight, value, V));
        line = input.readLine();
        n = Integer.valueOf(line.split(" ")[0]); // 4 5
        V = Integer.valueOf(line.split(" ")[1]);
        Arrays.fill(weight, 0);
        Arrays.fill(value, 0);
        for (int i = 0; i < n; i++){
            line = input.readLine();
            weight[i] = Integer.valueOf(line.split(" ")[0]);
            value[i] = Integer.valueOf(line.split(" ")[1]);
            nLimit[i] = Integer.valueOf(line.split(" ")[2]);
        }
        System.out.println("==== 多重背包问题 ====");
        System.out.println(MultiBag.multiBag(weight, value, nLimit, V));
        System.out.println(MultiBag.multiBagII(weight, value, nLimit, V));
        System.out.println(MultiBagIII.multiBagIII(weight, value, nLimit, V));
    }
}
