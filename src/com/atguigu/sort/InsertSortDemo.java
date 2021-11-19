package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/19 16:31
 */
public class InsertSortDemo {

    public static void main(String[] args) {
        int[] arr = {7,8,1,9,10,2} ;
        insertSort(arr);
    }

    public static void insertSort(int[] arr){

        int insertIndex = 0 ;
        int insertVal = 0 ;
        for(int i = 1 ; i < arr.length ; i++){

            insertIndex = i - 1 ;
            insertVal = arr[i] ;

            while(insertIndex >= 0 && arr[insertIndex] > insertVal ){
                arr[insertIndex + 1] = arr[insertIndex] ;
                insertIndex-- ;
            }
            if(insertIndex + 1 != i){   // 若insertIndex + 1 == i，则说明刚好有按照顺序
                arr[insertIndex + 1] = insertVal ;
            }
            System.out.println(Arrays.toString(arr));
        }
    }
}
