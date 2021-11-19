package com.atguigu.linkedlist;

/**
 * @author shengxiao
 * @date 2021/7/13 19:00
 *
 * 合并两个有序的单链表，合并之后的链表依然有序
 *
 * 解题思路：一定要用一个新的链表来保存LinkedList3
 *
 * 优化的代码写在leedcode里面
 */
public class MergeTwoLinkedListDemo {

    static class LinkedNode{
        private int data ;
        private LinkedNode next ;

        public LinkedNode(int data, LinkedNode next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "LinkedNode{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    static class LinkedList{
        private LinkedNode head = new LinkedNode(0,null) ;

        public LinkedNode getHead() {
            return head;
        }

        // 尾插法
        public void add(LinkedNode linkedNode){
            LinkedNode curNode = head ;
            while(true){
                if(curNode.next == null){
                    // 链表为空
                    break ;
                }
                curNode = curNode.next ;
            }
            linkedNode.next = curNode.next ;
            curNode.next = linkedNode ;
        }

        // 打印链表
        public void list(){
            if(head.next == null){
                System.out.println("链表为空");
                return ;
            }
            LinkedNode curNode = head.next ;
            while(true){
                if(curNode == null){
                    return ;    // 遍历到末尾
                }
                // 输出节点信息
                System.out.println(curNode.data);
                curNode = curNode.next ;
            }
        }

        // 合并两个单链表  // 返回头结点，以便于进行遍历
        // 这里我路跑偏了，就是，原来是不需要后继节点rear的加入，因为我在编写时候的疏忽，其实只需将新链表的尾结点链接到数据小的节点上，
        // 而不用控制数据小的节点的next指针，因为添加到最后，肯定有一个null，如果在前面就一直判断，效率会非常低。
        // 还有，参数最好传递的是两个链表的头结点，因为只要存在头结点，就一定能遍历整个链表
        // 而且合并为新的链表后，无需再进行创建LinkedList linkedList = new LinkedList() ; 直接创建一个虚拟头结点
        //public static LinkedNode mergeLinkedList(LinkedList linkedList1,LinkedList linkedList2) { 最好不要这样写
        public static LinkedNode mergeLinkedList(LinkedNode head1,LinkedNode head2) {

//          LinkedNode head1 = linkedList1.getHead();
//          LinkedNode head2 = linkedList2.getHead();
            LinkedNode cur1 = head1.next;
            //LinkedNode rear1 = null;
            LinkedNode cur2 = head2.next;
            //LinkedNode rear2 = null;
            //LinkedList linkedList3 = new LinkedList();
            //LinkedNode head3 = linkedList3.getHead();
            LinkedNode head3 =  new LinkedNode(0, null) ;
            LinkedNode cur3 = head3;


            // 注意：这个前面的两个判断其实可以不用写，直接在最后进行判断
            if (head1.next == null) {
                //return head2 ;
                return head2;
            }
            if (head2.next == null) {
                //return head1 ;
                return head1;
            }


            // 若有一个链表遍历完毕，则另外一个链表剩余的的节点就直接接到后面
            while (cur1 != null && cur2 != null) {
                if (cur1.data <= cur2.data) {
                    //rear1 = cur1.next;
                    //cur1.next = cur3.next;
                    cur3.next = cur1;
                    cur3 = cur3.next;
                    cur1 = cur1.next;

                } else {
                    //rear2 = cur2.next;
                    cur3.next = cur2;
                    //cur2.next = cur3.next;

                    //cur2 = rear2;
                    cur3 = cur3.next ;
                    cur2 = cur2.next ;
                }
            }
            // 任意为空，直接连接另一条链表
            // 直接链接起来，下面是我的错误代码
            if(cur1 == null){
                cur3.next = cur2 ;
            }else{
                cur3.next = cur1 ;
            }
            return head3 ;
            /*while (cur1 == null) {
                //rear2 = cur2.next;
                cur2.next = cur3.next;
                cur3.next = cur2;
                //cur2 = rear2;
                cur3 = cur3.next;
                linkedList3.list();
                return null;
            }
            while (cur2 == null) {
                //rear1 = cur1.next;
                //cur1.next = cur3.next;
                cur3.next = cur1;
                //cur1 = rear1;
                cur3 = cur3.next;
                linkedList3.list();
                return null;
            }
*/

            /*LinkedNode head = null ;    // 新的链表头结点
            if(head1 == null){
                return head2 ;
            }
            if(head2 == null){
                return head1 ;
            }
            if(head1.data <= head2.data){
                head = head1 ;
            }*/

        }
    }

    public static void main(String[] args) {

        LinkedNode linkedNode1 = new LinkedNode(1, null) ;
        LinkedNode linkedNode2 = new LinkedNode(2, null) ;
        LinkedNode linkedNode3 = new LinkedNode(7, null) ;
        LinkedNode linkedNode4 = new LinkedNode(6, null) ;
        LinkedNode linkedNode5 = new LinkedNode(9, null) ;
        LinkedNode linkedNode6 = new LinkedNode(8, null) ;

        LinkedList linkedList1 = new LinkedList() ;
        linkedList1.add(linkedNode1);
        linkedList1.add(linkedNode3);
        linkedList1.add(linkedNode5);
        linkedList1.list();

        LinkedList linkedList2 = new LinkedList() ;
        linkedList2.add(linkedNode2);
        linkedList2.add(linkedNode4);
        linkedList2.add(linkedNode6);
        linkedList2.list();

        System.out.println("合并后的链表：");
        LinkedNode head3Node =  LinkedList.mergeLinkedList(linkedList1.getHead(), linkedList2.getHead());
        LinkedNode cur = head3Node.next ;
        while(cur != null){
            System.out.println(cur.data);
            cur = cur.next ;
        }


    }
}
