package com.atguigu.tree.threadedbinarytree;

// 定义ThreadedBinaryTree

/**
 * 注意：这里：当前节点下一个节点的方向是表示往叶节点靠拢，前一个节点的方向是表示往根节点靠拢
 *  二叉树的前驱结点：即为当前节点指向的"下一个节点"【可以指向null】
 *  二叉树的后继结点：即为当前节点指向的"前一个节点"
 *
 *  但其实在一个复杂的二叉树中，其实对于这个前后关系，并没有太绝对，故你可以理解为另外一种思路：
 *  重点：下面这个是永恒成立的
 *      二叉树的前驱结点：即为当前节点的左子节点
 *      二叉树的后继结点：即为当前节点的右子节点
 *
 *
 *  这个和双向链表的前驱结点，后继节点的理解方式大同小异。
 *  所以在单链表中：
 *      我们所说的当前链表节点的"前一个节点"并不是前驱结点,而是 辅助节点，或者称作 哨兵节点
 *      而其next域指向的后一个节点，才是前驱结点
 *
 *  双向链表中：
 *      当前链表节点的 "前一个节点" 是后继节点
 *      而当前链表的 "后一个节点" 是前驱结点
 *
 */
public class ThreadedBinaryTree {
    private HeroNode root ;

    // 为了实现线索化，需要创建一个指向当前节点的前驱结点的指针
    // 在递归进行搜索化时，preNode总是保留下一个节点，即前驱结点
    private HeroNode preNode = null ;   // 注意：这里的preNode并不表示前一个节点，而是前驱结点

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    // 遍历线索化二叉树的方法【类似中序遍历(左根右)：最后遍历出来的结果就相当于普通二叉树的中序遍历】
    // 下面的threadedList()方法使用的是非递归的方法进行遍历
    // 遍历线索化中序二叉树：左根右：和普通的中序二叉树的遍历区别就在于，线索化中序二叉树存在前驱结点和后继节点
    // 由于这两个节点的出现会影响正常的中序遍历，因此我们需要按照首先找到该节点的前驱结点【向左子节点搜索】，然后进行输出该节点，
    // 之后向右子节点进行搜索 若存在后继节点，则依次输出，若不存在后继节点，则当前节点即刚好连接到右子树【恰好为原先未变的链接方式】，
    // 此时有一个特殊的地方，要将当前节点移动到右子树的位置，此时刚好结束一次，"左根右" 的类似中序的线索化二叉树遍历
    // 重复此操作，直到回溯到第一层入栈的此处，执行curNode == null，此时遍历结束，game over！！！
    /**
     * 中序遍历线索二叉树，按照后继方式遍历（思路：找到最左子节点开始）
     *
     */
    public void threadedInfixList(){
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode curNode = root ;
        while(curNode != null){
            // 循环的找到leftType == 1 的节点，第一个找到就是8节点
            // 后面随着遍历而变化，因为当leftType==1时，
            // 说明该节点是按照线索化处理后的有效节点

            // 即：1、找中序遍历方式开始的节点，当leftType == 1，则跳出循环
            while(curNode.getLeftType() == 0){
                curNode = curNode.getLeft() ;
            }

            // 打印当前这个节点
            System.out.println(curNode);

            // 如果当前节点的右指针指向的是后继节点，就一直输出
            // 为什么是这样输出？？？
            // 因为当前我们已经生成了中序线索二叉树，而此时的遍历输出并不能和往常中序遍历输出
            // 此时需要按照 若存在后继节点，则按照后继节点依次输出，否则，就按照当前节点的原始二叉树的下一个位置【非后继节点】
            // 进行输出；之后的即同上步骤依次执行。
            while(curNode.getRightType() == 1){     // 即如果右指针是线索指针
                // 获取到当前节点的后继节点
                curNode = curNode.getRight() ;
                System.out.println(curNode);
            }
            // 替换这个遍历的节点
            // 切记：这一步一定不能忘记
            // 因为，此时是因为curNode.getRightType()==0时候，即刚好连接到右子树，跳出while循环
            // 故此时，让其指向了原来真实的位置
            // 综上：跳出循环，说明右指针不是线索指针，则找到右子树开始的节点
            curNode = curNode.getRight() ;
        }
    }

