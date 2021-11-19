package com.atguigu.queue;

import java.util.Scanner;

/**
 * @author shengxiao
 * @date 2021/7/12 19:22
 *
 * *****综上：循环队列，不预留一个空间，无法判断是否队列是否已满或队列是否为空 *****
 */
public class CircleArrayQueueDemo {

    static class CircleArrayQueue{
        private int maxSize ;   // 表示数组的最大容量
        // front 变量的含义做一个调整：front就指向队列 的第一个元素，也就是说array[front]就是队列的第一个元素
        // front的初始值是0
        private int front ;
        // rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置，因为希望空出一个空间做为约定
        // 【注：这里队列能存放的最后一个元素是maxSize-1，而剩下的一个元素maxSize是为了区别对列是否已满满】
        // 即牺牲一个元素空间，来区别队空或队满。
        // rear的初始值是0
        private int rear ;  // 队列尾
        private int[] array ;   // 该数据用于存放数据，模拟队列

        // 创建队列的构造器
        public CircleArrayQueue(int arrMaxSize) {
            this.maxSize = arrMaxSize ;
//            this.front = 0 ;默认为0，故可以不写
//            this.rear = 0 ;
            this.array = new int[maxSize];
        }

        // 判断队列是否已满
        public boolean isFull(){
            return (rear + 1) % maxSize == front ;

        }

        // 判断队列是否为空
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
            array[rear] = value ;
            rear = (rear + 1) % maxSize ;   // rear始终指向没有最后一个添加元素的下一个位置，尾指针也要取模,可能尾指针rear还会在头指针front前面
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
            // 这里需要分析出front是指向队列的第一个元素
            // 1. 先把front 对应的值保存到一个临时变量
            // 2. 将front 后移 ,考虑取模
            // 3. 将临时变量保存的变量返回
            int value = array[front] ;
            front = (front + 1) % maxSize ;  // 可能头指针front也会到末尾，故需要取模
            return value ;


        }

        // 显示队列的所有数据，
        // 注意：这里的循环队列不同于单向队列，单向队列不论如何操作，这里的showQueue()方法都是将队列模拟的数组打印出来，但会输出无效数据【这个无太大影响：按front在前，rear在后【先进先出】】
        // 【而循环队列中，可能rear在前，而front在后，这个到无太大影响】就是，前面的元素已经被弹出了，此时不可能再按照队列模拟的数组直接打印出来，即此处要进行避免无效数据的输出。
        public void showQueue(){
            if(isEmpty()){
                System.out.println("队列空，没有数据");
            }

            // 思路：从front开始遍历，遍历多少个元素
            // 动脑筋
            // 从front遍历，相对位移是size(),注意，但后面可能会出现溢出，故最后取下标要取模
            for (int i = front; i < front + size() ; i++) {
                System.out.printf("array[%d]=%d\n", i % maxSize,array[i % maxSize]);    // 取模是为了防止越界
            }
        }

        // 求出当前队列有效数据的个数，否则无法遍历，因为这个是环形的。
        public int size(){
            return (rear - front + maxSize) % maxSize ; // 其实是用到了补数，加上一个模maxSize，防止取模后得到负数
        }

        // 显示队列的头数据，注意不是取出数据【即：并非弹出数据】
        // 这里只是模拟队列的功能
        public int headQueue(){
            // 判断是否为空
            if(isEmpty()){
                throw new RuntimeException("队列空，没有数据") ;
            }

            return array[front] ;   // front指向队列的第一个元素
        }


    }

    public static void main(String[] args) {
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4) ; // 注意：这里设置maxSize为4，其队列的有效数据最大是3，一个是为了判断是否对满
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
