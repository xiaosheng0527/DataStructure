package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author shengxiao
 * @date 2021/7/19 10:21
 */
public class SelectSort {

    public static void main(String[] args) {
        //int[] arr = {101,34,119,1} ;

        // 创建80000个的随机的数组
        int[] arr = new int[80000] ;
        for(int i = 0 ; i < 80000 ; i++){
            arr[i] = (int) (Math.random() * 8000000);  // 生成一个[0,8000000)的数，为什么要取怎么大，为了避免取到相同的值
        }

//        System.out.println("排序前");
//        System.out.println(Arrays.toString(arr));
//        selectSort(arr);

//        Date date1 = new Date() ;   // 实体类
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        long before = System.currentTimeMillis() ;
        //System.out.println("排序前的时间是=" + System.currentTimeMillis()) ;

          selectSort(arr);

//        Date date2 = new Date() ;   // 实体类
//        String date2Str = sdf.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);  // 选择排序用时4s

        long after = System.currentTimeMillis() ;
        //System.out.println("排序后的时间是=" + System.currentTimeMillis()) ;
        System.out.println("执行总时间" + (after - before) / 1000.0 + "s");    // 执行总时间3.774s


//        System.out.println("排序后");
//        System.out.println(Arrays.toString(arr));
    }

    // 选择排序
    // 特点：每一次排序都会选出一个最小值，然后和第一个元素交换
    // 选择排序事件复杂度是O(n^2),因为这里是嵌套for循环
    public static void selectSort(int[] arr) {

        // 在推导的过程中，我们发现了规律，因此，可以使用for循环来解决
        // 选择排序事件复杂度是O(n^2),因为这里是嵌套for循环
        for(int i = 0 ; i < arr.length - 1 ; i++){  // 此处的i确定要进行几趟排序，故只需排列array.length-1次，因为前面array.length-1趟都排好了，剩下最后一个就不用排了
            int minIndex = i ;  // 假定索引值为i，是最小值的索引，从i=0开始
            int min = arr[i] ;  // 假定索引值为i，对应的元素是最小值，从arr[0]开始
            // 此处的j确定要进行几次比较，j的初始值为i+1，即要和第i趟的第i个元素比较，因为前面i-1趟已经排好前面i-1个元素的顺序了；说明arr[j]要和第i趟初始化为min的arr[i]比较。
            // 因为选择排序每一趟是选择最小的元素放在【当前要进行比较的序列；并不是arr数组，而是排除了已经排好的元素】的第一个位置
            // 其实j的（索引下标）范围就是[i+1 ,arr.length - 1]
            for(int j = i + 1 ; j < arr.length ; j++){
                if(min >  arr[j]){  // 说明假定的最小值，并不是最小
                    min = arr[j];   // 重置min
                    minIndex = j ;  // 重置minIndex
                }
            }
            // 将最小值，放在arr[0],即交换
            // 注意：最好不要直接用min，利用minIndex这个变量;虽然此时的min==arr[minIndex],
            //但用后面的方法，无需再创建一个变量temp,其实没有什么差别；都可以进行理解
            /*int temp = 0 ;
            temp = min ;
            min = arr[i];
            arr[i] = temp ;*/
            // 切记：这里交换的顺序千万不能颠倒，否则会出现值的覆盖，造成未交换成功
            // 即要遵循：如果没有引入额外的临时变量temp，则要先将没有副本的元素先进行交换，这样才可以交换成功；这里没有副本的元素是arr[0]
            // 优化：如果minIndex为0，则说明最小值就是我们定义的数字，就不用交换
            // 将最小值，放在arr[0],即交换
            if(minIndex != i){
                arr[minIndex] = arr[i] ;
                arr[i] = min ;  // 这里不要写成arr[0] = arr[minIndex]; 因为上面已经赋值为arr[0],故这里需要用前面的变量min来赋值
            }
            //System.out.println("第"+ (i+1) + "轮后~~");
            //System.out.println(Arrays.toString(arr));   // 1,34,119,101
        }



        // 使用逐步推导的方式来，讲解选择排序
        // 第1轮
        // 原始的数组：   101,34，119,1
        // 第一轮排序：   1,34,119,101
        // 算法 先简单--> 再复杂，就是可以把一个复杂的算法，拆分成简单的问题->逐步解决
/*
        // 第一趟
        int minIndex = 0 ;  // 假定索引值为0是最小值的索引
        int min = arr[0] ;  // 假定索引值为0对应的元素是最小值
        for(int j = 0 + 1 ; j < arr.length ; j++){
            if(min >  arr[j]){  // 说明假定的最小值，并不是最小
                min = arr[j];   // 重置min
                minIndex = j ;  // 重置minIndex
            }
        }
        // 将最小值，放在arr[0],即交换
        *//* 注意：最好不要直接用min，利用minIndex这个变量;虽然此时的min==arr[minIndex],
        但用后面的方法，无需再创建一个变量temp,其实没有什么差别；都可以进行理解
        int temp = 0 ;
        temp = min ;
        min = arr[0];
        arr[0] = temp ;*//*
        // 切记：这里交换的顺序千万不能颠倒，否则会出现值的覆盖，造成未交换成功
        // 即要遵循：如果没有引入额外的临时变量temp，则要先将没有副本的元素先进行交换，这样才可以交换成功；这里没有副本的元素是arr[0]
        // 优化：如果minIndex为0，则说明最小值就是我们定义的数字，就不用交换
        if(minIndex != 0){
            arr[minIndex] = arr[0] ;
            arr[0] = min ;  // 这里不要写成arr[0] = arr[minIndex]; 因为上面已经赋值为arr[0],故这里需要用前面的变量min来赋值
        }

        System.out.println("第1轮后~~");
        System.out.println(Arrays.toString(arr));   // 1,34,119,101

        // 第二趟
        minIndex = 1 ;  // 假定索引值为0是最小值的索引
        min = arr[1] ;  // 假定索引值为0对应的元素是最小值
        for(int j = 1 + 1 ; j < arr.length ; j++){
            if(min >  arr[j]){  // 说明假定的最小值，并不是最小
                min = arr[j];   // 重置min
                minIndex = j ;  // 重置minIndex
            }
        }
        // 优化：如果minIndex为1，则说明最小值就是我们定义的数字，就不用交换
        if(minIndex != 1){
            arr[minIndex] = arr[1] ;
            arr[1] = min ;  // 这里不要写成arr[0] = arr[minIndex]; 因为上面已经赋值为arr[0],故这里需要用前面的变量min来赋值
        }

        System.out.println("第2轮后~~");
        System.out.println(Arrays.toString(arr));   // 1,34,119,101

        // 第三趟
        minIndex = 2 ;  // 假定索引值为0是最小值的索引
        min = arr[2] ;  // 假定索引值为0对应的元素是最小值
        for(int j = 2 + 1 ; j < arr.length ; j++){
            if(min >  arr[j]){  // 说明假定的最小值，并不是最小
                min = arr[j];   // 重置min
                minIndex = j ;  // 重置minIndex
            }
        }
        // 优化：如果minIndex为1，则说明最小值就是我们定义的数字，就不用交换
        if(minIndex != 2){
            arr[minIndex] = arr[2] ;
            arr[2] = min ;  // 这里不要写成arr[0] = arr[minIndex]; 因为上面已经赋值为arr[0],故这里需要用前面的变量min来赋值
        }

        System.out.println("第3轮后~~");
        System.out.println(Arrays.toString(arr));   // 1,34,101,119
    */
    }
}
