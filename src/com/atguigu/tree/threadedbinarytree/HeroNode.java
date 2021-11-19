package com.atguigu.tree.threadedbinarytree;

// 先创建HeroNode节点
public class HeroNode {
    private int no ;
    private String name ;
    private HeroNode left ;     // 默认为null
    private HeroNode right ;    // 默认为null

    // 说明
    // 1. 如果leftType == 0  表示指向的是左子树，如果是1 则表示指向前驱结点
    // 2. 如果rightType == 0 表示指向的是右子树，如果是1 则表示指向后继节点
    private int leftType ;  // 默认初始化为指向的是左子树
    private int rightType ;

    public HeroNode() {
    }

    // 下面这两个其实用第一个就好，但其实也没差
    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public HeroNode(int no, String name, HeroNode left, HeroNode right) {
        this.no = no;
        this.name = name;
        this.left = left;
        this.right = right;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 递归删除节点
    // 注意：判断是否为空树，或者是否root节点就是所要删除的节点
    // 1. 如果删除的节点是叶子节点，则删除该节点
    // 2. 如果删除的节点是非叶子节点，则删除该子树

    // 注意：递归删除节点，类似于前序遍历，根左右，只是区别在于，我们是判断当前结点的子结点是否需要删除结点，
    // 而不能去判断当前这个结点是不是需要删除结点【类似于单链表的删除，也需要找到待删除节点的前一个节点】
    public void delNode(int no){

        // 思路
        /*
         *  1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断
                当前这个结点是不是需要删除结点
            2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将 this.left = null; 并且就返回
                (结束递归删除)
            3. 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将 this.right= null ;并且就返回
                (结束递归删除)
            4. 如果第 2 和第 3 步没有删除结点，那么我们就需要向左子树进行递归删除
            5. 如果第 4 步也没有删除结点，则应当向右子树进行递归删除.
         */
        // 2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，
        //      就将 this.left = null; 并且就返回(结束递归删除)
        if(this.left != null && this.left.no == no){
            this.left = null ;
            return ;
        }
        // 3. 如果当前结点的右子结点不为空，并且右子结点
        //  就是要删除结点，就将 this.right= null ;并且就返回 (结束递归删除)
        if(this.right != null && this.right.no == no){
            this.right = null ;
            return ;
        }

        // 4. 我们就需要向左子树进行递归删除
        if(this.left != null){
            this.left.delNode(no);
        }
        // 5. 我们就需要向右子树进行递归删除
        if(this.right != null){
            this.right.delNode(no);
        }
    }

    // 编写前序遍历的方法
    // 注意：正在执行前序遍历，故此二叉树不为空
    // 若为空的话，什么前序，中序，后序遍历都不行了
    // 前序：根左右
    public void preOrder(){
        System.out.println(this);   // 先输出父节点
        // 递归向左子树前序遍历
        if(this.left != null){
            this.left.preOrder();
        }
        // 递归向右子树前序遍历
        if(this.right != null){
            this.right.preOrder();
        }
    }

    // 中序遍历
    // 注意：this是指向当前HeroNode对象【树对象】
    // 中序：左根右
    public void infixOrder(){
        // 递归向左子树前序遍历
        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);   // 先输出父节点，此时的父节点，可能不是根节点
        // 递归向右子树前序遍历
        if(this.right != null){
            this.right.infixOrder();
        }
    }

    // 后序遍历
    // 后序：左右根
    public void postOrder(){
        // 递归向左子树前序遍历
        if(this.left != null){
            this.left.postOrder();
        }
        // 递归向右子树前序遍历
        if(this.right != null){
            this.right.postOrder();
        }
        System.out.println(this);   // 最后输出父节点

    }


    // 前序遍历查找

    /**
     *
     * @param no    查找no
     * @return      如果找到就范湖该Node，如果没有找到就返回null
     */
    public HeroNode preOrderSearch(int no){
        // 实际上：前序遍历的次数要以实际的no值比较为主，此时刚好就是if(this.no == no){}语句
        // 注意：前序遍历的次数并不是递归了多少次，有些递归栈过程中并没有进行比较大小
        System.out.println("进入前序查找");     // 检测前序遍历的次数
        // 比较当前节点是不是要查找的点
        if(this.no == no){
            return this ;
        }
        // 1.则判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        // 2.如果左递归 前序查找，找到节点，则返回
        HeroNode resNode = null ;
        if(this.left != null){
            resNode = this.left.preOrderSearch(no) ;
        }
        // 如果左子树没有找到，则需要继续遍历右递归前序查找
        if(resNode != null){    // 说明我们在左子树找到了
            return resNode ;
        }

        // 1.左递归前序查找，找到节点,则返回，否则继续判断
        // 2.当前的节点的右子节点是否为空，如果不为空，则继续向右递归前序查找
        if(this.right != null){
            resNode = this.right.preOrderSearch(no) ;
        }
        // 此时不论查没查到，都需要返回,故这里无需if语句判断
        return resNode ;
    }

    // 中序遍历查找
    public HeroNode infixOrderSearch(int no){

        // 判断当前节点的左节点是否为空，如果不为空，则递归中序查找
        HeroNode resNode = null ;
        if(this.left != null){
            resNode = this.left.infixOrderSearch(no) ;

        }
        if(resNode != null){
            return resNode ;
        }
        // 实际上：中序遍历的次数要以实际的no值比较为主，此时刚好就是if(this.no == no){}语句
        // 注意：中序遍历的次数并不是递归了多少次，有些递归栈过程中并没有进行比较大小
        System.out.println("进入中序查找");     // 检测中序遍历的次数
        // 如果找到，则返回，如果没有找到，就和当前节点比较，如果是，则返回当前节点
        if (this.no == no) {

            return this ;
        }
        // 否则继续进行向右递归的中序查找
        if(this.right != null){
            resNode = this.right.infixOrderSearch(no) ;
        }
        return resNode ;
    }

    // 后序遍历查找
    public HeroNode postOrderSearch(int no){


        // 判断当前节点的左子节点是否为空，如果不为空，则递归后序查找
        HeroNode resNode = null ;
        if(this.left != null){
            resNode = this.left.postOrderSearch(no) ;
        }
        if(resNode != null){    // 说明在左子树找到
            return resNode ;
        }

        // 如果没有找到，则向右子树递归进行后序遍历查找
        if(this.right != null){
            resNode = this.right.postOrderSearch(no) ;
        }
        if(resNode != null){    // 说明在右子树找到
            return resNode ;
        }

        // 实际上：后序遍历的次数要以实际的no值比较为主，此时刚好就是if(this.no == no){}语句
        // 注意：后序遍历的次数并不是递归了多少次，有些递归栈过程中并没有进行比较大小
        System.out.println("进入后序查找");     // 检测后序遍历的次数
        // 如果左右子树都没有找到，就比较当前节点是不是我们要查的节点
        if(this.no == no){
            return this ;
        }
        return resNode ;
    }

}