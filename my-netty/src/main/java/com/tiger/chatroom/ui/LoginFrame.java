package com.tiger.chatroom.ui;

import com.tiger.chatroom.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Client client;

    public LoginFrame(Client client) {
        this();
        this.client = client;
    }

    public LoginFrame() {
        setTitle("欢迎登录");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 348, 192);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("账号");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(10, 25, 80, 25);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(95, 25, 160, 25);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("密码");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(10, 60, 80, 25);
        contentPane.add(lblNewLabel_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(95, 60, 160, 25);
        contentPane.add(passwordField);

        loginButton = new JButton("登录");
        loginButton.setBounds(130, 110, 90, 25);
        contentPane.add(loginButton);
        loginButton.addActionListener(e -> {
            String account = textField.getText();
            String password = new String(passwordField.getPassword());
            client.login(account, password);
        });
    }

}
