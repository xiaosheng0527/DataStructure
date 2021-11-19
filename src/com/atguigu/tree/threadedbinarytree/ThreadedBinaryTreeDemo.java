package com.atguigu.tree.threadedbinarytree;

/**
 * @author shengxiao
 * @date 2021/7/24 15:58
 * thread：线程，线索;穿过，通过
 */
public class ThreadedBinaryTreeDemo {   // 线索二叉树

    public static void main(String[] args) {
        // 测试一把中序线索二叉树的功能
        HeroNode root      = new HeroNode(1, "tom");
        HeroNode heroNode2 = new HeroNode(3, "jack");
        HeroNode heroNode3 = new HeroNode(6, "smith");
        HeroNode heroNode4 = new HeroNode(8, "mary");
        HeroNode heroNode5 = new HeroNode(10, "king");
        HeroNode heroNode6 = new HeroNode(14, "dim");

        // 二叉树，后面我们要递归创建，现在简单处理，使用手动创建
        root.setLeft(heroNode2);
        root.setRight(heroNode3);
        heroNode2.setLeft(heroNode4);
        heroNode2.setRight(heroNode5);
        heroNode3.setLeft(heroNode6);

        // 测试中序线索化
        ThreadedBinaryTree threadedBinaryTree1 = new ThreadedBinaryTree() ;
        threadedBinaryTree1.setRoot(root);
        threadedBinaryTree1.threadedInfixOrder();    // 前面已经调用好了这个方法

        // 测试：以10号节点测试
        HeroNode leftNode = heroNode5.getLeft() ;
        HeroNode rightNode = heroNode5.getRight() ;
        System.out.println("10号节点的前驱结点是 = " + leftNode);
        System.out.println("10号节点的后继结点是 = " + rightNode);

        // 当线索化二叉树后，不能再使用原来的遍历方法
        //threadedBinaryTree.InfixOrder();
        System.out.println("使用线索化的方式遍历 中序线索化二叉树");
        threadedBinaryTree1.threadedInfixList();  // 8,3,10,1,14,6




        // 测试中序线索化
        ThreadedBinaryTree threadedBinaryTree2 = new ThreadedBinaryTree() ;
        threadedBinaryTree2.setRoot(root);
        threadedBinaryTree2.threadedPreOrder();    // 前面已经调用好了这个方法

        // 当线索化二叉树后，不能再使用原来的遍历方法
        System.out.println("使用线索化的方式遍历 前序线索化二叉树");
        threadedBinaryTree2.threadedPreList();  // 8,3,10,1,14,6
    }

}


