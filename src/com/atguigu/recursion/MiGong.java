package com.atguigu.recursion;

/**
 * @author shengxiao
 * @date 2021/7/17 20:08
 */
public class MiGong {

    public static void main(String[] args) {
        // 先创建一个二维数组，模拟数组
        // 地图
        int[][] map = new int[8][7] ;
        // 使用1 表示墙
        // 上下全部置为1
        for(int j = 0 ; j < 7 ; j++){
            map[0][j] = 1 ;
            map[7][j] = 1 ;
        }
        // 左右全部置为1
        for(int i = 0 ; i < 8 ; i++){
            map[i][0] = 1 ;
            map[i][6] = 1 ;
        }

        // 设置挡板，用1表示
        map[3][1] = 1 ;
        map[3][2] = 1 ;
        
        // 输出地图
        System.out.println("地图的情况");
        for (int[] row: map) {
            for (int item: row) {
                System.out.print(item + "\t");
            }
            System.out.println();
        }

        // 使用递归回溯给小球找路
        //setWay(map, 1, 1) ;
        setWay2(map, 1, 1) ;

        // 输出新的地图，小球走过，并标识过的地图
        System.out.println("小球走过，并标识过的地图的情况");
        for (int[] row: map) {
            for (int item: row) {
                System.out.print(item + "\t");
            }
            System.out.println();
        }
    }

    // 使用递归回溯来给小球找路
    // 说明
    // 1. map 表示地图
    // 2. i,j 表示从地图的哪个位置开始出发（1,1）
    // 3. 如果小球能到map[6][5] 位置，则说明通路找到
    // 4. 约定：当map[i][j] 为 0 表示该点没有走过；当为1表示墙；当为2表示通路可以走【即该点已经走过，且是构成当前通路的路径；但没有确定完整通路的路径，因为可能存在回溯导致2变成3】；3表示该点已经走过，但是走不通
    // 5. 在走迷宫时，需要确定一个策略（方法），这里设置为 下->右->上->左，如果该点走不通，再回溯。
    //    【注意：千万不要不按套路出牌；即如果没有策略的乱走，效率会非常低】
    /**
     *
     * @param map   表示地图
     * @param i     从哪个位置开始找
     * @param j
     * @return  如果找到通路，就返回true，否则返回false
     *
     */
    // 注意：此方法只找出一条完整的路径，因为没有涉及到回溯后，再次递归找路线的代码
    public static boolean setWay1(int[][] map,int i,int j){
        if(map[6][5] ==2){  // 通路已经找到
            // 递归退出条件
            return true ;
        } else{
            if(map[i][j] == 0){ // 如果当前这个点还没有走过
                // 按照策略 下->右->上->左
                map[i][j] = 2 ; //  假定该点是可以走通【因为我们走的时候刚开始以为自己走的是对的】
                if(setWay1(map, i+1, j)){    // 向下走
                    return true ;
                } else if(setWay1(map, i, j+1)){   // 向右走
                    return true ;
                } else if(setWay1(map, i-1, j)){     // 向上走
                    return true ;
                } else if(setWay1(map, i, j-1)){    // 向左走
                    return true ;
                } else{
                    // 说明该点是走不通的，是死路【假设该点可以走通，假设失败】
                    // 注意：下面这行代码是在每走一步时候【按照策略下->右->上->左 走】，如果无法走通才会执行的分支代码
                    map[i][j] = 3 ;
                    return false ;
                }
            } else {    // 如果map[i][j] != 0，可能是1,2,3；即当前这个点已经走过
                // 但是值为1说明是墙，故不能走；值为2表示已经走过的路，不能再重复走【这里是按照假定该点可以走，遵循一定的策略】；
                // 换一个理解方式：当为2表示通路可以走【即该点已经走过，且是构成当前通路的路径；但没有确定完整通路的路径，因为可能存在回溯导致2变成3】
                // 值为3表示该点已经走过，但是走不通【死路】
                // 综上：如果执行到map[i][j] != 0，说明，要原路返回了
                return false ;
            }
        }
    }

    // 修改找路的策略，改成 上->右->下->左
    public static boolean setWay2(int[][] map,int i,int j){
        if(map[6][5] ==2){  // 通路已经找到
            // 递归退出条件
            return true ;
        } else{
            if(map[i][j] == 0){ // 如果当前这个点还没有走过
                // 按照策略 下->右->上->左
                map[i][j] = 2 ; //  假定该点是可以走通【因为我们走的时候刚开始以为自己走的是对的】
                if(setWay2(map, i-1, j)){    // 向上走
                    return true ;
                } else if(setWay2(map, i, j+1)){   // 向右走
                    return true ;
                } else if(setWay2(map, i+1, j)){     // 向下走
                    return true ;
                } else if(setWay2(map, i, j-1)){    // 向左走
                    return true ;
                } else{
                    // 说明该点是走不通的，是死路【假设该点可以走通，假设失败】
                    // 注意：下面这行代码是在每走一步时候【按照策略下->右->上->左 走】，如果无法走通才会执行的分支代码
                    map[i][j] = 3 ;
                    return false ;
                }
            } else {    // 如果map[i][j] != 0，可能是1,2,3；即当前这个点已经走过
                // 但是值为1说明是墙，故不能走；值为2表示已经走过的路，不能再重复走【这里是按照假定该点可以走，遵循一定的策略】；
                // 换一个理解方式：当为2表示通路可以走【即该点已经走过，且是构成当前通路的路径；但没有确定完整通路的路径，因为可能存在回溯导致2变成3】
                // 值为3表示该点已经走过，但是走不通【死路】
                // 综上：如果执行到map[i][j] != 0，说明，要原路返回了
                return false ;
            }
        }
    }

    // 最短路径策略：把可能的策略用一个算法设计出来，可以设计一个数组；用一个数组来表示不同策略。
    // 用一个for循环遍历这些策略，然后将每个策略中的值为2的元素保存到集合中，统计各个策略下2的个数。
    // 其实共有4！=4*3*2*1=24 种走法

}
