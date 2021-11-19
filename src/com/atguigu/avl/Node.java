package com.atguigu.avl;

// 创建Node节点
public class Node {
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

    // 注意:这里的当前节点是要添加节点的父节点
    // 其实大体上了解就可以了，具体里面的代码需要内化

    // 返回左子树的高度
    // 需要传入当前节点的左子节点，作为左边子节点的栈区，此时就只算出左子树的高度
    // 然后执行height()实例方法时，height()方法的对象就是当前节点的左子节点，
    // 然后继续判断left == null 和 right == null，此时相当于是判断【当前节点的左子节点，即当前对象】的左子节点 以及【当前节点的左子节点，即当前对象】的右子节点
    // 此时就只算出右子树的高度

    // 不要想得太复杂，只是刚开始添加的节点和当前节点(this)存在父子关系，也有可能存在失衡关系
    // 之后回溯add()方法，后的当前节点和添加的节点就没有关系，只是可能存在失衡的关系
    public int leftHeight(){
        if(this.left == null){  // 当前节点的左子节点【左子树】为null，故说明没有左子节点，则左子树的高度为0
            return 0 ;  // 返回 0
        }
        // 否则，返回左子树的高度
        // 如果存在当前节点，此时要进入height()方法，查看其是否存在左子树，
        // 若无左子树，返回1【只包含当前节点的高度】，若有右子树，则求出其高度【高度：至少为2】
        return this.left.height() ;
    }

    // 返回右子树的高度
    // 需要传入当前节点的右子节点，作为右子节点的栈区，
    // 此时就只算出当前节点的右子树的高度
    // 注意：当前节点和添加的节点不是同一个，当前节点的左子节点/右子节点是要添加的点
    public int rightHeight(){
        if(this.right == null){     // 当前节点的右子节点【右子树】为null，故说明没有右子节点，则右子树的高度为0
            return 0 ;
        }
        // 否则，返回左子树的高度
        // 如果存在当前节点，此时要进入height()方法，查看其是否存在左子树，
        // 若无右子树，返回1【只包含当前节点的高度】，若有左子树，则求出其高度【高度：至少为2】
        return this.right.height() ;
    }

    // 返回以该节点为根节点的树的高度【并不是说：我们就从根节点root开始计算高度，假设所要计算的点作为根节点】
    // 这里是我们刚刚添加的节点，将其作为根节点，this指向当前刚刚添加的节点
    public int height(){
        // 注意：Math函数是为了统计出这个节点的左子树和右子树的最大值
        // 一个易错点：这里的左子树的高度是以最长路径的节点的个数；同理右子树的高度是以最长路径的节点的个数
        // 但是，此时我们并没有把当前节点的一层高度进行计数，故最后算到的最大值 要 加上 1
        // 这里的左子树和右子树都要进行递归计算
        // left == null ? 0 : left.height() 先进行递归，统计出左子树的高度，然后进行回溯
        // 和right == null ? 0 : left.height()进行递归回溯进行比较，当前栈中的最大值，最后到第一个调用点，递归结束，
        // 经过max()函数，得到左右子树的最大高度，然后再加上1
        // 注意；这里的递归出口在于left == null 和 right == null 对应的 值为0，此时执行此栈中的return语句，执行完毕之后返回上一次调用点
        // 但这里需要注意一点的是：如果递归遍历到只有一个叶子节点，则，刚好left和right都为空，此时执行return语句，返回到上一个调用点，此时刚好上一个调用点时右子树上的节点，
        // 如果递归遍历到left和right，只有一个指针为空【例如：只有left为null】，那么right需要递归遍历，然后回溯将其值进行返回到此处，然后再执行此处的return语句，回溯
        // 如果递归遍历到left和right都不为空，则需要将left和right分别递归入栈，如果此时是处于同一个栈中【即操作的是同一个对象】，则可以进行计算，然后将结果分别回溯返回到调用点，然后再执行此处的return语句
        // 而且此处的return语句最后要 加上1 有两层含义：
        // 1. 经过max()函数，得到左右子树的最大高度，然后再加上1,最终得到以该节点为根节点的树的高度
        // 2. 如果不加上1的话，此递归函数，递归回溯的结果无论如何都是0
        // 这里的this指向刚刚添加的节点的左子节点和右子节点，为了计算刚刚添加的节点的左子树和右子树的高度的最大值
        return Math.max(this.left == null ? 0 : this.left.height(), this.right == null ? 0 : this.right.height()) + 1 ;
    }

