package com.atguigu.stack;

import javax.management.ListenerNotFoundException;

/**
 * @author shengxiao
 * @date 2021/7/15 20:09
 * 注意：利用单链表模拟栈的功能时，
 *  插入的方法是头插法
 *  注意：这里不使用头结点，即无头结点，第一个节点就是添加了数据的节点
 */
public class LinkedListStackDemo {

    static class LinkedNode{
        private int data ;
        private LinkedNode next ;

        public LinkedNode() {
        }

        public LinkedNode(int data) {
            this.data = data;
        }

        public LinkedNode(int data, LinkedNode next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "LinkedNode{" +
                    "data=" + data +
                    '}';
        }
    }

    static class LinkedListStack{

        private int top = -1 ;  // 初始化栈顶节点
        private int maxSize ;
        private LinkedNode head = new LinkedNode(0) ;    // 默认头结点的data域的值为0，赋不赋值都一样，默认初始化

        public LinkedListStack(int maxSize) {
            this.maxSize = maxSize;
        }

        public boolean isEmpty(){
            return top == -1 ;
        }

        public boolean isFull(){
            return top == maxSize -1 ;
        }

        // 入栈
        public void addFirst(LinkedNode linkedNode){

            if(isFull()){
                System.out.println("链表栈已满，无法添加");
                return ;
            }
            top++ ;
            linkedNode.next = head ;
            head = linkedNode ;

        }

        // 出栈
        public int removeFirst(){

            if(isEmpty()){
                //System.out.println("链表栈已空，无法继续出栈");
                throw new RuntimeException("链表栈已空，无法继续出栈") ;
            }
            int value = head.data ;
            head = head.next ;
            return value ;
        }

        // 遍历栈，后进先出，此时并没有出栈
        public void list(){

//            LinkedNode curNode = head ;
//            while(curNode != null){
//                System.out.printf("");
//            }
            LinkedNode curNode = head ;
            System.out.println("遍历顺序");
            for(int i = top ; i >= 0 ; i--){
                if(curNode == null || curNode.next == null){
                    return ;    // 已经遍历到最后
                }
                System.out.printf("链表栈LinkedListStack[%d]=%d\n",i,curNode.data);
                curNode = curNode.next ;
            }
        }
    }

    public static void main(String[] args) {

        LinkedNode linkedNode1 = new LinkedNode(1) ;
        LinkedNode linkedNode2 = new LinkedNode(6) ;
        LinkedNode linkedNode3 = new LinkedNode(8) ;
        LinkedNode linkedNode4 = new LinkedNode(10) ;
        LinkedListStack linkedListStack = new LinkedListStack(3) ;
        linkedListStack.addFirst(linkedNode1);
        linkedListStack.addFirst(linkedNode2);
        linkedListStack.addFirst(linkedNode3);
        linkedListStack.list();
        linkedListStack.addFirst(linkedNode4);

        //弹栈测试
        System.out.println();
        System.out.println("弹栈测试");
        System.out.println("第一次弹栈" + linkedListStack.removeFirst());
        System.out.println("第二次弹栈" + linkedListStack.removeFirst());
        System.out.println("第三次弹栈" + linkedListStack.removeFirst());

        linkedListStack.list();
    }
}
