package com.atguigu.search;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/21 22:27
 *
 * 首先考虑一个新问题，为什么一定要是折半，而不是折四分之一或者折更多呢？
 * 打个比方，在英文字典里面查“apple”，你下意识翻开字典是翻前面的书页还是后面的书页呢？如
 *      果再让你查“zoo”，你又怎么查？很显然，这里你绝对不会是从中间开始查起，而是有一定目的的往前或往后翻。
 * 同样的，比如要在取值范围1 ~ 10000 之间 100 个元素从小到大均匀分布的数组中查找5， 我们自然会考虑从数组下标较小的开始查找。
 * 经过以上分析，折半查找这种查找方式，还是有改进空间的，并不一定是折半的！
 * mid = （low+high）/ 2, 即 mid = low + 1/2 * (high - low);
 * 改进为下面的计算机方案（不知道具体过程）：mid = low + (key - a[low]) / (a[high] - a[low]) * (high - low)，
 * 也就是将上述的比例参数1/2改进了，根据关键字在整个有序表中所处的位置，让mid值的变化更靠近关键字key，这样也就间接地减少了比较次数。
 */
public class InsertValueSearch {

    public static void main(String[] args) {

        int[] arr = new int[100] ;
        for(int i = 0 ; i < 100 ; i++){
            arr[i] = i + 1 ;
        }
        //System.out.println(Arrays.toString(arr));

        int index = insertValueSearch(arr , 0, arr.length - 1, 100) ;
        System.out.println("index = " + index);
    }

    // 编写差值查找算法
    // 说明：差值查找算法，也要求是有序的
    /**
     *
     * @param arr   数组
     * @param left  左边索引
     * @param right 右边索引
     * @param findVal   查找值
     * @return  如果找到，就返回对应的下标，如果没有找到，则返回-1
     */
    public static int insertValueSearch(int[] arr,int left,int right,int findVal){

        System.out.println("插值查找次数~~");

        // 注意：findVal < arr[0] || findVal > arr[arr.length - 1])：这个条件是必须需要的，防止mid越界
        // 否则我们得到的mid 可能越界
        if(left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1 ;
        }

        // 求出mid，自适应
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]) ;
        int midVal = arr[mid] ;
        if(findVal > midVal){   // 说明应该向右边进行递归
            return insertValueSearch(arr, mid + 1, right, findVal) ;
        } else if(findVal < midVal){
            return insertValueSearch(arr , left, mid - 1 , findVal) ;
        }else{
            return mid ;
        }
    }


}
