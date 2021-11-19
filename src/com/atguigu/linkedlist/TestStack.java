package com.atguigu.linkedlist;

import java.util.Stack;

/**
 * @author shengxiao
 * @date 2021/7/13 17:13
 */
public class TestStack {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>() ;
        // 入栈
        stack.add("jack") ;
        stack.add("tom") ;
        stack.add("smith") ;

        // 出栈
        // Smith tom  jack
        while(stack.size() > 0){
            System.out.println(stack.pop());    // pop就是将栈顶的数据取出
        }
    }
}
