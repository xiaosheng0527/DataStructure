package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/20 10:32
 *
 * arr = [0,2,30,35,17,1]   -> 刚开始
 * arr = [0, 2, 1, 17, 30, 35]
 * arr = [0, 1, 2, 17, 30, 35]
 * arr = [0, 1, 2, 17, 30, 35]
 */
public class QuickSort {

    public static void main(String[] args) {
//        int[] arr = {0,2,30,35,17,1} ;
        int[] arr = {-9,78,0,23,-567,70} ;
        quickSort(arr, 0, arr.length - 1);
        //System.out.println("arr = " + Arrays.toString(arr));
    }

    /**
     *  出现了一个问题，如果是{0,2,30,35,17,1}，则第一次排序后[0, 2, 1, 17, 30, 35]，此时pivot的左边的2比pivot=1要大，这是因为pivot被覆盖了
     *  那么这样不是会和初衷相悖吗？原来我们不是要将pivot的左边放小于pivot的数，pivot的右边放小于pivot的数，现在条件不成立了
     *  老师这个代码出了一点bug
     */
    public static void quickSort(int[] arr,int left,int right){
        int l = left ;  // 左下标
        int r = right ; // 右下标
        // pivot：中轴，枢纽
        int pivot = arr[(left + right) / 2] ;
        int temp = 0 ;  // 临时变量，作为交换时来使用

        // while循环的目的是让比pivot值小的放到左边
        // 比pivot 值大的放到右边
        while(l < r){

            // 在pivot的左边一直找，找到大于等于pivot值，才退出
            // 注意：最坏的情况是，找到本身即arr[l] == pivot
            // 即找到本身即arr[l] == pivot，但是left和right在没有交换前，是不会超过pivot的，
            // 顶多刚好left=pivot或right=pivot，或者left = right 刚好在元素pivot的位置【相当于不用排序了，已经是升序】

            // 但要切记：pivot不能动，它是作为一个中心轴，如果是这个代码下面就出现了bug，虽然结果没问题，但是在第一次排序后
            // 因为pivot移动了，导致排序变化
            // 出现了arr = [0, 2, 1, 17, 30, 35]，即在pivot位置之前的元素也存在了比pivot大的元素

            while(arr[l] < pivot){
                l += 1 ;
            }

            // 在pivot的右边一直找，找到小于等于pivot值，才退出
            while(arr[r] > pivot){
                r -= 1 ;
            }



            // 如果l >= r 说明pivot 的左右两边的值，已经按照左边全部是小于等于pivot值
            // 右边全部是大于等于pivot值
            if(l >= r){     // 退出条件，左边的指示器l大于等于右边的计数器r
                break ;
            }
            // 如果不满足上面的条件，故可以进行交换了
            // 交换
            temp = arr[l] ;
            arr[l] = arr[r] ;
            arr[r] = temp ;

            // 如果交换完后，发现下一项的arr[l] == pivot 相等 r-- ，前移
            // 即若是相等就不用进行比较，如果比较还会一直执行上面的while循环，耗时，耗空间
            // 注意：交换完后，arr[l]会移动到右边，arr[r]会移动到左边，
            // 综上：加上下面两个语句的作用：防止左边和右边的数 和中间值pivot一样，此时会一直循环前面的代码

            if(arr[l] == pivot){    // 此时arr[l]在右边，若又满足arr[l] == pivot，则r前移
                r -= 1 ;
            }
            // 如果交换完后，发现下一项的arr[r] == pivot 相等 l-- ，后移
            if(arr[r] == pivot){    // 此时arr[r]在左边，若又满足arr[r] == pivot，则r后移
                l += 1 ;
            }
        }

        // 如果 l == r，必须 l++，r--，否则会出现栈溢出
        // 确实会出现栈溢出，但底层原理是为什么我也不晓得？？
        // 只能大概讲一下：因为第一次排序后，变成左边一堆，中轴pivot，右边一堆。而中轴已经默认排列好了
        // 前面while循环的退出条件为l==r，此时如果不让l++，r--，还会重复的排列pivot这个元素，那这样就不是我们想要的效果了
        // 所以需要l++，r--。但为什么会栈溢出，我也不懂？？？
        if(l == r){
            l += 1 ;
            r -= 1 ;
        }

        System.out.println("arr = " + Arrays.toString(arr));

        // 上面的while循环执行一趟后，排序了一次，然后下面都是重复的方法，故可以进行递归
        // 这里按照规则，先左递归，再右递归

        // 向左递归 防止死递归，要有出口。注意：上面的while循环导致r变化，退出循环后r跑到左边了
        // 防止死递归，故left < r
        if(left < r){
            quickSort(arr, left, r);
            //System.out.println("arr = " + Arrays.toString(arr));
        }
        // 向左递归 ,注意：上面的while循环导致l变化，退出循环后r跑到右边了
        // 防止死递归，right < l
        if(right > l){
            quickSort(arr, l, right);
            //System.out.println("arr = " + Arrays.toString(arr));
        }
    }
}