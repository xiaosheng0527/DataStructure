package com.atguigu.linkedlist;

import java.util.LinkedList;

/**
 * @author shengxiao
 * @date 2021/7/15 15:58
 * 编号为 0~n-1 的小孩，完丢手绢
 */
public class Josephu_LinkedList {

    // 通过链表来模拟
    // 使用java的封装类 ，LinkedList，底层利用双向循环链表来实现，故永远
    // 这里没哟给定初始计数的地方，默认编号为0的地方开始
    public int josephu_solution(int n,int m){
        if(n == 0 || m == 0){
            return -1 ;
        }

        LinkedList<Integer> list = new LinkedList<>() ;
        for(int i = 0 ; i < n ; i++){
            list.add(i) ;
        }

        // 这里默认初始位置是第一个小孩，即编号为0的索引
        int removeIndex = 0 ;   // 检查一下每次去除的是哪一个
        while(list.size() != 1 ){   // 当小孩只剩下一个的时候，说明其不用被kill，victory
            // removeIndex + m - 1：是控制要删除编号的位置索引
            // m - 1：代表移动的索引值，排除本身的一次报数
            removeIndex = (removeIndex + m - 1) % list.size() ; // 取模是为了避免溢出
            list.remove(removeIndex) ;  // 注意：java中的remove()会自动紧缩内存
        }
        return list.get(0) ;
    }

    public static void main(String[] args) {
        System.out.println(new Josephu_LinkedList().josephu_solution(8, 2));
    }
}