    // 重载一把threadNodes方法
    public void threadedInfixOrder(){
        this.threadedInfixOrder(root);
    }

    // 编写对二叉树进行中序线索化的方法【类似：中序：左根右】
    /**
     *
     * @param curNode  就是当前需要线索化的节点
     *
     *  注意：核心代码：就是线索化当前节点，查看是否要处理前驱结点和后继节点
     *               而线索化左子树和线索化右子树都是为核心代码服务的，就好比是
     *                中序遍历或者中序查找【左根右的思想】，但实际上是否要中序遍历输出和中序查找是否找得到
     *                 都需要看当前递归栈的当前节点，将其作为父节点
     */
    public void threadedInfixOrder(HeroNode curNode){

        // 如果heroNode == null，不能线索化
        if(curNode == null){
            return ;
        }

        // （一） 先线索化左子树
        threadedInfixOrder(curNode.getLeft());

        // （二） 线索化当前节点【有难度】
        // 处理当前节点的前驱结点
        //  以8节点来理解
        // 8节点的.left = null ,8节点的.leftType = 1

        // 为什么下面的处理后继节点时候，还要加上preNode！= null，而此处不用加上curNode！=null？？？
        // 因为第一步是先线索化左子树，当curNode==null时，返回到前一个调用点，此时curNode必定不为null，
        // 且恰好此时执行下面的if语句，故curNode！=null默认已经满足了，
        if(curNode.getLeft() == null){
            // 让当前节点的左指针指向前驱结点
            curNode.setLeft(preNode);
            // 修改当前节点的左指针的类型，指向前驱结点
            // 1. 如果leftType == 0  表示指向的是左子树，如果是1 则表示指向前驱结点
            // 即：你要明白一个道理：如果此时存在了指向左子树，则就不能将其指向前驱结点
            curNode.setLeftType(1); // 这里只是做了一个标记，并没有实质性的影响
        }

        // 处理后继节点
        // 处理后继节点是利用上一次得到的前驱结点指向，这一次的当前节点
        // 若不添加这个条件：preNode != null 会出现空指针异常
        if(preNode != null && preNode.getRight() == null){
            // 让前驱结点的右指针指向当前节点
            preNode.setRight(curNode);
            // 2. 如果rightType == 0 表示指向的是右子树，如果是1 则表示指向后继节点
            // 即：你要明白一个道理：如果此时存在了指向右子树，则就不能将其指向后继节点
            preNode.setRightType(1);    // 这里只是做了一个标记，并没有实质性的影响
        }
        // !!! 每处理一个节点后，让当前节点是下一个节点的前驱节点
        // 【即这两个节点要位于相邻状态，目的是为了保存上一次的curNode作为当前的preNode前驱节点，从而实现后继节点】
        preNode = curNode ;

        // （三） 再线索化右子树
        threadedInfixOrder(curNode.getRight()); // 注意：这里不需要判断，因为掐面
    }

    // 重载一把threadedPreOrder方法
    public void threadedPreOrder(){
        this.threadedPreOrder(root);
    }

    // 前序线索化二叉树【根左右】
    public void threadedPreOrder(HeroNode curNode){
        if(curNode == null){
            return  ;
        }

        // （一） 线索化当前节点【有难度】
        // 处理当前节点的前驱结点

        // 为什么下面的处理后继节点时候，还要加上preNode！= null，而此处不用加上curNode！=null？？？
        // 因为第一步是先线索化左子树，当curNode==null时，返回到前一个调用点，此时curNode必定不为null，
        // 且恰好此时执行下面的if语句，故curNode！=null默认已经满足了，

        // 左指针为空，将左指针指向前驱结点
        if(curNode.getLeft() == null){
            curNode.setLeft(preNode);
            curNode.setLeftType(1);
        }

        // 处理后继节点
        // 处理后继节点是利用上一次得到的 前驱结点 指向 这一次的当前节点
        // 若不添加这个条件：preNode != null 会出现空指针异常

        // 前一个节点的后继节点指向当前节点
        if(preNode != null && preNode.getRight() == null){
            preNode.setRight(curNode);
            preNode.setRightType(1);
        }

        preNode = curNode ;

        // （二）处理左子树
        if(curNode.getLeftType() == 0){
            threadedPreOrder(curNode.getLeft());    // 注意：这里的退出递归条件为：curNode == null，此时进行回溯
        }

        // （三） 处理右子树
        if(curNode.getRightType() == 0){
            threadedPreOrder(curNode.getRight());  // 注意：这里的退出递归条件为：curNode == null，此时进行回溯
        }



    }

