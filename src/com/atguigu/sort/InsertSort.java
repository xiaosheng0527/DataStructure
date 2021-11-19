package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author shengxiao
 * @date 2021/7/19 13:
 *
 * 插入排序法思想:
 *
 * 插入排序（Insertion Sorting）的基本思想是：
 *      把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，
 *      排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，
 *      将它插入到有序表中的适当位置，使之成为新的有序表。
 *  注意：第一个元素当做有序表的第一个位置，index=0,如果第二个元素比第一个元素要小，则第一个元素要后移，让第二个元素插在前面
 *
 *  关于变量声明在循环体内合适，还是循环体外合适的讨论？
 *  总之，将变量声明在循环体外的方式多少能节省点空间【即相当于只需要声明一次】，可是带来的变量声明周期变长，回收时间推后以及更加严重的隐性bug危险等问题很多。比较而言，有些得不偿失了。
 */
public class InsertSort {

    public static void main(String[] args) {
        //int[] arr = {101,34,119,1} ;

        // 创建80000个的随机的数组
        int[] arr = new int[80000] ;
        for(int i = 0 ; i < 80000 ; i++){
            arr[i] = (int) (Math.random() * 8000000);  // 生成一个[0,8000000)的数，为什么要取怎么大，为了避免取到相同的值
        }
//        Date date1 = new Date() ;   // 实体类
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        long before = System.currentTimeMillis() ;
        //System.out.println("排序前的时间是=" + System.currentTimeMillis()) ;

        insertSort(arr);

//        Date date2 = new Date() ;   // 实体类
//        String date2Str = sdf.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);  // 选择排序用时4s
        long after = System.currentTimeMillis() ;
        //System.out.println("排序后的时间是=" + System.currentTimeMillis()) ;
        System.out.println("执行总时间" + (after - before) / 1000.0 + "s");    // 执行总时间1.005s，
    // 注意添加下面92~94行的优化代码执行总时间变成1.134s，速度没有明显提升
    // 将insertVal和insertIndex这两个变量声明在循环体外，效率会提高一些，执行总时间0.983s
    // 总之，将变量声明在循环体外的方式多少能节省点空间【即相当于只需要声明一次】，
    // 可是带来的变量声明周期变长，回收时间推后以及更加严重的隐性bug危险等问题很多。比较而言，有些得不偿失了。

    }

    // 插入排序
    public static void insertSort(int[] arr) {

        int insertVal = 0 ;     // 要插入的元素，从当前无序表的第一个元素开始
        int insertIndex = 0 ;   // 要插入到有序列表位置的索引值（下标），一般insertIndex初始值都会指向当前有序表的最后一个元素的位置

        // 使用for循环来把代码简化【注意：这里只使用一次for循环】
        // 注意：这里为什么初始化为i=1，因为刚开始第一个元素就是在有序表中，故我们插入元素要从无序表中的第二个元素开始
        // 且此处的i确定要进行几趟排序，故只需排列array.length-1次，因为前面array.length-1趟都排好了，剩下最后一个就不用排了
        // 而这里恰好是从i=1开始，故i的(下标，索引)范围是[1,arr.length-1]；因为无序表的长度为arr.length-1,故只需经过arr.length-1趟排列
        for(int i = 1 ; i < arr.length ; i++){
            // 定义待插入的数
            insertVal = arr[i] ;    // 先保存arr[1]值的副本
            //  insertIndex刚开始始终需要指向有序表的最后一个位置
            insertIndex = i - 1 ;   // 即arr[1]的前面这个数的下标，

            // 给insertVal 找到插入的位置
            // 说明
            // 1.insertIndex >= 0 保证在给insertVal 找插入位置 不越界
            // 2.insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置,
            //   反之，insertVal >= arr[insertIndex]则说明，已找到【insertVal==arr[insertIndex]，相等，说明也不用移动】
            // 3.就需要将arr[insertIndex]后移
            // 注意：insertVal：代表要插入的变量【默认是在无序表的第一个元素】
            //      insertIndex：代表要插入到的位置的下标，insertIndex刚开始始终需要指向有序表的最后一个位置
            while(insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex + 1] = arr[insertIndex] ;
                // 要让insertIndex和前面如果还有存在的元素进行比较，如果满足，则继续移动，
                // 且如果insertIndex=-1时，会存在下标越界，此时就跳出循环，即将此元素添加到有序列表的第一个位置
                insertIndex-- ;
            }
            // 当退出while循环时候，说明插入的位置找到，insertIndex + 1；
            // 因为满足while循环时，在移动元素时候，最后存在移动指示器insertIndex的位置，即insertIndex--；如果刚好移动一次就可以跳出循环
            // 但是因为insertIndex-1，故此时不是指向插入位置，而是其前一个位置；故这里如果要插入元素，则需要insertIndex+1
            // 其实不懂可以举一个特例，边界值，
            // 第一轮添加，{101,34,119,1}-》{101,101,119,1} 此时insertIndex==-1，而我们需要添加在第一个位置，故需要insertIndex+1 -》{34,101,119,1}
            // 举例：理解不了，我们一会debug
            // 这里我们判断是否需要赋值,
            // 如果刚开始就有insertVal >= arr[insertIndex]，说明我们不用移动就找到要插入的位置，此时就直接跳过无意义的插入
            // 即insertIndex + 1 == i，刚好要插入的变量就在插入位置的后一个位置，此时不用移动，跳过赋值，即下面的if语句
            if(insertIndex + 1 != i){
                arr[insertIndex + 1] = insertVal ;
            }

//            System.out.println("第" + i + "轮插入");
//            System.out.println(Arrays.toString(arr));
        }


