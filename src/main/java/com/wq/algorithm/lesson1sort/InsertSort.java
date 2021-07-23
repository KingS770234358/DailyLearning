package com.wq.algorithm.lesson1sort;

/**
 * 插入排序
 * 当原数组有序： 1 2 3 4 5
 * 复杂度 O(n)
 * 当原数组有序： 5 4 3 2 1
 * 复杂度 O(n^2)
 * ===>与数组中数据的状况有关
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = new int[] {5, 9, 3, 7, 11, 0, 6};
        insertSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void insertSort(int[] arr){
        if(arr == null || arr.length < 2){
            return ;
        }
        for(int end = 1; end < arr.length; end++){
            // 前面部分是已经排好序的 出现不满足条件的直接停止内层循环了
            for(int i = end-1; i>=0 && arr[i] > arr[i+1]; i--){
                swap(arr, i, i+1);
            }
        }
    }
    public static void swap(int[]arr, int i, int j){
        arr[i] = arr[i] ^ arr[j]; // C = A ^ B
        arr[j] = arr[i] ^ arr[j]; // B = A ^ B ^ B = A
        arr[i] = arr[i] ^ arr[j]; // A = A ^ B ^ A = B
    }
}
