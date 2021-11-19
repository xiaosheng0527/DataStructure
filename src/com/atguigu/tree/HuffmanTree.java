package com.atguigu.tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author shengxiao
 * @date 2021/7/25 21:57
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1} ;
        Node root = createHuffmanTree(arr);

        // 测试一把
        preOrder(root); // 此处只需要获取到根节点就可以直接遍历

    }

    // 编写一个前序遍历的方法
    public static void preOrder(Node root){
        if(root != null){
            root.preOrder();
        }else {
            System.out.println("是空树，无法遍历");
        }
    }

    // 创建哈夫曼树的方法

    /**
     *
     * @param arr   需要创建成哈夫曼树的数组
     * @return      创建好后的哈夫曼树的root节点
     */
    public static Node createHuffmanTree(int[] arr){
        // 第一步：为了操作方便
        // 1. 已遍历arr数组
        // 2. 将arr的每个元素构成一个Node
        // 3. 将Node 放入到ArrayList中
        List<Node> nodeList = new ArrayList<>() ;
        for (int value : arr){
            nodeList.add(new Node(value)) ;
        }

        // 我们处理的过程是一个循环的过程
        while(nodeList.size() > 1){
            // 排序，从小到大
            Collections.sort(nodeList);

            System.out.println("nodeList = " + nodeList);

            // 取出根节点权值最小的两颗二叉树
            // （1） 取出权值最小的二叉树（二叉树）
            Node leftNode = nodeList.get(0) ;

            // (2) 取出权值第二小的节点（二叉树）
            Node rightNode = nodeList.get(1) ;

            // (3) 构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value) ;
            parent.left = leftNode ;
            parent.right = rightNode ;

            // （4）从ArrayList删除处理过的二叉树
            nodeList.remove(leftNode) ;
            nodeList.remove(rightNode) ;

            // (5) 将parent加入到nodeList
            nodeList.add(parent) ;

            System.out.println(nodeList);
        }

        // 返回哈夫曼树的root节点
        // 因为：刚好此时列表只剩下一个元素，即root节点，而且此root节点链接了当前数组的所有元素，
        // 故只需要获取到根节点，就可以直接遍历
        return nodeList.get(0) ;

    }

    // 创建哈夫曼树的方法

}

// 创建节点类
// 为了让Node 对象 支持 排序 Collections集合排序
// 让Node 实现Comparable接口
class Node implements Comparable<Node> {
    int value ;     // 节点权值
    char c ;    // 字符
    Node left ;     // 指向左子节点
    Node right ;     // 指向左子节点

    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    // 写一个前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 注意：当前传进来的 元素o 是"被比较者",其作为判断的主体；
        // 而"比较者"this.value作为判断当前传进来的元素
        // 比较者小于被比较者【即"被比较者"比较大，"比较者"比较小，则为降序】，那么返回负整数, 降序
        // 比较者等于被比较者，那么返回 0
        // 比较者大于被比较者【即"被比较者"比较小，"比较者"比较大，则为升序】（也就是compareTo方法里面的对象），那么返回正整数，升序
        return this.value - o.value ;
    }
}
