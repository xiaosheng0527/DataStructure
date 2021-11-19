package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/20 9:27
 *
 * 希尔排序法介绍
 *
 * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 *
 * 希尔排序法基本思想
 *
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 */
public class ShellSortDemo {

    public static void main(String[] args) {
        int[] arr = {8,9,1,7,2,3,5,4,6,0} ;
        //shellSort1(arr);
        shellSort2(arr);
    }

    /**
     * 希尔排序时， 对有序序列在插入时采用交换法, 并测试排序速度.
     * 交换法：如果发现两个数存在前面大于后面的情况下，立刻交换【类似冒泡】
     */
    public static void shellSort1(int[] arr){

        int temp = 0 ;
        for(int gap = arr.length / 2 ; gap >= 1 ; gap /= 2){    //增量只剩下一组时，就代表，步长为1

            for(int i = gap ; i < arr.length ; i++){

                for(int j = i - gap ; j >= 0 ; j -= gap ){
                    if(arr[j] > arr[j + gap]){
                        temp = arr[j] ;
                        arr[j] = arr[j + gap] ;
                        arr[j + gap] = temp ;
                    }
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 交换法：如果发现两个数存在前面大于后面的情况下，立刻交换【类似冒泡】
     */
    public static void shellSort2(int[] arr){

        int temp = 0 ;
        int j = 0 ;
        for(int gap = arr.length / 2 ; gap >= 1 ; gap /= 2){

            for(int i = gap ; i < arr.length ; i++){

                j = i ; // 定义要插入的元素的下标
                temp = arr[j] ;

                if(arr[j] < arr[j - gap]){

                    while(j - gap >=0 && arr[j - gap] > temp){

                        arr[j] = arr[j - gap] ;
                        j -= gap ;
                    }
                    arr[j - gap + gap] = temp ;
                }

            }
            System.out.println(Arrays.toString(arr));
        }
    }
}
