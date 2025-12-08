package cc.carm.study.courseworks.course3;

public class Q1 {
    public static class Obj {
        void f() {
            int a = 10, m = 10;
        }
    }

    public static class Test {

        public static void main(String[] args) {
            Obj obj = new Obj();

            // 访问 Obj内的 a 和 m
//            System.out.println(obj.a); // 不能访问，a是局部变量
//            System.out.println(obj.m); // 不能访问，m是局部变量
        }

    }

}