    // 左旋转方法
    // 注意：这个不需要背，只需要理解：
    // 其实就是把root节点往二叉树长的那一部分右移一个节点就行了

    /**
     *  其实左旋的这六步：总结起来就是：
     *      1. 把新的节点以及连接的左右子树作为后面平衡二叉树根节点的左子树，【俗称：拉下来】
     *      2. 而把把当前节点的右子树设置为当前节点的右子树的右子树,就是以当前节点为根节点，【俗称：拉上去】
     *      3. 然后将“拉上去”的根节点的 左孩子 链接到  “拉下去”的新的节点
     *      4. 最后得到的就是一颗平衡二叉树
     */
    private void leftRotate(){

        // 创建新的节点，以当前根节点的值【但注意：此新的节点最后并没有作为根节点，其只是为了链接方便，不影响其他的链接关系】
        // 而且此新的节点和当前根节点只有值是一样的，但地址空间不同，所以下面的6步具体操作，凡是链接方向改变了，原来的链接方向就会失效【相当于引用另外一块地址】
        // 而且注意：这里新创建了一个节点，就意味着原来的二叉树一定有一个节点最后没有引用指向，被JVM垃圾回收

        Node newNode = new Node(value) ;
        // 把新的节点的左子树设置成当前节点的左子树
        newNode.left = this.left ;
        // 把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.right = this.right.left ;
        // 把当前节点的值替换成右子节点的值
        this.value = this.right.value ;
        // 把当前节点的右子树设置为当前节点的右子树的右子树
        this.right = this.right.right ;
        // 把当前节点的左子树（左子节点）设置为新的节点
        this.left = newNode ;
    }

