package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/11/24 15:15
 */
public class HeapSort3 {

    public static void main(String[] args) {

        int[] arr = {4,6,8,5,9} ;
        heapSort3(arr) ;

        System.out.println(Arrays.toString(arr));
    }

    private static void heapSort3(int[] arr) {

        int temp = 0 ;  // 作为辅助变量，用来交换首尾的元素
        int len = arr.length ;

        // 调整第一个大顶堆
        // 步骤是：从最后一个非叶子节点开始进行搜索，求取每一个局部的大顶堆
        // 然后在交换首尾元素，将最大的元素放置到最后
        for (int i = arr.length / 2 - 1 ; i >= 0 ; i--){

            adjustHeap3(arr,i,i,len) ;
        }

        // 我们始终是和第一个元素【索引值为0】的交换，所以这里只需要确定另一个要交换的元素即可
        for(int j = arr.length - 1 ; j >= 1 ; j--){

            temp = arr[0] ;
            arr[0] = arr[j] ;
            arr[j] = temp ;
            // 上面已经将第一个大顶堆排序好了，此时移动到末尾，然后就需要重复此步骤再排 "次大顶堆"，
            // 而这里有个比较高效的算法，就是此时并没有又将 begin 指向 倒数第二个位置，可能第一次排序，已经差不多排序成功了，
            // 只是差了一点，那么排第二个顶堆的时候，其实可以省省力，直接从最高的顶堆出发，如果运气好的话，可能速度还更快。
            adjustHeap3(arr, 0, 0, j);
        }
    }

    private static void adjustHeap3(int[] arr, int begin, int end, int len) {

        int temp = arr[begin] ;

        //end = begin ;   // 传递的参数就有体现

        for(int k = 2 * begin + 1 ; k < len ; k = 2 * k + 1) {

            if(k + 1 < len && arr[k] < arr[k + 1]){

                k++ ;
            }

            if(temp < arr[k]){
                arr[end] = arr[k] ;
                end = k ;
            } else {

                break ;
            }
        }

        arr[end] = temp ;
    }
}
