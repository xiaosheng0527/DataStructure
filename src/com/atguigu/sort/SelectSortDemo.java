package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/19 13:39
 */
public class SelectSortDemo {

    public static void main(String[] args) {
        int[] arr = {4,8,3,10,7};
        selectSort(arr);
    }

    public static void selectSort(int[] arr){

        for(int i = 0 ; i < arr.length - 1 ; i++){

            int minIndex = 0 ;
            int min = arr[i] ;

            for(int j = i + 1 ; j < arr.length ; j++){
                if(arr[j] < min){
                    min = arr[j] ;
                    minIndex = j ;
                }
            }

            if(minIndex != i){
                arr[minIndex] = arr[i] ;
                arr[i] = min ;
            }
        }
        System.out.println(Arrays.toString(arr));
    }


}
