package cc.carm.study.practicum.student.view;

import cc.carm.study.practicum.student.Main;
import cc.carm.study.practicum.student.data.User;
import cc.carm.study.practicum.student.utils.BCrypt;
import cc.carm.study.practicum.student.utils.Validators;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private JTextField usernameField, cidField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private boolean registered = false;

    public RegisterDialog(Frame owner) {
        super(owner, "注册新用户", true);
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 15, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("用户名称:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("登陆密码:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("确认密码:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        formPanel.add(new JLabel("身份证号:"));
        cidField = new JTextField();
        formPanel.add(cidField);

        formPanel.add(new JLabel("手机号码:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("注册");
        okButton.addActionListener(e -> onRegister());
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String cid = cidField.getText();
        String phone = phoneField.getText();

        if (username.isEmpty() || password.isEmpty() || cid.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validators.validateUsername(username)) {
            JOptionPane.showMessageDialog(this, "用户名格式不正确（仅支持3-16位的字母、数字、下划线）", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Main.userController.get(username) != null) {
            JOptionPane.showMessageDialog(this, "用户名已存在！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validators.validateID(cid)) {
            JOptionPane.showMessageDialog(this, "身份证号格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validators.validatePhone(phone)) {
            JOptionPane.showMessageDialog(this, "手机号格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, BCrypt.hashPassword(password), cid, phone);
        if (Main.userController.register(newUser)) {
            registered = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "注册失败，请稍后重试。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isRegistered() {
        return registered;
    }
}

