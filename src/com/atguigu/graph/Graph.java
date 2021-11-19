package com.atguigu.graph;

import java.util.*;

/**
 * @author shengxiao
 * @date 2021/7/29 15:59
 *
 *  即：  深度优先遍历即为：一条道走到黑【对应邻接矩阵中即为“纵向搜索”，对于图来说即为“每一次都往最深处行走”】
 *       广度优先遍历即为：我愿意一层层的剥开你的心【对应邻接矩阵中即为“横向搜索”，对于图来说即为 ”水波的扩散“】
 */
public class Graph {

    private List<String> vertexList ;   // 存储顶点集合
    private int[][] edges ; // 存储图对应的邻接矩阵,里面的元素值为权值
    private int numOfEdges ;    // 表示边的个数
    // 定义个数组boolean[],记录某个节点是否被访问
    private boolean[] isVisited ;

    public Graph() {
    }

    // 有参构造器
    public Graph(int n) {
        //this.numOfEdges = numOfEdges;
        edges = new int[n][n] ;
        vertexList = new ArrayList<>(n) ;
        numOfEdges = 0 ;    // 刚开始邻接矩阵的边的个数未知，默认为0
        //isVisited = new boolean[n] ;    // 默认值为false
    }

    // 得到第一个邻接节点的下标 w
    /**
     *
     * @param index     当前节点的下标
     * @return  如果存在就返回下一个邻接节点对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index){
        // 这里的j代表从0开始查找下一个邻接节点的位置【从0~length-1】进行深度优先遍历【纵向：从邻接矩阵来看】
        // 注意：一旦在【0~length-1】找到了一个邻接节点，此时直接跳出循环，然后进行判断是否为访问过的点
        // 如果是还未访问过的点，则可以进行访问，然后再把当前以标记为访问的点作为当前节点，继续向下遍历【不会像执行之前 在(0~length-1) 找到了一个邻接节点后继续搜索，因为这是广度优先搜索的规则】
        // 即：深度优先遍历即为：一条道走到黑
        // 广度优先遍历即为：我愿意一层层的剥开你的心
        for(int j = 0 ; j < vertexList.size() ; j++){
            // 为什么需要这样判断？？？
            // 因为：这是dfs【深度优先遍历】，访问策略是优先往纵向挖掘深入，而不是对一个节点的所有邻接节点进行横向访问
            if(edges[index][j] > 0){    // 如果当前节点的下一个节点是邻接节点，则说明下一个邻接点存在，故返回其下标
                return j ;
            }
        }
        return -1 ;     // 说明当前节点的下一个节点并没有找到邻接节点，返回-1
    }

    // 为什么要存在这个方法？？？
    // 是因为当前节点存在第一个邻接节点【等价于上面的getFirstNeighbor()方法找到了下一个邻接节点】，
    // 此时我们就需要进行进行深度优先遍历，查看是否还存在对应的邻接节点
    // 根据前一个邻接节点的下标来获取下一个邻接节点【这里是优先往纵向挖掘深入】
    public int getNextNeighbor(int v1,int v2){
        // 这里为什么需要j=v2+1，因为前一个邻接节点已然找到了，此时需要找下一个邻接节点
        for(int j = v2 + 1 ; j < vertexList.size() ; j++){
            if(edges[v1][j] > 0){
                return j ;
            }
        }
        return -1 ;     // 邻接节点的下标来获取下一个邻接节点时候，找不到，故返回-1
    }

    // 深度优先遍历算法
    // i 第一次就是0

    /**
     *
     * @param isVisited        记录某个节点是否被访问
     * @param i                代表当前访问的节点，默认从索引值为0处访问第一个节点
     *
     *  注意：此函数其实考虑到连通图的遍历情况，即如果此图是连通图，dfs()方法，可直接遍历所有的节点
     */
    private void dfs(boolean[] isVisited,int i){
        // 首先我们访问该节点，输出
        System.out.print(getValueByIndex(i) + "->");
        // 将节点设置为已经访问
        isVisited[i] = true ;
        // 查找节点i的第一个邻接节点w【横向，查找】
        // 注意：如果没有查找到，则说明没有邻接节点，就要退出dfs()此方法
        int w = getFirstNeighbor(i) ;
        // 注意：这里的前后顺序要谨记：是在有邻接节点的情况下，判断该邻接节点是否被访问过
        // 如果 当前访问的节点的第一个临界节点找不到，则说明，此路不通
        // 但如果当前访问的节点的第一个临界节点找到了，则我们此时需要判断第一个邻接节点是否被访问过，
        // 因为它有可能是回溯回来的，那么已经访问过的，我们不能重复访问
        while(w != -1){     // 说明有邻接节点
            if(!isVisited[w]){  // 而且当前的邻接节点w还未被访问
                dfs(isVisited, w);  // 对第一个邻接节点w进行dfs【纵向查找】
            }
            // 如果w节点已经被访问过，则需要查找节点i的w邻接节点的同一层的下一个邻接节点，
            // 即要对深度遍历到的同一层的后一个邻接节点进行搜索判断【直到找到一个符合条件的，此时就可以继续深度优先遍历】
            // 这里的i：表示正在被访问的节点
            // w：表示已经找到的邻接节点，但是已经被访问过的
            w = getNextNeighbor(i, w) ;     // 【横向查找】
        }
    }

