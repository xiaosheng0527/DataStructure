package com.atguigu.hashtab;

import java.util.Scanner;

/**
 * @author shengxiao
 * @date 2021/7/22 22:15
 *
 * // 注意：假定本代码中的id不重复，即不会出现哈希冲突；因为若一个链表中两个节点的id都一样，说明会出现冲突
 *
 * 头结点：
 * 在单链表第一个元素结点之前设置的一个结点， 数据域可以不存任何信息，指针域指向单链表第一个元素的结点。
 * 对于单链表来说， 头结点可有可无，但为了操作方便，一般情况下单链表都具有头结点，后面的分析将会区别一下有头结点和没有头结点的区别。
 * 优点：
 * 减少了单链表添加删除时特殊情况的判断，减少了程序的复杂性，主要是添加和删除在第一个有元素的结点（首元结点）上有区别，
 * 如果链表没有头结点，则删除或添加时都得需要判断一次首元结点，有了头结点以后，首元结点实际为链表的第二个结点，
 * 使得所有的元素结点的添加删除更具有统一性，
 *
 * 总结：1.没有头结点，则在 插入 和 删除 时需要对第一个节点和最后一个节点进行特殊性判断，很复杂，可以看下面的 addByOrder() 方法
 *      2.有头结点，则完全可以通过next域依次进行判断。
 */
public class HashTabDemo {

    public static void main(String[] args) {
        // 创建哈希表
        HashTab hashTab = new HashTab(7) ;

        // 写一个简单的菜单
        String key = "" ;
        Scanner scanner = new Scanner(System.in) ;
        while(true){
            System.out.println("add          :    添加雇员");
            System.out.println("addByOrder   :    按序添加雇员");
            System.out.println("show         :    显示雇员");
            System.out.println("find         :    查找雇员");
            System.out.println("delete       :    删除雇员");
            System.out.println("exit         :    退出系统");

            key = scanner.next() ;
            switch(key){
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt() ;
                    System.out.println("输入名字");
                    String name = scanner.next() ;
                    // 创建 雇员
                    Emp emp = new Emp(id , name) ;
                    hashTab.addById(emp);
                    break ;
                case "addByOrder":
                    System.out.println("输入id");
                    id = scanner.nextInt() ;
                    System.out.println("输入名字");
                    name = scanner.next() ;
                    // 创建 雇员
                    emp = new Emp(id , name) ;
                    hashTab.addOrderById(emp);
                    break ;
                case "show":
                    hashTab.showList() ;
                    break ;
                case "find":
                    System.out.println("请输出要查找的id");
                    id = scanner.nextInt() ;
                    hashTab.queryEmpById(id);
                    break ;
                case "delete":
                    System.out.println("请输出要删除的id");
                    id = scanner.nextInt() ;
                    hashTab.removeById(id);
                    break ;
                case "exit":
                    scanner.close() ;
                    System.exit(0);
                default:
                    break ;
            }
        }
    }
}