    /**
     * 前序遍历线索二叉树（按照后继线索遍历），这里不使用递归
     * 前序：根左右
     * 故：综上：需要遍历的方式为：根(当前节点) 前驱  后继 【若不存在线索节点，再以真实子树节点为主】
     */
    public void threadedPreList(){
        HeroNode curNode = root ;
        // 始终进行while循环，直到curNode == null，则此时要进行退出循环
        while(curNode != null){

            // 如果当前节点的没有前驱结点，此时就直接输出
            while(curNode.getLeftType() == 0){
                System.out.println(curNode);
                curNode = curNode.getLeft() ;
            }

            // 当前节点存在前驱结点时候
            // 此时需要打印
            System.out.println(curNode);
            curNode = curNode.getRight() ;
            // 然后遍历右子树的节点，如果满足时后继节点，则一直循环输出
            // 若不满足，则重新执行外层的while(curNode != null)
//            while(curNode.getRightType() == 1){
//                System.out.println(curNode);    // 说明找到了后继节点
//                curNode = curNode.getRight() ;  // 向下移动
//            }

        }

    }

    // 删除节点
    public void delNode(int no){
        if(root != null){
            // 如果只有一个root节点，这里立即判断root是不是要删除该节点
            if(root.getNo() == no){
                root = null ;
            }else{
                // 递归删除
                root.delNode(no);   // 这里并不是调用BinaryTree类的方法，而是节点的方法，
                                    // 实际上，是HeroNode节点进行递归删除
            }
        }else{
            System.out.println("空树，无法删除");
        }
    }

    // 前序遍历
    public void preOrder(){
        if(this.root != null){  // 若root节点为空，则就不用遍历了，压根就不存在
            // 调用的时候，对象就是树的根节点
            this.root.preOrder();   // 注意：这里调用的方法是HeroNode类的方法
                                    // 我们这里为了看着会直观点，就这样分析，不同的类中两个同名的方法不会相互影响
        }else{
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序遍历
    public void infixOrder(){
        if(this.root != null){  // 若root节点为空，则就不用遍历了，压根就不存在
            this.root.infixOrder();
        }else{
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 后序遍历
    public void postOrder(){
        if(this.root != null){  // 若root节点为空，则就不用遍历了，压根就不存在
            this.root.postOrder();
        }else{
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 前序遍历
    public HeroNode preOrderSearch(int no){

        if(root  != null){  // 若root节点为空，则就不用遍历了，压根就不存在
            return this.root.preOrderSearch(no); // 此时并没有递归，而是进行到HeroNode节点对象内，前序递归
                                                // 这里的方法名刚好重复，要注意区分
        }else{
            return null ;
        }
    }

    // 中序遍历
    public HeroNode infixOrderSearch(int no){
        if(root != null){   // 若root节点为空，则就"不用遍历了，压根就不存在
            return this.root.infixOrderSearch(no) ;// 此时并没有递归，而是进行到HeroNode节点对象内，前序递归
                                                  // 这里的方法名刚好重复，要注意区分
        }else{
            return null ;
        }
    }

    // 后序遍历
    public HeroNode postOrderSearch(int no){
        if(root != null){   // 若root节点为空，则就不用遍历了，压根就不存在
            return this.root.postOrderSearch(no) ;
        }else{
            return null ;
        }
    }
}

