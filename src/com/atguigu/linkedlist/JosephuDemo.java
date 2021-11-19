package com.atguigu.linkedlist;

import javax.swing.plaf.IconUIResource;

/**
 * @author shengxiao
 * @date 2021/7/15 8:56
 *
 */
public class JosephuDemo {

    static class People{
        private int no ;
        private People next ;

        public People() {
        }

        public People(int no) {
            this.no = no;
        }

        public People(int no, People next) {
            this.no = no;
            this.next = next;
        }

        @Override
        public String toString() {
            return "People{" +
                    "no=" + no +
                    '}';
        }
    }

    // 这里的链表为无头结点
    static class SingleCircleLinkedList{

        private People first = null ;

        // 根据 输入的 人的个数来创建单向循环链表
        // 先进行参数校验

        /**
         *
         * @param nums  在圈中的人数
         */
        public void addPeople(int nums){
            if(nums < 1){
                System.out.println("参数nums输入的值无效，请重新输入");
                return ;
            }

            // 注意：这个千万不能放在for循环里面，不然会造成循环一次，重置一次
            People curPeople = null ;   // 定义辅助节点，初始值为空，来帮助我们来创建单向循环链表
            for(int i = 1 ; i <= nums ; i++){

                People people = new People(i) ;

                if(i == 1){
                    first = people ;
                    //people.next = first ;为什么不能写成people.next = first,而必须写成first.next = first ;
                    // 因为，此时要以first为主导节点【第一个节点】
                    first.next = first ;
                    curPeople = first ;

                }else{
                    // 添加的不是第一个节点
                    curPeople.next = people ;
//                    boy.setNext(first);    // 再将新创建出来的节点链接到first节点
//                    curBoy = boy ;         // 最后让当前辅助节点移动到新创建的节点处，等待下一次的添加,
                    // 我这里调换顺序，感觉也没什么问题，待会试试，确实没问题，因为没有影响睡眠
                    curPeople = curPeople.next ;
                    curPeople.next = first ;
                }
            }
        }

        // 遍历单向循环链表
        public void list(){
            if(first == null){
                System.out.println("圈中没有人，无法遍历");
                return ;
            }
            People curPeople = first ;  // 注意：这个千万不要放在while(true)循环内部，否则first一直是指向第一个节点，就会无限遍历
            while(true){

                System.out.printf("pepole的编号为 %d \n", curPeople.no);
                if(curPeople.next == first){
                    //System.out.println("遍历结束");
                    break ;
                }
                curPeople = curPeople.next ;

            }
        }

        // 出圈顺序

        /**
         * 注意：为什么小孩报数前，先让first和helper移动k-1次，而不是k次？
         * 因为此处要把定位到自己的编号k时，而这个是从编号1开始移动，此时刚开始编号1没有涉及到指针的移动，将其剔除，故只需移动k-1次
         * 为什么报数时候，报数为m，但最后，移动的指针，就只移动m-1次?
         * 因为刚开始定位到编号k的位置时，此时要报数m下，但编号k自己本身的报数并没哟涉及到指针的移动，故移动到最后要删除节点的位置的过程中，只需移动m-1次
         * @param startNo   初始人的位置
         * @param countNum  报几个数
         * @param nums      圈中的人数
         */
        public void removeOrder(int startNo,int countNum,int nums){
            // 参数校验,为了保证至少有一个people节点
            if(startNo < 1 || startNo > nums || first == null){
                System.out.println("参数无效，请重新输入");
                return ;
            }

            // 定义一个辅助指针，方便删除节点
            //移动k-1个指针，使得到达起始位置
            //People prePeople = null ;
            //prePeople.next = first ;    // 刚开始指辅助节点在first节点的前一个位置,错，这里会出现空指针异常，因为上面设置prePeople=null
            // 故，最好的办法是，通过已知的节点，将prePeople先遍历到最后一个节点处
            People prePeople = first ;
            // 需求创建一个辅助指针（变量）prePeople，事先应该遍历到单向循环链表的末尾处，使其指向链表的最后这个节点
            while(true){
                if(prePeople.next == first){
                    break ;
                }
                prePeople = prePeople.next ;
            }

            for(int i = 0 ; i < startNo - 1 ; i++){
                /*if(first == prePeople){
                    //System.out.printf("最后一个people的编号为 %d \n",prePeople.no);
                    break ;
                }*/
                first = first.next ;    // first指针已经指向初始位置
                prePeople = prePeople.next ;
            }

            // 移动m-1个指针，找到要删除的节点的位置
            // 当小孩报数时，让first 和 helper 指针同时 移动 m-1 次，然后出圈
            // 这里是一个循环操作，直到圈中只有一个节点，然后退出循环，剩下最后一个人，胜利
            while(true){
                if(prePeople == first){     // 说明圈中只有一个节点,注：helper.next == first条件是说明圈中存在至少两个节点的情况
                    break ;
                }

                for(int i = 0 ; i < countNum - 1 ; i++){
                    first = first.next ;
                    prePeople = prePeople.next ;
                }

                // 删除指定的节点
                // 这时first指向的节点，就是要出圈的节点
                System.out.printf("people %d 出圈\n",first.no);
                first = first.next ;
                prePeople.next = first ;
            }
            System.out.printf("最后留在圈中的人的编号为 %d \n",first.no);

        }
    }


    public static void main(String[] args) {

        SingleCircleLinkedList singleCircleLinkedList = new SingleCircleLinkedList() ;
        singleCircleLinkedList.addPeople(5);
        singleCircleLinkedList.list();

        singleCircleLinkedList.removeOrder(1, 2, 5);
    }
}
