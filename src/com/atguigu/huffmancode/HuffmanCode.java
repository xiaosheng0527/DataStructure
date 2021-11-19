package com.atguigu.huffmancode;

import java.util.*;

/**
 * @author shengxiao
 * @date 2021/7/26 13:32
 *
 * 注意：包装类不存在其数组形式，只有基本数据类型和引用数据类型（不包括包装类型）才有其数组形式
 */
public class HuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java" ;
        byte[] contentBytes = content.getBytes() ;
        System.out.println(contentBytes.length);

        List<Node> nodeList =  getNodeList(contentBytes) ;
        System.out.println("nodeList = " + nodeList);

        // 测试一把创建的二叉树
        System.out.println("哈夫曼树");
        Node huffmanTreeRoot = createHuffmanTree(nodeList) ;
        // 前序遍历
        // 遍历出来的叶子节点存放着字符和对应的权重，而非叶子节点则就不存放data
        huffmanTreeRoot.preOrder();

        // 测试一把是否生成了对应的哈夫曼编码
        //getCodes(huffmanTreeRoot,"",sb);    // 静态变量定义在下面
        getCodes(huffmanTreeRoot) ;
        System.out.println("生成的哈夫曼编码表" + huffmanCodeMap);

    }

    // 生成哈夫曼树对应的哈夫曼编码
    // 思路分析:
    // 1. 将哈夫曼编码表存放在 Map<Byte,String>  形式
    // 注意：字符和ascii码可以相互转化
    // 32->01  97->100  100->11000  等等【形式相同，但哈夫曼树可以不同，wpl相同】
    public static Map<Byte,String> huffmanCodeMap = new HashMap<>() ;
    // 2. 在生成哈夫曼编码表示，需要去拼接路径，定义一个StringBuilder，存储某个叶子节点的路径
    public static StringBuilder sb = new StringBuilder() ;

    // 为了调用方便，我们重载getCodes
    private static Map<Byte,String> getCodes(Node root){
        if(root == null){
            return null ;
        }
        // 处理root的左子树
        getCodes(root.left, "0", sb);
        // 处理root的右子树
        getCodes(root.right, "1", sb);
        return huffmanCodeMap ;
    }

    /**
     *  功能：获取到 传入node节点中所有叶子节点的 哈夫曼编码 ，将其放入到huffmanCodeMap集合中
     * @param node  传入的节点
     * @param code  路径：左子节点为0，右子节点为1
     * @param sb    拼接路径
     */
    private static void getCodes(Node node,String code,StringBuilder sb){
        // CharSequence是一个可读的char值序列。 此接口提供对许多不同类型char序列的统一只读访问
        // 故sb不能修改
        StringBuilder sb2 = new StringBuilder(sb) ;
        // 将传入的code 加入到 sb2
        sb2.append(code) ;
        if(node != null){   // 如果node == null 不处理
            // 判断当前节点是叶子节点还是非叶子节点
            if(node.data == null){  // 非叶子节点
                // 递归处理
                // 向左
                getCodes(node.left, "0", sb2);
                // 向右递归
                getCodes(node.right, "1", sb2);

            }else{  // 说明是一个叶子节点
                // 就表示找到某个叶子节点的最后
                huffmanCodeMap.put(node.data, sb2.toString()) ;
            }
        }
    }

    // 前序遍历的方法
    private static void preOrder(Node root){
        if(root == null){
            System.out.println("空树，无法遍历");
        }else{
            root.preOrder();
        }
    }

    /**
     *
     * @param bytes     接收一个字节数组
     * @return          返回的就是Node{{data = 9, weight = 5},Node{data = 32, weight = 9}
     */
    private static List<Node> getNodeList(byte[] bytes){

        // 1. 创建一个ArrayList
        ArrayList<Node> nodeList = new ArrayList<>() ;

        // 存储每一个byte出现的次数 利用-> map[key,value]
        Map<Byte,Integer> countMap = new HashMap<>() ;
        for(byte b : bytes){
            Integer count = countMap.get(b) ;   // 返回指定键映射到的值
            if(count == null){  // Map还没有这个字符数据，第一次
                // put()方法：将指定值与此映射中的指定键相关联（可选操作）。
                // 如果映射先前不包含键的映射，则直接将键和值相关联
                // 如果映射先前包含键的映射，则旧值将替换为指定值
                countMap.put(b, 1) ;
            } else{ // 说明Map集合已经存在该元素【即包含键的映射】，则旧值将替换为指定值
                countMap.put(b, count + 1) ;
            }
        }

        // 把每一个键值对完成一个Node对象，并加入到nodeList集合
        // 遍历map： 可以用内部类，获取键值对的集合，再通过遍历每一个键值对，取出相关的数据
        //          也可以先获取映射的键对应的集合，再通过遍历每一个键得到值
        // Map.Entry<Byte,Integer>好像是内部类，获取map的每一个键值对【元素】
        for(Map.Entry<Byte,Integer> entry : countMap.entrySet()){
            nodeList.add(new Node(entry.getKey(),entry.getValue())) ;
        }
        return nodeList ;
    }

    // 可以通过List 创建 对应的哈夫曼树
    private static Node createHuffmanTree(List<Node> nodeList){

        while(nodeList.size() > 1){

            // 排序,从小到大
            Collections.sort(nodeList);
            // 取出第一颗最小的二叉树
            Node leftNode = nodeList.get(0) ;
            // 取出第一颗最小的二叉树
            Node rightNode = nodeList.get(1) ;
            // 创建一颗新的二叉树，它的根节点 没有data，只有权值
            // 因为，所有的字符最终都是放在叶子节点上，故没有data
            Node parentNode = new Node(null , leftNode.weight + rightNode.weight) ;
            parentNode.left = leftNode ;
            parentNode.right = rightNode ;

            // 将已经处理的两颗二叉树从nodeList移除
            nodeList.remove(leftNode) ;
            nodeList.remove(rightNode) ;
            // 将新的二叉树加入到nodeList
            nodeList.add(parentNode) ;

            //System.out.println(nodeList);

        }
        // nodeList最后剩下的元素，就是哈夫曼树的根节点
        return nodeList.get(0) ;
    }
}

// 创建Node，放置 数据 和 权值
class Node implements Comparable<Node>{
    // Byte引用数据类型，是byte类型的包装类
    Byte data ; // 存放数据本身，比如 'a' => 97  ' ' => 32
    int weight ;   // 权值，表示字符出现的次数
    Node left ;
    Node right ;

    public Node() {
    }

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public Node(Byte data, int weight, Node left, Node right) {
        this.data = data;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }


    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight ;
    }

    // 前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }
}
