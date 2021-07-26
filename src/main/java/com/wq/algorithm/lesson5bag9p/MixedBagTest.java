package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MixedBagTest {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        int n = Integer.valueOf(line.split(" ")[0]); // 4 5
        int V = Integer.valueOf(line.split(" ")[1]);

        int[] weight = new int[n];
        int[] value = new int[n];
        int[] type = new int[n];

        for (int i = 0; i < n; i++){
            line = input.readLine();
            weight[i] = Integer.valueOf(line.split(" ")[0]);
            value[i] = Integer.valueOf(line.split(" ")[1]);
            type[i] = Integer.valueOf(line.split(" ")[2]);
        }

        System.out.println(MixedBag.mixedBag(weight, value, type, V)); // 8

    }
}
