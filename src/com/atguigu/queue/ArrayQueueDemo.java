package com.atguigu.queue;

import java.util.Scanner;

/**
 * @author shengxiao
 * @date 2021/7/12 15:06
 * 目的：利用数组来模拟单向队列
 * 注意：但若元素添加完后，再进行弹出(删除)，此时指针front指向了尾部，之后前面的地址空间虽然是理论上不存放数据，
 * 但我们不能重复使用，即只能一次性使用,所以需要优化一下代码，改进为循环队列，此时弹出的元素，其剩余空间可以添加数据
 *
 * 综上：目前数组使用一个就不能用，没有达到复用的效果
 *      将这个数组使用算法，改造为一个环形的队列  利用取模：%
 *      注：环形队列，这里要空出一个空间做为约定，以此能进行循环判断是否要添加元素。否则，就比较复杂。
 */
public class ArrayQueueDemo {

    // 静态内部类，外部可以直接访问
    static class ArrayQueue{
        private int maxSize ;   // 表示数组的最大容量
        private int front ;     // 队列头
        private int rear ;      // 队列尾
        private int[] array ;   // 该数组用于存放数据，模拟队列

        // 创建队列的构造器
        public ArrayQueue(int arrMaxSize){
            this.maxSize = arrMaxSize ;
            array = new int[maxSize] ;
            // 初始化的时候是-1，指向队列头部，分析出front是指向队列头的前一个位置【始终指向队列头的前一个位置】
            // 什么时候front会改变，front会随着数据输出而改变。但front始终指向队列头的前一个位置
            front = -1 ;
            // 初始化的时候是-1，指向队列尾的数据（即就是队列最后一个数据），而rear是随着数据输入而改变
            // 注意：在空队列的情况下，其实也可以将rear认为是指向队列头的前一个位置。没有什么差别。
            rear = -1  ;
        }

        // 判断队列是否已满
        public boolean isFull(){
            return rear == maxSize - 1 ;
        }

        // 判断队列是否为空，只有在还没有添加数据的情况下，rear=front=-1
        // 注意：当添加元素时候，front还是-1，rear加1变为rear=0
        public boolean isEmpty(){
            return front == rear ;
        }

        // 添加数据到队列，注意：这里不考虑头插法，只有尾插法，因为队列是先进先出
        public void addQueue(int value){
            // 判断队列是否已满
            if(isFull()){
                System.out.println("队列已满，不能添加数据");
                return ;
            }
            array[++rear] = value ; // 让rear后移
        }

        // 获取队列的数据，出队列，这只是进行模拟出队【实际上数据还是在数组里，只是front指针改变】
        public int popQueue(){
            // 判断队列是否为空
            if(isEmpty()){
                /* 注意：下面的情况最好不要写，不然返回-1也可能是被认为正常的返回结果，最好使用异常
                System.out.println("队列已空，不能删除数据");
                return -1 ;
                */
                // 通过抛出异常，手动抛出
                throw new RuntimeException("队列已空，不能删除数据") ;
            }
            return array[++front] ; //每一次调用都会执行front+1,相当于从队头取出数据【模拟出队功能】

        }

        // 显示队列的所有数据
        public void showQueue(){
            if(isEmpty()){
                System.out.println("队列空，没有数据");
            }
//            for (int data:array){
//                System.out.println();
//            }
            for (int i = 0; i < array.length; i++) {
                System.out.printf("array[%d]=%d\n", i,array[i]);
            }
        }

        // 显示队列的头数据，注意不是取出数据【即：并非弹出数据】
        // 这里只是模拟队列的功能
        public int headQueue(){
            // 判断是否为空
            if(isEmpty()){
                throw new RuntimeException("队列空，没有数据") ;
            }
            // 这两个返回效果不同
            //return array[++front] ;   // 每一次调用都会执行front+1
            return array[front + 1] ;   // 只要调用相同的方法，都只会显示第一个队头的元素，因为front并没有移动
        }
    }




    public static void main(String[] args) {

        ArrayQueue arrayQueue = new ArrayQueue(3) ;
        char key = ' ' ;    // 接收用户输入
        Scanner scanner = new Scanner(System.in) ;
        boolean loop = true ;   // loop表示循环
        // 输出一个菜单
        while(loop){
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出程序");
            System.out.println("a(add)：添加数据到队列");
            System.out.println("p(pop)：从队列取出数据");
            System.out.println("h(head)：查看队列头的数据");
            // next()方法，返回一个String
            key = scanner.next().charAt(0) ;  // 接收一个字符
            switch (key){
                case 's' :
                    arrayQueue.showQueue();
                    break ;
                case 'a' :
                    System.out.println("输入一个数：");
                    int value = scanner.nextInt() ;
                    arrayQueue.addQueue(value);
                    break ;
                case 'p' :
                    // 此方法执行可能会有异常，这里最好捕获，不要再进行抛出，arrayQueue.showQueue();
                    try {
                        int res = arrayQueue.popQueue() ;
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println(e.getMessage()); // 此处输出的异常信息，就是上面抛出的时候带上的字符串
                    }
                    break ;
                case 'h' :  // 查看队列头的数据
                    try {
                        int res = arrayQueue.headQueue() ;
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println(e.getMessage()); // 此处输出的异常信息，就是上面抛出的时候带上的字符串
                    }
                    break ;
                case 'e' :  // 退出
                    scanner.close();    // 不关闭可能会提示异常信息,scanner不用的时候要记得关闭
                    loop = false ;
                default :
                    break ;
            }
        }
        System.out.println("程序退出");
    }
}
