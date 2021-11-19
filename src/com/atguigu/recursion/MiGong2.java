package com.atguigu.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/18 8:34
 */

public class MiGong2 {
    /**
     * 定义迷宫数组
     */
    private int[][] array = {
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 1, 1, 0, 1},
            {0, 1, 1, 1, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 1},
            {0, 1, 1, 1, 1, 0, 0, 1},
            {1, 1, 0, 0, 0, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0}

    };
    private int maxLine = 8;
    private int maxRow = 9;

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        new MiGong2().check(0, 0);
        System.out.println(System.currentTimeMillis());
    }

    // 下面对于每一层调用check()方法，都会产生一个子栈【递归】，故存在回溯。
    private void check(int i, int j) {
        //如果到达右下角出口,递归出口，但此问题要找到所有的路径，故要进行回溯。可能还会继续递归调用。
        if (i == maxRow - 1 && j == maxLine - 1) {
            print();
            return;
        }

        //向右走
        if (canMove(i, j, i, j + 1)) {  // 判断当前点的下一个点是否到达边界，值是否为1或5，若满足任何一种情况，则此路不通
            array[i][j] = 5;    // 排除上面的情况下，可以先置为5，但后面有可能存在死路，也有可能找到了一条完整的路径，此时要进行回溯。而导致array[i][j]置为0
            check(i, j + 1);    // 判断当前点的下一个点是否到达终点，若到达时，即可打印出一个结果，然后返回到前一个调用点，继续执行下面的代码,array[i][j]=0;因为这条路走过了，不能再走了，只能找下一条路
                                  // 若未到达终点，此时需要继续进行按照 规定的顺序检索路径。即继续递归调用。
            array[i][j] = 0;    // 为什么要置为0，因为到达终点时候，此时找到一条路线，现在要找下一条路线，故要将之前那条路线的值清空，即array[i][j]=0，然后进行回溯；也可能当前路是死路，此时要将其置为0，然后进行回溯
        }
        //向左走
        if (canMove(i, j, i, j - 1)) {
            array[i][j] = 5;
            check(i, j - 1);
            array[i][j] = 0;
        }
        //向下走
        if (canMove(i, j, i + 1, j)) {
            array[i][j] = 5;
            check(i + 1, j);    // 都可以判断是否达到终点，没有达到就再次按照策略，访问
            array[i][j] = 0;
        }
        //向上走
        if (canMove(i, j, i - 1, j)) {
            array[i][j] = 5;
            check(i - 1, j);
            array[i][j] = 0;
        }
    }

    private boolean canMove(int i, int j, int targetI, int targetJ) {
//        System.out.println("从第" + (i + 1) + "行第" + (j + 1) + "列，走到第" + (targetI + 1) + "行第" + (targetJ + 1) + "列");
        if (targetI < 0 || targetJ < 0 || targetI >= maxRow || targetJ >= maxLine) {
//            System.out.println("到达最左边或最右边，失败了");
            return false;
        }
        if (array[targetI][targetJ] == 1) {
//            System.out.println("目标是墙，失败了");
            return false;
        }
        //避免在两个空格间来回走
        if (array[targetI][targetJ] == 5) {
//            System.out.println("来回走，失败了");
            return false;
        }

        return true;
    }

    private void print() {
        //List<Integer> list new ArrayList<>() ;
        int count = 0 ;
        System.out.println("得到一个解：");
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxLine; j++) {

                System.out.print(array[i][j] + " ");
                if(array[i][j] == 5){
                    count++ ;
                }
            }
            System.out.println();
        }
        System.out.println("当前路径的长度为：" + count + '\n');

    }
}
