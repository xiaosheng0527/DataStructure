package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/11/16 23:36
 */
public class QuickSort3 {

    // 首先确定一个思想，就是快排是利用分治算法来进行的，及一项任务的完成需要多个步骤，这就是分步骤，
    // 然后对于每个步骤都需要求解出对应的解，
    // 核心思想：递归+分治算法

    /**
     * 这里的三个步骤：
     * 1.先将第一个位置的元素进行抽取，记为 pivot，然后从最右边开始遍历，将小于pivot的元素放到pivot的左边，大于pivot的元素放到pivot的右边，一轮移动后，将原来保存的pivot的元素放到剩余的位置
     * 2. 将pivot的左边序列的元素进行快排
     * 3. 将pivot的右边的序列的元素进行快排
     * 注意：刚才这层的pivot的位置已经确定了，所以之后的排序无需考虑它，
     *
     */

    public static void main(String[] args) {

        int[] arr = {5,80,37,26,12,66} ;

        // 先确定两端的指针，left和right
        int left = 0 ;
        int right = arr.length - 1 ;


        // 在一个方法进行进行业务操作
        // 这个方法得到对应的pivot的位置，这个是确定的
        // 这个是递归的入口，
        quickSort(arr,left,right) ;

        System.out.println(Arrays.toString(arr));

    }


    private static void quickSort(int[] arr, int L, int R) {

        // 递归出口
        if(L >= R){
            return ;
        }

        int left = L ;
        int right = R ;

        // 中心轴始终要以当前层要排的序列的最左边的元素为主
        int pivot = arr[left] ;

        // 注意：这里有一个天坑，L和R是确定边界，而定义的辅助指针left和right是为了遍历
        // 不然移动了位置，下次向左进行快排的时候就会忘了当前层的边界

        // 这里退出循环的条件是left == right
        while(left < right){

            // 先对右指针进行判断
            while(left < right && arr[right] >= pivot){
                right-- ;
            }

            // 退出循环，可能存在两种情况，
            // 1. 是因为left < right 的条件不满足
            // 2. 是因为 arr[right] >= pivot 的条件不满足

            if(left < right){
                // 将元素覆盖到left位置
                arr[left] = arr[right] ;
                left++ ;    // 这是左指针left右移动，因为此时的left指针存放的是移动到的元素，就可以考虑下一个位置了
            }

            // 此时移动left指针进行判断
            while(left < right && arr[left] <= pivot){
                left++ ;
            }

            //
            if(left < right){
                arr[right] = arr[left] ;
                right-- ;
            }
        }

        // 当退出大循环后,此时left == right
        // 此时需要将剩余的位置放置pivot
        if(left >= right){
            arr[left] = pivot ;
        }


        // 之后需要向左进行快排
        quickSort(arr, L, right - 1);

        // 向右进行快排
        quickSort(arr, left + 1, R);
    }
}
