package com.atguigu.tree;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/27 9:12
 */
public class HeapSortDemo {

    public static void main(String[] args) {
        // 要求将数组进行升序排序
        int[] arr = {4,6,8,5,9} ;
        heapSort(arr);
    }

    public static void heapSort(int[] arr){

        int temp = 0 ;  // 用来交换首尾元素的临时变量
        System.out.println("堆排序！！");


        for(int i = arr.length / 2 - 1 ; i >= 0 ; i--){ // 第一次排大顶堆时，非叶子节点的从右向左，从下向上的搜索
            adjustHeap(arr, i, arr.length);
        }

        // 跳出for循环说明，第一次大顶堆排序完毕

        for(int j = arr.length - 1 ; j > 0 ; j--){
            temp = arr[0] ;
            arr[0] = arr[j] ;
            arr[j] = temp ;
            adjustHeap(arr, 0, j);  // j代表要调整的元素个数，以及是交换元素的下标，二者差1
        }

        System.out.println("数组=" + Arrays.toString(arr));
    }

    /**
     *
     * @param arr   待调整的数组
     * @param i     表示非叶子节点在数组中的索引  【注意：i=0代表是数组的第一个元素，也是二叉树的第一个节点】
     * @param length    表示对多少个元素的调整，length 是在逐渐减少
     */
    public static void adjustHeap(int[] arr,int i , int length){

        int temp = arr[i] ;
        // 开始调整
        // 说明
        // 此for循环是确定非叶子节点的左子节点,切记：完全二叉树，如果是最后一个非叶子节点，那么一定会存在左子节点，不一定存在右子节点
        // 故下面需要对右子节点进行判断，k + 1 < length，避免越界
        // 其实：这里的k也可以看做是一个指针【指示器】
        for(int k = 2 * i + 1 ; k < length ; k = 2 * i + 1){

            if(k + 1 < length && arr[k] < arr[k + 1]){
                k++ ;
            }

            if(temp < arr[k]){

                arr[i] = arr[k] ;
                i = k ; // ！！！ i 指向 k，继续循环比较，把 i看做是一个指针，可能当前叶子节点的左子树还存在左子节点，
                // 所以需要对原来的非叶子节点的索引值i 进行 覆盖，以便对下一次循环的判断
            }else{
                break ;     // 难点！！！这里为什么可直接break；【无论该节点是否存在子节点，满足arr[k] <= temp条件，都可以跳出循环】
                            // 但实际上最根本的原因是arr[k] <= temp，且非叶子节点是按照从右向左，从下向上的顺序查找【子节点是按照从左向右查找】
                            // 故如果这个条件满足，则说明此排序没有要进行交换元素，而之前的排序好的也不用动了。故就可以直接跳出循环
            }
        }
        // 当for循环结束后，我们己经将以i 为父节点的树的最大值，放在了 排序当前非叶子节点的堆顶（局部调整）
        arr[i] = temp ;     // 将temp值放到调整后的位置
    }
}
