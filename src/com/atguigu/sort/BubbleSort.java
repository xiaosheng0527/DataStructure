package com.atguigu.sort;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author shengxiao
 * @date 2021/7/19 8:35
 *
 * Arrays.toString()：返回指定数组内容的字符串表示形式。 字符串表示由数组元素的列表组成
 * Math.random(): 返回一个带正号的double精度值，大于或等于0.0且小于1.0
 */
public class BubbleSort {

    public static void main(String[] args) {
//        int[] arr = {3,9,-1,10,20} ;
//
//        // 为了容量理解，我们把冒泡排序的演变流程，给大家展示
//
//        System.out.println("排序前");
//        System.out.println(Arrays.toString(arr));

        // 测试一个冒泡排序的速度O(n^2)，给80000个数据，测试
        // 创建80000个的随机的数组
        int[] arr = new int[80000] ;
        for(int i = 0 ; i < 80000 ; i++){
            arr[i] = (int) (Math.random() * 80000);  // 生成一个[0,80000)的数，为什么要取怎么大，为了避免取到相同的值
        }
//        Date date1 = new Date() ;   // 实体类
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        long before = System.currentTimeMillis() ;
        //System.out.println("排序前的时间是=" + System.currentTimeMillis()) ;

        bubbleSort(arr);

//        Date date2 = new Date() ;   // 实体类
//        String date2Str = sdf.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);  // 选择排序用时4s

        long after = System.currentTimeMillis() ;
        //System.out.println("排序后的时间是=" + System.currentTimeMillis()) ;

        System.out.println("执行总时间" + (after - before) / 1000.0 + "s");    // 执行总时间14.159s

        /*System.out.println("排序后");
        System.out.println(Arrays.toString(arr));*/
    }

        /*// 第一趟排序，就是将最大的数排在最后
        for(int i = 0 ; i < arr.length - 1 ; i++){
            // 如果前面的数比后面的数要大，则交换
            if(arr[i] > arr[i +1]){
                temp = arr[i] ;
                arr[i] = arr[i + 1] ;
                arr[i + 1] = temp ;
            }
        }
        System.out.println("第一趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第二趟排序，就是将第二大的数排在倒数第二位
        for(int i = 0 ; i < arr.length - 1 -1 ; i++){
            // 如果前面的数比后面的数要大，则交换
            if(arr[i] > arr[i +1]){
                temp = arr[i] ;
                arr[i] = arr[i + 1] ;
                arr[i + 1] = temp ;
            }
        }

        System.out.println("第二趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第三趟排序，就是将第三大的数排在倒数第三位
        for(int i = 0 ; i < arr.length - 1 - 2 ; i++){
            // 如果前面的数比后面的数要大，则交换
            if(arr[i] > arr[i +1]){
                temp = arr[i] ;
                arr[i] = arr[i + 1] ;
                arr[i + 1] = temp ;
            }
        }

        System.out.println("第三趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第四趟排序，就是将第四大的数排在倒数第四位
        for(int i = 0 ; i < arr.length - 1 - 3 ; i++){
            // 如果前面的数比后面的数要大，则交换
            if(arr[i] > arr[i +1]){
                temp = arr[i] ;
                arr[i] = arr[i + 1] ;
                arr[i + 1] = temp ;
            }
        }

        System.out.println("第四趟排序后的数组");
        System.out.println(Arrays.toString(arr));
    }*/

    // 将前面的的冒泡排序方法，封装成一个方法
    public static void bubbleSort(int[] arr){
        // 冒泡排序的时间复杂度O（n^2）；因为这里是嵌套for循环
        int temp = 0 ;  // 临时变量
        boolean flag = false ;  // 标识变量，标识是否进行过交换；目的是：如果某一趟中没有进行交换，就不用再继续进行下一趟排序
        // 根据下面每一趟如何进行排序，得出一个规律，见此代码，最终版
        for(int i = 0 ; i < arr.length - 1 ; i++){  // 此处的i确定要进行几趟排序，需要排列array.length-1次，因为前面array.length-1趟都排好了，剩下最后一个就不用排了
            // 此处的j确定，要进行几次交换的比较，即arr.length - 1 - i，可以拆解为(arr.length-1)-i,因为第一趟排序（i=0）时候需要交换arr.length-1次，
            // 之后每一趟排序后，都会选出一个最大的元素，冒泡出来，之后要处理的元素依次减少，减少的大小就由前面的i：第几趟排序的值确定。
            for(int j = 0 ; j < arr.length - 1 - i  ; j++){

                // 如果前面的数比后面的数要大，则交换
                if(arr[j] > arr[j +1]){
                    flag = true ;
                    temp = arr[j] ;
                    arr[j] = arr[j + 1] ;
                    arr[j + 1] = temp ;
                }
            }
            //System.out.println("第" + (i + 1) +"趟排序后的数组");
            //System.out.println(Arrays.toString(arr));

            // 打一个布尔标记，优化了代码
            if(!flag){  // 在一趟排序后，一次交换都没有发生过，说明在某一趟排序中已经完成了最终的排序，就不用再继续进行下一趟排序
                break ;
            }else{
                flag = false ;  // 重置flag，进行下次 判断
            }
        }
    }
}
