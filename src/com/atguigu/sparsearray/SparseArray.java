package com.atguigu.sparsearray;

import java.awt.*;
import java.io.*;

/**
 * @author shengxiao
 * @date 2021/7/12 9:28
 */
public class SparseArray {

    public static void main(String[] args) throws Exception {
        // 创建一个原始的二维数组11 * 11
        // 0：表示没有  1：表示黑子  2：表示蓝子
        int[][] chessArray = new int[11][11] ;
        chessArray[1][2] = 1 ;
        chessArray[2][3] = 2 ;
        chessArray[4][5] = 2 ;
        System.out.println("原始数组的形式：");
        for (int[] row: chessArray){    // 二维数组先按序取出一维数组
            for (int data : row) {      // 一维数组再依次取出里面的元素
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        // 将 二维数组 转 稀疏数组 的思路
        // 1. 先遍历二维数组 得到非0数据的个数
        int sum = 0 ;
        for (int i = 0 ; i < chessArray.length ; i++){
            for (int j = 0 ; j < chessArray[i].length ; j++){
                if(chessArray[i][j] != 0){
                    sum++ ;
                }
            }
        }
        System.out.println(sum);

        // 创建一个稀疏数组
        int[][] sparseArray = new int[sum + 1][3] ;
        // 给稀疏数组的第一行初始化赋值
        sparseArray[0][0] = 11 ;
        sparseArray[0][1] = 11 ;
        sparseArray[0][2] = sum ;

        // 遍历二维数组 将非0数据存放到稀疏数组
        int count = 0 ;     // 添加一个计数器,类似指针，用于记录时第几个非0数据
        for (int i = 0 ; i < chessArray.length ; i++){
            for (int j = 0 ; j < chessArray[i].length ; j++){
                if(chessArray[i][j] != 0){
                    count++ ;
                    sparseArray[count][0] = i ;
                    sparseArray[count][1] = j ;
                    sparseArray[count][2] = chessArray[i][j] ;
                }
            }
        }

        // 将稀疏数组保存到硬盘中，可以按行读取
        File file = new File("D:\\Java\\DataStructure\\src\\com\\atguigu\\sparsearray\\map.data") ;
        FileOutputStream fos = new FileOutputStream(file) ;

        OutputStreamWriter writer  = new OutputStreamWriter(fos,"UTF-8") ;



        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到的稀疏数组的形式如下形式：");
        for (int i = 0 ; i < sparseArray.length ; i++){
            System.out.printf("%d\t%d\t%d\t\n",sparseArray[i][0],sparseArray[i][1],sparseArray[i][2]);
            // 按行添加到map.data文件中
            if(i < sparseArray.length - 1){
                writer.append(sparseArray[i][0] + "," + sparseArray[i][1] + "," + sparseArray[i][2] + ",") ;
            }else{
                writer.append(sparseArray[i][0] + "," + sparseArray[i][1] + "," + sparseArray[i][2]) ;
            }
        }

        System.out.println("正在写入文件中...");
        writer.close();
        fos.close();

        System.out.println("打开文件中...");
        Desktop.getDesktop().open(file);  // 此方法会在本电脑页面打开指定的文件

        System.out.println("先读取map.data");

        // 创建 FileReader 对象
        FileInputStream fis = new FileInputStream(file) ;

        InputStreamReader reader = new InputStreamReader(fis,"UTF-8") ;
        StringBuffer sb = new StringBuffer() ;
        while(reader.ready()){
            sb.append((char)reader.read()) ;    // 转成char加到StringBuffer对象中
        }

        System.out.println(sb.toString());
        reader.close(); // 关闭读取流
        fis.close();    // 关闭输入流，释放系统资源

        System.out.println("将稀疏数组恢复为二维数组:");
        String[] str = sb.toString().split(",") ;
        int sparse_row = str.length / 3 ;
        int[][] getSparseArray = new int[sparse_row][3] ;
        // 给 稀疏数组赋值
        int i = 0 ;
        for(String s : str){
            // 这个方法充分用到了向下取整和取模
            getSparseArray[i/3][i % 3] = Integer.parseInt(s) ;  //java运算默认是向下取整
            i++ ;
        }

        // 将稀疏数组恢复为二维数组
        /**
         * 将稀疏数组 恢复为  原始的  二维数组
         *
         * 1.   先读取稀疏数组的第一行，根据第一行的数据，创建原始的的二维数组，
         * 2.   再读取稀疏数组的后几行的数据，并赋给 原始的 二维数组  即可。
         */
        // 1.取出稀疏数组的第一行的元素得到原始二维数组的行数，列数，数据个数
        int row = getSparseArray[0][0] ;
        int col = getSparseArray[0][1] ;
        int num = getSparseArray[0][2] ;
        int[][] chessArray2 = new int[row][col] ;
        // 2. 在读取稀疏数组的第一行（从第二行开始），并赋给原始的二维数组 即可
        for(int m = 1 ; m < getSparseArray.length ; m++){
            int get_sparse_row = getSparseArray[m][0] ;
            int get_sparse_col = getSparseArray[m][1] ;
            int get_sparse_val = getSparseArray[m][2] ;
            chessArray2[get_sparse_row][get_sparse_col] = get_sparse_val ;

        }
        System.out.println();
        System.out.println("恢复为原始数组：");
        // 遍历恢复到原始数组的形式
        for (int[] val : chessArray2) {
            for (int data : val) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }



    }
}