// 创建HashTab    管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray ;    // 链表数组
    private int size ;  // 表示共有多少条链表

    // 构造器
    public HashTab(int size){
        this.size = size ;
        // 初始化empLinkedListArray
        empLinkedListArray = new EmpLinkedList[size] ;
        // 留一个坑？？？
        // 这时不要忘了，这时不要忘记要分别初始化每个链表
        // 因为如果没有初始化每个链表时，则每一个链表在堆中就没有开辟一块空间，
        // 之后对其进行的任何操作，都会造成空指针异常
        for(int i = 0 ; i < size ; i++){
            empLinkedListArray[i] = new EmpLinkedList() ;
        }
    }

    // 添加雇员
    // addById：注意：此处添加雇员并不是随机存放的，而是按照顺序添加到尾部，
    // 因为其内部EmpLinkedList链表的add()方法是尾插法
    // 而这里我命名为addById，是为了说明要通过id，然后通过哈希函数得到其存放位置。
    public void addById(Emp emp){
        // 根据员工的id，得到该员工的应当添加到哪个链表
        int empLinkedListNo = hashFun(emp.id) ;
        // 将emp 添加到对应的链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 添加雇员，按照id的升序进行添加
    public void addOrderById(Emp emp){
        // 根据员工的id，得到该员工的应当添加到哪个链表
        int empLinkedListNo = hashFun(emp.id) ;
        // 将emp 添加到对应的链表中
        empLinkedListArray[empLinkedListNo].addSeqById(emp);
        //empLinkedListArray[empLinkedListNo].addSeqById(emp);
    }

    // 根据输入的id，查找雇员
    public void queryEmpById(int id){
        // 使用散列函数确定到哪条链表查找，而id是我们要查找的id，不能再使用哈希函数
        int empLinkedListNo = hashFun(id) ;
        Emp emp = empLinkedListArray[empLinkedListNo].findEmpById(id) ;
        if(emp != null){    // 找到
            System.out.printf("在第%d条链表中找到 雇员 id = %d\n",(empLinkedListNo + 1),id);
        }else{
            System.out.println("在哈希表中，没有找到该雇员");
        }
    }

    // 根据输入的id，删除雇员
    public void removeById(int id){
        // 使用散列函数确定到哪条链表查找，而id是我们要查找的id，不能再使用哈希函数
        int empLinkedListNo = hashFun(id) ;

        empLinkedListArray[empLinkedListNo].deleteById(id);

    }

    // 编写散列函数，使用一个简单取模法
    public int hashFun(int id){
        return id % size ;
    }

    // 遍历所有的链表,遍历hashtab
    public void showList(){
        for(int i = 0 ; i < size ; i++){
            empLinkedListArray[i].list(i);
        }
    }


}

// 表示一个雇员
class Emp{
    public int id ;
    public String name ;
    public Emp next ;   // next默认为null

    public Emp() {
    }

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// 创建EmpLinkedList，表示链表
class EmpLinkedList{
    // 头指针，执行第一个Emp，因此我们这个链表的head是直接指向第一个Emp【即此链表无头节点】
    // 而如果链表有头结点，那么此时需要创建出一个头结点，即new Emp() ;
    private Emp head ;  // 默认为null

    // 添加雇员到链表，此处是默认添加，即尾插法
    // 说明
    // 1. 假定，当添加雇员时，id是自动增长，即id的分配总是从小到大
    //     因此我们将该雇员直接加入到本链表的最后即可
    public void add(Emp emp){   // 相当于尾插法
        // 如果是添加第一个雇员
        if(head == null){
            head = emp ;
            return ;
        }
        // 如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
        Emp curEmp = head ;
        /* 其实这个和下面的是
        while(curEmp.next != null){     // 判断链表是否到最后
            curEmp = curEmp.next ;  // 后移
        }
        // 退出时直接将emp 加入链表
        //emp.next = curEmp.next ; 注意：这一步可以不用写，因为创建出来的emp对象，默认其emp.next = null ,默认初始化
        curEmp.next = emp ;*/
        while(true){
            if(curEmp.next == null){    // 说明遍历到最后
                break ;
            }
            curEmp = curEmp.next ;  // 后移
        }
        // 退出时直接将emp加入链表
        curEmp.next = emp ;
    }