    // 对dfs 进行重载，遍历我们所有的节点，并进行 dfs
    // 其实如果只是PPT里面的那个连通图，其实只需要上面的dfs就可以了，不用重载
    // 每一次进行深度优先遍历，不符合再回溯，返回到上一个调用点，然后再进行深度优先dfs
    // 但是这里的重载dfs的目的在于，如果是非连通图，此时下面的重载就很有必要了
    // 因为不管怎么回溯，我们一次dfs都会将连通图的所有节点都遍历一遍，但和此连通图不相邻的点还是遍历不到
    public void dfs(){
        isVisited = new boolean[vertexList.size()] ;
        // 遍历所有的节点，进行dfs【回溯】
        for(int i = 0 ; i < getNumOfVertex() ; i++){
            if(!isVisited[i]){

                dfs(isVisited, i);
                // eg：当第一个节点dfs后，还需要深度遍历第二个节点，此这里进行遍历所有的节点
                // 但是此图是连通图，其实当i=0时候，经过递归dfs，再回溯，在递归dfs，已经将连通图中所有的节点都遍历了一遍
                // 故后面i = 1 ，2,3...都会直接跳出dfs()方法
                // 但是这里的重载dfs的目的在于，如果是非连通图，此时下面的重载就很有必要了
            }
        }
    }

    // 对一个节点进行广度优先遍历的方法
    private void bfs(boolean[] isVisited,int i){
        int u ; // 表示队列的头结点对应下标
        int w ; // 邻接节点w
        // 队列，记录节点访问的顺序，利用LinkedList来模拟
        LinkedList queue = new LinkedList<>() ; // 底层维护了一个双向链表
        // 访问节点，输出节点信息
        System.out.print(getValueByIndex(i) + "=>");
        // 标记为已经访问
        isVisited[i] = true ;
        // 将节点加入队列
        queue.addLast(i);

        while(!queue.isEmpty()){
            // 取出队列的头节点下标
            u = (Integer) queue.removeFirst();
            // 得到第一个邻接节点的下标w
            w = getFirstNeighbor(u) ;   // 横向搜索
            while(w != -1){     // 判断节点u的第一个邻接节点w是否存在
                // 是否访问过
                if(!isVisited[w]){
                    System.out.print(getValueByIndex(w) + "=>");
                    // 标记已经访问
                    isVisited[w] = true ;
                    // 入队，即为将标记已访问的邻接节点入队
                    queue.addLast(w);
                }
                // 以u为前驱点【并不一定是之前前驱，可能是间接前驱】，找w后面的下一个邻接节点
                w = getNextNeighbor(u, w) ;     // 体现出我们的广度优先
            }
        }

    }

    // 遍历所有的节点，都进行广度优先搜索
    public void bfs(){
        isVisited = new boolean[vertexList.size()] ;
        for(int i = 0 ; i < getNumOfVertex() ; i++){
            if(!isVisited[i]){
                bfs(isVisited, i);
            }
        }
    }


    // 图中常用的方法
    // 返回节点的个数
    // 其实封装后，重构性强，不会影响原来调用的代码，直接在方法中修改即可
    public int getNumOfVertex(){
        return vertexList.size() ;
    }

    // 显示图对应的矩阵
    public void showGraph(){
        for(int[] link : edges){
            System.out.println(Arrays.toString(link));  // 输出一个个一维数组
        }
    }

    // 得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    // 返回节点i（下标）对应的数据  0 -> "A" 1 -> "B"  2 -> "C"
    public  String getValueByIndex(int i){
        return vertexList.get(i) ;
    }

    // 返回v1 和 v2 的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2] ;
    }

    // 插入节点
    public void insertVertex(String vertex){
        vertexList.add(vertex) ;
    }
    // 添加边
    /**
     * 注意：这里的第一个位置，对应的是索引值为0
     * @param v1    表示第一个顶点的下标， eg："A" - "B" 的关系中 "A"->0  "B"->1
     * @param v2    表示第二个顶点对应的下标
     * @param weight    权值
     *
     * 注意："A" - "B" 的关系中 "A"->0  "B"->1，和 "A"->1  "B"->0 是等价的，
     *     因为对于同一个图中，两个顶点的权值【在不同 的比较下都是不会变的】
     *
     * 注意：这里的 "A","B","C","D","E"对应着索引位置为[0,4]
     *
     * 而且注意：这里的的下标为0就表示第一个元素
     */
    public void insertEdge(int v1,int v2,int weight){
        // 这里为什么要交换元素对应的索引位置然后赋给相同的值，因为邻接矩阵是无向的，即双向的
        // 而且行索引和列索引在对应的位置上表示的是赋给相同的值，但我们表示成二维数组是行索引在前，列索引在后，
        // 故这里需要交换元素对应的索引，然后赋给相同的值
        edges[v1][v2] = weight ;
        edges[v2][v1] = weight ;
        numOfEdges++ ;
    }

    public static void main(String[] args) {
        // 测试一把图是否创建ok
        int n = 5 ; // 节点的个数
        String[] vertexs = {"A","B","C","D","E"} ;
        // 创建图对象
        Graph graph = new Graph(n) ;
        // 循环的添加节点
        for(String vertex : vertexs){
            graph.insertVertex(vertex); // 存储顶点集合,执行insertVertex()方法，将顶点存放到List集合中
        }

        // 添加边
        // A-B  A-C  B-C  B-D  B-E
        graph.insertEdge(0,1,1);    // A-B
        graph.insertEdge(0,2,1);    // A-C
        graph.insertEdge(1,2,1);    // B-C
        graph.insertEdge(1,3,1);    // B-D
        graph.insertEdge(1,4,1);    // B-E

        // 显示一把邻接矩阵
        graph.showGraph();

        // 测试一把，我们的dfs遍历是否ok
        System.out.println("深度遍历");
        graph.dfs();    //  A->B->C->D->E

        System.out.println();

        System.out.println("广度优先");
        graph.bfs();

    }


}