    // 右旋转
    /**
     *  其实右旋的这六步：总结起来就是：
     *      1. 把新的节点以及连接的左右子树作为后面平衡二叉树根节点的右子树，【俗称：拉下来】
     *      2. 而把把当前节点的左子树设置为当前节点的左子树的左子树,就是以当前节点为根节点，【俗称：拉上去】
     *      3. 然后将“拉上去”的根节点的 右孩子 链接到  “拉下去”的新的节点
     *      4. 最后得到的就是一颗平衡二叉树
     */
    private void rightRotate(){

        // 创建新的节点，以当前根节点的值【但注意：此新的节点最后并没有作为根节点，其只是为了链接方便，不影响其他的链接关系】
        // 而且此新的节点和当前根节点只有值是一样的，但地址空间不同，所以下面的6步具体操作，凡是链接方向改变了，原来的链接方向就会失效【相当于引用另外一块地址】
        // 而且注意：这里新创建了一个节点，就意味着原来的二叉树一定有一个节点最后没有引用指向，被JVM垃圾回收

        Node newNode = new Node(value) ;
        newNode.right = right ;
        newNode.left = left.right ;
        value = left.value ;
        left = left.left ;
        right = newNode ;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    // 查找要删除的节点

    /**
     *
     * @param value 希望删除的节点的值
     * @return      如果找到返回该节点，否则返回null
     */
    public Node search(int value){
        if(value == this.value){    // 找到就是该节点
            return this ;
        } else if(value < this.value){  // 如果查找的值小于当前节点，向左子树递归查找
            // 如果左子节点为空，就直接返回
            if(this.left == null){
                return null ;
            }
            return this.left.search(value) ;

        } else{     // value > this.value
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
    public Node searchParent(int value){
        if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
            return this ;
        } else{
            // 如果 当前节点的左子节点不为空,并且 查找的值小于当前节点的值
            if(this.left != null && value < this.value){
                return this.left.searchParent(value) ;  // 向左子树递归查找
            }
            // 反之。如果 当前节点的右子节点不为空,并且 查找的值大于或等于当前节点的值
            else if(this.right != null && value >= this.value){     // 此处为什么可以有这个条件：value==this.value
                return this.right.searchParent(value) ; // 向右子树递归查找
            } else{
                return null ;   // 没有找到父节点
            }
        }
    }

    // 添加节点的方法
    // 递归的形式添加节点，注意需要满足二叉排序树的要求
    // 总之：在每一次调用add()方法时，都需要判断是否处于失衡二叉树
    // 而且this关键字，在每一次执行add()方法后，每一次进入递归栈中，遇到add()方法搜索。
    // 检索方向：是"从下到上"，故一般来说都是先比较小树是否平衡，最后比较刚开始调用add()方法的根节点root，所在的左右子树是否平衡
    // 之前我一直以为对于树的判断是否平衡只判断一次，其实在每一次回溯add()方法的时，都会判断是否平衡
    public void add(Node node){
        if(node == null){
            return ;
        }

        // 判断传入的节点的值，和当前子树的根节点的值关系
        if(node.value < this.value){
            // 如果当前节点的左子节点为null
            if(this.left == null){
                this.left = node ;  // 递归是为了此处添加节点服务
            } else{
                // 递归的向左子树添加
                this.left.add(node);
            }
        } else{ // 添加的节点的值大于 当前节点的值
            if(this.right == null){
                this.right = node ; // 递归是为了此处添加节点服务
            } else {
                // 递归的向右子树添加
                this.right.add(node);
            }
        }

        // ----------------------------------------------------
        // 注意：上面是没有处理是否为平衡二叉树，下面是处理，按照规则处理得到平衡二叉树
        // ----------------------------------------------------

        // 当给 当前节点/对象 添加完一个左子/右子节点后，如果：【当前节点】(右子树的高度 - 左子树的高度) > 1 ,左旋转
        // 注意：此时我们就将当前添加的节点的父节点作为根节点，而不是将当前节点作为根节点，
        // 因为：上面我们在添加元素时候都是this.left = node;以及this.right = node
        // 故this的指向是当前节点/对象【或者假设是失衡树的根节点】，它是添加了一个左子节点或右子节点的父节点【根节点】

        // 注意：在任何添加节点的过程中，this的指向是当前节点/对象【或者假设是失衡树的根节点】，
        // 如果this指向的当前节点是失衡树的根节点，则进行旋转，如果不是，则进行递归回溯到上一次调用add()的方法处，
        // 一层层的判断，直到处理完后一整颗树都是平衡二叉树

        if(this.rightHeight() - this.leftHeight() > 1){
            // 如果它的右子树的左子树高度大于它的右子树的高度
            //  【此时添加节点后，使得当前二叉树处于失衡RL特性，需要先右旋，再左旋】
            if(this.right != null && this.right.leftHeight() > this.right.rightHeight()){
                // 先对当前节点的右子节点（右子树） -> 右旋转
                this.right.rightRotate();
                // 然后再对当前节点进行左旋转
                this.leftRotate();   // 左旋转。。。
            }else {
                // 直接进行左旋转即可【此时添加节点后，使得当前二叉树处于失衡RR特性，需要左旋】
                this.leftRotate();   // 左旋转。。。
            }
            // 综上：添加这个return语句，就是怕后面的代码影响了调整过后的平衡二叉树
            return ;    // 必须要！！！即如果前面进行左旋完毕之后要立刻返回，不然可能还会满足下面的条件，
                        // 则就会造成左旋转后的二叉树，被打乱，所以需要严谨，添加一个return
        }

        // 当添加完一个节点后，如果：(左子树的高度 - 右子树的高度) > 1 ,右旋转
        if(this.leftHeight() - this.rightHeight() > 1){

            // 如果它的左子树的右子树高度大于它的左子树的高度
            // 【此时添加节点后，使得当前二叉树处于失衡LR特性，需要先左旋，再右旋】
            if(this.left != null && this.left.rightHeight() > this.left.leftHeight()){
                // 先对当前节点的左子节点（左子树） -> 左旋转
                this.left.leftRotate();
                // 再对当前节点进行右旋转
                this.rightRotate();
            } else {
                // 直接进行右旋转即可【此时添加节点后，使得当前二叉树处于失衡LL特性，需要右旋】
                this.rightRotate();
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


