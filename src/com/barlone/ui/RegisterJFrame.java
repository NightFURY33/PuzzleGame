package com.barlone.ui;


import cn.hutool.core.io.FileUtil;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegisterJFrame extends JFrame implements ActionListener {
    // 定义组件
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField rePasswordField = new JPasswordField();
    JButton submitButton = new JButton("Submit");
    JButton backButton = new JButton("Back");

    public RegisterJFrame() {
        initJFrame();
        initView();
        this.setVisible(true);
    }

    private void initJFrame() {
        this.setSize(488, 500);
        this.setTitle("Register New Account");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
    }

    private void initView() {
        // 1. Username
        JLabel usernameTag = new JLabel("Username:");
        usernameTag.setBounds(50, 100, 150, 30);
        this.getContentPane().add(usernameTag);

        usernameField.setBounds(200, 100, 200, 30);
        this.getContentPane().add(usernameField);

        // 2. Password
        JLabel passwordTag = new JLabel("Password:");
        passwordTag.setBounds(50, 150, 150, 30);
        this.getContentPane().add(passwordTag);

        passwordField.setBounds(200, 150, 200, 30);
        this.getContentPane().add(passwordField);

        // 3. Re-enter Password
        JLabel rePasswordTag = new JLabel("Re-enter Password:");
        rePasswordTag.setBounds(50, 200, 150, 30);
        this.getContentPane().add(rePasswordTag);

        rePasswordField.setBounds(200, 200, 200, 30);
        this.getContentPane().add(rePasswordField);

        // 4. Buttons
        submitButton.setBounds(120, 300, 100, 40);
        submitButton.addActionListener(this);
        this.getContentPane().add(submitButton);

        backButton.setBounds(250, 300, 100, 40);
        backButton.addActionListener(this);
        this.getContentPane().add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            handleRegister();
        } else if (e.getSource() == backButton) {
            this.dispose();
            new LoginJFrame(); // 返回登录界面
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String rePassword = new String(rePasswordField.getPassword());

        // SQE 质量校验逻辑 (Robustness & Functional Correctness)
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty!");
            return;
        }

        if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        if (isUsernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists! Please choose another one.");
            return;
        }

        // 写入文件的逻辑：遵循之前 user_info.txt 的格式
        String userInfo = "username=" + username + "&password=" + password;

        try {
            // 使用 Hutool 追写文件 (Append mode)
            FileUtil.appendUtf8String("\n" + userInfo, "user_info.txt");
            JOptionPane.showMessageDialog(this, "Register Success! Please login.");
            this.dispose();
            new LoginJFrame();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "System Error: Failed to write data.");
            ex.printStackTrace();
        }
    }

    private boolean isUsernameExists(String username) {
        try {
            // 读取所有行
            List<String> lines = FileUtil.readUtf8Lines("user_info.txt");
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) continue;

                // 假设格式是 username=allen&password=123
                String[] parts = line.split("&");
                String existUser = parts[0].replace("username=", "");

                if (existUser.equals(username)) {
                    return true; // 发现重复
                }
            }
        } catch (Exception e) {
            // 如果文件不存在，视作没有重复
            return false;
        }
        return false;
    }
}