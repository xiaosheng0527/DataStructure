package com.atguigu.demo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/31 13:47
 */
class Solution {
    /**
     注意：此题并没有对原来链表进行修改，故需要新创建一个链表头
     但此处是无头结点的，

     */

    static class ListNode{
        int val ;
        ListNode next ;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null ;  // 代表新的链表的头结点，此头结点就相当于第一个有数据节点，用于后面的遍历使用，故不能动
        ListNode curNode = null ;   // 尾插法，在链表尾部插入元素，遍历链表
        int carry = 0 ; // 代表进位
        int sum = 0 ;   // 代表当前位的本位和 = 当前位 + 上一位的进位
        while(l1 != null || l2 != null){
            //如果两个链表的长度不同，则可以认为长度短的链表的后面有若干个0
            // 以保证链表长度比较的统一性 ，这里使用三目运算符，条件表达式
            int num1 = (l1 != null) ? l1.val : 0 ;
            int num2 = (l2 != null) ? l2.val : 0 ;
            sum = num1 + num2 + carry ;
            if(head == null){
                head = curNode = new ListNode(sum % 10) ;   // 链表处相应位置的数字
            }
            // 此时添加的就不是第一个节点
            curNode.next = new ListNode(sum % 10) ;
            curNode = curNode.next ;
            carry = sum / 10 ;
            // 当前位的两个数计算完之后，需要移动到后一个位置
            if(l1 != null){
                l1 = l1.next ;
            }
            if(l2 != null){
                l2 = l2.next ;
            }
        }

        // 当l1或者l2在最后一个节点，跳出循环后，可能存在最高位的数字sum的进位，此时需要判断
        if(carry > 0){
            curNode.next = new ListNode(carry) ;
            //curNode = curNode.next ;
        }

        return head ;
    }

    public static void main(String[] args) {
        List<ListNode> linkedList = new LinkedList<>() ;
        linkedList.add(new ListNode(2)) ;
    }
}
