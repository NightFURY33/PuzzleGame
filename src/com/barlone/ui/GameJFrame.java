package com.barlone.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //初始化二维数组
    String[][] data = new String[4][4];

    //记录缺口位置
    int x = 0;
    int y = 0;

    String path = "animal";

    JMenuItem replayItem = new JMenuItem("ReGame");
    JMenuItem reloginItem = new JMenuItem("ReLogin");
    JMenuItem closeItem = new JMenuItem("Close");
    JMenuItem OfficialItem = new JMenuItem("Official Account");

    //JMenuItem changePhoto = new JMenuItem("Change Photo");
    JMenuItem animal = new JMenuItem("Animal");
    JMenuItem girl = new JMenuItem("Girl");
    JMenuItem scene = new JMenuItem("Scene");

    String[][] win ={
        {"00","10","20","30"},
        {"01","11","21","31"},
        {"02","12","22","32"},
        {"03","13","23","33"}
    };

    //步长记录
    int step = 0;


    public GameJFrame(){
        initializeJF();

        initializeMenu();

        //初始化数据（打乱）
        initialData();
        //初始化图片
        initialImage();
        //使界面显示
        this.setVisible(true);
    }

    private void initialData() {
        //初始化数据
        String[] tempArr = {"00","01","02","03","10","11","12","13","20","21","22","23","30","31","32","33"};
        Random r = new Random();
        //打乱
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            String temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        //插入二维数组
        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i].equals("33")){
                x=i/4;
                y=i%4;
            }
            data[i/4][i%4] = tempArr[i];


        }
    }

    private void initialImage() {
        //清空已有的图片
        this.getContentPane().removeAll();
        //创建图片imageIcon的对象
        //添加背景图片
        if (victory()) {
            //显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("resource/victory.png"));
            winJLabel.setBounds(200,100,180,116);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("Step Count: " + step);
        stepCount.setBounds(43,45,200,30);

        stepCount.setFont(new Font("Arial", Font.BOLD, 20));
        stepCount.setForeground(Color.WHITE);

        this.getContentPane().add(stepCount);

        for (int i = 0;i<4;++i) {
            for (int j = 0; j < 4; j++) {
                String num = data[i][j];
                JLabel jLabel2 = new JLabel(new ImageIcon("resource/"+path+"/"+num+".png"));
                jLabel2.setBounds(128*j+40,128*i+90,128,128);
                //给图片添加边框
                jLabel2.setBorder(new BevelBorder(1));
                this.getContentPane().add(jLabel2);
            }

        }
        JLabel background = new JLabel(new ImageIcon("resource/background.png"));
        background.setBounds(6,5,590,620);
        this.getContentPane().add(background);

        //重新加载
        this.getContentPane().repaint();
    }

    private void initializeMenu() {
        //初始化菜单
        JMenuBar jMenubar = new JMenuBar();
        //创建菜单上两个选项的对象
        JMenu functionJMenu = new JMenu("Function");
        JMenu aboutJMenu = new JMenu("About us");
        //创建选项下的条目对象

        //将条目关联到选项中
        JMenu changePhoto = new JMenu("Change Photo");

        functionJMenu.add(changePhoto);

        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);



        changePhoto.add(girl);
        changePhoto.add(animal);
        changePhoto.add(scene);

        aboutJMenu.add(OfficialItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        OfficialItem.addActionListener(this);

        girl.addActionListener(this);
        animal.addActionListener(this);
        scene.addActionListener(this);

        //将菜单的两个选项添加到菜
        jMenubar.add(functionJMenu);
        jMenubar.add(aboutJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenubar);
    }

    private void initializeJF() {
        //设置界面宽和高
        this.setSize(603,680);
        //设置界面标题
        this.setTitle("PuzzleGame v1.0");
        //使界面置顶
        this.setAlwaysOnTop(true);
        //界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认居中
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);




    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 65){
            //删除界面所有图片
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all = new JLabel(new ImageIcon("resource/"+path+"/all.png"));
            all.setBounds(40,90,512,512);

            this.getContentPane().add(all);
            //加载背景图片
            JLabel background = new JLabel(new ImageIcon("resource/background.png"));
            background.setBounds(6,5,590,620);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //游戏胜利后禁止移动
        if (victory()) {
            return;
        }

        int code = e.getKeyCode();
        if (code == 37){
            if (y==3) return;
            //交换位置
            data[x][y] = data[x][y+1];
            data[x][y+1]="33";
            y++;
            step++;
            //重新加载
            initialImage();
        }
        else if(code == 38){
            if (x==3) return;
            //交换位置
            data[x][y] = data[x+1][y];
            data[x+1][y]="33";
            x++;
            step++;
            //重新加载
            initialImage();
        }
        else if(code == 39){
            if (y==0) return;
            //交换位置
            data[x][y] = data[x][y-1];
            data[x][y-1]="33";
            y--;
            step++;
            //重新加载
            initialImage();
        }
        else if(code == 40){
            if (x==0) return;
            //交换位置
            data[x][y] = data[x-1][y];
            data[x-1][y]="33";
            x--;
            step++;
            //重新加载
            initialImage();
        } else if (code == 65) {
            initialImage();
        } else if (code == 87) {
            data = new String[][]{
                    {"00","10","20","30"},
                    {"01","11","21","31"},
                    {"02","12","22","32"},
                    {"03","13","23","33"}
            };
            initialImage();
            x=3;y=3;
        }

    }
    
    //检查胜利条件
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(!data[i][j].equals(win[i][j])){
                    return false;
                }

            }
        }return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前点击的条目对象
        Object obj = e.getSource();
        if (obj == replayItem) {
            //计步器清零
            step=0;
            //打乱图片顺序
            initialData();
            //重新加载图片
            initialImage();

        } else if (obj == reloginItem) {
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        } else if (obj == closeItem) {
            //关闭虚拟机
            System.exit(0);
        } else if (obj == OfficialItem) {
            //创建弹框对象
            JDialog jDialog = new JDialog();
            //创建容器对象
            JLabel jLabel = new JLabel(new ImageIcon("resource/author.png"));
            //设置位置
            jLabel.setBounds(0,0,200,200);
            //加入界面
            jDialog.getContentPane().add(jLabel);
            //弹框设置大小
            jDialog.setSize(344,344);
            //置顶
            jDialog.setAlwaysOnTop(true);
            //居中
            jDialog.setLocationRelativeTo(null);
            //不关闭无法操作下方界面
            jDialog.setModal((true));
            //弹窗显示
            jDialog.setVisible(true);
        } else if (obj == girl) {
            //计步器清零
            step=0;
            //切换路径
            path = "girl";
            //打乱图片顺序
            initialData();
            //重新加载图片
            initialImage();
        } else if (obj == animal) {
            //计步器清零
            step=0;
            //切换路径
            path = "animal";
            //打乱图片顺序
            initialData();
            //重新加载图片
            initialImage();
        } else if (obj == scene) {
            //计步器清零
            step=0;
            //切换路径
            path = "scene";
            //打乱图片顺序
            initialData();
            //重新加载图片
            initialImage();
        }
    }
}


