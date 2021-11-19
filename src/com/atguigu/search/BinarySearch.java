package com.atguigu.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/21 19:55
 */
// 注意：二分查找的前提是 该数组是有序的
public class BinarySearch {

    public static void main(String[] args) {
        //int[] arr = {1,8,10,89,100,1234} ;

//        int resIndex = binarySearch(arr, 0, arr.length - 1, 99) ;
//        System.out.println(resIndex);

        int[] arr = {1,8, 1000, 1000, 1000,1024, 1111,1234} ;
        List<Integer> resIndexList =  binarySearch2(arr, 0, arr.length - 1, 1000) ;
        System.out.println("resIndexList = " + resIndexList);
    }

    // 二分查找算法

    /**
     *
     * @param arr   数组
     * @param left  左边的索引
     * @param right 右边的索引
     * @param findVal   要查找的值
     * @return  如果找到就返回下标，如果没有找到，就返回-1
     */
    public static int binarySearch(int[] arr,int left,int right,int findVal){

        int mid = (left + right) / 2 ;
        int midVal = arr[mid] ;

        // 当left > right 时，说明递归整个数组，但是没有找到
        if(left > right){
            return -1 ; // 递归出口
        }
        // 如果没有到递归出口，就可以进行下面的 判断
        if(findVal > midVal){   // 向右递归
            return  binarySearch(arr, mid + 1, right, findVal) ;
        } else if(findVal < midVal){    //向左递归
            return binarySearch(arr, left, mid - 1, findVal) ;
        } else{     // findVal == midVal
            return mid ;    // 说明找到了
        }
    }

    //完成一个课后思考题:
    /*
     * 课后思考题： {1,8, 1000, 1000, 1000,1024, 1111，1234} 当一个有序数组中，
     * 有多个相同的数值时，如何将所有的数值都查找到，比如这里的 1000
     *
     * 思路分析
     * 1. 在找到mid 索引值，不要马上返回
     * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
     * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
     * 4. 将Arraylist返回
     */

    public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal){

        System.out.println("二分查找次数~~");

        int mid = (left + right) / 2 ;
        int midVal = arr[mid] ;

        // 当left > right 时，说明递归整个数组，但是没有找到
        if(left > right){
            return null ; // 递归出口
        }
        // 如果没有到递归出口，就可以进行下面的 判断
        if(findVal > midVal){   // 向右递归
            return  binarySearch2(arr, mid + 1, right, findVal) ;
        } else if(findVal < midVal){    //向左递归
            return binarySearch2(arr, left, mid - 1, findVal) ;
        } else{     // findVal == midVal
//            * 思路分析
//            * 1. 在找到mid 索引值，不要马上返回
//            * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
//            * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
//            * 4. 将Arraylist返回
            List<Integer> resIndexList = new ArrayList<>() ;
            // 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            int temp = mid - 1 ;
            while(true){
                // 注意：这两个条件的先后顺序，如果temp >= 0时候，那么如果其左边不存在重复的元素，此时就可以跳出循环
                if(temp < 0 || arr[temp] != findVal){   // 退出
                    break ;
                }
                // 否则，就把temp放入到resIndexList
                resIndexList.add(temp) ;
                temp -- ;   // temp左移
            }
            resIndexList.add(mid) ;

            // 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            temp = mid + 1 ;
            while(true){
                // 注意：这两个条件的先后顺序，如果temp >= 0时候，那么如果其左边不存在重复的元素，此时就可以跳出循环
                if(temp > arr.length - 1 || arr[temp] != findVal){   // 退出
                    break ;
                }
                // 否则，就把temp放入到resIndexList
                resIndexList.add(temp) ;
                temp ++ ;   // temp左移
            }

            // 注意：这里千万不要再添加mid，因为无非最不理想的情况是，mid的左右边都有连续相等的元素,
            // 所以，可以先向左查询，找到后添加，然后再添加mid对应的值，然后再次向右查询，找到后添加
            // resIndexList.add(mid) ;

            return resIndexList ;
        }
    }


}
