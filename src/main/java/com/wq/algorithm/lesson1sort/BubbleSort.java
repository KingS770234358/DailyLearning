package com.wq.algorithm.lesson1sort;

/**
 * 冒泡排序
 * 当原数组有序： 1 2 3 4 5
 * 复杂度 O(n^2)
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[] {5, 9, 3, 7, 11, 0, 6};
        bubbleSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void bubbleSort(int[] arr){
        if(arr == null || arr.length < 2){
            return ;
        }
        // end 从数组最末尾 到数组第2个元素 index=1
        for(int end = arr.length - 1; end > 0; end--){
            for(int i = 0; i < end; i++){
                if(arr[i] > arr[i+1]){
                    swap(arr, i, i+1);
                }
            }
        }
    }
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
