package com.wq.algorithm.lesson1sort;

import java.util.Arrays;

/**
 * 对数器
 * 排序算法的对数器
 */
public class Matcher {

    public static void main(String[] args) {
        int testTime = 500000; // 测试次数
        int size = 10; // 每次测试数组的大小
        int value = 100;
        boolean success = true;
        for(int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(size, value);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            // BubbleSort.bubbleSort(arr1); // 正确性待验证的算法
            // SelectionSort.selectionSort(arr1);
            InsertSort.insertSort(arr1);
            rightMethod(arr2);// 绝对正确的算法
            if(!isEqual(arr1, arr2)){
                success = false;
                printArray(arr);
                break;
            }
        }
        System.out.println(success ? "Nice!" : "No...");
        int[] arr = generateRandomArray(size, value);
        printArray(arr);
        InsertSort.insertSort(arr);
        printArray(arr);
    }


    // 产生随机样本
    public static int[] generateRandomArray(int size, int value){
        // 1个长度随机的数组 (int)的强制转换，长度范围：[0, size]
        int[] arr = new int [ (int) ( (size + 1) * Math.random())];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)((value + 1) * Math.random()) - (int)( value * Math.random());
        }
        return arr;
    }

    // 绝对正确的方法 复杂度高 但是保证正确性
    // 需要逐渐调试该方法 直至完全正确
    public static void rightMethod(int[] arr){
        Arrays.sort(arr);
    }

    // 数组拷贝
    public static int[] copyArray(int[] arr){
        if(arr == null){
            return null;
        }
        int[] res = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            res[i] = arr[i];
        }
        return res;
    }

    // 判断两个数组是否相等
    public static boolean isEqual(int[] arr1, int[] arr2){
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)){
            return false;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        if(arr1 == null && arr2 ==null){
            return true;
        }
        for(int i = 0; i < arr1.length; i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    
    public static void printArray(int[] arr){
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
