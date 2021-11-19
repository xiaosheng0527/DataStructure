package com.atguigu.avl;


/**
 * @author shengxiao
 * @date 2021/7/27 17:47
 */
public class AVLTree {

    private Node root ;

    public Node getRoot() {
        return root;
    }

    // 查找要删除的节点
    public Node search(int value){
        if(root == null){
            return null ;
        } else{
            return this.root.search(value) ;
        }
    }

    // 查找要删除的节点的父节点
    public Node searchParent(int value){
        if(root == null){
            return null ;
        } else{
            return this.root.searchParent(value) ;
        }
    }

    // 编写方法：
    // 可以是对右子树进行遍历，找到值最小的节点【注意：值最小节点是在一直向左子树遍历，直到最后一个叶子节点】
    // 也可以是对左子树进行遍历，找到值最大的节点【注意：值最大节点是在一直向右子树遍历，直到最后一个叶子节点】
    // 此方法以对右子树进行遍历，找到值最小的节点
    // 1. 返回以node 为根节点的二叉排序树的最小节点的值
    // 2. 删除node 以根节点的二叉排序树的最下节点
    //
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
        // 这时 target就指向了最小节点
        // 删除最小节点
        delNode(target.value);
        return target.value ;
    }

    // 删除节点
    public void delNode(int value){
        if(root == null){
            return ;
        } else{
            // 1.需要先去找到要删除的节点  targetNode
            Node targetNode = search(value) ;
            // 如果没有找到要删除的节点
            if(targetNode == null){
                return  ;
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
            Node parent = searchParent(value) ;
            // 要实现删除叶子节点，则需要先确定叶子节点后面的情况，再确定叶子节点在父节点中的情况
            // 这个和链表删除及其类似

            // 如果要删除的节点是叶子节点
            // 切记：这里无需考虑父节点为空，因为这里是删除叶子节点，故父节点永远不为空
            if(targetNode.left == null && targetNode.right == null){
                // 判断targetNode 是 父节点的左子节点，还是右子节点
                if(parent.left != null && parent.left.value == value){  // 如果是左子节点
                    // 其实写成parent.left = targetNode.left ; parent.left = targetNode.right;
                    // 那还不如直接写成null，避免误会
                    parent.left = null ;    // 将parent.left置为null，则说明此处删除的是叶子节点，未影响到前面的节点
                } else if(parent.right != null && parent.right.value == value){ // 如果是右子节点
                    parent.right = null ;
                }
            }

            // 如果要删除有两颗子树的节点，即之后需要将右子树中值最小的节点往上移
            // 错了，这样会影响树的结构，所以正解应该是：
            // 其实此处删除的本质就是将右子树的最小值的节点进行删除，然后将其返回的最小值覆盖到要删除的节点，即要删除的节点只是修改了值而已
            // 这样才不会影响树的结构
            // 而且：要切记：这里无需考虑是否有父节点
            else if(targetNode.left != null && targetNode.right != null){
                int minVal = delRightTreeMin(targetNode.right) ;    // 从右子树找，找到最小值对应的节点，并返回最小值
                targetNode.value = minVal ;     // 然后将右子树的最小值放置到删除节点的位置
            }

            // 删除只有一颗子树的节点，这个情况很复杂，故直接用else语句来简化里面的判断语句的条件
            // 此时就需要移动树，而且，此时也需要考虑父节点
            else{
                // 如果要删除的节点有左子节点
                if(targetNode.left != null){
                    // 为什么这里还需要判断是否存在父节点，因为，上面的判断是因为如果只有一个节点的情况下，
                    // 而这里，如果是只有左子树和根节点，而不存在右子树，
                    // 那么如果此时删除的是根节点，则其无父节点，故如果不加这个条件就会出现空指针异常
                    if(parent != null){ // 判断是否存在父节点
                        //  如果 targetNode 是 parent 的左子结点
                        if(parent.left.value == value){
                            parent.left = targetNode.left ;
                        } else{ // targetNode 是 parent 的右子节点
                            parent.right = targetNode.left ;
                        }
                    } else {    // 如果不存在父节点，说明要删除的节点是位于根节点处
                        root = targetNode.left ;
                    }

                } else{     // 如果要删除的节点有右子节点
                    // 为什么这里还需要判断是否存在父节点，因为，上面的判断是因为如果只有一个节点的情况下，
                    // 而这里，如果是只有根节点和右子树，不存在左子树
                    // 那么如果此时删除的是根节点，则其无父节点，故如果不加这个条件就会出现空指针异常
                    if(parent != null){
                        // 如果targetNode 是 parent  的 左子节点
                        if(parent.left.value == value){
                            parent.left = targetNode.right ;
                        }  else{    // 如果targetNode是parent的右子节点
                            parent.right = targetNode.right ;
                        }
                    } else{
                        root = targetNode.right ;
                    }

                }
            }

        }
    }

    // 添加节点的方法
    public void add(Node node){
        if(this.root == null){
            this.root = node ;   // 如果root为空，则直接让root指向node
            // 注意：切记引用类型变量的魅力，都可以随机指向，但如果是root.next = node，则会出现空指针异常
        } else{
            this.root.add(node);
        }
    }

    // 中序遍历
    public void infixOrder(){
        if(this.root != null){
            this.root.infixOrder();
        } else{
            System.out.println("二叉排序树为空，不能遍历");
        }
    }

    // 前序遍历
    public void preOrder(){
        if(this.root == null){
            System.out.println("空树，无法遍历");
        }
        this.root.preOrder();
    }
}
