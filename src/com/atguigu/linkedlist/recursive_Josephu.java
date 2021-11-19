package com.atguigu.linkedlist;

/**
 * @author shengxiao
 * @date 2021/7/15 15:27
 * 编号为 0~n-1 的小孩，完丢手绢
 * // 这里没哟给定初始报数的地方，默认编号为0的地方开始
 */
public class recursive_Josephu {

    /**
     *
     * @param n     n指的是总共有多少人
     * @param m     报数的个数
     * @return      返回编号
     */
    public int josephu(int n,int m){

        /**
            // 法1：循环,相当于递推，从底往上推
            // 初始条件，如果只有一个人，则
            if(n == 0 || m == 0){
                return -1 ; // 代表圈中没有人
            }
            int last = 0 ;  // 如果只有一个人，则直接返回初始值last=0
            // 注意：
            // f(n,m) = (f(n-1,m) + m) % n  这个公式里的n会发生,因为进行递归，每一次会进行压栈，且模拟一个循环出队顺序。
            // 因为，每一次kill一个人，环里的人数减去1
            for(int i = 2 ; i <= n ; i++){
                last = (last + m) % i ; // 看CSDN收藏夹推导
            }
            return last ;
        */
        // 法2：递归法
        // 退出条件，这里不考虑没有元素的情况，主要就是算法分析，不像是尚硅谷老师教的很详细。
        if(n == 1){
            return 0 ;  //这里返回下标,从0开始，只有一个元素就是第一个节点 编号【下标】为0，【这里我们模拟为节点构成的队列】
                        // 注意：如果刚开始输入时候的n ！= 1 ，则此时要进行递归返回，即相当于弹栈
        }else{
            return (josephu(n - 1,m) + m) % n ;     // 递归嵌套，一直进行压栈
        }
    }

    public static void main(String[] args) {
        System.out.println(new recursive_Josephu().josephu(16, 2));
    }
}
