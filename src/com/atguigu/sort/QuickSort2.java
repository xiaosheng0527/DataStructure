package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/20 16:19
 *
 * [0, 2, 30, 35, 17, 1]    刚开始的列表
 * [0, 1, 2, 35, 17, 30]
 * [0, 1, 2, 30, 17, 35]
 * [0, 1, 2, 17, 30, 35]
 *
 * 快速排序基本思想：
 * 1.选定pivot中心轴【默认选择当前序列的最左边为pivot】
 * 2.将大于pivot的数字放在pivot的右边
 * 3.将小于pivot的数字放在pivot的左边
 * 4.分别对左右子序列重复前三步操作
 *
 *  可以叫做key或者为pivot
 *  快速排序主要有三个参数，left 为区间的开始地址，right 为区间的结束地址，Key 为当前的开始的值。
 *  我们从待排序的记录序列中选取一个记录（通常第一个）作为基准元素（称为key）key=arr[left]，然后设置两个变量，left指向数列的最左部，right 指向数据的最右部。
 *
 *  具体步骤详解：
 *  【即先和右边的元素进行比较，不满足条件继续移动right指针【right--】，满足后将right指向的值进行移动到前面，然后相当于空出right指针指向的位置
 *    然后交替换成left指针，不满足条件继续移动left指针【left++】，满足后将left指向的值进行移动到后面，然后相当于空出left指针指向的位置
 *   重复此操作，直到left==right，此时将pivot【中轴】插入到剩余的空缺位置，则第一趟排序完成】
 *
 *   之后对于每一个子序列分别进行快排，因为是利用相同的步骤，故可以利用递归，分为pivot左边的元素进行递归，pivot右边的元素进行递归。【此时pivot不能动】
 *
 *   切记：每一趟排完后的pivot对于下一趟排序而言，是不能动的。【固定】
 *
 * key 首先与 arr[right] 进行比较，如果 arr[right]<key，则arr[left]=arr[right]将这个比key小的数放到左边去，
 * 如果arr[right]>key则我们只需要将right--，right--之后，
 * 再拿arr[right]与key进行比较，直到arr[right]<key交换元素为止。
 *
 * 具体的移动图解可以看b站，里面的一个图解很详细
 */
public class QuickSort2 {

    public static void main(String[] args) {
        //int[] arr = {0, 2, 30, 35, 17, 1};
        //quickSort2(arr, 0, arr.length - 1);
//        System.out.println(Arrays.toString(arr));

        // 创建80000个的随机的数组
        int[] arr = new int[8] ;
        for(int i = 0 ; i < 8 ; i++){
            arr[i] = (int) (Math.random() * 80);  // 生成一个[0,8000000)的数，为什么要取怎么大，为了避免取到相同的值
        }
//        Date date1 = new Date() ;   // 实体类
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        long before = System.currentTimeMillis() ;
        //System.out.println("排序前的时间是=" + System.currentTimeMillis()) ;

        // 递归入口
        // 需要先传入边界指针【边界索引/边界值】
        // 因为每一层的递归都需要边界指针
        quickSort2(arr,0,arr.length - 1);

//        Date date2 = new Date() ;   // 实体类
//        String date2Str = sdf.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);  // 选择排序用时4s
        long after = System.currentTimeMillis() ;

        System.out.println("执行总时间" + (after - before) / 1000.0 + "s");    // 80000数据，执行总时间0.028s
    }                                                                        // 800000数据，执行总时间0.169s
                                                                             // 8000000数据，执行总时间1.573s


