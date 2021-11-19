package com.atguigu.linkedlist;

/**
 * @author shengxiao
 * @date 2021/7/14 11:15
 */
public class DoubleLinkedListDemo {

    // 定义HeroNode2，每一个Hero 对象都是一个节点
    static class HeroNode2 {
        private int no;
        private String name;
        private String nickname;    // 昵称
        private HeroNode2 next;     // 指向下一个节点，默认为null
        private HeroNode2 pre;      // 指向前一个节点，默认为null

        public HeroNode2() {
        }

        // 默认情况下，next和pre没有初始化值时候，会默认赋值为null
        public HeroNode2(int no, String name, String nickname) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
        }

        // 注意：第四个参数next，其实可以不用赋值，因为创建出来的初始节点的next都是null
        public HeroNode2(int no, String name, String nickname, HeroNode2 next, HeroNode2 pre) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
            this.next = next;
            this.pre = pre;
        }

        @Override
        public String toString() {
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }


    // 创建一个双向链表的类
    static class DoubleLinkedList{

        // 先初始化一个投机诶单，头结点不要动，不存放具体的数据
        private HeroNode2 head = new HeroNode2(0,"","") ;

        public HeroNode2 getHead() {
            return head;
        }

        // 遍历双向链表的一个方法
        // 也是从头往后遍历，因为头结点在前面
        public void list(){
            if(head.next == null){
                System.out.println("链表为空");
                return ;
            }
            HeroNode2 temp = head.next ;
            while(temp != null){
                System.out.println(temp);
                temp = temp.next ;
            }
        }

        // 默认添加一个节点到双向链表的尾部
        public void addLastLinkedList(HeroNode2 heroNode){
            HeroNode2 temp = head ;
            while(true){
                if(temp.next == null){
                    //System.out.println("链表为空,也相当于遍历到链表尾部");
                    break  ;    // 跳出循环，此时就可以添加节点到链表尾部
                }
                temp = temp.next ;
            }
            // 当退出了while循环时，temp就指向了链表的最后
            // 形成一个双向链表
            temp.next = heroNode ;
            heroNode.pre = temp ;
            //heroNode.next = temp.next ;  // 不能这样写，因为上面temp.next已将改过，此时默认heroNode.next不指定，则以null显示
        }

        // 按照编号从小到大顺序添加节点
        // 注意：双向链表添加节点的时候也需要找到前驱结点【哨兵节点】/【辅助节点】
        public void addLinkedListByNo(HeroNode2 heroNode){
            // 链表为空也可以添加元素
//            if(head.next == null){
//                System.out.println("链表为空，无法添加");
//                return ;
//            }

            HeroNode2 temp = head ;
            boolean flag = false ;  // 标志添加的编号是否已经存在，默认为false【如果添加重复编号的英雄，则添加无效，这里打一个布尔标记】
            while(true){
                if(temp.next == null){
                    //System.out.println("链表已经遍历完毕");
                    break ;
                }else if(temp.next.no > heroNode.no){
                    break ;
                }else if(temp.next.no == heroNode.no){
                    flag = true ;
                    break ;
                }
                temp = temp.next ;
            }
            if(flag){
                System.out.printf("要添加编号为 %d 的节点已经存在", heroNode.no);
            }else{
                /*
                //为什么下面的这个会报错，显示空指针异常，因为我没有考虑到覆盖导致节点的改变，使得某些行的代码无效【理解错误，其实我是没有考虑到第一个节点的添加，此时此节点的next域为null，不用考虑】
                // 如果添加的节点不是第一个，则要链接节点时候要用四步，这四步其实也有覆盖关系，即综上要考虑是否为添加第一个节点，以及是否存在覆盖节点。
                // 故在指定位置添加节点时，若不是添加第一个节点，则四步代码：最好先链接要添加节点的后面的节点，然后再链接前面的节点。【一般都是先后再前，避免覆盖】
                heroNode.next = temp.next ;
                temp.next.pre = heroNode ;  // 注意：这里的temp.next本来是没有问题的，但此处我还让其指向pre,而temp.next可能压根就没有，故会报空指针异常
                temp.next = heroNode ;
                heroNode.pre = temp ;*/
                // 改为
                //heroNode.next = temp.next ;
                //temp.next = heroNode ;

               /*if(temp.next != null){
//                   heroNode.next = temp.next ;
//                   temp.next = heroNode ;
//                   heroNode.pre = temp ;
//                   heroNode.next.pre = heroNode ;
                    改进代码
//                   heroNode.next = temp.next ;
//                   temp.next.pre = heroNode ;  // 注意：这里的temp.next本来是没有问题的，但此处我还让其指向pre,而temp.next可能压根就没有，故会报空指针异常
//                   temp.next = heroNode ;
//                   heroNode.pre = temp ;

               }else{
                   temp.next = heroNode ;
                   heroNode.pre = temp ;
               }*/
                // 改进代码
                if(temp.next != null){
                    heroNode.next = temp.next ;
                    temp.next.pre = heroNode ;  // 注意：这里的temp.next本来是没有问题的，但此处我还让其指向pre,而temp.next可能压根就没有【】，故会报空指针异常
                }
                // 如果是temp.next == null ,则只能执行下面的两步代码；反之，则四步代码都能执行
                temp.next = heroNode ;
                heroNode.pre = temp ;

            }
        }

        // 修改一个节点的内容,可以看到双向链表的节点内容修改和单向链表一样
        // 只是节点类型改成 HeroNode2
        public void update(HeroNode2 newHeroNode){
            if(head.next == null){
                //System.out.println("链表为空");
                return ;
            }
            HeroNode2 temp = head.next ;
            boolean flag = false ;  // 表示是否找到该节点
            while(true){
                if(temp == null){
                    //System.out.println("已经遍历到链表的尾部");
                    break ;
                }else if(temp.no == newHeroNode.no){
                    flag = true ;
                    break ;
                }
                temp = temp.next ;
            }
            if(flag){
                temp.name = newHeroNode.name;
                temp.nickname = newHeroNode.nickname;
            }else{
                System.out.printf("无法找到编号为 %d 的节点，不能修改\n",newHeroNode.no);
            }
        }

        // 删除双向链表的节点
        // 说明
        // 1. 对于双向链表，我们可以直接找到要删除的这个节点
        // 2. 找到后，自我删除即可
        public void delete(int no){
            if(head.next == null){
                System.out.println("链表为空，无法删除");
                return ;
            }
            HeroNode2 temp = head.next ;
            boolean flag = false ;  // 表示是否找到待删除的节点
            while(true){
                if(temp == null){
                    break ; // 已经遍历到链表的尾部
                }else if(temp.no == no){
                    flag = true ;
                    break ;
                }
                temp = temp.next ;
            }
            if(flag){
                temp.pre.next = temp.next ;
                // 这里我们的代码有问题？
                // 如果是最后一个节点，就不需要执行下面这句活，否则会出现空指针异常【我经常犯这个错误】
                // 因为如果是最后一个节点，则temp.next = null,故其不存在temp.next.pre
                // 注意：这个和上面的addLinkedListByNo()方法要考虑的情况差不多
                if(temp.next != null){
                    temp.next.pre = temp.pre ;
                }

            }else {
                System.out.printf("无法找到编号为 %d 的节点，不能删除\n",no);
            }

        }
    }



    public static void main(String[] args) {
        // 测试
        System.out.println("双向链表的测试");
        // 先创建节点
        HeroNode2 heroNode1 = new HeroNode2(1, "宋江", "及时雨") ;
        HeroNode2 heroNode2 =  new HeroNode2(2, "卢俊义", "玉麒麟") ;
        HeroNode2 heroNode3 = new HeroNode2(3, "吴用", "智多星") ;
        HeroNode2 heroNode4 =  new HeroNode2(4, "林冲", "豹子头") ;
        // 创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList() ;
//        doubleLinkedList.addLastLinkedList(heroNode1);
//        doubleLinkedList.addLastLinkedList(heroNode2);
//        doubleLinkedList.addLastLinkedList(heroNode3);
//        doubleLinkedList.addLastLinkedList(heroNode4);

        // 指定位置插入测试
        System.out.println("指定位置插入测试");
        doubleLinkedList.addLinkedListByNo(heroNode2);
        doubleLinkedList.addLinkedListByNo(heroNode3);
        doubleLinkedList.addLinkedListByNo(heroNode4);
        doubleLinkedList.addLinkedListByNo(heroNode1);

        doubleLinkedList.list();

        // 修改测试
        System.out.println();
        System.out.println("修改测试");

        HeroNode2 newHeroNode = new HeroNode2(4,"小林","haha") ;
        doubleLinkedList.update(newHeroNode);
        doubleLinkedList.list();

        // 删除测试
        doubleLinkedList.delete(3) ;
        System.out.println("删除后的链表情况");
        doubleLinkedList.list();


    }
}
