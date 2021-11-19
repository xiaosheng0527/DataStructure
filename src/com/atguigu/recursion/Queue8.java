package com.atguigu.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/18 11:41
 *
 * 一共有92种摆法；
 * 你可以看到里面的摆法很有规律，严格遵循回溯的思想。
 * 假如找到第一种：0 4 7 5 2 6 1 3 ，那么下一次return会返回到1所在的子栈区，然后再向下搜索看后面的列值是否符合，符合即又进行递归入栈，不符合又进行回溯【层层递归，层层回溯】
 * 来来往往；可以把第一个皇后在第0列的摆法都计算出来，4种；然后第一个皇后第0列就不能再摆了，就往下摆第1列，然后，重复这些操作。很有规律！！！
 * 切记：一定要存在递归出口，若没有，则会出现栈溢出。

 */
public class Queue8 {

    // 定义一个max表示共有多少个皇后
    int max = 8 ;
    static int count = 0 ; // 计数，统计8皇后有多少种摆法
    static int judgeCount = 0 ;
    // 定义数组array，保存皇后位置的结果，比如arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3}
    // 这里不用二维数组，因为一维数组可以索引保存行，元素值保存列，而且便于下面条件的判断
    int[] array = new int[max] ;

    // 注意：这里的二维列表在new对象的时候，只需要用黄金表达式<>,类型根据前面的类型自动匹配
    private List<List<Integer>> resList = new ArrayList<>() ;  // 用一个二维列表保存每一次在一维列表中保存的一种方案

    public static void main(String[] args) {
        // 测试一把，8皇后是否正确
        Queue8 queue8 = new Queue8() ;
        queue8.check(0);
        System.out.println("共有" + count + "种摆法");
        System.out.println("一共判断冲突的次数为" + judgeCount);

        System.out.println("resList = " + queue8.resList);
    }

    // 编写一个方法，放置第n个皇后
    // 特别注意：check 是每一次递归时，进入到check中都有 for(int i = 0 ; i < max ; i++)，因此会有回溯
    private void check(int n){
        if(n == max){ // n = 8 ,其实8个皇后已然放好了
            print() ;
            return ;
        }

        // 依次放入皇后并判断冲突，这里的for循环的变量i是列标，注意这里始终根据回溯始终能找到一种摆法，
        // 在这个for循环存在回溯的思想，一个点用过了，就可以考虑下一个点
        for(int i = 0 ; i < max ; i++){
            // 先把当前皇后n，放到该行的第一列；即假设可以放置
            array[n] = i ;
            // 判断当放置第n个皇后到i列时，是否冲突
            if(judge(n)){   // 不冲突
                // 接着放n+1个皇后，即开始递归
                check(n + 1);
            }
            // 如果冲突，就继续执行 array[n] = i ; 即将第n个皇后，放置在本行的后一个位置
            //array[n] = i + 1 ;  注意：这里不需要写，直接for循环遍历i+1,即可找到下一列；如果再写了，就会出bug了
            // 什么情况下会执行这下面的代码？？？
            // 1.检测出攻击，此时就要找下一个列位置
            // 2.对于之前已经得到的一种结果，然后进行回溯，寻找下一个解
            // 因为之前我们定义的一维数组的索引值代表第几个皇后，元素值为第几列，故此时当摆某一个皇后时，冲突时可以通过修改列值，进行覆盖
            continue ;  // 加上这一行，主要是便于我们找第n个皇后的，第i列的下一个位置，回溯。
        }
    }

    // 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    /**
     *
     * @param n     表示第n个皇后，要和前面所有已经排好的皇后进行检测，是否存在攻击，这里n从0开始
     *              n=0表示第一个皇后
     * @return
     */
    // 要和前面所有已经排好的皇后进行检测，是否存在攻击
    private boolean judge(int n){
        judgeCount++ ;
        for(int i = 0 ; i < n ; i++){
            //1. array[i] == array[n]: 表示判断 第n个皇后是否和前面的n-1个皇后在同一列
            //2. Math.abs(n - i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第i个皇后[即前面n-1个皇后]是否在同一斜线
            // 注意：斜线其实有两个方向，假设原点(0,0)在左下角，则，当我们要摆的这个皇后，需要和45°和135°两个对角线方向的位置的元素进行比较，
            // 这里刚好使用 Math.abs(n - i) == Math.abs(array[n] - array[i]) ，加了绝对值，所以考虑了两个层面，同时这里使用一维数组来
            // 记录每种皇后的摆法。
            // n = 1 放置第2列 array[1] = 1
            // Math.abs(1 - 0) == 1  Math.abs(array[n] - array[i]) = Math.abs(1-0) = 1
            if(array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])){
                return false ;
            }
        }
        return true ;
    }

    // 写一个方法，可以将皇后摆放的位置输出
    // 注意：这里的每一种摆法都是用一个一维数组进行保存，其实也可以用一个一维列表保存，然后将每种情况对应的一维列表放在二维列表中
    private void print(){
        List<Integer> tempList = new ArrayList<>() ;
        count++ ;
        for (int item:array){
            System.out.print(item + " ");
            tempList.add(item) ;
        }
        resList.add(tempList) ;
        System.out.println();

    }

}
