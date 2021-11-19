package com.atguigu.tree;

/**
 * @author shengxiao
 * @date 2021/7/23 20:53
 *
    注意：
        前序遍历(根左右)：若要能输出元素，一定在父节点上，每一个非空节点都可以当做本次递归的父节点
        中序遍历(左根右)：若要能输出元素， 一定在父节点上，每一个非空节点都可以当做本次递归的父节点
        后序遍历(左右根)：若要能输出元素， 一定在父节点上，每一个非空节点都可以当做本次递归的父节点
    why？？
    你有注意到，输出元素都是在根节点（即父节点），根左右【前序】，左根右【中序】，左右根【后序】
     */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        // 创建出一颗二叉树
        BinaryTree binaryTree = new BinaryTree() ;
        // 创建需要的节点
        HeroNode root = new HeroNode(1,"宋江") ;
        HeroNode heroNode2 = new HeroNode(2,"吴用") ;
        HeroNode heroNode3 = new HeroNode(3,"卢俊义") ;
        HeroNode heroNode4 = new HeroNode(4,"林冲") ;
        HeroNode heroNode5 = new HeroNode(5,"关胜") ;

        // 说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(heroNode2);
        root.setRight(heroNode3);
        heroNode3.setRight(heroNode4);
        heroNode3.setLeft(heroNode5);
        binaryTree.setRoot(root);   // 将root给二叉树

        // 测试
//        System.out.println("前序遍历"); // 1,2,3,4
//        binaryTree.preOrder();

        // 测试
//        System.out.println("中序遍历"); // 1,2,3,4
//        binaryTree.infixOrder();

        // 测试
//        System.out.println("后序遍历"); // 1,2,3,4
//        binaryTree.postOrder();

        // 前序遍历
        // 前序遍历的次数：4
//        System.out.println("前序遍历方式：");
//        HeroNode resNode = binaryTree.preOrderSearch(5) ;
//        if(resNode != null){
//            System.out.printf("找到了，信息为 no=%d name=%s",resNode.getNo(),resNode.getName());
//        }else{
//            System.out.printf("没有找到 no = %d 的英雄",5);
//        }

        // 中序遍历
        // 中序遍历的次数：3
//        System.out.println("中序遍历方式：");
//        HeroNode resNode = binaryTree.infixOrderSearch(5) ;
//        if(resNode != null){
//            System.out.printf("找到了，信息为 no=%d name=%s",resNode.getNo(),resNode.getName());
//        }else{
//            System.out.printf("没有找到 no = %d 的英雄",5);
//        }

        // 后序遍历
        // 后序遍历的次数：2
//        System.out.println("后序遍历方式：");
//        HeroNode resNode = binaryTree.postOrderSearch(2) ;
//        if(resNode != null){
//            System.out.printf("找到了，信息为 no=%d name=%s",resNode.getNo(),resNode.getName());
//        }else{
//            System.out.printf("没有找到 no = %d 的英雄",2);
//        }

        // 测试一把删除节点的代码

        System.out.println("删除前，前序遍历");
        binaryTree.preOrder();  // 1,2,3,5,4
        binaryTree.delNode(5);
        System.out.println("删除后，前序遍历");
        binaryTree.preOrder();
    }
}

// 定义BinaryTree
class BinaryTree{
    private HeroNode root ;

    public void setRoot(HeroNode root) {
        this.root = root;
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

// 先创建HeroNode节点
class HeroNode{
    private int no ;
    private String name ;
    private HeroNode left ;     // 默认为null
    private HeroNode right ;    // 默认为null

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