    // 根据id值来删除
    public void deleteById(int id){
        if(head == null){
            System.out.println("id值经过哈希函数后，链表为空，无法删除");
            return ;
        }
        //要删除的元素与头结点的元素相同
        while(head != null && head.id == id){
            System.out.printf("正在删除id=%d的雇员\n",id);
            head = head.next ;
            return ;
        }

        /**
         * 上面已经对头节点判别是否要进行删除
         * 所以要对头结点的下一个结点进行判别
         */
        // 下面是说明要删除的是非头结点的元素
        Emp curEmp = head ; // 注意：上面对于头结点的元素判断时候不满足，才执行下面的代码
        while(curEmp != null && curEmp.next != null){   // 说明此节点不是最后一个节点
            if(curEmp.next.id == id){
                System.out.printf("正在删除id=%d的雇员",id);
                curEmp.next = curEmp.next.next ;    // 这行很经典
            }
            else{   // curEmp.next.id ！= id  即当前节点的id值没有匹配上
                curEmp = curEmp.next ;  // 如果找不到，则向下遍历，看是否存在和要删除的元素相同。
            }
        }
    }
    // 头插法
    public void addFirst(Emp emp){
        emp.next = head;
        head = emp ;
    }


    // 根据id，按照id从小到大进行添加【难点】
    // 注意：不可能无法添加，除非相等的id，此时出现哈希冲突
    // 解题技巧：引用老师之前对有头结点的思路，但因为无头结点在添加，删除第一个节点时候，要多加一个判断

    // 注意：无头结点的按序添加元素，这个题目比有头结点要复杂的多，
    // 具体的要和package com.atguigu.linkedlist;里面的SingleLinkedListDemo类的addByOrder()方法好好比较一下
    // 无头结点且要按序要添加元素，【为什么比有头结点添加的要复杂】，因为它和
    // 需要分几种，
    // 即1.如果是添加到第一个位置，则需要头插法
    //   2.如果是添加到非第一个位置，但也不是最后一个位置，即插入到中间位置
    //   3.如果是添加到最后一个位置，
    public void addSeqById(Emp emp){


        boolean flag = false ; // 打一个布尔标记，判断是否是添加到尾部，即
        // 这里还是不用打布尔标记了，因为无头结点本身添加元素就麻烦，还要升序，此时打布尔标记未免太复杂

        if(head == null){
            this.addFirst(emp);
            return ;
        }
        Emp curEmp = head ;     // 注意：刚开始head为null

        // 注意：这里的非头节点，且要按进行按序添加节点，需要多重if，else语句，
        // 涉及到的坑，包括，非头结点的特殊判断，以及非头结点的头插法，尾插法，
        // 这里刚好我是将头插法封装为一个方法，此时才会简单
        while(true){

            // 退出条件
            // 为什么没有反应，因为没有退出条件

            // 注意：
            // 有头结点：要添加的元素要和前驱结点的后一个节点【后继节点】进行比较，若要添加的节点对应的值比较小，则可以直接插入
            // 无头结点：则这里不存在前驱结点，只有后一个节点，故比较时候，比较判断很繁琐。且退出条件也是容易弄错。
            //          故这里我们是将curEmp当做后一个节点，而且退出条件对于不同情况下也是不同的。
            // 1. 没有节点时：直接覆盖，后退出
            // 2. 当存在一个节点时，需要进行比较大小，
            //  2.1 若要添加的节点对应的id小，则需要头插法
            //  2.2 若要添加的节点对应的id大，此时还需要进行判断是否遍历到最后一个节点
            //  2.3 若还没有遍历到最后一个节点，此时当没有满足要添加的节点的id小于后一个节点，则curEmp会指向下一个，知道curEmp.next == null，
            //      此时就相当于是最后一个节点，就相当于是尾插法，千万不要把判断条件设置为curEmp == null,否则最后在链接要添加的节点时候，
            //      会出现空指针异常
            // 注意：这里蛮骚的一点就是，每一次添加元素时都需要先和head节点，进行比较，这和有头结点的大不一样。

            // 要让head的id值和emp的id值进行比较，若前面大于后面，就让emp 添加到head的前面
            // 通俗来讲：让preNode的后一个节点和要插入节点进行比较，若前面大于后面，则可以插入，而且插入过程中还需要用到preNode
            if(head.id > emp.id){
                this.addFirst(emp);    // 头插法，特殊情况
                break ;
            }else if(head.id == emp.id){
                //this.add(emp);
                System.out.println("哈希冲突");
                break ;
            }else if(curEmp.id > emp.id){   // 并不是头插法，下面是正常插入，即可能是中间插入，可以是尾部插入
                emp.next = curEmp.next ;
                curEmp.next = emp ;
                break ;
            }else if(curEmp.id == emp.id){
                System.out.println("哈希冲突");
                break ;
                // 执行下面这个语句是因为，curEmp.id < emp.id,则说明要插入的元素比当前节点的id值要大，此时需要移动curEmp指针
                // 即凡是想比较大小，都要和前一个节点的后
            }else if(curEmp.next != null && curEmp.id < emp.id){  // 如果刚好curEmp指向的不是最后一个元素，且刚好curEmp.id < emp.id，此时就直接将curEmp指向下一个位置
                curEmp = curEmp.next ;
            }
            // 如果刚好curEmp指向的是最后一个元素，且刚好curEmp.id < emp.id，此时就直接跳出循环,所以这个相当于要进行尾插法
            else if(curEmp.next == null && curEmp.id < emp.id){
                flag = true ;
                break ;
            }
        }
        if(flag){
            curEmp.next = emp ;
        }



    }

