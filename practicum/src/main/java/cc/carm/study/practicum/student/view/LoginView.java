package cc.carm.study.practicum.student.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.UUID;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField verifyCodeField;
    private JLabel verifyCodeLabel;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        initComponents();
        refreshVerifyCode();
    }

    private void initComponents() {
        setTitle("学生管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("用户: ");
        JLabel passwordLabel = new JLabel("密码: ");
        JLabel verifyCodeInputLabel = new JLabel("验证码: ");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        verifyCodeField = new JTextField(10);

        verifyCodeLabel = new JLabel();
        verifyCodeLabel.setFont(new Font(verifyCodeLabel.getFont().getName(), Font.BOLD, verifyCodeLabel.getFont().getSize() + 2));

        loginButton = new JButton("登录");
        registerButton = new JButton("注册新用户");

        // Create layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(usernameLabel)
                        .addComponent(passwordLabel)
                        .addComponent(verifyCodeInputLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(usernameField)
                        .addComponent(passwordField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(verifyCodeField)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verifyCodeLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton)
                                .addComponent(registerButton)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(verifyCodeInputLabel)
                        .addComponent(verifyCodeField)
                        .addComponent(verifyCodeLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(registerButton))
        );

        pack();
        setLocationRelativeTo(null); // Center the frame
        getRootPane().setDefaultButton(loginButton);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getVerifyCodeInput() {
        return verifyCodeField.getText();
    }

    public String getVerifyCode() {
        return verifyCodeLabel.getText();
    }

    public void refreshVerifyCode() {
        String code = UUID.randomUUID().toString().substring(0, 4);
        verifyCodeLabel.setText(code);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
