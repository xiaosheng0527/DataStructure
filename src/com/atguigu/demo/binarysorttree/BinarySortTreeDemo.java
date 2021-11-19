package com.atguigu.demo.binarysorttree;

/**
 * @author shengxiao
 * @date 2021/7/26 18:57
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9,2} ;
        BinarySortTree binarySortTree = new BinarySortTree() ;
        for(int i = 0 ; i < arr.length ; i++){
            binarySortTree.add(new Node(arr[i]));
        }
        binarySortTree.infixOrder();

        // 测试一下删除叶子节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);

        System.out.println("删除节点后");
        binarySortTree.infixOrder();
    }
}

class BinarySortTree{

    Node root ; // 默认初始化为空

    public BinarySortTree() {
    }

    // 查找要删除的节点
    public Node search(int value){
        if(root == null){
            return null ;
        } else{
            return this.root.search(value) ;
        }
    }

    // 查找要删除节点的父节点
    public Node searchParent(int value){
        if(root == null){
            return null ;
        } else{
            return this.root.searchParent(value) ;
        }
    }

    /**
     *
     * @param node  传入的节点（当做二叉排序树的根节点）
     * @return      返回的是 以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node){
        Node target = node ;
        // 循环的查找左节点，就会找到 最小值
        while(target.left != null){
            target = target.left ;
        }
        // 这时 target就指向了最小节点.
        // 删除最小节点【刚好是叶子节点】
        // 此时有需要调用delNode方法，然后又要去search()方法中查看是否存在最小值对应的节点，找到了，就可以直接删除
        delNode(target.value);
        return target.value;
    }

    // 删除节点
    public void delNode(int value){
        if(root == null){
            return ;
        } else{
            // 1.需要先去找到要删除的节点  targetNode
            Node targetNode = this.root.search(value) ;
            // 如果没有找到要删除的节点
            if(targetNode == null){
                return ;
            }

            // 如果我们发现当前这颗二叉排序树只有一个节点，即只有根节点，说明其没有父节点
            // 那么就直接删除这个节点
            if(root.left == null && root.right == null){
                root = null ;
                return ;
            }

            // 这里是一个分水岭：
            // 上面最近的if语句是二叉排序树只有一个节点【没有父节点】，故若要删除，则直接将root置空即可
            //下面是有父节点的代码

            // 去找到targetNode的父节点
            Node parent = this.root.searchParent(value) ;
            // 要实现删除叶子节点，则需要先确定叶子节点后面的情况，再确定叶子节点在父节点中的情况
            // 这个和链表删除及其类似
            // 如果要删除的节点是叶子节点
            if(targetNode.left == null && targetNode.right == null){
                if(parent.left != null && parent.left.value == value){
                    // 其实写成parent.left = targetNode.left ; parent.left = targetNode.right;
                    // 那还不如直接写成null，避免误会
                    parent.left = null ;
                } else if(parent.right != null && parent.right.value == value){
                    parent.right = null ;
                }
            }
            // 如果要删除有两颗子树的节点，即之后需要将右子树中值最小的节点往上移
            // 错了，这样会影响树的结构，所以正解应该是：
            // 其实此处删除的本质就是将右子树的最小值的节点进行删除，然后将其返回的最小值覆盖到要删除的节点，即要删除的节点只是修改了值而已
            // 这样才不会影响树的结构
            else if(targetNode.left != null && targetNode.right != null){
                int minVal = delRightTreeMin(targetNode.right) ;
                targetNode.value = minVal ;     // 然后将右子树的最小值放置到删除节点的位置
            }
            // 删除只有一颗子树的节点，这个情况很复杂，故直接用else语句来简化里面的判断语句的条件
            else{
                // 如果要删除的节点有左子节点
                if(targetNode.left != null){
                    if(parent != null){
                        //  如果 targetNode 是 parent 的左子结点
                        if(parent.left.value == value){
                            parent.left = targetNode.left ;
                        }else {
                            parent.right = targetNode.left ;
                        }
                    } else{
                        root = targetNode.left ;
                    }
                }else{
                    if(parent != null){
                        if(parent.left.value == value){
                            parent.left = targetNode.right ;
                        }else{
                            parent.right = targetNode.right ;
                        }
                    }else{
                        root = targetNode.right ;
                    }
                }
            }

        }
    }

    // 添加节点的方法
    public void add(Node node){
        if(root == null){
            root = node ;
        } else{
            root.add(node);
        }
    }

    // 添加中序遍历的方法
    public void infixOrder(){
        if(root == null){
            System.out.println("空树，无法遍历");
        }
        root.infixOrder();
    }

}

class Node{
    int value ;
    Node left ;
    Node right ;

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

    // 查找 要删除的节点
    /**
     *
     * @param value 希望删除的节点的值
     * @return      如果找到返回该节点，否则返回null
     */
    public Node search(int value){  // 此处的对象是要删除的节点本身

        if(value == this.value){
            return this ;
        } else if(value < this.value){
            if(this.left == null){  // 如果查找的值小于当前节点，向左子树递归查找
                return null ;
            }
            return this.left.search(value) ;
        } else{
            if(this.right == null){
                return null ;
            }
            return this.right.search(value) ;
        }
    }

    // 查找要删除节点的父节点
    /**
     *
     * @param value     要找到的节点的值
     * @return          返回的是要删除的节点的父节点，如果没有就返回null
     *                  注意：如果当前节点没有左子节点或者是右子节点，则说明此节点并不是待删除节点的父节点，就直接返回null
     */
    public Node searchParent(int value){    // 此处的对象是父节点
        if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
            return this ;
        } else{
            // 如果 当前节点的左子节点不为空,并且 查找的值小于当前节点的值
            if(this.left != null && value < this.value){
                return this.left.searchParent(value) ;
            }
            // 反之。如果 当前节点的右子节点不为空,并且 查找的值大于或等于当前父节点的值
            else if(this.right != null && value >= this.value){   // 此处为什么可以有这个条件：value==this.value
                return this.right.searchParent(value) ;
            } else{
                return null ;   // 没有找到父节点
            }
        }
    }

    // 中序遍历
    public void infixOrder(){

        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.infixOrder();
        }
    }



    // 添加节点的方法
    // 递归的形式添加节点，注意需要满足二叉排序树的要求
    // 假设：此处不为空树，即前面的add方法添加了第一个节点作为root
    // 注意：凡是可以添加都是在叶子节点处添加
    public void add(Node node){

        if(node == null){   // 边界值判断
            return ;
        }
        // 判断传入的节点的值，和当前子树的根节点的值关系
        // 即首先是需要判断值的大小【先前条件】，确定要插入的值是在子节点的左边还是右边，依据此条件再判断当前子树是否为空，
        // 若为空，则直接添加，否则，进行递归
        if(node.value < this.value){
            if(this.left == null){
                this.left = node ;
            } else{
                this.left.add(node);
            }
        } else{
            if(this.right == null){
                this.right = node ;
            } else{
                this.right.add(node);
            }
        }
    }
}




