package com.barlone.ui;

import javax.swing.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        JFrame gameJframe = new JFrame();//游戏主界面
        gameJframe.setSize(603,680);
        gameJframe.setVisible(true);
        //游戏登陆界面
        System.out.println(Runtime.getRuntime().availableProcessors());
        Runtime.getRuntime().exec("shutdown ");

    }
}
