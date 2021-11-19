package com.atguigu.stack;

import java.util.Scanner;

/**
 * @author shengxiao
 * @date 2021/7/15 17:16
 */
public class ArrayStackDemo {

    // 定义一个ArrayStack 表示栈
    static class ArrayStack{
        private int maxSize ;   // 栈的大小
        private int[] stack ;   // 数组，数组模拟栈，数据就放在该数组
        private int top = -1 ;

        // 构造器


        public ArrayStack() {
        }

        public ArrayStack(int maxSize) {
            this.maxSize = maxSize;
            this.stack = new int[this.maxSize] ;
        }

        // 栈满
        public boolean isFull(){
            return top == maxSize - 1 ;
        }

        // 栈空
        public boolean isEmpty(){
            return top == -1 ;
        }

        // 入栈 - push
        public void push(int value){
            // 先判断栈是否已满
            if(isFull()){
                return ;
            }
            top++ ;
            stack[top] = value ;
        }

        // 出栈
        public int pop(){
            // 先判断是否为空
            if(isEmpty()){
                // 抛出异常
                // 注意：运行时异常【即非受检异常】抛出后，可以不捕获
                throw new RuntimeException("栈空，没有数据") ;
            }
            int value = stack[top];
            top-- ;
            return value ;
        }

        // 显示栈的情况【遍历栈】，遍历时，需要从栈顶开始显示数据
        // 因为栈帧一直指向栈顶，我们只能通过栈帧才能遍历到栈内部的元素
        public void list(){
            if(isEmpty()){
                System.out.println("栈空，没有数据~");
            }
            // 需要从栈顶开始显示数据
            for(int i = top ; i >= 0 ; i--){
                System.out.printf("stack[%d]=%d\n", i,stack[i]);
            }
        }

    }

    public static void main(String[] args) {
        // 测试一下ArrayStack是否正确
        // 先创建一个ArrayStack对象 -> 表示栈
        ArrayStack stack = new ArrayStack(4) ;
        String key = "" ;
        boolean loop = true ;   // 控制是否退出循环
        Scanner scanner = new Scanner(System.in);

        while(loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        // 注意：前面手动抛出了一个运行时异常【非受检异常】，可以不捕获，
                        // 但这里为了显示错误信息，最好自己捕获一下
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    // 使用scanner（system.in）时，使用完毕后，一定要关闭扫描器，
                    // 因为system.in属于IO流，一旦打开，它一直在占用资源，因此使用完毕后切记要关闭
                    scanner.close();
                    loop = false;
                    break;  // 退出switch语句
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }
}
