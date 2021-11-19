package com.atguigu.linkedlist;

/**
 * @author shengxiao
 * @date 2021/7/14 16:24
 *
 *          * 注意：为什么小孩报数前，先让first和helper移动k-1次，而不是k次？
 *          * 因为此处要把定位到自己的编号k时，而这个是从编号1开始移动，此时刚开始编号1没有涉及到指针的移动，将其剔除，故只需移动k-1次
 *          * 为什么报数时候，报数为m，但最后，移动的指针，就只移动m-1次?
 *          * 因为刚开始定位到编号k的位置时，此时要报数m下，但编号k自己本身的报数并没哟涉及到指针的移动，故移动到最后要删除节点的位置的过程中，只需移动m-1次
 */
public class Josephu {

    // 创建一个Boy类，表示一个节点
    static class Boy{
        private int no ;    // 编号
        private Boy next ;  // 指向下一个节点

        public Boy() {
        }

        public Boy(int no) {
            this.no = no;
        }

        public Boy(int no, Boy next) {
            this.no = no;
            this.next = next;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public Boy getNext() {
            return next;
        }

        public void setNext(Boy next) {
            this.next = next;
        }
    }

    // 创建一个环形的单向链表，注意：环形单向链表可以有头结点，也可以没有头结点
    // 但是此处是用了，无头结点的思路
    static class CircleSingleLinkedList{

        // 创建一个first节点，当前没有编号,即默认为空
        private Boy first = null ;

        // 添加小孩节点，构建成一个环形的链表
        // nums：代表小孩的人数
        public void addBoy(int nums){
            // nums 做一个数据校验
            if(nums < 1){
                System.out.println("nums的值不正确");
                return ;
            }
            Boy curBoy = null ;     // 辅助指针，帮助构建环形链表
            // 使用for循环来创建我们的环形链表
            for(int i = 1 ; i <= nums ; i++){
                // 根据编号，创建小孩节点
                Boy boy = new Boy(i) ;
                // 如果是第一个小孩
                if(i == 1){
                    first = boy ;   // 让first指针指向新创建的boy节点
                    first.setNext(first);   // 构成环形
                    curBoy = first ;    // 此处first节点不能动【first指针一直指向第一个节点】，让辅助指针curBoy指向第一个小孩，从而以后可以用curBoy帮我们构成环状
                } else{
                    // 让辅助节点【当前节点】指针新创建出来的节点【即先链接，此时之前first.setNext(first)的连接线处于断链】，
                    curBoy.setNext(boy);    // 让辅助节点【当前节点】指针新创建出来的节点【即先链接，此时之前first.setNext(first)的连接线处于断链】
                    boy.setNext(first);    // 再将新创建出来的节点链接到first节点
                    curBoy = boy ;         // 最后让当前辅助节点移动到新创建的节点处，等待下一次的添加
                }
            }
        }

        // 遍历当前的环形链表
        public void showBoy(){
            // 判断链表是否为空 ,
            // 注意：千万不要直接first.next == frist,因为这样写还是说明存在一个小孩【节点】
            if(first == null){
                System.out.println("没有任何小孩");
                return ;
            }
            // 因为first不能动，因此我们任然使用一个辅助指针完成遍历
            Boy curBoy = first ;
            while(true){
                System.out.printf("小孩的编号 %d \n" ,curBoy.getNo());
                if(curBoy.getNext() == first){  // 说明已经遍历完毕
                    break ;
                }
                curBoy = curBoy.getNext() ; // curBoy后移
                // 其实上面这个代码也可以写成curBoy = curBoy.next ;
            }
        }

        // 根据用户端输入，计算出小孩出圈的顺序

        /**
         *
         * @param startNo   表示从第几个小孩开始数数
         * @param countNum  表示数几下
         * @param nums      表示最初有多少小孩在圈中
         */
        public void leaveOrderBoy(int startNo,int countNum,int nums){
            // 先对数据进行校验
            if(first == null || startNo < 1 || startNo > nums){
                System.out.println("参数输入有误，请重新输入");
                return ;
            }
            // 创建一个辅助指针，帮助完成小孩出圈
            Boy helper = first ;
            // 需求创建一个辅助指针（变量）helper，事先应该指向链表的最后这个节点
            while(true){
                if(helper.getNext() == first){   // 说明helper指向了环形链表的最后一个节点
                    break ;
                }
                helper = helper.getNext() ;
            }
            // 小孩报数前，先让 first 和helper 移动 k-1次
            // 注意：移动k-1是为了让报数的第一个小孩始终是first
            for(int j = 0 ; j < startNo - 1 ; j++){
                first = first.getNext() ;   // 注意：这里没有所谓的头结点，即无头结点，也无null
                helper = helper.getNext() ;
            }

            // 当小孩报数时，让first 和 helper 指针同时 移动 m-1 次，然后出圈
            // 这里是一个循环操作，直到圈中只有一个节点
            while(true){
                if(helper == first){    // 说明圈中只有一个节点,注：helper.next == first条件是说明圈中存在至少两个节点的情况
                    break ;
                }
                // 让 first 和 helper 指针同时的移动 countNum - 1，然后出圈
                for(int j = 0 ; j < countNum - 1 ; j++){
                    first = first.getNext() ;
                    helper = helper.getNext() ;
                }
                // 这时first指向的节点，就是要出圈的节点
                System.out.printf("小孩 %d 出圈\n",first.getNo());
                // 这时将first指向后面的小孩节点，然后进行出圈
                first = first.getNext() ;  // 这时将first指向后面的小孩节点
                helper.setNext(first);     // 然后进行出圈,即等价为helper.next = first ;

                /* 注意：上面的两行的指向后面的小孩节点，然后进行出圈不能进行合并，不能用下面的一行代码代替
                因为：这里的代码并没有移动first指针，此时虽然链接上，但因为丢失了first指针【少了first = first.next】，使得后面的小孩节点出现空指针异常
                helper.next = first.next ;
                */
            }
            System.out.printf("最后留在圈中的小孩编号%d\n",first.getNo());
        }
    }

    public static void main(String[] args) {


        // 测试，构建环形链表，和遍历是否ok
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList() ;
        circleSingleLinkedList.addBoy(5);       // 加入5个小孩节点
        circleSingleLinkedList.showBoy();

        // 测试一下，小孩出圈是否正确
        circleSingleLinkedList.leaveOrderBoy(1, 2, 5);   // 2->4->1->5->3
    }
}
