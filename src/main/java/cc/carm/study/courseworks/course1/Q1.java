package cc.carm.study.courseworks.course1;

import java.util.Scanner;

public class Q1 {

    /*
     * 使用Scanner获取用户输入的以下信息（需声明对应类型变量存储）：
     * ◦ 姓名（String类型）
     * ◦ 年龄（int类型）
     * ◦ 体重（double类型，如52.5）
     * ◦ 是否为班干部（boolean类型，输入true或false）
     * <p>
     * 再通过printf格式化输出
     * 姓名：[姓名]，年龄：[年龄]岁，体重：[体重]kg，
     * 是否为班干部：[是/否]”，要求体重保留1位小数，boolean值需转换为中文“是”或“否”。

     */

    public static void main(String[] args) {
        // 创建Scanner对象获取用户输入
        Scanner scanner = new Scanner(System.in);

        // 声明变量存储用户输入的信息
        String name;      // 姓名
        int age;          // 年龄
        double weight;    // 体重
        boolean cadre;  // 是否为班干部

        // 获取用户输入
        System.out.print("请输入姓名：");
        name = scanner.next();

        System.out.print("请输入年龄：");
        age = scanner.nextInt();

        System.out.print("请输入体重（kg）：");
        weight = scanner.nextDouble();

        System.out.print("是否为班干部（true/false）：");
        cadre = scanner.nextBoolean();

        // 使用printf格式化输出，boolean值转换为中文
        System.out.printf(
                "姓名：%s，年龄：%d岁，体重：%.1fkg，是否为班干部：%s%n",
                name, age, weight, cadre ? "是" : "否"
        );

        // 关闭Scanner
        scanner.close();
    }

}
