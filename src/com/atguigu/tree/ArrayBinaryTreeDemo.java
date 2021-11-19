package com.atguigu.tree;

/**
 * @author shengxiao
 * @date 2021/7/24 14:57
 *
 * // 注意：前序遍历(根左右)：若要能输出元素，一定在父节点上，每一个非空节点都可以当做本次递归的父节点
 *         中序遍历(左根右)：若要能输出元素， 一定在父节点上，每一个非空节点都可以当做本次递归的父节点
 *         后序遍历(左右根)：若要能输出元素， 一定在父节点上，每一个非空节点都可以当做本次递归的父节点
 *   why？？
 *   你有注意到，输出元素都是在根节点（即父节点），根左右【前序】，左根右【中序】，左右根【后序】
 */
public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7} ;
        // 创建一个ArrayBinaryTree
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr) ;
        //arrayBinaryTree.preOrder();    // 从根节点出发，而根节点对应的数组的下标为0
        //arrayBinaryTree.infixOrder();
        arrayBinaryTree.postOrder();
    }
}

// 编写一个ArrayBinaryTree，实现顺序存储二叉树遍历

class ArrayBinaryTree{
    private int[] arr ;    // 存储数据节点的数组

    public ArrayBinaryTree() {
    }

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    // 重载preOrder
    public void preOrder(){
        this.preOrder(0);
    }

    // 编写一个方法，完成顺序存储二叉树的前序遍历
    // 前序：根左右
    public void preOrder(int index){
        // 如果数组为空，或者arr.length = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
        // 输出当前这个元素
        System.out.println(arr[index]);
        // 向左递归遍历
        if((index * 2 + 1) < arr.length){
            preOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if((index * 2 + 2) < arr.length){
            preOrder(index * 2 + 2);
        }
    }

    // 重载infixOrder
    public void infixOrder(){
        this.infixOrder(0);
    }

    // 完成顺序存储二叉树的中序遍历
    public void infixOrder(int index){

        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的中序遍历");
        }

        // 向左递归遍历
        if((index * 2 + 1) < arr.length){
            infixOrder(index * 2 + 1);
        }
        // 输出当前这个元素
        System.out.println(arr[index]);

        // 向右递归遍历
        if((index * 2 + 2) < arr.length){
            infixOrder(index * 2 + 2);
        }
    }

    // 重载postOrder
    public void postOrder(){
        this.postOrder(0);
    }

    // 完成顺序存储二叉树的后序遍历
    public void postOrder(int index){

        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的中序遍历");
        }

        // 向左递归遍历
        if((index * 2 + 1) < arr.length){
            postOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if((index * 2 + 2) < arr.length){
            postOrder(index * 2 + 2);
        }

        // 输出当前这个元素
        System.out.println(arr[index]);
    }
}
