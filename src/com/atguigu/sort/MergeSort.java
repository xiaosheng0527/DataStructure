package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author shengxiao
 * @date 2021/7/20 21:51
 *
 * 每次合并都是合并完才执行下一次合并函数，所以上一次合并的数组已经释放了，即当每一次临时数组合并完之后都会进行释放空间
 *
 * 归并排序（Merge sort）:
 *      是建立在归并操作上的一种有效的排序算法，归并排序对序列的元素进行逐层折半分组，
 *      然后从最小分组开始比较排序【按照升序排列】，合并成一个大的分组，逐层进行，最终所有的元素都是有序的。
 *      注意：最小分组本来就是已经排好序的，不论是从大到小还是从小到大都是如此，故可直接回溯到前一个调用点，继续对倒数第二小的分组进行合并。
 *
 *         // 注意：递归不要 被绕晕了，主要是回溯的思想比较复杂，你可以宏观看一下，是先向左递归，然后再向右递归 ，然后再合并；
 *         // 而其内部的调用很复杂，但始终每一个递归栈中的这三个递归方法，都是在为每一组合并的进行按序服务；
 *         // 你可以看看我在快排QuickSort2 里面对具体递归中方法的如何调用分析了一个层面；不要陷得太深，具体的思路理解就好。
 *
 *  可以看到这种结构很像一棵完全二叉树，本文的归并排序我们采用递归去实现（也可采用迭代的方式去实现）。分阶段可以理解为就是递归拆分子序列的过程。
 *  为什么说归并排序算法的拆解与 合并 很像是一颗完全二叉树;
 *  注意：其递归调用的方式，即相当于进行拆解，即拆解到左边递归函数的出口时候，进行返回，然后再进行右边递归函数，直到出口进行返回，然后将对应的值进行合并
 *      这是不是很像，二叉树中的 后序遍历【先访问左子节点，再访问右子节点，再访问根节点】
 */
public class MergeSort {

    public static void main(String[] args) {
//        int[] arr = {8,4,5,7,1,3,6,2} ;
//        int[] temp = new int[arr.length] ;  // 归并排序需要一个额外的空间
//        mergeSort(arr, 0, arr.length - 1, temp);

        // 创建80000个的随机的数组
        int[] arr = new int[8000000];
        int[] temp = new int[arr.length];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 800000000);  // 生成一个[0,80000)的数，为什么要取怎么大，为了避免取到相同的值
        }
//        Date date1 = new Date() ;   // 实体类
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        long before = System.currentTimeMillis();
        //System.out.println("排序前的时间是=" + System.currentTimeMillis()) ;

        mergeSort(arr, 0, arr.length - 1, temp);