    // 遍历链表的雇员信息
    public void list(int no){
        if(head == null){
            System.out.println("第" + (no + 1) + "条链表为空");
            return ;
        }
        System.out.print("第" + (no + 1) + "条链表的信息为");
        Emp curEmp = head ; // 辅助指针
        while(true){
            System.out.printf("=> id=%d name=%s\t",curEmp.id,curEmp.name);
            if(curEmp.next == null){    // 说明curEmp已经是最后节点
                break ;
            }
            curEmp = curEmp.next ;  // 后移，遍历
        }
        System.out.println();
    }

    // 根据id查找雇员
    // 如果找到，就返回Emp，如果没有找到，就返回null
    // 注意：假定id不重复，即不会出现哈希冲突；因为若一个链表中两个节点的id都一样，说明会出现冲突
    public Emp findEmpById(int id){
        /*// 判断链表是否为空
        if(head == null){
            System.out.println("链表为空");
            return null ;
        }

        // 借助辅助指针
        Emp curEmp = head ;
        Emp resEmp = null ;
        while(true){

            if(curEmp.id == id){    // 找到
                resEmp = curEmp ;
                break ; // 这时curEmp就指向要查找的雇员
            }

            // 依次执行都没有找到，退出while循环条件
            if(curEmp.next == null){    // 说明遍历当前链表没有找到该雇员
                // 此行代码执行是为了说明没有找到该雇员，故return curEmp 为 null
                // 并不是理解为：将此链表的最后一个节点置为null，但实际上已经将原来的链表给修改了
                // 还是我太嫩了，其实curEmp只是一个引用数据类型的变量，其存储在栈中，而链表和节点是存放到堆中，
                // 凡是没有涉及到堆中的数据变化时，都不会影响；

                // 此处是将curEmp原先指向的节点处 转向为 指向一块没有被访问的地址空间null，而实际链表中的地址空间还是存在
                // 注意：那为什么在链表中添加节点，删除节点，时候，此时用到的辅助指针都会对实际的链表空间的节点进行操作；
                // 因为此时当前节点和辅助指针指向的是同一块内存空间，
                // 综上：我们要理清楚引用的概念；
                // 注意：加上resEmp相当于多此一举
                // curEmp = null ;
                resEmp = null ;
                break ;
            }
            curEmp = curEmp.next ;

        }
        return resEmp ;*/

        //判断链表是否为空
        if(head == null) {
            System.out.println("链表为空");
            return null;
        }
        //辅助指针
        Emp curEmp = head;
        while(true) {
            if(curEmp.id == id) {//找到
                break;//这时curEmp就指向要查找的雇员
            }
            //退出
            if(curEmp.next == null) {//说明遍历当前链表没有找到该雇员
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;//以后
        }

        //list(id);

        return curEmp;
    }


}