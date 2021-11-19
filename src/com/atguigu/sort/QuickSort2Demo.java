package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/21 9:19
 */
public class QuickSort2Demo {

    public static void main(String[] args) {
        int[] arr = {0, 2, 30, 35, 17, 1};
        quickSort2(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort2(int[] arr, int L, int R) {

        if (L >= R) {
            return;    //退出递归
        }

        int left = L;  // left指针,注意：重新赋给一个变量这样看着会条理清晰
        int right = R; // right指针，注意：重新赋给一个变量这样看着会条理清晰
        int pivot = arr[left]; // 每次都以左边的第一个作为中轴

        while(left < right){
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            // 注意：如果是left=right，则这趟排序就结束了，此时应该将pivot放置在空余位置
            if (left < right) {   // 说明之前while循环退出是因为arr[right] >= pivot不满足条件，而不是left < right这个条件不满足
                arr[left] = arr[right];
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            if (left < right) {
                arr[right] = arr[left];
            }
            // 当最后left == right，说明此时已经第一趟排序完毕，再将left指针的指向位置存放pivot元素
            // 若不满足，则继续进行移动指针，故最外层需要有一个while(left < right)
            if (left >= right) {
                arr[left] = pivot;
            }
        }

        quickSort2(arr, L, right - 1);

        //注意：以左边递归为例：quickSort2(arr, left, right - 1); 这样写是错的，因为第一趟排序后left == right，故如果这样调用，说明是无效递归

        quickSort2(arr, left + 1, R);
    }
}
