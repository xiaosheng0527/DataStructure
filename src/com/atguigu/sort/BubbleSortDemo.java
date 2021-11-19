package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/19 9:56
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] arr = {8,6,3,9,2,4} ;
        bubbleSort(arr);
    }

    public static void bubbleSort(int[] arr){

        boolean flag = false ;  // 为了确定当前这一趟，是否交换过元素
        for(int i = 0 ; i < arr.length - 1 ; i++){
            for(int j = 0 ; j < arr.length - 1 - i ; j++){
                if(arr[j] > arr[j + 1]){
                    int temp = 0 ;
                    flag = true ;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp ;
                }
            }
            if(!flag){
                break ; // 跳出最外层的for循环，说明当前这一趟不存在交换的元素
            }else{
                flag = false ;  // 进行重置，以免前面的布尔值对后面的影响，"覆盖"
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
