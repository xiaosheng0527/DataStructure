package com.atguigu.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/18 16:45
 *
 * 注意：定义为实例变量，主要是可以在非静态方法下进行访问
 * 这里规定：默认第一个皇后从0开始
 */
public class Solution {
    private List<List<String>> resList;
    private int[] x;    // 主要是保存对应行下的皇后的位置

    /**
     * @param n     代表有几个皇后
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        // 为什么要定义容量为n+1，因为索引范围为0~n，而0不使用，
        x = new int[n + 1];     // 用一维数组x来表示对应索引【即对应行】下保存皇后的位置
        resList = new ArrayList<>();
        // 注意：这里的第一个皇后从第1行开始计数【不是第0行】，其实这样比较好，因为题目给定的是n，
        // 如果从0开始，那么我们下面这个方法queens(0,n-1) ; 这样看着不具有可读性
        queens(1,n);
        return resList;
    }

    /**
     *
     * @param row   代表当前行
     * @param n     代表共有n个皇后【n为最后一个皇后，变量n是solveNQueens()方法传递过来的】
     *              注意：这里不像最原始的八皇后，原始的八皇后的n的值是在实例变量定义的，故在添加皇后时，check()方法就不用传递参数
     */
    public void queens(int row,int n){
        if(row>n){  // 即当前行比最后一个皇后的位置还要大，此时就不用进行添加皇后了，就把每一行符合条件的皇后赋值为"Q"，不符合条件的元素赋值为"."，然后进行拼接
            List<String> list = new ArrayList<>();
            for(int i = 1; i <= n; i++){   // i代表行值，即x数组的索引值，从1开始
                StringBuffer sb = new StringBuffer() ;
                for(int j = 1; j <= n; j++){  // j代表列值，即x数组的元素值，从1开始
                    if(x[i] == j)   // 如果
                        sb.append('Q') ;
                    else
                        sb.append('.') ;
                }
                list.add(sb.toString());
            }
            resList.add(list);
        }
        else{   // 还没有到摆完最后一个皇后
            for(int i = 1; i <= n;i++){
                x[row] = i;     // 假设可以添加此皇后，i代表列数
                // 判断当放置第row个皇后到i列时，是否冲突
                if(judge(row)){
                    queens(row+1,n);    // 满足条件，即可排列下一个皇后
                }
                // 冲突，就继续循环i+1， x[row] = i ; 即将第row个皇后，放置在本行的后一个列位置
                continue ;    // 加上这一行，主要是可以清晰的明白要进行下一次循环，找第row个皇后的，第i列的下一个位置，回溯。
            }
        }
    }
    // 要和前面所有已经排好的皇后进行检测，是否存在攻击

    /**
     * @param row   代表当前行
     * @return
     */
    public boolean judge(int row){
        for(int i = 1 ; i < row ; i++){    // i代表以前排列过的皇后和当前行row排列的皇后的判断
            //1. x[row]==x[i]: 表示判断 第row个皇后是否和前面的row-1个皇后在同一列
            //2. Math.abs(row - i) == Math.abs(array[n] - array[i]) 表示判断第row个皇后是否和前面row-1个皇后是否在同一斜线
            if(x[row]==x[i] || Math.abs(row-i) == Math.abs(x[row]-x[i]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}


