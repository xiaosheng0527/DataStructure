package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/11/19 12:20
 *
 * // 此处我是分析了归并算法，自己模拟一下
 */
public class MergeSort2 {

    public static void main(String[] args) {

        int[] arr = {8,4,5,7,1,3,6,2} ;

        // 定义临时数组
        int[] temp = new int[arr.length] ;

        // 递归入口
        // 需要先传入边界指针【边界索引/边界值】
        // 因为每一层的递归都需要边界指针
        mergeSort2(arr,0,arr.length - 1,temp) ;

        System.out.println(Arrays.toString(arr));

    }

    // 分解的代码比较简单，主要就是边界的缩减，就是left和right的控制
    private static void mergeSort2(int[] arr, int left, int right, int[] temp) {

        // 递归入口
        if(left == right){
            return ;
        }

        //int mid = left + (left + right) / 2 ;
        int mid = (left + right) / 2 ;

        // 左序列分解
        mergeSort2(arr, left, mid, temp);

        // 右序列分解
        mergeSort2(arr, mid + 1, right, temp);

        // 合并
        merge(arr,left,mid,right,temp) ;
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {

        int i = left ;
        int j = mid + 1 ;

        int p = 0 ; // 用于temp数组的遍历添加

        // 大边界，一个不满足就可以将另一个序列的剩余的元素直接添加到后面
        // 这里先定一个规则，左序列小于等于右序列时，可以添加左序列
        while(i <= mid && j <= right){

            if(arr[i] <= arr[j]){
                temp[p++] = arr[i++] ;
            } else {
                temp[p++] = arr[j++] ;
            }

        }

        // 当退出循环时候，你不清楚是哪个还没遍历完
        // 如果是i还没遍历完
        // 注意：这里最好不要先判断if，再while循环，代码太丑，为什么不干脆合起来，看着舒服一点。

        while(i <= mid){
            temp[p++] = arr[i++] ;
        }

        while(j <= right){
            temp[p++] = arr[j++] ;
        }

        // 最后需要将当前层中 对于 left 和 right边界下合并后的元素覆盖到原数组arr

        int tempLeft = left ;

        p = 0 ; // 将指针p重置为0，因为p还是作为遍历的辅助指针

        while(tempLeft <= right){

            arr[tempLeft++] = temp[p++] ;
        }
    }
}
