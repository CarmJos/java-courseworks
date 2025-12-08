package cc.carm.study.courseworks.course2;

import java.util.Scanner;

public class Q1 {

    // 编写一个简单的计算器程序：
    // 从键盘输入两个数字和一个运算符（+、-、*、/）
    // 使用switch语句根据运算符执行相应的计算处理除零异常情况
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 输入第一个数字
            System.out.print("请输入第一个数字: ");
            double num1 = readValidNumber(scanner);

            // 输入运算符
            System.out.print("请输入运算符 (+, -, *, /): ");
            char operator = readValidOperator(scanner);

            // 输入第二个数字
            System.out.print("请输入第二个数字: ");
            double num2 = readValidNumber(scanner);

            // 执行计算
            double result = calculate(num1, num2, operator);

            // 输出结果
            System.out.printf("计算结果: %.2f %c %.2f = %.2f%n",
                    num1, operator, num2, result);

        } catch (ArithmeticException e) {
            System.err.println("算术错误: " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("输入错误: " + e.getLocalizedMessage());
        } catch (Exception e) {
            System.err.println("发生未知错误: " + e.getLocalizedMessage());
        }
    }

    /**
     * 获取有效的数字输入
     *
     * @param scanner 输入扫描器
     * @return 有效的数字
     */
    private static double readValidNumber(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("输入无效，请输入一个有效的数字: ");
            scanner.next(); // 清除无效输入
        }
        return scanner.nextDouble();
    }

    /**
     * 获取有效的运算符输入
     *
     * @param scanner 输入扫描器
     * @return 有效的运算符
     */
    private static char readValidOperator(Scanner scanner) {
        String input;
        char operator;

        do {
            input = scanner.next().trim();
            if (input.length() != 1) {
                System.out.print("请输入单个运算符 (+, -, *, /): ");
                continue;
            }

            operator = input.charAt(0);
            if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
                System.out.print("无效的运算符，请输入 (+, -, *, /): ");
                continue;
            }

            break;
        } while (true);

        return operator;
    }

    /**
     * 使用switch语句执行计算
     *
     * @param num1     第一个数字
     * @param num2     第二个数字
     * @param operator 运算符
     * @return 计算结果
     * @throws ArithmeticException      当除零时抛出异常
     * @throws IllegalArgumentException 当运算符无效时抛出异常
     */
    private static double calculate(double num1, double num2, char operator)
            throws ArithmeticException, IllegalArgumentException {

        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ArithmeticException("除数不能为零");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("不支持的运算符: " + operator);
        };
    }

}
