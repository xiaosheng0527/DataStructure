package com.atguigu.search;

/**
 * @author shengxiao
 * @date 2021/7/21 19:37
 */
public class SequenceSearch {

    public static void main(String[] args) {
        int[] arr = {1,9,11,-1,34,89} ;
        int index = sequenceSearch(arr, 11) ;
        if(index == -1){
            System.out.println("没有找到");
        }else{
            System.out.println("找到，下标为" + index);
        }
    }

    /**
     *  这里我们实现的线性查找是找到一个满足条件的值，就返回;
     *  如果我们要查找的元素存在多个，且此时都想一一返回，则利用集合进行存放即可。【集合可以选List集合】
     * @param arr
     * @param value
     * @return
     */
    public static int sequenceSearch(int[] arr,int value){
        // 线性查找是逐一比对，发现有相同值，就返回下标
        for(int i = 0 ; i < arr.length ; i++){
            if(arr[i] == value){
                return i ;
            }
        }
        return -1 ;
    }
}