        // 使用逐步推导的方式来讲解，便于理解
        // 第一轮{101,34,119,1}；=》 {34,101,119,1}

        /*// 定义待插入的数
        int insertVal = arr[1] ;    // 先保存arr[1]值的副本
        //  insertIndex刚开始始终需要指向有序表的最后一个位置
        int insertIndex = 1 - 1 ;   // 即arr[1]的前面这个数的下标，

        // 给insertVal 找到插入的位置
        // 说明
        // 1.insertIndex >= 0 保证在给insertVal 找插入位置 不越界
        // 2.insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置,
        //   反之，insertVal >= arr[insertIndex]则说明，已找到【insertVal==arr[insertIndex]，相等，说明也不用移动】
        // 3.就需要将arr[insertIndex]后移
        // 注意：insertVal：代表要插入的变量【默认是在无序表的第一个元素】
        //      insertIndex：代表要插入到的位置的下标，insertIndex刚开始始终需要指向有序表的最后一个位置
        while(insertIndex >= 0 && insertVal < arr[insertIndex]){
            arr[insertIndex + 1] = arr[insertIndex] ;
            // 要让insertIndex和前面如果还有存在的元素进行比较，如果满足，则继续移动，
            // 且如果insertIndex=-1时，会存在下标越界，此时就跳出循环，即将此元素添加到有序列表的第一个位置
            insertIndex-- ;
        }
        // 当退出while循环时候，说明插入的位置找到，insertIndex + 1；
        // 因为满足while循环时，在移动元素时候，最后存在移动指示器insertIndex的位置，即insertIndex--；如果刚好移动一次就可以跳出循环
        // 但是因为insertIndex-1，故此时不是指向插入位置，而是其前一个位置；故这里如果要插入元素，则需要insertIndex+1
        // 其实不懂可以举一个特例，边界值，
        // 第一轮添加，{101,34,119,1}-》{101,101,119,1} 此时insertIndex==-1，而我们需要添加在第一个位置，故需要insertIndex+1 -》{34,101,119,1}
        // 举例：理解不了，我们一会debug
        arr[insertIndex + 1] = insertVal ;

        System.out.println("第一轮插入");
        System.out.println(Arrays.toString(arr));

        // 第2轮
        insertVal = arr[2] ;
        insertIndex = 2 - 1 ;

        while(insertIndex >= 0 && insertVal < arr[insertIndex]){
            arr[insertIndex + 1] = arr[insertIndex] ;
            // 要让insertIndex和前面如果还有存在的元素进行比较，如果满足，则继续移动，
            // 且如果insertIndex=-1时，会存在下标越界，此时就跳出循环，即将此元素添加到有序列表的第一个位置
            insertIndex-- ;
        }
        // 当退出while循环时候，说明插入的位置找到，insertIndex + 1；
        // 因为满足while循环时，在移动元素时候，最后存在移动指示器insertIndex的位置，即insertIndex--；如果刚好移动一次就可以跳出循环
        // 但是因为insertIndex-1，故此时不是指向插入位置，而是其前一个位置；故这里如果要插入元素，则需要insertIndex+1
        // 其实不懂可以举一个特例，边界值，
        // 第一轮添加，{101,34,119,1}-》{101,101,119,1} 此时insertIndex==-1，而我们需要添加在第一个位置，故需要insertIndex+1 -》{34,101,119,1}
        // 举例：理解不了，我们一会debug
        arr[insertIndex + 1] = insertVal ;

        System.out.println("第二轮插入");
        System.out.println(Arrays.toString(arr));

        // 第3轮
        insertVal = arr[3] ;
        insertIndex = 3 - 1 ;

        while(insertIndex >= 0 && insertVal < arr[insertIndex]){
            arr[insertIndex + 1] = arr[insertIndex] ;
            // 要让insertIndex和前面如果还有存在的元素进行比较，如果满足，则继续移动，
            // 且如果insertIndex=-1时，会存在下标越界，此时就跳出循环，即将此元素添加到有序列表的第一个位置
            insertIndex-- ;
        }
        // 当退出while循环时候，说明插入的位置找到，insertIndex + 1；
        // 因为满足while循环时，在移动元素时候，最后存在移动指示器insertIndex的位置，即insertIndex--；如果刚好移动一次就可以跳出循环
        // 但是因为insertIndex-1，故此时不是指向插入位置，而是其前一个位置；故这里如果要插入元素，则需要insertIndex+1
        // 其实不懂可以举一个特例，边界值，
        // 第一轮添加，{101,34,119,1}-》{101,101,119,1} 此时insertIndex==-1，而我们需要添加在第一个位置，故需要insertIndex+1 -》{34,101,119,1}
        // 举例：理解不了，我们一会debug
        arr[insertIndex + 1] = insertVal ;

        System.out.println("第二轮插入");
        System.out.println(Arrays.toString(arr));
    */
    }
}