    /**
     *
     * @param arr   待排序数组
     * @param L     数组最左端下标
     * @param R     数组最右端下标
     */
    // 切记：在每一趟排序的时候最好先对right指针进行判断，再对left指针判断，比较好
    // 具体的移动图解可以看b站，里面的一个图解很详细
    public static void quickSort2(int[] arr,int L,int R){
        if(L >= R){
            return ;    // 递归出口
        }
        int left = L ;  // 先指向最右端
        int right = R ; // 先指向最左端
        int pivot = arr[left] ;     // 中心轴选择最左端，将pivot抽离出来
        while(left < right){    // 下面的两个while循环和if语句始终是right指针比left指针优先判断
            while(left < right && arr[right] >= pivot){ // 若恰好满足arr[right] >= pivot，则说明可以不用移动，然后让right--
                right-- ;
            }
            // 跳出while循环，如果left < right,说明还没结束判断
            if(left < right){   // 说明之前while循环退出是因为arr[right] >= pivot不满足条件，而不是left < right这个条件不满足
                arr[left] = arr[right] ;
            }
            while(left < right && arr[left] <= pivot){
                left++ ;
            }
            if(left < right){   // 说明之前while循环退出是因为arr[left] <= pivot不满足条件，而不是left < right这个条件不满足
                arr[right] = arr[left] ;
            }
            // 当最后left == right，说明此时已经第一趟排序完毕，再将left指针的指向位置存放pivot元素
            // 若不满足，则继续进行移动指针，故最外层需要有一个while(left < right)
            if(left >= right){
                arr[left] = pivot ; // 注意：此时的left==right，故此处也可以arr[right] = pivot ;
            }
        }
        System.out.println(Arrays.toString(arr));
        // 以为下面的代码都是重复性的，故可以利用递归思想
        // 但这里要切记：每一趟排序【每一个子序列的pivot当快排结束后】，再进行下一趟排序时候，之前的pivot在递归时就不用考虑。故每次左递归时候，right-1，右递归left+1
        //  向pivot的左边递归，直到，递归调用到最后L >= R,此时递归返回，注意：第一次递归入栈会对pivot的左边的子序列进行快排,但此时左边的序列快排后也会再分隔成左子序列，和右子序列
        //  此时我们再将左子序列再次进行递归；       即这里也存在回溯，即在最后一次对左序列快排时，刚好符合L >= R,返回到上一个调用点，然后再进行右边序列的递归
        // 注意一点：这里右边的递归会和左边的递归进行挂钩，比如说，在左边递归到最后L>R时，此时递归返回，返回到前一个调用点，然后继续执行下面右子序列，此时的右子序列的变量left

        /**
         *
         * 综上：你不要咬文嚼字，就是不要死磕，了解大概的方向即可，不要刻意的计较递归的实际变化情况，这样会搞晕的
         *      你可以宏观的看下，首先是对左边进行递归，然后对右边进行递归
         *       如果你想稍微理解一下可以看如下我说的意思：
         *       第一次递归入栈会对pivot的左边的子序列进行快排,但此时左边的序列快排后也会再分隔成左子序列，和右子序列
         *       此时我们再将左子序列再次进行递归；重复此操作，递归调用到最后L >= R,此时递归返回，但是我们忘了一点，
         *       就是刚我们只是在一直对左边的序列中的左子序列进行快排，而忽略了左边的序列中的右子序列，此时进行回溯，
         *       在每一个递归返回点，要继续执行quickSort2(arr, left + 1, R) ，你要注意，此时最开始进入函数体的quickSort2(arr, L, right - 1)
         *       的递归函数还没有执行完毕，故quickSort2(arr, left + 1, R)调用是为左边的序列中的右子序列而服务的。
         *       最终：左边的序列就完全排序完毕，退出到第一个调用函数的函数体处，在执行下面的右边的序列的排序
         *
         *       同理：在pivot的右边的序列进行快排，第一次递归会对pivot的右边的子序列进行快排，但此时右边的序列快排后也会再分隔成左子序列，和右子序列
         *       此时我们再将右子序列再次进行递归；重复此操作，递归调用到最后L >= R,此时递归返回，但是我们忘了一点，
         *       就是刚我们只是在一直对右边的序列中的右子序列进行快排，而忽略了右边的序列中的左子序列，此时要进行回溯，
         *       在每一个递归返回点，继续执行quickSort2(arr, L, right - 1) ，你要注意，此时最刚开始进入函数体的quickSort2(arr, left + 1, R)
         *       的递归函数还没有执行完毕，故quickSort2(arr, L, right - 1)调用是为右边的序列中的左子序列而服务的
         *       最终：右边的序列就完全排序完毕，退出到第一个调用函数的函数体处
         *
         *       因为：左边的序列和右边的序列都以排序完毕，则可以返回至主函数main()，结束执行
          */


        // 即最终应该明白：先需要排列左序列，然后再排右序列，具体的实现由递归实现，此时只需注意递归的出口即可
        quickSort2(arr, L, right - 1);   //  向pivot的左边递归，直到，递归调用到最后 L >= R, 此时递归返回，此时相当于是对每一趟都对左边的序列进行排列

        //注意：以左边递归为例：quickSort2(arr, left, right - 1); 这样写是错的，因为第一趟排序后left == right，故如果这样调用，说明是无效递归

        //  向pivot的左边递归，直到，递归调用到最后L >= R,此时递归返回，注意：每一次递归入栈都会对pivot的右边的序列进行快排
        quickSort2(arr, left + 1, R);    //  向pivot的右边递归，直到，递归调用到最后 L >= R, 此时递归返回，此时相当于是对每一趟都对左边的序列进行排列


    }
}