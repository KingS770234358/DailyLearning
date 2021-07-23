package com.wq.algorithm.lesson1sort;

/**
 * 选择排序
 * 当原数组有序： 1 2 3 4 5
 * 复杂度 O(n^2)
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = new int[] {5, 9, 3, 7, 11, 0, 6};
        selectionSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void selectionSort(int[] arr){
        if(arr == null || arr.length < 2){
            return ;
        }
        for(int i = 0; i < arr.length - 1; i++){
            int minIndex = i; // i 当前轮最小值的最终放置位置
            for(int j = i + 1; j < arr.length; j++){
                minIndex = arr[j] < arr[minIndex] ? j : minIndex; // minIndex 记录当前轮最小数的下标
            }
            swap(arr, i, minIndex);
        }
    }
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
