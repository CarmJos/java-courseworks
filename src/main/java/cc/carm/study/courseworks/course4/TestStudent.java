package cc.carm.study.courseworks.course4;

public class TestStudent {

    public static void changeCredit(Course c, int newCredit) {
        c.setCredit(newCredit);
    }

    public static void main(String[] args) {
        // 创建2门课程
        Course javaCourse = new Course("Java", 4);
        Course dataStructureCourse = new Course("数据结构", 3);

        // 创建学生1：学号"2401"，姓名"张三"，初始选Java课
        Student student1 = new Student("2401", "张三", new Course[]{javaCourse});

        // 创建学生2：无参创建，再用setter设置信息
        Student student2 = new Student();
        student2.setStuId("2402");
        student2.setName("李四");
        student2.addCourse(dataStructureCourse);

        // 调用两个学生的showStudent()方法打印信息
        System.out.println("=== 学生信息 ===");
        student1.showStudent();
        student2.showStudent();

        // 打印学生总数
        System.out.println("学生总数：" + Student.total);
        System.out.println();

        // 调用changeCredit方法将Java课学分改为5
        changeCredit(javaCourse, 5);

        // 再次打印学生1的信息观察变化
        System.out.println("=== 修改Java课学分后，学生1的信息 ===");
        student1.showStudent();
    }
}
