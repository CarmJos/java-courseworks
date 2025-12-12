package cc.carm.study.practicum.student;

import cc.carm.study.practicum.student.data.User;
import cc.carm.study.practicum.student.database.DataManager;
import cc.carm.study.practicum.student.manager.StudentManager;
import cc.carm.study.practicum.student.manager.UserManager;
import cc.carm.study.practicum.student.view.LoginView;
import cc.carm.study.practicum.student.view.MainView;
import cc.carm.study.practicum.student.view.RegisterDialog;
import com.formdev.flatlaf.FlatLightLaf;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Main {

    public static DataManager dataManager;
    public static StudentManager studentManager;
    public static UserManager userManager;

    static @Nullable User loggedUser;

    public static void main(String[] args) {
        System.out.println("学生信息管理系统启动中...");

        System.out.println("正在链接数据库...");
        dataManager = new DataManager();
        if (!dataManager.initialize()) {
            System.out.println("数据库链接失败，系统无法启动。");
            return;
        }

        studentManager = new StudentManager();
        userManager = new UserManager();

        // 欢迎消息
        System.out.println("欢迎使用学生信息管理系统！");
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.display();

            loginView.addLoginListener(e -> {
                String username = loginView.getUsername();
                String password = loginView.getPassword();
                String verifyCodeInput = loginView.getVerifyCodeInput();
                String verifyCode = loginView.getVerifyCode();

                if (username.isEmpty() || password.isEmpty() || verifyCodeInput.isEmpty()) {
                    JOptionPane.showMessageDialog(loginView, "用户名、密码和验证码不能为空！", "登录失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!verifyCodeInput.equalsIgnoreCase(verifyCode)) {
                    JOptionPane.showMessageDialog(loginView, "验证码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
                    loginView.refreshVerifyCode();
                    return;
                }

                User user = userManager.get(username);
                if (user == null || !user.validatePassword(password)) {
                    JOptionPane.showMessageDialog(loginView, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
                    loginView.refreshVerifyCode();
                } else {
                    loggedUser = user;
                    loginView.dispose();
                    MainView mainView = new MainView();
                    mainView.display();
                }
            });

            loginView.addRegisterListener(e -> {
                RegisterDialog registerDialog = new RegisterDialog(loginView);
                registerDialog.setVisible(true);
                if (registerDialog.isRegistered()) {
                    JOptionPane.showMessageDialog(loginView, "注册成功！请使用新账户登录。", "成功", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        });
    }

}
