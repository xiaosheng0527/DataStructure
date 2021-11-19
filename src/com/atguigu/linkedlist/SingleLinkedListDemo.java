package com.atguigu.linkedlist;

import java.util.Stack;

/**
 * @author shengxiao
 * @date 2021/7/12 21:44
 */
public class SingleLinkedListDemo {

    // 定义HeroNode,每个HeroNode 对象就是一个节点
    static class HeroNode{
        private int no ;
        private String name ;
        private String nickname ;
        private HeroNode next ;     // 指向下一个节点

        public HeroNode() {
        }

        // 默认没有给定next赋值时候，默认为null
        public HeroNode(int no, String name, String nickname) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
        }

        // 注意：第四个参数next，其实可以不用赋值，因为创建出来的初始节点的next都是null
        public HeroNode(int no, String name, String nickname, HeroNode next) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
            this.next = next;
        }

        // 为了显示方法，我们重新写toString
        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }


    }

    // 定义SingleLinkedList  管理我们的英雄
    static class SingleLinkedList{
        // 先初始化一个头结点,头结点不要动，不存放具体的数据
        // 注意：创建新的节点时候，第四个参数可写可不写，因为默认情况下赋值也是null
        private HeroNode head = new HeroNode(0,"","",null) ;

        public HeroNode getHead() {
            return head;
        }

        // 添加节点到单向链表
        // 思路：当不考虑编号顺序时
        //1. 找到当前链表的最后节点
        //2. 将最后这个这个节点的next指向 新的节点
        // 尾插法
        public void add(HeroNode heroNode){

            // 因为head节点不能动，因为我们需要一个辅助遍历 temp
            // 注意：这里不需要判断链表是否为空，没有意义
            HeroNode temp = head ;
            while(true){
                // 注意：判断条件，如果初始化就进行next，此时在判断时候，就不能next;如果初始化没有进行next，此时在判断时，就要next
                if(temp.next == null){
                    break ;     // 遍历到链表末尾  ，这里一定不能写成return，否则问题很严重
                }
                // 如果没有找到最后，将temp后移
                temp = temp.next ;
            }
            // 当退出while循环时，temp就指向了链表的最后
            // 将最后这个节点的next 指向 新的节点
            temp.next = heroNode ;
            //heroNode.next = null ; 这行代码不用写，因为在构造方法执行时，会初始化实例变量
        }

        //  第二种方式在添加英雄时，根据排名将英雄插入到指定位置(如果有这个排名，则添加失败，并给出提示)
        public void addByOrder(HeroNode heroNode){
            // 因为头结点不能动，因此我们任然通过一个辅助指针(变量)来帮助找到添加的位置
            // 因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
            // 此处添加节点的时候，也需要注意刚开始preNode=head,而不能preNode=head.next,否则假设添加到头结点后的第一个位置，那么会因为找不到preNode,而添加失败
            HeroNode preNode = head ;
            boolean flag = false ;  // 标志添加的编号是否存在，默认为false【如果添加重复编号的英雄，则添加无效，这里打一个布尔标记】
            while(true){
                if(preNode.next == null){   // 说明pre已经在链表的最后,此时可以添加到尾部
                    //System.out.println("链表为空，无法插入");
//                    heroNode.next = preNode.next ; 这两行代码不要在这里写，下面的if(flag){}语句统一编写，简化代码
//                    preNode.next = heroNode ; ;
                    //return ;  此处不使用return，是因为，下面还要执行if(flag){}语句，进行显示给用户
                    break ;
                }
                // 要让preNode.next的no值和heroNode的no值进行比较，若前面大于后面，就让heroNode添加到preNode和preNode.next的中间
                // 通俗来讲：让preNode的后一个节点和要插入节点进行比较，若前面大于后面，则可以插入，而且插入过程中还需要用到preNode
                if(preNode.next.no > heroNode.no){  //  位置找到，就在preNode的后面插入
                    break ;

                }else if(preNode.next.no == heroNode.no){   // 说明希望添加的heroNode的编号已经存在
                    flag = true ;   // 说明编号存在
                    break ;
                }
                // 执行下面这个语句是因为，preNode.next.no < heroNode.no,此时需要移动preNode指针
                preNode = preNode.next ;    // 后移，遍历当前链表

            }
            // 判断flag的布尔值,这个方法很好
            if(flag){
                System.out.printf("准备插入的英雄编号 %d 已经存在了，不能加入\n",heroNode.no);
            }else{
                // 插入到链表中，preNode的后面
                // 注意：这里的链接的两步，一定要链接后面的然后再链接前面，不然容易出现空指针异常，或者是栈溢出，或者是节点覆盖
                heroNode.next = preNode.next ;
                preNode.next = heroNode ;
            }
        }

        // 修改节点的信息，根据no编号来修改，即no编号不能改【而且不能替换节点】
        // 说明
        // 1. 根据newHeroNode 的 no 来修改即可
        public void update(HeroNode newHeroNode){
            // 判断是否为空
            if(head.next == null){
                System.out.println("链表为空");
                return ;
            }

            /* 我也学一下老师，打布尔标记，很方便
            while(true){

                if(preNode == null){
                    System.out.println();
                    return ;
                }else if(preNode.next.no == newHeroNode.no){
                    newHeroNode.next = preNode.next.next ;
                    preNode.next = newHeroNode ;
                    return ;
                }
                preNode = preNode.next ;
            }*/
            // 找到需要修改的节点，根据no编号
            // 定义一个辅助变量
            HeroNode preNode = head.next ;
            boolean flag = false ;  // 表示是否找到该节点
            while(true){

                if(preNode == null){
                    break ;     // 已经遍历完链表，此处并不是说链表为空
                }else if(preNode.no == newHeroNode.no){
                    // 题目理解错误，以为是把整个节点进行替换
//                    newHeroNode.next = preNode.next.next ;
//                    preNode.next = newHeroNode ;
                    flag = true ;
                    break ;
                }
                preNode = preNode.next ;
            }
            // 根据flag 判断是否找打要修改的节点
            if(flag){
                preNode.name = newHeroNode.name;
                preNode.nickname = newHeroNode.nickname;
                System.out.println("根据no修改节点成功");
            }else{
                // 没有找到
                System.out.printf("无法找到编号为 %d 的节点，不能修改\n",newHeroNode.no);
            }
        }

        // 删除节点
        // 思路
        // 1. head 不能动，因此我们需要一个preNode辅助节点，找到待删除节点的前一个节点
        public void deleteByNo(int no){
            /*
            注意：我这里写为head.next == null ，然后后面preNode = head.next,那么如果要删除第一个节点时候，就找不到前一个节点pre，故导致要删除的节点永远也删除不了
            if(head.next == null){
                System.out.println("链表为空，无法删除");
                return ;
            }
            HeroNode preNode = head.next ;
            boolean flag = false ;  // 标记是否找到待删除节点
            while(true){
                if(preNode == null){
                    //System.out.println("已经遍历到链表尾部");
                    break;
                }else if(preNode.next.no == no){    // 找到要删除的节点
                    flag = true ;
                    break ;
                }
                preNode = preNode.next ;
            }
            if(flag){
                preNode.next = preNode.next.next ;
            }else{
                System.out.printf("无法找到编号为 %d 的节点，不能删除\n",no);
            }*/
            // 注意：此时必须写成前面preNode为head，后面为preNode.next,这样才可以删除
            HeroNode preNode = head ;
            boolean flag = false ;  // 标记是否找到待删除节点
            while(true){
                if(preNode.next == null){   // 此处的if条件判断是否遍历到链表尾部，相当于变相的判断了链表是否为空
                    //System.out.println("已经遍历到链表尾部");
                    break;
                }else if(preNode.next.no == no){    // 找到要删除的节点
                    flag = true ;
                    break ;
                }
                preNode = preNode.next ;
            }
            if(flag){
                preNode.next = preNode.next.next ;
            }else{
                System.out.printf("无法找到编号为 %d 的节点，不能删除\n",no);
            }

        }

        // 显示链表【遍历】
        public void list(){
            // 判断链表是否为空
            if(head.next == null){
                System.out.println("链表为空");
                return ;
            }
            // 因为头结点，不能动，因此我们需要一个辅助变量
            HeroNode temp = head.next ;
            while(true){
                // 注意：这里不能写成temp.next == null，否则此处判断temp会跳过中间的一个节点，因为上面已经是temp = head.next
                // 注意：判断条件，如果初始化就进行next，此时在判断时候，就不能next;如果初始化没有进行next，此时在判断时，就要next
                if(temp == null){
                    break;  // 退出 条件 ，已经遍历完链表，此处并不是说链表为空
                }
                // 输出节点的信息
                System.out.println(temp);
                // 将temp后移，一定小心，若没加这句，会造成死循环
                temp = temp.next ;
            }
        }

        // 新浪面试题：获取到单链表的节点的个数（如果是带头结点的链表，需要不统计头结点）
        /**
         *
         * @param head  链表的头结点
         * @return      返回的就是有效节点的个数
         */
        public static int nodeCount(HeroNode head){

            if(head.next == null){  // 空链表
                return 0 ;
            }
            int count = 0 ;
            HeroNode temp = head.next ;
            while(true){
                if(temp == null){   // 遍历到尾结点
                    return count ;
                }
                temp = temp.next ;
                count++ ;
            }
        }

        // 新浪面试题： 查找单链表中的倒数第k个节点【应该不算头结点】
        //思路
        //1. 编写一个方法，接收head节点，同时接收一个index
        //2. index 表示是倒数第index个节点,index范围是倒数第一个到第maxSize个
        //3. 先把链表从头到尾遍历，得到链表的总的长度 getLength
        //4. 得到size 后，我们从链表的第一个开始遍历 (size-index)个，就可以得到
        //5. 如果找到了，则返回该节点，否则返回null
        public static HeroNode nodeBackSearchByIndex(HeroNode head,int index){
            if(head.next == null){
                System.out.println("链表为空");
                return null ;   // 没有找到
            }
            // 第一次遍历得到链表的长度（节点个数）【不包括头结点】
            int size = nodeCount(head) ;
            // 第二次遍历 size-index 位置，就是我们倒数的第k个节点
            // 但还是要对index进行合法性判断
            // index == size : 这里若是此条件，则取出的节点就是head.next
            if(index <= 0 || index > size){
                return null ;
            }
            // 定义辅助变量，
            HeroNode temp = head.next ;
            // 注意：上面已经限制了index的范围和判断了非空链表，故下面就无需重复判断,加上while(true)多此一举
            /*while(true){
                if(temp == null){
                    System.out.println("遍历到尾结点此时，没有找到");
                }
                for(int i = 0 ; i < (size - index) ; i++){
                    temp = temp.next ;
                }
                return temp ;
            }*/
            for(int i = 0 ; i < (size - index) ; i++){
                temp = temp.next ;
            }
            return temp ;

        }

        // 逆序打印单链表的节点
        // 切记：题目可以先进行反转链表，但效率会大打折扣，最好的办法是维护一个栈的数据结构，先进后出
        // 方式2：
        // 可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，就实现了逆序打印的效果
        public static void reversePrint(HeroNode head){
            if(head.next == null){
                return ;    // 空链表，无法打印
            }
            // 创建要给的一个栈，将各个节点压入栈中
            Stack<HeroNode> nodeStack = new Stack<>() ;
            HeroNode curNode = head.next ;
            /*while(true){
                if(curNode == null){
                    return ;    // 遍历结束
                }
                nodeStack.add(curNode) ;
                curNode = curNode.next ;
            }*/
            // 这样写更加简便
            while(curNode != null){
                nodeStack.push(curNode) ;
                curNode = curNode.next ;    // cur后移，这样就可以压入下一个节点
            }
            // 将栈中的节点进行打印，pop出栈
            while(nodeStack.size() > 0){
                System.out.println(nodeStack.pop());    // 栈的特点是先进后出
            }
        }

        // 将单链表进行反转【腾讯面试题】
        // 注意：最后反转后的节点必须是原来创建的，可以引入节点变量作为临时节点
        // 思路：将每一个节点都取出来利用头插法插入到新的链表中，然后，最后将原来的头结点再替换反转头节点，生成最后的反转单链表
        public static void reverseLinkedList(HeroNode head){
            // 如果当前链表为空，或者只有一个节点，无需反转，直接返回
            if(head.next == null || head.next.next == null){
                //System.out.println("空链表");
                return ;
            }

            // 定义一个辅助指针（变量），帮助我们遍历原来的元素
            HeroNode curNode = head.next ;
            HeroNode rearNode = null ;  // 指向当前节点【curNode】的下一个节点，避免当节点移动到反转链表上时，原来的链表的下一个节点就找不到了
            HeroNode reverseHeadNode = new HeroNode(0, "", "", null) ;
            // 遍历原来的链表
            // 每遍历一个节点，就将其取出，并放在新的链表reverseHeadNode的最前端
            while(true){
                if(curNode == null){
                    //System.out.println("已经遍历到链表尾部");
                    // 此处千万不能用return，否则会无法执行此代码之后的代码：会显示unreachable statement异常
                    // 怪不得head.next = reverseHeadNode.next ;这行代码一直显示报错，但也没有什么具体的错误提示
                    break ;
                }
                rearNode = curNode.next ;   // 先链接，此时curNode还没移动，只是通过rearNode来保存下一个节点
                // 将curNode的下一个节点指向新的链表的最前端，*****这个最难想*****
                // 因为刚开始reverseHeadNode.next=null，没有指向节点，故想得会复杂一些，其实我们可以将null也看做一个节点，此时就比较好理解
                // 第二、三次头插法其实也就是那样写，
                curNode.next = reverseHeadNode.next ;
                reverseHeadNode.next = curNode ;
                curNode = rearNode ;    //  将当前节点移动到下一个节点处

            }
            // 此时将原先的头结点head替代刚才创建的反转头结点reverseHeadNode，因为最后反转后的节点必须是原来创建的
            // 将head.next 指向 reverseHead.next
            head.next = reverseHeadNode.next ;

            /*while(curNode != null){
                rearNode = curNode.next ;   // 先链接，此时curNode还没移动，只是通过rearNode来保存下一个节点
                curNode.next = reverseHeadNode.next ;  // 将curNode的下一个节点指向新的链表的最前端，*****这个最难想*****
                reverseHeadNode.next = curNode ;
                curNode = rearNode ;
            }
            // 将head.next 指向 reverseHead.next
            //reverseHeadNode.next ;
            head.next = reverseHeadNode.next ;*/

        }

    }

    public static void main(String[] args) {
        // 测试
        // 先创建节点
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨", null) ;
        HeroNode heroNode2 =  new HeroNode(2, "卢俊义", "玉麒麟", null) ;
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星", null) ;
        HeroNode heroNode4 =  new HeroNode(4, "林冲", "豹子头", null) ;

        // 创建要给链表
        SingleLinkedList singleLinkedList = new SingleLinkedList() ;
        // 加入
//        singleLinkedList.add(heroNode1);
//        singleLinkedList.add(heroNode2);
//        singleLinkedList.add(heroNode3);
//        singleLinkedList.add(heroNode4);

        // 加入按照编号的顺序
        singleLinkedList.addByOrder(heroNode1);
        singleLinkedList.addByOrder(heroNode4);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode3);
        //singleLinkedList.addByOrder(heroNode3);

        // 显示
        singleLinkedList.list() ;

        // 测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2, "鲈鱼", "仙人掌",null) ;
        singleLinkedList.update(newHeroNode);

        System.out.println("修改后的链表情况");
        // 显示
        singleLinkedList.list() ;

        // 删除一个节点，【注意：测试的时候最好测试第一个和最后一个，因为代码出现bug可能就出现在边界】
        singleLinkedList.deleteByNo(4);
        System.out.println("删除后的链表情况");
        // 显示
        singleLinkedList.list() ;

        // 测试一下  求单链表中有效节点的个数
        System.out.println("有效节点的个数" + SingleLinkedList.nodeCount(singleLinkedList.getHead()));

        // 测试一下  查找单链表中的倒数第k个节点【应该不算头结点】
        System.out.println("倒数第3个节点为：" + SingleLinkedList.nodeBackSearchByIndex(singleLinkedList.getHead(), 3));


        // 测试一下，单链表的反转功能
        System.out.println("原来链表的情况~~~");
        singleLinkedList.list();
        // 原地操作，即对原来的单链表有影响
        System.out.println("链表的反转情况~~~");
        SingleLinkedList.reverseLinkedList(singleLinkedList.getHead());
        singleLinkedList.list();

        // 测试一下，逆序打印单链表
        // 非原地操作，即对原来的单链表不影响
        System.out.println("逆序打印单链表");
        SingleLinkedList.reversePrint(singleLinkedList.getHead());
    }
}
