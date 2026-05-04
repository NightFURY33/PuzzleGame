package com.barlone.ui;
import cn.hutool.core.io.FileUtil;
import com.barlone.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.List;
import java.util.ArrayList; // 如果后续要用到实现类


public class LoginJFrame extends JFrame implements ActionListener {

    ArrayList<User> allUsers = new ArrayList<>();

    JTextField usernameField;
    JPasswordField passwordField;
    JTextField codeField;
    JLabel codeLabel;

    JButton loginButton;
    JButton resetButton;
    JButton registerButton;

    String code;

    public LoginJFrame(){
        readUserInfo();
        initializeJF();
        initializeView();
        generateCode();
        this.setVisible(true);
    }
    //读取本地文件中的用户信息
    private void readUserInfo(){
        List<String> userInfoStrict = FileUtil.readUtf8Lines("user_info.txt");
        for (String str : userInfoStrict) {
            String[] userInfoArr = str.split("&");
            String[] arr1 = userInfoArr[0].split("=");
            String[] arr2 = userInfoArr[1].split("=");
            User u = new User(arr1[1],arr2[1]);
            allUsers.add(u);
        }
    }
    private void initializeJF(){
        this.setSize(488,430);
        this.setTitle("Login v1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
    }

    private void initializeView(){

        JLabel username = new JLabel("Username:");
        username.setBounds(100,100,80,30);
        this.getContentPane().add(username);

        usernameField = new JTextField();
        usernameField.setBounds(180,100,150,30);
        this.getContentPane().add(usernameField);


        JLabel password = new JLabel("Password:");
        password.setBounds(100,150,80,30);
        this.getContentPane().add(password);

        passwordField = new JPasswordField();
        passwordField.setBounds(180,150,150,30);
        this.getContentPane().add(passwordField);


        JLabel codeText = new JLabel("Code:");
        codeText.setBounds(100,200,80,30);
        this.getContentPane().add(codeText);

        codeField = new JTextField();
        codeField.setBounds(180,200,80,30);
        this.getContentPane().add(codeField);


        codeLabel = new JLabel();
        codeLabel.setBounds(270,200,60,30);
        codeLabel.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                generateCode(); //点击刷新验证码
            }
        });
        this.getContentPane().add(codeLabel);


        loginButton = new JButton("Login");
        loginButton.setBounds(120,270,100,35);
        loginButton.addActionListener(this);
        this.getContentPane().add(loginButton);


        resetButton = new JButton("Reset");
        resetButton.setBounds(240,270,100,35);
        resetButton.addActionListener(this);
        this.getContentPane().add(resetButton);

        //  初始化注册按钮
        registerButton = new JButton("Register");
        registerButton.setBounds(120, 320, 220, 35); // 示例：横跨两个按钮的宽度
        registerButton.addActionListener(this);
        this.getContentPane().add(registerButton);
    }

    private void generateCode(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<4;i++){
            int index = r.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        code = sb.toString();
        codeLabel.setText(code);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == loginButton){

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String inputCode = codeField.getText();

            User loginUser = new User(username,password);

            if(!inputCode.equalsIgnoreCase(code)){
                JOptionPane.showMessageDialog(this,"Verification code error");
                generateCode();
                return;
            }

            if(allUsers.contains(loginUser)){
                JOptionPane.showMessageDialog(this,"Login Success");
                this.dispose();
                new GameJFrame();
            }else{
                JOptionPane.showMessageDialog(this,"Username or Password Incorrect");
            }
        }

        if(e.getSource() == resetButton){
            usernameField.setText("");
            passwordField.setText("");
            codeField.setText("");
        }

        if (e.getSource() == registerButton) {
            // 关闭当前登录界面
            this.dispose();
            // 开启注册界面
            new RegisterJFrame();
        }
    }

}