//        Date date2 = new Date() ;   // 实体类
//        String date2Str = sdf.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);  // 选择排序用时4s
        long after = System.currentTimeMillis();

        System.out.println("执行总时间" + (after - before) / 1000.0 + "s");    // 80000数据，执行总时间0.026s
                                                                             // 800000数据，执行总时间0.211s
                                                                             // 8000000数据，执行总时间1.885s
        //System.out.println("归并排序后 = "+Arrays.toString(arr));
    }

    // 分 和 分+合的方法
    /**
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param right 右边末尾元素的索引
     * @param temp  做中转的数组，最后修改后的元素还要再次拷贝回原数组arr
     *
     * 注意：这里为什么不需要添加一个参数mid，因为这个mid参数对mergeSort没有实质性的影响！！
     *       故可以不用添加，而是在每个递归栈中进行定义;而且如果添加了这个函数中的函数形参变量，
     *       还会影响到下面向左递归和向右递归；
     *              eg：向左递归时，mergeSort(arr, left, mid, temp);，此时如果再添加一个参数mid表示中间索引，那么左递归时，int right参数定义就没有用了
     *            同理：向右递归时，mergeSort(arr, mid + 1, right, temp); ，此时如果再添加一个参数mid表示中间索引，那么右递归时，int left参数定义就没有用了
     *       综上：  mergeSort()方法是不需要再方法的定义时候，声明int mid这个变量
     *       但merge()合并函数，需要将序列按照从小到大顺序保存到temp数组中，故需要这个中间索引；
     *              若没有提前提供，则我们还要自己定义，这样才会导致方法出现太多的冗余代码；故这样子还不如直接作为方法的定义时，就将参数传递过去。
     *
     */
    public static void mergeSort(int[] arr,int left,int right,int[] temp){
        /*if(left < right){   // 只要满足left < right 就可以一直分
            int mid = (left + right) / 2 ;  // 获取中间索引
            // 向左递归进行分解
            mergeSort(arr, left, mid, temp);
            // 向右递归进行分解
            mergeSort(arr, mid + 1, right, temp);
            // 到合并
            merge(arr, left, mid, right, temp);
        }*/
        // 改进一下，虽然上面如果不满足if语句就相当于此方法执行结束，返回，但我们如果存在return语句会更加清晰
        if(left >= right){  // 相当于已经分解为 1个元素为 1组，此时就达到递归出口，
                            // 因为1个元素为1组的元素本身各自就是有序的，故不用拷贝到原始数组对其进行修改
            return ;
        }
        // 下面是不满足left >= right，此时就可以一直分解
        // mid   中间索引，不包含右边有序序列的索引，故中间索引其实是左边序列的最后一个元素的下标，故右边序列的初始索引为mid+1
        // 折半递归
        int mid = (left + right) / 2 ;  // 获取中间索引

        // 注意：递归不要 被绕晕了，主要是回溯的思想比较复杂，你可以宏观看一下，是先向左递归，然后再向右递归 ，然后再合并；
        // 而其内部的调用很复杂，但始终每一个递归栈中的这三个递归方法，都是在为每一组合并的进行按序服务；
        // 你可以看看我在快排QuickSort2 里面对具体递归中方法的如何调用分析了一个层面；不要陷得太深，具体的思路理解就好。

        // mid   中间索引，不包含右边有序序列的索引，故中间索引其实是左边序列的最后一个元素的下标，故右边序列的初始索引为mid+1
        mergeSort(arr, left, mid, temp);    // 向左递归进行分解

        // mid   中间索引，不包含右边有序序列的索引，故中间索引其实是左边序列的最后一个元素的下标，故右边序列的初始索引为mid+1
        mergeSort(arr, mid + 1, right, temp);           // 向右递归进行分解

        // 合并，为什么要这样写？？？
        // 因为：假设某一次的序列 进行左递归分解成左序列，右递归分解成右边序列，那么我们要怎么将这个两个子序列进行合并，就需要用merge()方法
        // 此时的边界范围是【left，right】，还需要一个中间索引，用于对左序列和右序列进行按从小到大排序，放到temp数组，然后再拷贝到原始数组arr
        merge(arr, left, mid, right, temp);
    }

    // 合并的方法

    /**
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param mid   中间索引，不包含右边有序序列的索引，故右边序列的初始索引为mid+1
     * @param right 右边末尾元素的索引
     * @param temp  做中转的数组，最后修改后的元素还要再次拷贝回原数组arr
     *
     */
    // 合并arr数组中下标为left到middle，和下标为middle到right两个部分 到一个中转数组
    // 注意：左边有序序列和右边有序序列都是在一个序列中，只是分成两部分
    // 如果有10【偶数】个元素，那么mid=（0+9）/2=4，下标索引分为【0，mid】和【mid+1，right】=>【0，4】，【5,9】
    // 如果有11【奇数】个元素，那么mid=（0+10）/2=5，下标索引分为【0，mid】和【mid+1，right】=>【0，5】，【6,10】
    // 按照中间索引mid对于奇数和偶数的序列分组情况，只有【0，mid】和【mid+1，right】这样分才会大体上均匀
    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        //System.out.println("^_^");
        int i = left ;  // 初始化i，左边有序序列的初始索引
        int j = mid + 1 ;   // 初始化j，右边有序序列的初始索引 ，这里为什么mid要加1
        int t = 0 ; // t是指向temp数组的当前索引

        //（一）
        // 先把左右两边（有序）的数据按照规则填充到temp数组
        // 【注意：依次填充到temp数组不是说要将arr数组的元素，下标和temp数组的元素，下标一一对应，而是说temp数组只是用来临时保存要合并的数据【从小到大排序】，
        //        然后根据left，right范围依次拷贝到原数组，并么有别的什么用途；故每一趟合并的时候都需要将temp数组的指示器，即指针t置为0】

        // 直到左右两边的有序序列，有一边处理完毕为止,然后将另一边按照顺序再依次填充
        // 最后将temp数组拷贝到原始数组arr
        while(i <= mid && j <= right){  // 继续,mid：中间索引，不包含右边有序序列的索引
            // 如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            // 即将左边的当前元素，填充到temp数组
            // 然后t++，i++
            if(arr[i] < arr[j]){
                temp[t] = arr[i] ;
                t += 1 ;
                i += 1 ;
            }else{  // 反之，如果左边的有序序列的当前元素，大于等于右边有序序列的当前元素
                // 将右边有序序列的当前元素，填充到temp数组
                temp[t] = arr[j] ;
                t += 1 ;
                j += 1 ;

            }
        }

        //（二）
        // 把有剩余数据的一边的数据依次全部填充到temp
        while(i <= mid){    // 左边的有序序列还有剩余的元素，就全部填充到temp
            temp[t] = arr[i] ;
            t += 1 ;
            i += 1 ;
        }

        while(j <= right){    // 右边的有序序列还有剩余的元素，就全部填充到temp
            temp[t] = arr[j] ;
            t += 1 ;
            j += 1 ;
        }

        // （三）
        // 将temp数组的元素拷贝到arr
        // 注意：并不是每次都拷贝所有的元素，对于不同的分组，存在不同的拷贝方法，每一次的拷贝方法只是针对当前合并的分组而言，

        // 每次合并都是合并完才执行下一次合并函数，所以上一次合并的数组下次再次使用可能会出现值的覆盖现象，但我们是通过指针【指示器】t来获取，而指针t在每次使用都会重置为0，
        // 即当每一次临时数组合并完之后并不会释放temp数组的释放空间，只是在下一次合并的时候将指针t重新置为0，可能会出现值的覆盖，但这对于合并无影响，
        // 因为边界left和right确定了，使得当前merge()函数的指针t的范围就固定在left和right范围之间了

        t = 0 ; // 将指示器t重新指向索引值为0的位置
        int tempLeft = left ;   // tempLeft代表临时left指针，目的是将临时数组中的数据拷贝到原始数组
        // 第一次合并 tempLeft = 0 ，right = 1    第二次合并 tempLeft = 2 ，right = 3；第三次合并 tempLeft=0，right=3
        // 最后一次 tempLeft = 0 ，right = 7
        // 这里合并的下标范围是【tempLeft，right】，每一次合并的都不同，最后一次就是将tempLeft=0和right=7合并到原始数组中，即【0,7】拷贝到原始数组
        // 切记：每一次合并都是符合分+合的结合，故我们可以看出，每一次的拷贝方法只是针对当前合并的分组而言，
        //System.out.println("tempLeft=" + tempLeft + "right=" + right);

        // 这里是将temp数组中的数据合并到当前的left，right下的组中
        while(tempLeft <= right){   // 这里的right指针其实可以不用动，因为这里是从左向右进行遍历的，这里的right只是作为一个边界指针使用
            arr[tempLeft] = temp[t] ;
            t += 1 ;
            tempLeft += 1 ;
        }


    }
}
