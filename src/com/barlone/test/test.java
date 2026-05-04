package com.barlone.test;

import java.util.Random;

public class test {
    public static void main(String[] args) {
        //初始化数据
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        Random r = new Random();
        //打乱
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //初始化二维数组
        int[][] data = new int[4][4];
        //插入二维数组
        for (int i = 0; i < tempArr.length; i++) {
            data[i/4][i%4] = tempArr[i];
        }


    }
}
