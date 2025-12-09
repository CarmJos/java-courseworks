package cc.carm.study.practicum.student;

import cc.carm.study.practicum.student.data.Student;
import cc.carm.study.practicum.student.data.User;
import cc.carm.study.practicum.student.database.DataManager;
import cc.carm.study.practicum.student.manager.StudentManager;
import cc.carm.study.practicum.student.manager.UserManager;
import cc.carm.study.practicum.student.utils.BCrypt;
import cc.carm.study.practicum.student.utils.Validators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class Main {

    static DataManager dataManager;
    static StudentManager studentManager;
    static UserManager userManager;

    static boolean running = true;
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

        // 循环检测命令行输入
        Scanner scanner = new Scanner(System.in);
        while (running) {
            if (loggedUser == null) {
                System.out.println("请注册或登录以继续：（0=退出, 1=登陆, 2=注册）。");
            } else {
                System.out.println("请输入命令：（0=登出, 1=列出学生, 2=查询学生, 3=添加学生, 4=删除学生, 5=修改学生）。");
            }
            System.out.print("> ");

            String command = scanner.nextLine().trim();

            if (loggedUser == null) {
                handleEntry(command);
            } else {
                handleLoggedIn(command);
            }

        }

        dataManager.shutdown();
        System.out.println("系统已关闭，感谢使用！");

    }

    /**
     * 处理未登录状态下的命令
     *
     * @param input 用户输入
     */
    static void handleEntry(String input) {
        if (input.equals("0")) {
            running = false;
            return;
        }
        switch (input.toLowerCase()) {
            case "1" -> handleLogging();
            case "2" -> handleRegistering();
            default -> System.out.println("未知的命令，请重新输入。（0=退出，1=登陆，2=注册）");
        }
    }

    /**
     * 处理登录操作
     */
    static void handleLogging() {

        System.out.println("您正在进行登录操作。");
        User user = reading("用户名: ", input -> {
            if (input.isEmpty()) {
                System.out.println("用户名不能为空，请重新输入。");
                return null;
            }

            User found = userManager.get(input);
            if (found == null) {
                System.out.println("用户不存在，请重新输入。");
                return null;
            }

            return found;
        });

        int validating = reading("密码: ", new Function<>() {
            int errorCount = 0;

            @Override
            public Integer apply(String input) {
                if (input.isEmpty()) {
                    System.out.println("密码不能为空，请重新输入。");
                    return null;
                }
                if (user.validatePassword(input)) {
                    return 1;
                }

                errorCount++;
                if (errorCount >= 3) {
                    System.out.println("密码错误次数过多，登录失败。");
                    return -1;
                } else {
                    System.out.println("密码错误，请重新输入。");
                    return null;
                }
            }
        });

        String verifyCode = UUID.randomUUID().toString().substring(0, 6);
        System.out.println("验证码: " + verifyCode);
        boolean success = reading("请输入验证码: ", input -> {
            if (input.isEmpty()) {
                System.out.println("验证码不能为空，请重新输入。");
                return null;
            }
            if (input.equalsIgnoreCase(verifyCode)) {
                return true;
            } else {
                System.out.println("验证码错误，登录失败。");
                return false;
            }
        });

        if (success && validating == 1) {
            loggedUser = user;
            System.out.println("登录成功，欢迎 " + user.username() + "！");
        } else {
            System.out.println("登录失败，返回主菜单。");
        }

    }

    /**
     * 处理注册操作
     */
    static void handleRegistering() {
        System.out.println("您正在进行注册操作。");

        String username = reading("用户名: ", input -> {
            if (input.isEmpty()) {
                System.out.println("用户名不能为空，请重新输入。");
                return null;
            }
            if (!Validators.validateUsername(input)) {
                System.out.println("用户名格式不正确（仅支持3-16位的字母、数字、下划线），请重新输入。");
                return null;
            }
            if (userManager.get(input) != null) {
                System.out.println("用户名已存在，请重新输入。");
                return null;
            }
            return input;
        });

        String password = reading("密码: ", input -> {
            if (input.isEmpty()) {
                System.out.println("密码不能为空，请重新输入。");
                return null;
            }
            return input;
        });

        reading("确认密码: ", input -> {
            if (!input.equals(password)) {
                System.out.println("两次输入的密码不一致，请重新输入。");
                return null;
            }
            return true;
        });

        String cid = reading("身份证号: ", input -> {
            if (!Validators.validateID(input)) {
                System.out.println("身份证号格式不正确，请重新输入。");
                return null;
            }
            return input;
        });

        String phone = reading("手机号: ", input -> {
            if (!Validators.validatePhone(input)) {
                System.out.println("手机号格式不正确，请重新输入。");
                return null;
            }
            return input;
        });

        User newUser = new User(username, BCrypt.hashPassword(password), cid, phone);
        userManager.register(newUser);

        System.out.println("注册成功！欢迎 " + username + "！请再次登录以继续。");
    }

    /**
     * 处理登录状态下的命令
     *
     * @param input 用户输入
     */
    static void handleLoggedIn(String input) {
        if (input.equals("0")) {
            loggedUser = null;
            System.out.println("您已成功登出。");
            return;
        }

        switch (input.toLowerCase()) {
            case "1" -> {
                System.out.println("学号 \t姓名 \t年龄 \t地址 \t");
                for (Student student : studentManager.list()) {
                    System.out.printf("%s \t%s \t%d \t%s \t%n",
                            student.id(),
                            student.name(),
                            student.age(),
                            student.address()
                    );
                }
            }
            case "2" -> {
                String studentId = reading("请输入要查询的学生学号: ", inputId -> {
                    if (inputId.isEmpty()) {
                        System.out.println("学号不能为空，请重新输入。");
                        return null;
                    }
                    return inputId;
                });

                Student student = studentManager.find(studentId);
                if (student == null) {
                    System.out.println("未找到学号为 " + studentId + " 的学生。");
                } else {
                    System.out.printf("学号: %s%n姓名: %s%n年龄: %d%n地址: %s%n",
                            student.id(),
                            student.name(),
                            student.age(),
                            student.address()
                    );
                }
            }
            case "3" -> {
                String id = reading("- 学号: ", inputId -> {
                    if (inputId.isEmpty()) {
                        System.out.println("学号不能为空，请重新输入。");
                        return null;
                    }
                    if (studentManager.find(inputId) != null) {
                        System.out.println("学号已存在，请重新输入。");
                        return null;
                    }
                    return inputId;
                });

                String name = reading("- 姓名: ", inputName -> {
                    if (inputName.isEmpty()) {
                        System.out.println("姓名不能为空，请重新输入。");
                        return null;
                    }
                    return inputName;
                });

                int age = reading("- 年龄: ", inputAge -> {
                    try {
                        int a = Integer.parseInt(inputAge);
                        if (a <= 0) {
                            System.out.println("年龄必须为正整数，请重新输入。");
                            return null;
                        }
                        return a;
                    } catch (NumberFormatException e) {
                        System.out.println("年龄格式不正确，请输入一个整数。");
                        return null;
                    }
                });

                String address = reading("- 地址: ", inputAddress -> {
                    if (inputAddress.isEmpty()) {
                        System.out.println("地址不能为空，请重新输入。");
                        return null;
                    }
                    return inputAddress;
                });

                Student newStudent = new Student(id, name, age, address);
                studentManager.add(newStudent);
                System.out.println("学生添加成功！");
            }
            case "4" -> {
                String studentId = reading("请输入要删除的学生学号: ", inputId -> {
                    if (inputId.isEmpty()) {
                        System.out.println("学号不能为空，请重新输入。");
                        return null;
                    }
                    return inputId;
                });

                boolean removed = studentManager.remove(studentId);
                if (removed) {
                    System.out.println("学号为 " + studentId + " 的学生已被删除。");
                } else {
                    System.out.println("未找到学号为 " + studentId + " 的学生，删除失败。");
                }
            }
            case "5" -> {
                String studentId = reading("请输入要修改的学生学号: ", inputId -> {
                    if (inputId.isEmpty()) {
                        System.out.println("学号不能为空，请重新输入。");
                        return null;
                    }
                    return inputId;
                });

                Student student = studentManager.find(studentId);
                if (student == null) {
                    System.out.println("未找到学号为 " + studentId + " 的学生，修改失败。");
                    return;
                }

                String name = reading("- 姓名 (" + student.name() + "): ", inputName -> {
                    if (inputName.isEmpty()) {
                        return student.name();
                    }
                    return inputName;
                });

                int age = reading("- 年龄 (" + student.age() + "): ", inputAge -> {
                    if (inputAge.isEmpty()) {
                        return student.age();
                    }
                    try {
                        int a = Integer.parseInt(inputAge);
                        if (a <= 0) {
                            System.out.println("年龄必须为正整数，请重新输入。");
                            return null;
                        }
                        return a;
                    } catch (NumberFormatException e) {
                        System.out.println("年龄格式不正确，请输入一个整数。");
                        return null;
                    }
                });

                String address = reading("- 地址 (" + student.address() + "): ", inputAddress -> {
                    if (inputAddress.isEmpty()) {
                        return student.address();
                    }
                    return inputAddress;
                });

                Student updatedStudent = new Student(student.id(), name, age, address);
                studentManager.add(updatedStudent);
                System.out.println("学生信息修改成功！");
            }

        }


    }

    /**
     * 快捷读取输入方法
     *
     * @param comment 输入提示
     * @param handler 输入处理器 （返回NULL则等待下次输入）
     * @param <T>     返回类型
     * @return 处理结果
     */
    static <T> @NotNull T reading(@NotNull String comment,
                                  @NotNull Function<String, T> handler) {
        Scanner scanner = new Scanner(System.in);
        @Nullable T result = null;
        while (result == null) {
            System.out.print(comment);
            String input = scanner.nextLine().trim();
            result = handler.apply(input);
        }
        return result;
    }

}
