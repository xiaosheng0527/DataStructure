package com.atguigu.recursion;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/21 12:57
 *
 * 思路：利用递归，回溯思想
 *
 * 注意：为什么我这次写的磕磕碰碰，因为我对这个算法那没有完全掌握，
 *      因为我没有对回溯思想完全掌握
 */
public class Queue8Demo {

    int maxSize = 8 ;
    int[] arr = new int[maxSize] ;
    static int count = 0 ;

    public static void main(String[] args) {
        Queue8Demo queue8Demo = new Queue8Demo() ;
        queue8Demo.queue8(0);

        System.out.println("一共有" + count +"中摆法");
    }

    // 当前正在摆第几个皇后
    // 编写一个方法，放置第n个皇后
    public void queue8(int n){

        if(n == maxSize){
            print();
            return ;
        }
        // 这里的for循环是确定排列的是第几个
        // 这里的for循环是的变量i是列标，从第一个列位置0~maxSize-1进行遍历
        // 在这个for循环存在回溯的思想，一个点用过了，就可以考虑下一个点
        for(int i = 0 ; i < maxSize ; i++){

            arr[n] = i ;
            if(judge(n)){

                queue8(n + 1);
            }
            // 如果冲突，就继续执行 array[n] = i ; 即将第n个皇后，放置在本行的后一个位置
            //array[n] = i + 1 ;  注意：这里不需要写，直接for循环遍历i+1,即可找到下一列；如果再写了，就会出bug了
            // 什么情况下会执行这下面的代码？？？
            // 1.检测出攻击，此时就要找下一个列位置
            // 2.对于之前已经得到的一种结果，然后进行回溯，寻找下一个解
            // 因为之前我们定义的一维数组的索引值代表第几个皇后，元素值为第几列，故此时当摆某一个皇后时，冲突时可以通过修改列值，进行覆盖
            continue;
        }


    }

    // 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    public boolean judge(int n){

        for(int i = 0 ; i < n ; i++){

            while(arr[i] == arr[n] || Math.abs(n - i) == Math.abs(arr[n] - arr[i])){

                return false ;
            }
        }
        return true ;
    }

    public void print(){
        count++ ;
        System.out.println(Arrays.toString(arr));
    }


}
