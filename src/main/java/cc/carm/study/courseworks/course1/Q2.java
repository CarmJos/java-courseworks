package cc.carm.study.courseworks.course1;

import java.util.Scanner;

public class Q2 {

    /*
    使用Scanner获取用户输入的商品信息：
    ◦ 商品名称（String类型）
    ◦ 商品单价（double类型，如39.9）
    ◦ 购买数量（int类型）
    计算商品总价（单价×数量），通过printf格式化输出
      购买商品：[商品名称]，单价：[单价]元，数量：[数量]件，
      总价：[总价]元"，要求单价和总价均保留2位小数。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 获取商品名称
        System.out.print("请输入商品名称：");
        String productName = scanner.nextLine();

        // 获取商品单价
        System.out.print("请输入商品单价：");
        double unitPrice = scanner.nextDouble();

        // 获取购买数量
        System.out.print("请输入购买数量：");
        int quantity = scanner.nextInt();

        // 计算总价
        double totalPrice = unitPrice * quantity;

        // 格式化输出
        System.out.printf(
                "购买商品：%s，单价：%.2f元，数量：%d件，总价：%.2f元%n",
                productName, unitPrice, quantity, totalPrice
        );

        scanner.close();
    }

}
