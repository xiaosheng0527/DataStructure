package com.atguigu.avl;

/**
 * @author shengxiao
 * @date 2021/7/27 16:36
 *
 * 注意：判断是否要旋转的时候，不仅要判断与添加节点同层子树是否满足条件，还需要判断，添加节点的左右子树是否满足条件
 *
 * 如果在添加节点的过程中，刚好添加此节点导致多棵树不平衡，此时是优先处理最小树
 *
 * 如何构建一颗平衡二叉树
 * 1. 本质上跟构建二叉排序树一致
 * 2. 在构建二叉排序树的过程中，如果发现树不符合特性，需要进行调整
 *    【即出现LL RR  RL  LR】,如果遇到多棵树不平衡，此时是优先处理最小树
 *
 * 如何判断调整类型？【LL RR  RL  LR】
 * 1. 找到失衡树的根节点root
 * 2. 找到导致树失衡的节点node，node在root的哪一侧  -> 确定L/R
 * 3. 判断node在root孩子的哪一侧                  -> 确定L/R
 */
public class AVLTreeDemo {

    public static void main(String[] args) {
        // 注意：这里面具体的分析图解：见qq中summary里面的算法文件夹，图解.xlsx
        //int[] arr = {4,3,6,5,7,8} ;       // 往左旋的测试用例
        //int[] arr = {10,12,8,9,7,6} ;   // 往右旋的测试用例
        int[] arr = { 10, 11, 7, 6, 8, 9 }; // 大方向是右旋，但其中满足一个条件，则需要先执行另一个条件，然后再进行右旋
        // 创建一个AVLTree对象
        AVLTree avlTree = new AVLTree() ;
        // 添加节点
        for(int i = 0 ; i < arr.length ; i++){
            // 在条件允许的过程中，因为每添加一次都需要判断是否变为失衡二叉树
            // 每一次执行指令，存在this关键字，故递归进栈过程中，到递归出口过程中，要判断当前节点(this)
            // 是否是失衡树的根节点，如果不满足则进行旋转，
            // 然后回溯到上一个调用点继续执行后面的代码 ，
            // 而如果不满足条件，则继续守株待兔
            // add()方法存在递归 调用，同样也存在递归返回，在递归返回的时候，利用到了回溯，进而判断是否是平衡的，
            // 一直返回到第一个调用add()方法的位置，即根节点root的位置，直到root节点的左右子树是非失衡的，则直接返回，不用调整
            // 否则在任何一步如果存在失衡的树，都要进行调整，调整的规则是，优先处理最小树。【这里恰好体现了回溯的思想】
            // 而且：回溯的过程中也需要进行递归求得其左右子树的高度，相当于递归->【返回上一层->递归】(重复n次){回溯思想}->返回到第一次调用点->返回至main函数
            avlTree.add(new Node(arr[i]));
        }

        // 遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

        // 在没有处理之前，是在add()方法没有对左右子树的高度进行控制，此时虽然是一个二叉排序树
        // 但因为非平衡，造成效率比较低
        // 这里以双旋转为例：以为上面前两个实例一个是左旋，一个是右旋，但实际情况可能要进行双旋转
        // 在处理之前，满足二叉排序树的性质，中序遍历可以得到有序的序列
//        System.out.println("在平衡处理之前~");
//        System.out.println("树的高度 = "  + avlTree.getRoot().height());    // 4
//        System.out.println("树的左子树的高度 = "  + avlTree.getRoot().leftHeight());    // 3
//        System.out.println("树的右子树的高度 = "  + avlTree.getRoot().rightHeight());   // 1
//        System.out.println("当前的根节点 = " + avlTree.getRoot());    // 当前的根节点 = Node{value=10}

        // 在处理之后，即在add()方法有对左右子树的高度进行控制，此时就会得到一个平衡二叉树
        // 这里以双旋转为例：因为上面前两个实例一个是左旋，一个是右旋，但实际情况可能要进行双旋转
        // 而且在处理之后，还是满足二叉排序树的性质，中序遍历依然可以得到有序的序列
        System.out.println("在平衡处理后~");
        System.out.println("树的高度 = "  + avlTree.getRoot().height());    // 3
        System.out.println("树的左子树的高度 = "  + avlTree.getRoot().leftHeight());    // 2
        System.out.println("树的右子树的高度 = "  + avlTree.getRoot().rightHeight());   // 2
        System.out.println("当前的根节点 = " + avlTree.getRoot());

        // 遍历
        System.out.println("前序遍历");
        avlTree.preOrder();
    }
}
